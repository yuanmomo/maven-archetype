package net.yuanmomo.generator.plugin.controller.jsp;

import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class ControllerPlugin  extends PluginAdapter{
	/**
	 *  配置business的包名
	 */
	private String businessPackage;
	/**
	 *  配置controller的包名
	 */
	private String controllerPackage;
	/**
	 *  配置自定义exception
	 */
	private String exceptionClass;
	
	@Override
	public boolean validate(List<String> warnings) {
		businessPackage = properties.getProperty("businessPackage"); 
		controllerPackage = properties.getProperty("controllerPackage"); //$NON-NLS-1$
		exceptionClass = properties.getProperty("exceptionClass"); 
        return true;
	}
	
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		// 判断是否生成Controller文件
        String javaControllerName = 
				introspectedTable.getTableConfiguration().getProperty("generatedControllerName");
		if(javaControllerName == null || "".equals(javaControllerName.trim())){
			return null;
		}
		// Business 文件名
		String javaBusinessName = 
				introspectedTable.getTableConfiguration().getProperty("generatedBusinessName");
		return ControllerPluginUtil.getControllerJavaFile(context, introspectedTable, 
				controllerPackage, javaControllerName, businessPackage, javaBusinessName, exceptionClass);
	}
}
