package net.yuanmomo.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyBatisGeneratorTool {
	private static Logger logger = LoggerFactory.getLogger(MyBatisGeneratorTool.class);
	
	/**
	 * generate: 调用MyBatis Generator 生成相应代码. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param generatorConfigPath  generatorConfigPath.xml配置文件的路径
	 * @since JDK 1.7
	 */
	public static void generate(String generatorConfigPath) {
		if(generatorConfigPath == null || "".equals(generatorConfigPath.trim())){
			logger.error("The generatorConfigPath.xml location specified is null, user default locate config files.");
			return;
		}
		
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		logger.info("Current path is " + new File(".").getAbsolutePath());
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
			logger.error("SQLException",e);
		} catch (IOException e) {
			logger.error("IOException",e);
		} catch (InterruptedException e) {
			logger.error("InterruptedException",e);
		}
	}
}
