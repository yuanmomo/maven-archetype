package net.yuanmomo.generator.plugin.business;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

import java.util.ArrayList;
import java.util.List;

public class InsertSelectiveBusinessGenerator {
	/**
	 * 	生成方法
	 * @return
	 */
	public static List<Method> generator(FullyQualifiedJavaType beanType,String beanName,
			String mapperFieldName){
		List<Method> methodList = new ArrayList<Method>();
		
		// add insertSelective single
        Method method = new Method();
        method.addAnnotation(BusinessPluginUtil.transactionAnno);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("insertSelective"); //$NON-NLS-1$
        method.addParameter(new Parameter(beanType, "obj")); //$NON-NLS-1$
        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
        method.addBodyLine("if(obj  == null ){"); 
        method.addBodyLine("return 0;"); 
        method.addBodyLine("}"); 
        method.addBodyLine("return this." + mapperFieldName + ".insertSelective(obj);");
        methodList.add(method);
        
        
        // add insertSelective list
        method = new Method();
        method.addAnnotation(BusinessPluginUtil.transactionAnno);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("insertSelective"); //$NON-NLS-1$
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<"+ beanName +">"), "list")); //$NON-NLS-1$
        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
        method.addBodyLine("int insertCount = 0;");
        method.addBodyLine("if (list == null || list.size() == 0) {");
        method.addBodyLine("return insertCount;");
        method.addBodyLine("}");
        method.addBodyLine("for ("+ beanName +"  obj : list) {");
        method.addBodyLine("if (obj == null) {");
        method.addBodyLine("continue;");
        method.addBodyLine("}");
        method.addBodyLine("try {");
        method.addBodyLine("insertCount += this."+ mapperFieldName +".insertSelective(obj);");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("throw e;");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("return insertCount;");
        methodList.add(method);
        
        return methodList;
	}
}
