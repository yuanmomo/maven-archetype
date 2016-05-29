package net.yuanmomo.generator.plugin.business;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import net.yuanmomo.generator.PluginUtil;
import net.yuanmomo.util.CollectionUtil;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.PropertyRegistry;

public class BusinessPluginUtil {
	/**
	 *  business需要导入的包列表
	 */
	public static List<String> businessImportStringList = new ArrayList<String>();
	
	static{
		businessImportStringList.add("java.util.ArrayList");
        businessImportStringList.add("java.util.List");
        businessImportStringList.add("org.slf4j.Logger");
        businessImportStringList.add("org.slf4j.LoggerFactory");
        businessImportStringList.add("org.springframework.beans.factory.annotation.Autowired");
        businessImportStringList.add("org.springframework.stereotype.Service");
        businessImportStringList.add("org.springframework.transaction.annotation.Isolation");
        businessImportStringList.add("org.springframework.transaction.annotation.Propagation");
        businessImportStringList.add("org.springframework.transaction.annotation.Transactional");
	}
	
	/**
	 *  business的事务配置 annotation
	 */
	public static String transactionAnno = "@Transactional(propagation=Propagation.REQUIRED," +
			"isolation =Isolation.REPEATABLE_READ, rollbackFor = Exception.class)";
	
	
	/**
	 * 	生成java business 文件
	 * 
	 * @return
	 */
	public static List<GeneratedJavaFile> getBusinessJavaFile(
			Context context,
			IntrospectedTable introspectedTable,
			String businessPackage,
			String javaBusinessName){
		
		// 当前bean的完整名称 (包名 + 类名: xxxx.xxx.xx.xx.Test)
		String beanClass = introspectedTable.getBaseRecordType();
		// Bean的名称(Test)
		String beanName = introspectedTable.getTableConfiguration().getDomainObjectName();
		FullyQualifiedJavaType beanType = new FullyQualifiedJavaType(beanClass);
		// BeanExample的名称(TestCriteria)
		String criteriaName = introspectedTable.getExampleType();
		FullyQualifiedJavaType criteriaType = new FullyQualifiedJavaType(criteriaName);
		// Mapper 类名
		String mapperName = introspectedTable.getMyBatis3JavaMapperType();
		FullyQualifiedJavaType mapperType = new FullyQualifiedJavaType (mapperName);
		// Mapper field name 
		String mapperFieldName = StringUtils.uncapitalize(mapperType.getShortName());
		
		// business类的包名和类名
		businessPackage = PluginUtil.getBusinessPackage(businessPackage, beanClass);
		javaBusinessName = PluginUtil.getBusinessName(javaBusinessName, beanName);
		
		// 注释生成器
		CommentGenerator commentGenerator = context.getCommentGenerator();
		
		// 取得business类的全名（包名 + 类名）
        FullyQualifiedJavaType businessType = new FullyQualifiedJavaType(businessPackage + "." + javaBusinessName);
        
        TopLevelClass targetBusinessClass = new TopLevelClass(businessType);
        // 生成注释
        commentGenerator.addJavaFileComment(targetBusinessClass);
        targetBusinessClass.setVisibility(JavaVisibility.PUBLIC);

        // 添加annotation 和 import包列表
        targetBusinessClass.addAnnotation("@Service");
        for(String str : BusinessPluginUtil.businessImportStringList){
        	targetBusinessClass.addImportedType(str);
        }
        // 导入bean类
        targetBusinessClass.addImportedType(beanClass);
        // 导入criteria类
        targetBusinessClass.addImportedType(criteriaName);
        // 导入mapper类
        targetBusinessClass.addImportedType(mapperType);
        
        // add logger
        Field loggerField = PluginUtil.getLoggerField(javaBusinessName);
        if(loggerField != null){
        	commentGenerator.addFieldComment(loggerField, introspectedTable);
        	targetBusinessClass.addField(loggerField);
        }
        
        
        Field field = PluginUtil.getField("@Autowired ", mapperType, mapperFieldName);
        if(field != null){
        	commentGenerator.addFieldComment(field, introspectedTable);
        	targetBusinessClass.addField(field);
        }
        
        List<Method> methodList = new ArrayList<Method>();
        
        // add insert method list
        List<Method> insertMethodList = InsertSelectiveBusinessGenerator.generator(beanType, beanName, mapperFieldName);
        if(!CollectionUtil.isNull(insertMethodList)){
        	methodList.addAll(insertMethodList);
        }

        // add selectXXXByKey
        // 判断是否包含主键
        GeneratedKey generatedKey = introspectedTable.getTableConfiguration().getGeneratedKey();
		String generatedKeyColumn  = null;
        if(generatedKey != null){
	        generatedKeyColumn = generatedKey.getColumn();
	        FullyQualifiedJavaType keyType = introspectedTable.getColumn(generatedKeyColumn).getFullyQualifiedJavaType();
	        if(generatedKey != null){
	        	List<Method> getByKeyMethodList = GetByKeyBusinessGenerator.generator(beanType, beanName, mapperFieldName,keyType);
	            if(!CollectionUtil.isNull(getByKeyMethodList)){
	            	methodList.addAll(getByKeyMethodList);
	            }
	        }
        }

		// add update method list
		String beanGeneratedKeyGetter = null;
		if(StringUtils.isNotBlank(generatedKeyColumn)){
			beanGeneratedKeyGetter = "get" + StringUtils.capitalize(introspectedTable.getColumn(generatedKeyColumn).getJavaProperty());
		}

		List<Method> updateMethodList = UpdateBusinessGenerator.generator(beanType, beanName, mapperFieldName,beanGeneratedKeyGetter);
		if(!CollectionUtil.isNull(updateMethodList)){
			methodList.addAll(updateMethodList);
		}

        // add selectList
        List<Method> selectListMethodList = SelectListBusinessGenerator.generator(beanType, beanName, mapperFieldName,criteriaType);
        if(!CollectionUtil.isNull(selectListMethodList)){
        	methodList.addAll(selectListMethodList);
        }
        
        // add countList
        List<Method> countListMethodList = CountListBusinessGenerator.generator(beanType, beanName, mapperFieldName,criteriaType);
        if(!CollectionUtil.isNull(countListMethodList)){
        	methodList.addAll(countListMethodList);
        }
        
        for(Method m : methodList){
    		commentGenerator.addGeneralMethodComment(m, introspectedTable);
            targetBusinessClass.addMethod(m);
    	}
        
        GeneratedJavaFile gjf = new GeneratedJavaFile(targetBusinessClass,
			context.getJavaClientGeneratorConfiguration().getTargetProject(),
            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
            context.getJavaFormatter());
        
        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
		list.add(gjf);
		return list;
	}
}
