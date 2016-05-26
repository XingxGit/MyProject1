package cn.sibat.warn.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.websocket.Session;

import cn.sibat.warn.model.kpi.KPILightScore;
import cn.sibat.warn.socketServer.WebSocketTest;

public class HandleScore {
	Session session;
	public void calScore(String company_id) throws Exception {
		System.out.println("begin to calScore !");
		List<String> redList = new ArrayList<>();
		redList.add("拖欠工资3个月及以上");
		redList.add("工资未足额支付正班工资");
		redList.add("非法用工童工");
		redList.add("非法用工无证派遣工");
		redList.add("集体争议、群体诉求人数占全厂员工比重50%以上");
		redList.add("拟搬迁生产(经营)、企业收购兼并");
		redList.add("生产(经营)停顿");
		redList.add("经济性裁员20%以上");
		redList.add("法定代表人或经营者失联");
		redList.add("行政处罚停止生产(经营)整顿");
		redList.add("主管机关依法取缔");
		Connection conn = null;
		String sql;
		List<String> kpiList = new ArrayList<>();
		List<String> upList = new ArrayList<>();
		Map<String,String> upMap = new HashMap<>();
		PreparedStatement  stmt;
		Statement st;  
		Boolean redIndicate = false;
		String url = "jdbc:mysql://localhost:3306/prewarning?"
				+ "user=root&password=1234&useUnicode=true&characterEncoding=UTF8";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			sql = "select kpi_name from kpi";
			stmt = conn.prepareStatement(sql);
			st = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				kpiList.add(rs.getString(1));
			}
			sql = "select second_kpi,third_kpi from kpi_light_score,case_upload where kpi_light_score.third_kpi=case_upload.kpi_ids and case_upload.company_id='"+company_id+"'";
			rs = st.executeQuery(sql);
			while(rs.next()){
				if(redList.contains(rs.getString(2)))
					redIndicate = true;
				upList.add(rs.getString(1));
				upMap.put(rs.getString(2), "");
			}
			kpiList.removeAll(upList);
			Map<String,String> smap = upMap;
			List<KPILightScore> ksList = new ArrayList<>();
			for(String s:smap.keySet()){
				sql = "select green_score,blue_score,yellow_score,red_score,second_kpi from kpi_light_score where third_kpi='"+s+"'";
				rs = st.executeQuery(sql);
				while(rs.next()){
					KPILightScore kls = new KPILightScore();
					kls.setSecond_kpi(rs.getString(5));
					kls.setGreen_score(rs.getDouble(1));
					kls.setBlue_score(rs.getDouble(2));
					kls.setYellow_score(rs.getDouble(3));
					kls.setRed_score(rs.getDouble(4));
					ksList.add(kls);
				}
			}
			for(String s: kpiList){
				KPILightScore kls = new KPILightScore();
				kls.setSecond_kpi(s);
				kls.setGreen_score(100d);
				kls.setBlue_score(0d);
				kls.setYellow_score(0d);
				kls.setRed_score(0d);
				ksList.add(kls);
			}
			
			Double gscore = 0d;
			Double bscore = 0d;
			Double yscore = 0d;
			Double rscore = 0d;
			
			for(KPILightScore kls:ksList){
				sql = "select kpi_weight,green_weight,blue_weight,yellow_weight,red_weight from kpi where kpi_name='"+kls.getSecond_kpi()+"'";
				rs = st.executeQuery(sql);
				while(rs.next()){
					gscore += rs.getDouble(1)*rs.getDouble(2)*kls.getGreen_score();
					bscore += rs.getDouble(1)*rs.getDouble(3)*kls.getBlue_score();
					yscore += rs.getDouble(1)*rs.getDouble(4)*kls.getYellow_score();
					rscore += rs.getDouble(1)*rs.getDouble(5)*kls.getRed_score();
				}
			}
			Map<Double,String> tmap = new TreeMap<>();
			String lightGrade = "";
			tmap.put(gscore, "green");
			tmap.put(bscore, "blue");
			tmap.put(yscore, "yellow");
			tmap.put(rscore, "red");
			for (Double d : tmap.keySet()) {
				lightGrade = tmap.get(d);
			}
			
			if(redIndicate)
				lightGrade = "red";
			Date create_time = null;
			sql = "select * from company_warn where company_id='"+company_id+"'";
			rs = st.executeQuery(sql);
			while(rs.next()){
				create_time = rs.getTimestamp(3);
			}
			
			sql = "delete from company_warn where company_id='"+company_id+"'";
			st.execute(sql);
			
			sql = "update company_info set is_case = 'true' where company_id='"+company_id+"'";
			st.execute(sql);
			
			String company_address = "";
			String company_name = "";
			String street_name = "";
			String industry_name = "";
			sql = "select company_address,company_name,street_name,industry_name from company_info where company_id='"+company_id+"'";
			rs = st.executeQuery(sql);
			while(rs.next()){
				company_address = rs.getString(1);
				company_name = rs.getString(2);
				street_name = rs.getString(3);
				industry_name = rs.getString(4);
			}
			
			
			sql = "insert into company_warn(company_id,green_score,blue_score,yellow_score,red_score,light_grade,create_time,modify_time,time,company_address,company_name,street_name,industry_name) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, company_id);
			stmt.setDouble(2, gscore);
			stmt.setDouble(3, bscore);
			stmt.setDouble(4, yscore);
			stmt.setDouble(5, rscore);
			stmt.setString(6, lightGrade);
			stmt.setString(10, company_address);
			stmt.setString(11, company_name);
			stmt.setString(12, street_name);
			stmt.setString(13, industry_name);
			if(create_time!=null){
			stmt.setTimestamp(7, new java.sql.Timestamp(create_time.getTime()));
			stmt.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
			}else{
			stmt.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
			stmt.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
			}
			stmt.setString(9, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			stmt.addBatch();
			stmt.executeBatch();
			
			sql = "update light_pending set light_grade = '"+lightGrade+"'where company_id = '"+company_id+"'";
			st.execute(sql);
			st.close();
			stmt.close();
			conn.close();
			System.out.println("finish calScore!");
			WebSocketTest.onMessage("1:upload case", session);
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new HandleScore().calScore("34253829X");
	}

}
