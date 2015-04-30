package net.yuanmomo.mybatis.generator.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.yuanmomo.mybatis.generator.util.StringUtil;

import org.mybatis.generator.internal.util.JavaBeansUtil;


public class TableXMLGenerator {
	/**
	 * 当前连接
	 */
	private Connection con = null;
	/**
	 * 表集合
	 */
	private ResultSet tableListRs = null;
	/**
	 * 每张表的列字段集合
	 */
	private ResultSet columListRS = null;
	
	/**
	 *  执行Desc table
	 */
	private PreparedStatement psmt = null; 
	
	public List<String> generate(String driver,String url, String user, String password) {
		List<String> output = new ArrayList<String>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			DatabaseMetaData metadata = con.getMetaData();
			
			// 从数据库的元数据中取得所有的边表名
			tableListRs = metadata.getTables(null, null, "%", new String[]{"TABLE" });
			while (tableListRs.next()) {
				String tableName = tableListRs.getString(3);
				String javaBeanName = JavaBeansUtil.getCamelCaseString(tableName, true);
				
				output.add("\t\t<table tableName=\""+ tableName + "\" domainObjectName=\""+ javaBeanName +"\">");
				output.add("\t\t\t<property name=\"generatedBusinessName\" value=\"" +javaBeanName + "Business\"/>");
				output.add("\t\t\t<property name=\"generatedControllerName\" value=\"" +javaBeanName + "Controller\"/>");
				
				// 判断表是否含有id的主键
				psmt = con.prepareStatement("desc " + tableName);
				columListRS = psmt.executeQuery();
				while(columListRS.next()){
					String extra = columListRS.getString("Extra");
					if(extra.equalsIgnoreCase("auto_increment") ){
						String col = columListRS.getString("Field").toLowerCase();
						output.add("\t\t\t<generatedKey column=\""+ col +"\" sqlStatement=\"MySql\" identity=\"true\"/>");
					}
				}
				output.add("\t\t</table>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				psmt.close();
				columListRS.close();
				tableListRs.close();
				this.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	
	public List<String> generator(String filePath) throws Exception{
		Properties prop = new Properties();
		prop.load(new FileInputStream(new File(filePath)));
		
		String driver = prop.getProperty("driver");
		if(StringUtil.isBlank(driver)){
			throw new Exception("properties文件中的数据库驱动名称请配置为：driver");
		}
		
		String url = prop.getProperty("url");
		if(StringUtil.isBlank(url)){
			throw new Exception("properties文件中的数据库连接请配置为：url");
		}
		
		String user = prop.getProperty("user");
		if(StringUtil.isBlank(user)){
			throw new Exception("properties文件中的数据库连接用户名名称请配置为：user");
		}
		
		String password = prop.getProperty("password");
		if(StringUtil.isBlank(password)){
			throw new Exception("properties文件中的数据库驱动名称请配置为：password");
		}
		return this.generate(driver, url, user, password);
	}
}
