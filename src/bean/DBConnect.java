package bean;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class DBConnect {
    Connection conn;
    //각 DB제조사에서 재공하는 상수
    String driver="oracle.jdbc.driver.OracleDriver";//제조사 사이트에서 확인가능한 상수
    //연결하려고 하는 DB서버 주소 거의 상수처럼 사용
    String url="jdbc:oracle:thin:@localhost:1521:xe";//@서버주소:포트번호:ServiceID
    //String url="jdbc:oracle:thin:@localhost:1521:xe";//@서버주소:포트번호:ServiceID
    //정식버전에서는 sid가 orcl
    String user="hr2";
    String pwd="hr2"; 
	public DBConnect() {
		  try{
			  Class.forName(driver).newInstance();
			  
			  
			  //System.out.println("Driver loadiong ok...");
			  /*Context init = new InitialContext();
			  DataSource ds =(DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
			  conn = ds.getConnection();*/
			  //System.out.println("Connection ok.....");
			  conn = DriverManager.getConnection(url, user, pwd);
		  }catch(Exception e){
			  System.out.println("Connection fail....");
			  e.printStackTrace();
		  }
	}
	public Connection getConn() {
		return conn;
	}
}
