package com.wangchao.commons.dbutils.oracle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Oracle插入Blob字段
 * @author Administrator
 *
 */
public class BlobInsert {
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.50.156:1521:airport", "etiri", "etiri");
		    con.setAutoCommit(false);
		    Statement st = con.createStatement();
		    //插入一个空对象empty_blob()
		    st.executeUpdate("insert into t_data1 (ID, content, file1) values (6,6, empty_blob())");
		    //锁定数据行进行更新，注意“for update”语句
		    ResultSet rs = st.executeQuery("select file1 from t_data1 where ID=6 for update");
		    if (rs.next())
		    {
		    	System.out.println(111);
		        //得到java.sql.Blob对象后强制转换为oracle.sql.BLOB
		        oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("file1");
		        OutputStream outStream = blob.getBinaryOutputStream();
		        //data是传入的byte数组，定义：byte[] data
		        File f = new File("D:\\17所环境说明.doc");
		        FileInputStream fin = new FileInputStream(f);
		        byte [] b = new byte[1024];
		        while(fin.read(b)!=-1){
		        	outStream.write(b, 0, b.length);
		        }
		   
			    outStream.flush();
			    outStream.close();
		    }
		    con.commit();
		    con.close();
	}

}
