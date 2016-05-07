package net.yuanmomo.generator.plugin.controller.jsp;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.springframework.ui.ModelMap;

public class GetByKeyControllerGenerator {
	public static List<Method> generator(FullyQualifiedJavaType beanType,String beanName,
			String businessFieldName,FullyQualifiedJavaType keyType, String keyFiledName){
		List<Method> methodList = new ArrayList<Method>();
		
		Method method = new Method();
        method.addAnnotation("@RequestMapping(value = \"get" + beanName + "ByKey.do\")");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(String.class.getName()));
        method.setName("get" + beanName + "ByKey");
        Parameter param = new Parameter(keyType, keyFiledName);
        Parameter paramModelMap = new Parameter(new FullyQualifiedJavaType(ModelMap.class.getName()), "map");
        param.addAnnotation("@RequestParam(\"" + keyFiledName + "\") ");
        method.addParameter(param); 
        method.addParameter(paramModelMap); 
        // 方法body
        method.addBodyLine("try {");
        method.addBodyLine("if(" + keyFiledName + " == null || " + keyFiledName + " < 0){");
        method.addBodyLine("map.put(\"message\",\"ID 错误。\"); ");
        method.addBodyLine(ControllerPluginUtil.RETURN);
        method.addBodyLine("}");
        method.addBodyLine(beanName + " result = this."+ businessFieldName +".get"+ beanName +"ByKey(" + keyFiledName + ");");
        method.addBodyLine("map.put(\"message\",result);");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("logger.error(\"主键获取详情异常;key=\"+" + keyFiledName + " + e.getMessage());");
        method.addBodyLine("map.put(\"message\",\"主键获取详情异常;key=\"+" + keyFiledName + " + e.getMessage());");
        method.addBodyLine("}");
        method.addBodyLine(ControllerPluginUtil.RETURN);
        
		methodList.add(method);
        
        return methodList;
	}
}
