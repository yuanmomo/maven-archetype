package net.yuanmomo.generator.plugin.controller.jsp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.springframework.ui.ModelMap;

public class BatchUpdateControllerGenerator {
	public static List<Method> generator(FullyQualifiedJavaType innerClassType,String beanName,
			String businessFieldName){
		List<Method> methodList = new ArrayList<Method>();
		
		String innerClassFiledName = StringUtils.uncapitalize(innerClassType.getShortName());
		
		Method method = new Method();
        method.addAnnotation("@RequestMapping(value = \"batchUpdateSave" + beanName + ".do\")");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(String.class.getName()));
        method.setName("batchUpdateSave" + beanName);
        Parameter param1 = new Parameter(innerClassType, innerClassFiledName);
        param1.addAnnotation("@ModelAttribute ");
        Parameter paramModelMap = new Parameter(new FullyQualifiedJavaType(ModelMap.class.getName()), "map");
        method.addParameter(param1); 
        method.addParameter(paramModelMap); 
        // 方法body
        method.addBodyLine("try {");
        method.addBodyLine("if(" + innerClassFiledName + " != null && CollectionUtil.isNotNull(" + innerClassFiledName + ".get"+ beanName + "List())){");
        method.addBodyLine("int updateCount = this." + businessFieldName + ".update(" + innerClassFiledName + ".get"+ beanName + "List());");
        method.addBodyLine("if(updateCount >0 ){");
        method.addBodyLine("map.put(\"message\",\"更新成功。\");");
		method.addBodyLine("}else{");
		method.addBodyLine("map.put(\"message\",\"更新失败。\");");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("logger.error(\"批量更新异常\" + e.getMessage());");
        method.addBodyLine("map.put(\"message\", \"批量更新异常\" + e.getMessage());");
        method.addBodyLine("}");
        method.addBodyLine(ControllerPluginUtil.RETURN);
        
		methodList.add(method);
		
	    return methodList;
	}	
}
