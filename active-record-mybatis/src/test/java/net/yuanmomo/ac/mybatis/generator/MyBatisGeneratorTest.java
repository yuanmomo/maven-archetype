/**
 * Project Name : Tools
 * File Name    : MyBatisGeneratorTest.java
 * Package Name : net.yuanmomo.ac.tools.db.orm.mybatis
 * Created on   : 2013-12-24下午5:13:58
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.ac.mybatis.generator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * ClassName : MyBatisGeneratorTest Function : TODO ADD FUNCTION. Reason : TODO
 * ADD REASON. Date : 2013-12-24 下午5:13:58
 * 
 * @author : Hongbin Yuan
 * @version
 * @since JDK 1.6
 * @see
 */
public class MyBatisGeneratorTest {
	private static final String generatorConfigPath = "src\\test\\resources\\mybatis\\generatorConfig.xml";
	
	/**
	 * generate: 调用MyBatis Generator 生成相应代码. <br/>
	 * 
	 * @author Hongbin Yuan
	 * @param generatorConfigPath
	 *            generatorConfigPath.xml配置文件的路径
	 * @since JDK 1.6
	 */
	public static void main(String args[]) {
		System.out.println("Start to test MyBatisGeneratorTest.testGenerator()....................");
		new File(".").getAbsoluteFile();
		
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		System.out.println("Current path is " + new File(".").getAbsolutePath());
		File configFile = new File(generatorConfigPath);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = null;
		try {
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			myBatisGenerator.generate(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finish Testing MyBatisGeneratorTest.testGenerator()....................");
	}
}
