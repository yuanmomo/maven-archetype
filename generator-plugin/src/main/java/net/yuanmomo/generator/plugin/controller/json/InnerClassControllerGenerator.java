package net.yuanmomo.generator.plugin.controller.json;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class InnerClassControllerGenerator {
	public static InnerClass generator(FullyQualifiedJavaType controllerType,String beanName ){
		String innerClassFullName = controllerType.getFullyQualifiedName() + "." + beanName +"List";
        InnerClass innerClass = new InnerClass(new FullyQualifiedJavaType(innerClassFullName));
        innerClass.setVisibility(JavaVisibility.DEFAULT);
        innerClass.setStatic(true);
        
        // add List<Bean> 内部类
        String beanListFieldName = StringUtils.uncapitalize(beanName + "List");
        FullyQualifiedJavaType beanListType = new FullyQualifiedJavaType("java.util.List<"+ beanName +">");
        Field beanList = new Field();
        beanList.setVisibility(JavaVisibility.PRIVATE);
        beanList.setType(beanListType);
        beanList.setName(beanListFieldName);
        innerClass.addField(beanList);
        
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(beanListType);
        method.setName("get"+ beanName + "List");
        method.addBodyLine("return " + beanListFieldName + ";");
        innerClass.addMethod(method);
        
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set"+ beanName + "List");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<"+ beanName +">"), beanListFieldName)); 
        method.addBodyLine("this."+ beanListFieldName + " = "+ beanListFieldName + ";");
        innerClass.addMethod(method);
        
        return innerClass;
	}
}
