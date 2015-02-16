package net.yuanmomo.mybatis.generator.plugin.controller;

import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.mybatis.generator.util.AjaxResponseBean;
import net.yuanmomo.mybatis.generator.util.StringUtil;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class BatchUpdateControllerGenerator {
	public static List<Method> generator(FullyQualifiedJavaType innerClassType,String beanName,
			String businessFieldName){
		List<Method> methodList = new ArrayList<Method>();
		
		String innerClassFiledName = StringUtil.lowerFirstChar(innerClassType.getShortName());
		
		Method method = new Method();
        method.addAnnotation("@RequestMapping(value = \"batchUpdateSave" + beanName + ".do\")");
        method.addAnnotation("@ResponseBody");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(AjaxResponseBean.class.getName()));
        method.setName("batchUpdateSave" + beanName);
        Parameter param1 = new Parameter(innerClassType, innerClassFiledName);
        param1.addAnnotation("@ModelAttribute ");
        method.addParameter(param1); 
        // 方法body
        method.addBodyLine("try {");
        method.addBodyLine("if(" + innerClassFiledName + " != null && CollectionUtil.isNotNull(" + innerClassFiledName + ".get"+ beanName + "List())){");
        method.addBodyLine("int updateCount = this." + businessFieldName + ".update(" + innerClassFiledName + ".get"+ beanName + "List());");
        method.addBodyLine("if(updateCount >0 ){");
        method.addBodyLine("return AjaxResponseBean.Const.SUCCESS_RESPONSE_BEAN;");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("return AjaxResponseBean.Const.ERROR_RESPONSE_BEAN;");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("logger.error(\"批量更新异常\" + e.getMessage());");
        method.addBodyLine("return AjaxResponseBean.getErrorResponseBean(\"批量更新异常\" + e.getMessage());");
        method.addBodyLine("}");
		methodList.add(method);
		
	    return methodList;
	}	
}
