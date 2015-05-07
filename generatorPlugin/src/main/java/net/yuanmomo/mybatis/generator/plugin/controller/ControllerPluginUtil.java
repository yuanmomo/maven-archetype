package net.yuanmomo.mybatis.generator.plugin.controller;

import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.mybatis.generator.plugin.business.BusinessPluginUtil;
import net.yuanmomo.mybatis.generator.util.CollectionUtil;
import net.yuanmomo.mybatis.generator.util.PluginUtil;
import net.yuanmomo.mybatis.generator.util.StringUtil;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.PropertyRegistry;

public class ControllerPluginUtil{
	
	/**
	 *  controller需要导入的包列表
	 */
	public static List<String> controllerImportStringList = new ArrayList<String>();
	static{
		controllerImportStringList.add("java.util.List");
		controllerImportStringList.add("net.yuanmomo.mybatis.generator.util.AjaxResponseBean");
		controllerImportStringList.add("net.yuanmomo.mybatis.generator.util.CollectionUtil");
		controllerImportStringList.add("net.yuanmomo.mybatis.generator.util.PaginationUtil");
		controllerImportStringList.add("net.yuanmomo.mybatis.generator.util.PaginationBean");
		controllerImportStringList.add("org.slf4j.Logger");
		controllerImportStringList.add("org.slf4j.LoggerFactory");
		controllerImportStringList.add("org.springframework.beans.factory.annotation.Autowired");
		controllerImportStringList.add("org.springframework.stereotype.Controller");
		controllerImportStringList.add("org.springframework.web.bind.annotation.ModelAttribute");
		controllerImportStringList.add("org.springframework.web.bind.annotation.RequestMapping");
		controllerImportStringList.add("org.springframework.web.bind.annotation.RequestParam");
		controllerImportStringList.add("org.springframework.web.bind.annotation.ResponseBody");
	}
	
	/**
	 * 	生成java business 文件
	 * 
	 * @return
	 */
	public static List<GeneratedJavaFile> getControllerJavaFile(
			Context context,
			IntrospectedTable introspectedTable,
			String controllerPackage,
			String javaControllerName,
			String businessPackage,
			String javaBusinessName){
		// 当前bean的完整名称 (包名 + 类名: xxxx.xxx.xx.xx.Test)
		String beanClass = introspectedTable.getBaseRecordType();
		// Bean的名称(Test)
		String beanName = introspectedTable.getTableConfiguration().getDomainObjectName();
		// bean Filed Name
        String beanFieldName = StringUtil.lowerFirstChar(beanName);
		FullyQualifiedJavaType beanType = new FullyQualifiedJavaType(beanClass);
		// BeanExample的名称(TestCriteria)
		String criteriaName = introspectedTable.getExampleType();
		FullyQualifiedJavaType criteriaType = new FullyQualifiedJavaType(criteriaName);
		
		// business类的包名和类名
		businessPackage = PluginUtil.getBusinessPackage(businessPackage, beanClass);
		javaBusinessName = PluginUtil.getBusinessName(javaBusinessName, beanName);
		
		// controller类的包名和类名
		controllerPackage = PluginUtil.getControllerPackage(controllerPackage, beanClass);
		javaControllerName = PluginUtil.getControllerName(javaControllerName, beanName);
		
		// 生成business 文件
		List<GeneratedJavaFile> businessFileList = BusinessPluginUtil.getBusinessJavaFile(context, introspectedTable, 
				businessPackage, javaBusinessName);
		// business type
		FullyQualifiedJavaType businessType = new FullyQualifiedJavaType(businessPackage + "." + javaBusinessName);
		// business Field Name
        String businessFieldName = StringUtil.lowerFirstChar(javaBusinessName);
		
		// 注释生成器
		CommentGenerator commentGenerator = context.getCommentGenerator();
		
		FullyQualifiedJavaType controllerType = new FullyQualifiedJavaType(controllerPackage + "." + javaControllerName);
		TopLevelClass targetControllerClass = new TopLevelClass(controllerType);
        targetControllerClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(targetControllerClass);
        
        targetControllerClass.addAnnotation("@Controller");
        targetControllerClass.addAnnotation("@RequestMapping(\"/backend/" + beanFieldName + "/\")");
        for(String str : controllerImportStringList){
        	targetControllerClass.addImportedType(str);
        }
        targetControllerClass.addImportedType(beanClass);
        
        // 导入bean类
        targetControllerClass.addImportedType(beanClass);
        // 导入criteria类
        targetControllerClass.addImportedType(criteriaName);
        // 添加business包
        targetControllerClass.addImportedType(businessType);
        
        // add logger field
        Field loggerField = PluginUtil.getLoggerField(javaControllerName);
        if(loggerField != null){
        	commentGenerator.addFieldComment(loggerField, introspectedTable);
        	targetControllerClass.addField(loggerField);
        }
        // add business field
        Field field = PluginUtil.getField("@Autowired ", businessType, businessFieldName);
        if(field != null){
        	commentGenerator.addFieldComment(field, introspectedTable);
        	targetControllerClass.addField(field);
        }
        
        List<Method> methodList = new ArrayList<Method>();

        // add insert
        List<Method> insertMethodList = InsertControllerGenerator.generator(beanType, beanName, businessFieldName);
        if(!CollectionUtil.isNull(insertMethodList)){
        	methodList.addAll(insertMethodList);
        }
        
        // add getXXXByKey
        // 判断是否包含主键
        GeneratedKey generatedKey = introspectedTable.getTableConfiguration().getGeneratedKey();
        if(generatedKey != null){
	        String keyColumn = generatedKey.getColumn();
	        FullyQualifiedJavaType keyType = introspectedTable.getColumn(keyColumn).getFullyQualifiedJavaType();
	        if(generatedKey != null){
	        	List<Method> getByKeyMethodList = GetByKeyControllerGenerator.generator(beanType, beanName, businessFieldName,
	        			keyType,keyColumn);
	            if(!CollectionUtil.isNull(getByKeyMethodList)){
	            	methodList.addAll(getByKeyMethodList);
	            }
	        }
        }
        // add selectBeanList
        List<Method> selectListMethodList = SelectListControllerGenerator.generator(beanType, beanName, businessFieldName,
        		criteriaType.getShortName());
        if(!CollectionUtil.isNull(selectListMethodList)){
        	methodList.addAll(selectListMethodList);
        }
        
        // add updateSaveBean
        List<Method> updateSaveBeanMethodList = UpdateControllerGenerator.generator(beanType, beanName, businessFieldName);
        if(!CollectionUtil.isNull(updateSaveBeanMethodList)){
        	methodList.addAll(updateSaveBeanMethodList);
        }
        
        InnerClass innerClass = InnerClassControllerGenerator.generator(controllerType, beanName);
        targetControllerClass.addInnerClass(innerClass);
        
        
        // add batchUpdateSaveBean
        List<Method> batchUpdateSaveBeanMethodList = BatchUpdateControllerGenerator.generator(innerClass.getType(), beanName, businessFieldName);
        if(!CollectionUtil.isNull(batchUpdateSaveBeanMethodList)){
        	methodList.addAll(batchUpdateSaveBeanMethodList);
        }
        
        for(Method m : methodList){
    		commentGenerator.addGeneralMethodComment(m, introspectedTable);
    		targetControllerClass.addMethod(m);
    	}
        
		GeneratedJavaFile gjf = new GeneratedJavaFile(targetControllerClass,
				context.getJavaClientGeneratorConfiguration().getTargetProject(),
	            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
	            context.getJavaFormatter());
		
		List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
		list.add(gjf);
		list.addAll(businessFileList);
		return list;
	}
}
