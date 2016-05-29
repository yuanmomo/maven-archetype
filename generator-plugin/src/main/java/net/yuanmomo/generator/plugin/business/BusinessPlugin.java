package net.yuanmomo.generator.plugin.business;

import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class BusinessPlugin extends PluginAdapter{
	/**
	 *  配置business的包名
	 */
	private String businessPackage;
	
	/**
	 * validate: 生成business由xml中的table标签来控制，配置了plugin，但是不一定生成. <br/>
	 *
	 * @param warnings
	 * @return
	 * @see org.mybatis.generator.api.Plugin#validate(List)
	 */
	public boolean validate(List<String> warnings) {
		businessPackage = properties.getProperty("businessPackage"); //$NON-NLS-1$
        return true;
	}
	
	
	/* (non-Javadoc)
	 * @see org.mybatis.generator.api.PluginAdapter#contextGenerateAdditionalJavaFiles(org.mybatis.generator.api.IntrospectedTable)
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		// 判断是否需要生成business文件
		String javaBusinessName = 
				introspectedTable.getTableConfiguration().getProperty("generatedBusinessName");
		// 不生成Business文件
		if(javaBusinessName == null || "".equals(javaBusinessName.trim())){
			return null;
		}
		
		return BusinessPluginUtil.getBusinessJavaFile(context, introspectedTable, 
				businessPackage, javaBusinessName);
	}
}
