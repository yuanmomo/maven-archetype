package net.yuanmomo.generator.plugin.business;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.ArrayList;
import java.util.List;

public class UpdateBusinessGenerator {
	/**
	 * 	生成方法
	 * @return
	 */
	public static List<Method> generator(
            FullyQualifiedJavaType beanType,
            String beanName,
			String mapperFieldName,
            String beanGeneratedKeyGetter){
		List<Method> methodList = new ArrayList<Method>();
		Method method = new Method();
        method.addAnnotation(BusinessPluginUtil.transactionAnno);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("update"); //$NON-NLS-1$
        method.addParameter(new Parameter(beanType, "obj")); //$NON-NLS-1$
        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
        method.addBodyLine("if(obj  == null ){"); 
        method.addBodyLine("return 0;"); 
        method.addBodyLine("}"); 
        method.addBodyLine("return this." + mapperFieldName + ".updateByPrimaryKeySelective(obj);");
        methodList.add(method);
        
        // add update list
        method = new Method();
        method.addAnnotation(BusinessPluginUtil.transactionAnno);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("update"); //$NON-NLS-1$
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<"+ beanName +">"), "list")); //$NON-NLS-1$
        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
        method.addBodyLine("int updateCount = 0;");
        method.addBodyLine("if (list == null || list.size() == 0) {");
        method.addBodyLine("return updateCount;");
        method.addBodyLine("}");
        method.addBodyLine("for ("+ beanName +"  obj : list) {");
        if(StringUtils.isNotBlank(beanGeneratedKeyGetter)){
            method.addBodyLine("if (obj == null || obj."+ beanGeneratedKeyGetter +"() == null || obj."+ beanGeneratedKeyGetter +"() <= 0 ) {");
        }else {
            method.addBodyLine("if (obj == null) {");
        }
        method.addBodyLine("continue;");
        method.addBodyLine("}");
        method.addBodyLine("try {");
        method.addBodyLine("updateCount += this."+ mapperFieldName +".updateByPrimaryKeySelective(obj);");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("throw e;");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("return updateCount;");
        methodList.add(method);
        
		return methodList;
	}
}
