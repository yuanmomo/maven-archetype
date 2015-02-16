package net.yuanmomo.mybatis.generator.plugin.business;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class SelectListBusinessGenerator {
	public static List<Method> generator(FullyQualifiedJavaType beanType,String beanName,
			String mapperFieldName,FullyQualifiedJavaType paramType){
		List<Method> methodList = new ArrayList<Method>();
		
		// add get by key method
		Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType("java.util.List<"+ beanName +">"));
        method.setName("select" + beanName + "List"); //$NON-NLS-1$
        method.addParameter(new Parameter(paramType, "param")); //$NON-NLS-1$
        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
        method.addBodyLine("return this."+ mapperFieldName +".selectByExample(param);");
        
        methodList.add(method);
        // add new method here
        
        return methodList;
	}
}
