package net.yuanmomo.generator.plugin.controller.json;

import net.yuanmomo.util.generator.AjaxResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.ArrayList;
import java.util.List;


public class InsertControllerGenerator {
	public static List<Method> generator(FullyQualifiedJavaType beanType,String beanName,
			String businessFieldName){
		List<Method> methodList = new ArrayList<Method>();
		
		String beanFieldName = StringUtils.uncapitalize(beanName);
		
		Method method = new Method();
		method.addAnnotation("@RequestMapping(value = \"insert.do\")");
		method.addAnnotation("@ResponseBody");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType(AjaxResponseBean.class.getName()));
		method.setName("insert");
		Parameter param = new Parameter(beanType, beanFieldName);
		param.addAnnotation("@ModelAttribute(\"" + beanFieldName + "\") ");
		method.addParameter(param); 
		// 方法body
		method.addBodyLine("try {");
		method.addBodyLine("// 数据校验");
		method.addBodyLine("");
		method.addBodyLine("this." + businessFieldName + ".insertSelective(" + beanFieldName + ");");
		method.addBodyLine("return AjaxResponseBean.Const.SUCCESS_RESPONSE_BEAN;");
		method.addBodyLine("} catch (Exception e) {");
		method.addBodyLine("logger.error(\"插入异常\" + e.getMessage());");
		method.addBodyLine("return AjaxResponseBean.getErrorResponseBean(\"插入异常\" + e.getMessage());");
		method.addBodyLine("}");
		
		methodList.add(method);
        
        return methodList;
	}
}
