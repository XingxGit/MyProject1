package test;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sibat.warn.util.HashUtil;

public class JDtest {
	public static void main(String[] args)throws Exception {
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
			String s = "345";
			sql = "select * from company_warn where company_id='"+s+"'";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			Date d = null;
			while(rs.next()){
				d = rs.getTimestamp(3);
				System.out.println(d);
			}
			
			sql = "insert into company_warn(company_id,create_time) values(?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, HashUtil.getRandomId());
			stmt.setTimestamp(2, new java.sql.Timestamp(d.getTime()));
			stmt.execute();
	}
}
