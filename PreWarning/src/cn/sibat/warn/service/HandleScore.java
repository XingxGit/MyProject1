package cn.sibat.warn.service;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.sibat.warn.model.kpi.KPILightScore;
import cn.sibat.warn.util.HashUtil;

public class HandleScore {

	public void calScore(String company_id) throws Exception {
		Connection conn = null;
		String sql;
		List<String> kpiList = new ArrayList<>();
		List<String> upList = new ArrayList<>();
		Map<String,Double> upMap = new HashMap<>();
		PreparedStatement  stmt;
		String url = "jdbc:mysql://localhost:3306/prewarning?"
				+ "user=root&password=1234&useUnicode=true&characterEncoding=UTF8";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			sql = "select kpi_name from kpi";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				kpiList.add(rs.getString(1));
			}
			sql = "select kpi_ids,value from case_upload where company_id='"+company_id+"'";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				upList.add(rs.getString(1));
				upMap.put(rs.getString(1), Double.valueOf(rs.getString(2)));
			}
			kpiList.removeAll(upList);
			Map<String,String> smap = AchiScore.getSeIndex(upMap);
			List<KPILightScore> ksList = new ArrayList<>();
			for(String s:smap.keySet()){
				sql = "select green_score,blue_score,yellow_score,red_score from kpi_light_score where second_kpi='"+s+"'"+" and third_kpi='"+smap.get(s)+"'";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					KPILightScore kls = new KPILightScore();
					kls.setSecond_kpi(s);
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
				break;
			}
			
			Double gscore = 0d;
			Double bscore = 0d;
			Double yscore = 0d;
			Double rscore = 0d;
			
			for(KPILightScore kls:ksList){
				sql = "select kpi_weight,green_weight,blue_weight,yellow_weight,red_weight from kpi where kpi_name='"+kls.getSecond_kpi()+"'";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery(sql);
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
			Date create_time = null;
			sql = "select * from company_warn where company_id='"+company_id+"'";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				create_time = rs.getTimestamp(3);
			}
			
			sql = "delete from company_warn where company_id='"+company_id+"'";
			stmt = conn.prepareStatement(sql);
			stmt.execute(sql);
			
			
			sql = "insert into company_warn(company_id,green_score,blue_score,yellow_score,red_score,light_grade,create_time,modify_time,time) values(?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, company_id);
			stmt.setDouble(2, gscore);
			stmt.setDouble(3, bscore);
			stmt.setDouble(4, yscore);
			stmt.setDouble(5, rscore);
			stmt.setString(6, lightGrade);
			if(create_time!=null){
			stmt.setTimestamp(7, new java.sql.Timestamp(create_time.getTime()));
			}else{
			stmt.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
			stmt.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
			}
			stmt.setString(9, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			stmt.execute();
			stmt.close();
			conn.close();
	}
	
	public static void main(String[] args) throws Exception {
		new HandleScore().calScore("tesy8");
	}

}
