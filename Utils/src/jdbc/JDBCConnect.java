package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCConnect {

	public void jdbcConnect(){
		try {
			//调用jdbc包的初始化mysql的连接程序（静态代码）
			Class.forName("com.mysql.jdbc.Driver");
			
			//接下来都是jdk的API
			Connection conn = DriverManager.getConnection("");
			Statement stmt = conn.createStatement();
			String sql = "select * from A";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getString("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
