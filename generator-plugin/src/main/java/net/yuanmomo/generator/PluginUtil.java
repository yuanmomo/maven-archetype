package net.yuanmomo.generator;


import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;

public class PluginUtil {
	/**
	 *  生成class中的logger属性
	 * 
	 * @return
	 */
	public static Field getLoggerField (String className){
		Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(new FullyQualifiedJavaType("Logger"));
        field.setName("logger");
        field.setStatic(true);
        field.setInitializationString("LoggerFactory.getLogger(" + className + ".class)");
        return field;
	}
	
	/**
	 * 	添加属性域
	 * 
	 * @param annotation
	 * @param classType
	 * @param fieldName
	 * @return
	 */
	public static Field getField (String annotation,String classType, String fieldName){
        return getField(annotation, new FullyQualifiedJavaType(classType), fieldName);
	}
	
	/**
	 * 	添加属性域
	 * 
	 * @param annotation
	 * @param classType
	 * @param fieldName
	 * @return
	 */
	public static Field getField (String annotation,FullyQualifiedJavaType classType, String fieldName){
		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.addAnnotation(annotation);
		field.setType(classType);
		field.setName(fieldName);
        return field;
	}
	
	public static String getBusinessPackage(String businessPackage,String beanClass){
		if(StringUtils.isBlank(beanClass)){
			throw new RuntimeException("Class name should not be empty.");
		}
		if(StringUtils.isBlank(businessPackage)){
			businessPackage = beanClass.substring(0,beanClass.lastIndexOf(".") + 1) + "business.mybatis";
		}
		return businessPackage;
	}
	
	public static String getBusinessName(String javaBusinessName,String beanName){
		if(StringUtils.isBlank(beanName)){
			throw new RuntimeException("Bean name should not be empty.");
		}
		if(StringUtils.isBlank(javaBusinessName)){
			javaBusinessName = beanName + "Business";
		}
		return javaBusinessName;
	}
	
	
	public static String getControllerPackage(String businessPackage,String beanClass){
		if(StringUtils.isBlank(beanClass)){
			throw new RuntimeException("Class name should not be empty.");
		}
		if(StringUtils.isBlank(businessPackage)){
			businessPackage = beanClass.substring(0,beanClass.lastIndexOf(".") + 1) + "controller.mybatis";
		}
		return businessPackage;
	}
	
	public static String getControllerName(String javaBusinessName,String beanName){
		if(StringUtils.isBlank(beanName)){
			throw new RuntimeException("Bean name should not be empty.");
		}
		if(StringUtils.isBlank(javaBusinessName)){
			javaBusinessName = beanName + "Controller";
		}
		return javaBusinessName;
	}
	
}
