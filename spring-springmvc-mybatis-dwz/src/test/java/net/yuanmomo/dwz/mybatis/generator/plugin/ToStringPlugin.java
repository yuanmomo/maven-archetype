/**
 * Project Name : Tools
 * File Name    : safas.java
 * Package Name : net.yuanmomo.dwz.tools.db.orm.mybatis.generator.plugin
 * Created on   : 2014-2-27下午7:43:51
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.dwz.mybatis.generator.plugin;
/**
 * ClassName : safas 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-2-27 下午7:43:51 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ToStringPlugin extends PluginAdapter {
	public boolean validate(List<String> arg0) {
		return true;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		generateToString(introspectedTable, topLevelClass);
		return true;
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		generateToString(introspectedTable, topLevelClass);
		return true;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		generateToString(introspectedTable, topLevelClass);
		return true;
	}

	private void generateToString(IntrospectedTable introspectedTable,
			InnerClass innerClass) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getStringInstance());
		method.setName("toString");
		if (introspectedTable.isJava5Targeted()) {
			method.addAnnotation("@Override");
		}

		if (innerClass instanceof TopLevelClass) {
			context.getCommentGenerator().addGeneralMethodComment(method,
					introspectedTable);
		}

		method.addBodyLine("StringBuilder sb = new StringBuilder();");
		method.addBodyLine("sb.append(getClass().getSimpleName());");
		method.addBodyLine("sb.append(\"[\");");
		method.addBodyLine("sb.append(\"Hash = \").append(hashCode());");
		StringBuilder sb = new StringBuilder();
		for (Field field : innerClass.getFields()) {
			String property = field.getName();
			sb.setLength(0);
			sb.append("sb.append(\"").append(",").append(property)
					.append("=\")").append(".append(").append(property)
					.append(");");
			method.addBodyLine(sb.toString());
		}

		method.addBodyLine("sb.append(\"]\");");
		method.addBodyLine("return sb.toString();");

		innerClass.addMethod(method);
	}
}
