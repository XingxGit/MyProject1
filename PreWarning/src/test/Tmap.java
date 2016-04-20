package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Tmap {
//	public static void main(String[] args) {
//		Map<Double,String> tmap = new TreeMap<>();
//		tmap.put(2.1, "green");
//		tmap.put(3.9, "blue");
//		tmap.put(3.4, "yellow");
//		tmap.put(1.9, "red");
//		for (Double d : tmap.keySet()) {
//			System.out.println(d);
//		}
//	}
	
	public static void main(String[] args) throws Exception{
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
			String s = "d";
			sql = "delete from company_warn where company_id='"+s+"'";
			stmt = conn.prepareStatement(sql);
			 stmt.execute(sql);
//			while(rs.next()){
//				System.out.println(rs.getDouble(1));
//			}
			
	}

}
