package net.yuanmomo.generator.plugin.controller.json;

import net.yuanmomo.util.generator.AjaxResponseBean;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.ArrayList;
import java.util.List;

public class GetByKeyControllerGenerator {
	public static List<Method> generator(FullyQualifiedJavaType beanType,String beanName,
			String businessFieldName,FullyQualifiedJavaType keyType, String keyFiledName){
		List<Method> methodList = new ArrayList<Method>();
		
		Method method = new Method();
        method.addAnnotation("@RequestMapping(value = \"get" + beanName + "ByKey.do\")");
        method.addAnnotation("@ResponseBody");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(AjaxResponseBean.class.getName()));
        method.setName("get" + beanName + "ByKey");
        Parameter param = new Parameter(keyType, keyFiledName);
        param.addAnnotation("@RequestParam(\"" + keyFiledName + "\") ");
        method.addParameter(param); 
        // 方法body
        method.addBodyLine("try {");
        method.addBodyLine("if(" + keyFiledName + " == null || " + keyFiledName + " < 0){");
        method.addBodyLine("return AjaxResponseBean.Const.PARAMETER_INVALID_ERROR_RESPONSE_BEAN; ");
        method.addBodyLine("}");
        method.addBodyLine(beanName + " result = this."+ businessFieldName +".get"+ beanName +"ByKey(" + keyFiledName + ");");
        method.addBodyLine("return AjaxResponseBean.getReturnValueResponseBean(result);");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("logger.error(\"主键获取详情异常;key=\"+" + keyFiledName + " + e.getMessage());");
        method.addBodyLine("return AjaxResponseBean.getErrorResponseBean(\"主键获取详情异常;key=\"+" + keyFiledName + " + e.getMessage());");
        method.addBodyLine("}");
		
		methodList.add(method);
        
        return methodList;
	}
}
