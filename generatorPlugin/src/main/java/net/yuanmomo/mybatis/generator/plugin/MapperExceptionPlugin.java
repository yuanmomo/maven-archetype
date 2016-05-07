
/**
 * Project Name : Tools
 * File Name    : MapperException.java
 * Package Name : net.yuanmomo.tools.db.orm.mybatis.generator.plugin
 * Created on   : 2014-2-18下午3:51:44
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.mybatis.generator.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * ClassName : MapperException 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-2-18 下午3:51:44 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
public class MapperExceptionPlugin extends PluginAdapter {
	
	private void addException(Method method,IntrospectedTable introspectedTable){
		String exceptionClassStr = this.getProperties().getProperty("exceptionClass");
		try {
			method.addException(new FullyQualifiedJavaType(exceptionClassStr));
		} catch (Exception e) {
			method.addException(new FullyQualifiedJavaType(RuntimeException.class.getName()));
		}
	};

	/**
	 * validate:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param warnings
	 * @return
	 * @see org.mybatis.generator.api.Plugin#validate(java.util.List)
	 */
	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * clientGenerated:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param interfaze
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 * @see org.mybatis.generator.api.PluginAdapter#clientGenerated(org.mybatis.generator.api.dom.java.Interface, org.mybatis.generator.api.dom.java.TopLevelClass, org.mybatis.generator.api.IntrospectedTable)
	 */
	@Override
	public boolean clientGenerated(Interface interfaze,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		List<Method> mothodList = interfaze.getMethods();
		if(mothodList !=null && mothodList.size()>0){
			for(Method m : mothodList){
				// 给没一个接口方法添加异常抛出
				addException(m,introspectedTable);
			}
		}
		return true;
	}
}
