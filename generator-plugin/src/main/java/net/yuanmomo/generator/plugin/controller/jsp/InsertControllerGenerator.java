package net.yuanmomo.generator.plugin.controller.jsp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.springframework.ui.ModelMap;

public class InsertControllerGenerator {
	public static List<Method> generator(FullyQualifiedJavaType beanType,String beanName,
			String businessFieldName){
		List<Method> methodList = new ArrayList<Method>();
		
		String beanFieldName = StringUtils.uncapitalize(beanName);
		
		Method method = new Method();
		method.addAnnotation("@RequestMapping(value = \"insert.do\")");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType(String.class.getName()));
		method.setName("insert");
		Parameter param = new Parameter(beanType, beanFieldName);
		param.addAnnotation("@ModelAttribute(\"" + beanFieldName + "\") ");
		Parameter paramModelMap = new Parameter(new FullyQualifiedJavaType(ModelMap.class.getName()), "map");
		method.addParameter(param); 
		method.addParameter(paramModelMap); 
		// 方法body
		method.addBodyLine("try {");
		method.addBodyLine("// 数据校验");
		method.addBodyLine("");
		method.addBodyLine("if(this." + businessFieldName + ".insertSelective(" + beanFieldName + ") == 1){");
		method.addBodyLine("map.put(\"message\",\"插入成功。\");");
		method.addBodyLine("}else{");
		method.addBodyLine("map.put(\"message\",\"插入失败。\");");
		method.addBodyLine("}");
		method.addBodyLine("} catch (Exception e) {");
		method.addBodyLine("logger.error(\"插入异常\" + e.getMessage());");
		method.addBodyLine("map.put(\"message\",\"插入异常\" + e.getMessage());");
		method.addBodyLine("}");
		method.addBodyLine(ControllerPluginUtil.RETURN);
		
		methodList.add(method);
        
        return methodList;
	}
}
