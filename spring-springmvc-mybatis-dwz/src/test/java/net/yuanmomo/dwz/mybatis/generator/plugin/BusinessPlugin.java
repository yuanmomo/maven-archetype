
/**
 * Project Name : Tools
 * File Name    : DAOPlugin.java
 * Package Name : net.yuanmomo.tools.db.orm.mybatis.generator.plugin.dao
 * Created on   : 2014-2-17下午8:23:27
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.dwz.mybatis.generator.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * ClassName : DAOPlugin 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-2-17 下午8:23:27 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
public class BusinessPlugin extends PluginAdapter {
	private static final String generatedBusinessNamePro="generatedBusinessName";
	private static final String generatedBusinessPackagePro="generatedBusinessPackage";
	
	private static String packageName;
	
	private static List<String> importStringList = new ArrayList<String>();
	static{
		importStringList.add("java.util.ArrayList");
        importStringList.add("java.util.List");
        importStringList.add("org.slf4j.Logger");
        importStringList.add("org.slf4j.LoggerFactory");
        importStringList.add("org.springframework.beans.factory.annotation.Autowired");
        importStringList.add("org.springframework.stereotype.Service");
        importStringList.add("org.springframework.transaction.annotation.Isolation");
        importStringList.add("org.springframework.transaction.annotation.Propagation");
        importStringList.add("org.springframework.transaction.annotation.Transactional");
	}
	
	private static String transactionAnno = "@Transactional(propagation=Propagation.REQUIRED,isolation =Isolation.REPEATABLE_READ, rollbackFor = Exception.class)";
	
	/**
	 * validate:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param warnings
	 * @return
	 * @see org.mybatis.generator.api.Plugin#validate(java.util.List)
	 */
	@Override
	public boolean validate(List<String> warnings) {
		packageName = properties.getProperty("packageName"); //$NON-NLS-1$

        boolean valid = stringHasValue(packageName);
        if (!valid) {
            if (!stringHasValue(packageName)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "BusinessPlugin", //$NON-NLS-1$
                        "packageName")); //$NON-NLS-1$
            }
        }
        return valid;
	}

	/**
	 * contextGenerateAdditionalJavaFiles:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param introspectedTable
	 * @return
	 * @see org.mybatis.generator.api.PluginAdapter#contextGenerateAdditionalJavaFiles(org.mybatis.generator.api.IntrospectedTable)
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		// 判断是否需要生成class文件
		String javaBusinessName = 
				introspectedTable.getTableConfiguration().getProperty(generatedBusinessNamePro);
		// 不生成Business文件
		if(javaBusinessName == null || "".equals(javaBusinessName.trim())){
			return new ArrayList<GeneratedJavaFile>();
		}
		
		// 取得包名
		String generatedBusinessPackage = 
				introspectedTable.getTableConfiguration().getProperty(generatedBusinessPackagePro);
		if(generatedBusinessPackage == null || "".equals(generatedBusinessPackage.trim())){
			generatedBusinessPackage = packageName;
		}
		
		CommentGenerator commentGenerator = context.getCommentGenerator();
		
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(generatedBusinessPackage + "." + javaBusinessName);
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        topLevelClass.addAnnotation("@Service");
        for(String str : importStringList){
        	topLevelClass.addImportedType(str);
        }
        
        // beanName的名称 
        String beanName = introspectedTable.getTableConfiguration().getDomainObjectName();
        topLevelClass.addImportedType(introspectedTable.getBaseRecordType());
        // 添加criteria包
        topLevelClass.addImportedType(introspectedTable.getExampleType());
        // mapper文件的名称
        String javaMapperName = introspectedTable.getMyBatis3JavaMapperType();
        FullyQualifiedJavaType mapperFieldNameType = new FullyQualifiedJavaType (javaMapperName);
        topLevelClass.addImportedType(mapperFieldNameType);
        String mapperFieldName = lowerFirstChar(mapperFieldNameType.getShortName());
        
        // add logger
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(new FullyQualifiedJavaType("Logger"));
        field.setName("logger");
        field.setStatic(true);
        field.setInitializationString("LoggerFactory.getLogger(" + javaBusinessName + ".class)");
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        
        // add mapper
        Field mapper = new Field();
        mapper.setVisibility(JavaVisibility.PRIVATE);
        mapper.addAnnotation("@Autowired ");
        mapper.setType(mapperFieldNameType);
        mapper.setName(mapperFieldName);
        commentGenerator.addFieldComment(mapper, introspectedTable);
        topLevelClass.addField(mapper);
        
        
        // add insertSelective single
        Method method = new Method();
        method.addAnnotation(transactionAnno);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("insertSelective"); //$NON-NLS-1$
        method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "obj")); //$NON-NLS-1$
        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
        method.addBodyLine("if(obj  == null ){"); 
        method.addBodyLine("return 0;"); 
        method.addBodyLine("}"); 
        method.addBodyLine("List<"+ beanName +"> list = new ArrayList<"+ beanName +">();"); 
        method.addBodyLine("list.add(obj);"); 
        method.addBodyLine("return this.insertSelective(list);"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        
        // add insertSelective list
        method = new Method();
        method.addAnnotation(transactionAnno);
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
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        
        
     	// add update single
        method = new Method();
        method.addAnnotation(transactionAnno);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("update"); //$NON-NLS-1$
        method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "obj")); //$NON-NLS-1$
        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
        method.addBodyLine("if(obj  == null ){"); 
        method.addBodyLine("return 0;"); 
        method.addBodyLine("}"); 
        method.addBodyLine("List<"+ beanName +"> list = new ArrayList<"+ beanName +">();"); 
        method.addBodyLine("list.add(obj);"); 
        method.addBodyLine("return this.update(list);"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        
        // add update list
        method = new Method();
        method.addAnnotation(transactionAnno);
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
        method.addBodyLine("if (obj == null) {");
        method.addBodyLine("continue;");
        method.addBodyLine("}");
        method.addBodyLine("try {");
        method.addBodyLine("updateCount += this."+ mapperFieldName +".updateByPrimaryKeySelective(obj);");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("throw e;");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("return updateCount;");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        
        
        // add selectList
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType("java.util.List<"+ beanName +">"));
        method.setName("select" + beanName + "List"); //$NON-NLS-1$
        method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getExampleType()), "param")); //$NON-NLS-1$
        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
        method.addBodyLine("return this."+ mapperFieldName +".selectByExample(param);"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        
        // add countList
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("count" + beanName + "List"); //$NON-NLS-1$
        method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getExampleType()), "param")); //$NON-NLS-1$
        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
        method.addBodyLine("return this."+ mapperFieldName +".countByExample(param);"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        
        GeneratedJavaFile gjf = new GeneratedJavaFile(topLevelClass,
			context.getJavaClientGeneratorConfiguration().getTargetProject(),
            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
            context.getJavaFormatter());
        List<GeneratedJavaFile>  gifList = new ArrayList<GeneratedJavaFile>();
        
        gifList.add(gjf);
        
		return gifList;
	}
	
	public static String lowerFirstChar(String oldString) {
		if (oldString == null || "".equals(oldString.trim())) {
			return oldString;
		}
		String target = new StringBuffer()
				.append(oldString.substring(0, 1).toLowerCase())
				.append(oldString.substring(1)).toString();
		return target;
	} 
}
