
/**
 * Project Name : Tools
 * File Name    : DAOPlugin.java
 * Package Name : net.yuanmomo.tools.db.orm.mybatis.generator.plugin.dao
 * Created on   : 2014-2-17下午8:23:27
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.mybatis.generator.plugin.business;

import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.mybatis.generator.util.AjaxResponseBean;
import net.yuanmomo.mybatis.generator.util.PaginationBean;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.GeneratedKey;
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
public class DWZBusinessControllerPlugin extends PluginAdapter {
	private String businessPackageName;
	private String controllerPackageName;
	
	private static List<String> businessImportStringList = new ArrayList<String>();
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
	private static List<String> controllerImportStringList = new ArrayList<String>();
	static{
		controllerImportStringList.add("java.util.List");
		controllerImportStringList.add("net.yuanmomo.dwz.bean.AjaxResponseBean");
		controllerImportStringList.add("net.yuanmomo.dwz.util.CollectionUtil");
		controllerImportStringList.add("net.yuanmomo.dwz.util.PaginationUtil");
		controllerImportStringList.add("net.yuanmomo.dwz.util.PaginationBean");
		controllerImportStringList.add("org.slf4j.Logger");
		controllerImportStringList.add("org.slf4j.LoggerFactory");
		controllerImportStringList.add("org.springframework.beans.factory.annotation.Autowired");
		controllerImportStringList.add("org.springframework.stereotype.Controller");
		controllerImportStringList.add("org.springframework.web.bind.annotation.ModelAttribute");
		controllerImportStringList.add("org.springframework.web.bind.annotation.RequestMapping");
		controllerImportStringList.add("org.springframework.web.bind.annotation.RequestParam");
		controllerImportStringList.add("org.springframework.web.bind.annotation.ResponseBody");
	}
	
	private static String transactionAnno = "@Transactional(propagation=Propagation.REQUIRED,isolation =Isolation.REPEATABLE_READ, rollbackFor = Exception.class)";
	
	/**
	 * validate: 生成business和controller又xml中的table标签来控制. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param warnings
	 * @return
	 * @see org.mybatis.generator.api.Plugin#validate(java.util.List)
	 */
	public boolean validate(List<String> warnings) {
		businessPackageName = properties.getProperty("businessPackageName"); //$NON-NLS-1$
		controllerPackageName = properties.getProperty("controllerPackageName"); //$NON-NLS-1$
        return true;
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
		// 判断是否需要生成business文件
		String javaBusinessName = 
				introspectedTable.getTableConfiguration().getProperty("generatedBusinessName");
		// 不生成Business文件
		if(javaBusinessName == null || "".equals(javaBusinessName.trim())){
			return new ArrayList<GeneratedJavaFile>();
		}
		
		CommentGenerator commentGenerator = context.getCommentGenerator();
		
        FullyQualifiedJavaType businessType = new FullyQualifiedJavaType(businessPackageName + "." + javaBusinessName);
        TopLevelClass topLevelClass = new TopLevelClass(businessType);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        topLevelClass.addAnnotation("@Service");
        for(String str : businessImportStringList){
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
        
        // add selectXXXByKey
        // 判断是否包含主键
        GeneratedKey generatedKey = introspectedTable.getTableConfiguration().getGeneratedKey();
        String keyColumn = generatedKey.getColumn();
        FullyQualifiedJavaType keyType = introspectedTable.getColumn(keyColumn).getFullyQualifiedJavaType();
        if(generatedKey != null){
	        method = new Method();
	        method.setVisibility(JavaVisibility.PUBLIC);
	        method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
	        method.setName("get" + beanName + "ByKey"); //$NON-NLS-1$
	        method.addParameter(new Parameter(keyType, "key")); //$NON-NLS-1$
	        method.addException(new FullyQualifiedJavaType(Exception.class.getName()));
	        method.addBodyLine("return this."+ mapperFieldName +".selectByPrimaryKey(key);"); 
	        commentGenerator.addGeneralMethodComment(method, introspectedTable);
	        topLevelClass.addMethod(method);
        }
        
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
        
        /***************************************************************************************
         * 			生成controller文件
         */
        String javaControllerName = 
				introspectedTable.getTableConfiguration().getProperty("generatedControllerName");
		// 不生成Business文件
		if(javaControllerName == null || "".equals(javaControllerName.trim())){
			return gifList;
		}
		
		FullyQualifiedJavaType controllerType = new FullyQualifiedJavaType(controllerPackageName + "." + javaControllerName);
        topLevelClass = new TopLevelClass(controllerType);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        
        // bean param参数的名称
        String beanParamName = lowerFirstChar(beanName);
        
        topLevelClass.addAnnotation("@Controller");
        topLevelClass.addAnnotation("@RequestMapping(\"/backend/" + beanParamName + "/\")");
        for(String str : controllerImportStringList){
        	topLevelClass.addImportedType(str);
        }
        
        topLevelClass.addImportedType(introspectedTable.getBaseRecordType());
        
        // 添加criteria包
        topLevelClass.addImportedType(introspectedTable.getExampleType());
        String criteriaName = new FullyQualifiedJavaType(introspectedTable.getExampleType()).getShortName();
        // 添加business包
        topLevelClass.addImportedType(businessType);
        // mapper文件的名称
        String businessFieldName = lowerFirstChar(javaBusinessName);
        
        // add logger
        Field logger = new Field();
        logger.setVisibility(JavaVisibility.PRIVATE);
        logger.setType(new FullyQualifiedJavaType("Logger"));
        logger.setName("logger");
        logger.setStatic(true);
        logger.setInitializationString("LoggerFactory.getLogger(" + controllerType.getShortName() + ".class)");
        commentGenerator.addFieldComment(logger, introspectedTable);
        topLevelClass.addField(logger);
        
        // add business
        Field business = new Field();
        business.setVisibility(JavaVisibility.PRIVATE);
        business.addAnnotation("@Autowired ");
        business.setType(businessType);
        business.setName(businessFieldName);
        commentGenerator.addFieldComment(business, introspectedTable);
        topLevelClass.addField(business);
        
        
        // add insert
        method = new Method();
		method.addAnnotation("@RequestMapping(value = \"insert.do\")");
		method.addAnnotation("@ResponseBody");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType(AjaxResponseBean.class.getName()));
		method.setName("insert");
		Parameter param = new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), beanParamName);
		param.addAnnotation("@ModelAttribute(\"" + beanParamName + "\") ");
		method.addParameter(param); 
		// 方法body
		method.addBodyLine("try {");
		method.addBodyLine("// 数据校验");
		method.addBodyLine("");
		method.addBodyLine("this." + businessFieldName + ".insertSelective(" + beanParamName + ");");
		method.addBodyLine("return AjaxResponseBean.Const.SUCCESS_RESPONSE_BEAN;");
		method.addBodyLine("} catch (Exception e) {");
		method.addBodyLine("logger.error(\"插入异常\" + e.getMessage());");
		method.addBodyLine("return AjaxResponseBean.getErrorResponseBean(\"插入异常\" + e.getMessage());");
		method.addBodyLine("}");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
        
        // add getXXXByKey
        if(generatedKey != null){
        	 method = new Method();
             method.addAnnotation("@RequestMapping(value = \"get" + beanName + "ByKey.do\")");
             method.addAnnotation("@ResponseBody");
             method.setVisibility(JavaVisibility.PUBLIC);
             method.setReturnType(new FullyQualifiedJavaType(AjaxResponseBean.class.getName()));
             method.setName("get" + beanName + "ByKey");
             param = new Parameter(keyType, generatedKey.getColumn());
             param.addAnnotation("@RequestParam(\"" + generatedKey.getColumn() + "\") ");
             method.addParameter(param); 
             // 方法body
             method.addBodyLine("try {");
             method.addBodyLine("if(" + generatedKey.getColumn() + " == null || " + generatedKey.getColumn() + " < 0){");
             method.addBodyLine("return AjaxResponseBean.Const.PARAMETER_INVALID_ERROR_RESPONSE_BEAN; ");
             method.addBodyLine("}");
             method.addBodyLine("Test result = this.testBusiness.getTestByKey(" + generatedKey.getColumn() + ");");
             method.addBodyLine("return AjaxResponseBean.getReturnValueResponseBean(result);");
             method.addBodyLine("} catch (Exception e) {");
             method.addBodyLine("logger.error(\"主键获取详情异常;key=\"+" + generatedKey.getColumn() + " + e.getMessage());");
             method.addBodyLine("return AjaxResponseBean.getErrorResponseBean(\"主键获取详情异常;key=\"+" + generatedKey.getColumn() + " + e.getMessage());");
             method.addBodyLine("}");
             commentGenerator.addGeneralMethodComment(method, introspectedTable);
             topLevelClass.addMethod(method);
        }
        
        // add selectBeanList
        method = new Method();
        method.addAnnotation("@RequestMapping(value = \"select" + beanName + "List.do\")");
        method.addAnnotation("@ResponseBody");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(AjaxResponseBean.class.getName()));
        method.setName("select" + beanName + "List");
        Parameter param1 = new Parameter(new FullyQualifiedJavaType(Short.class.getName()), "conditionType");
        param1.addAnnotation("@RequestParam(\"conditionType\") ");
        Parameter param2 = new Parameter(FullyQualifiedJavaType.getStringInstance(), "conditionValue");
        param2.addAnnotation("@RequestParam(\"conditionValue\") ");
        Parameter param3 = new Parameter(new FullyQualifiedJavaType(PaginationBean.class.getName()), "paginationBean");
        param3.addAnnotation("@ModelAttribute ");
        method.addParameter(param1); 
        method.addParameter(param2); 
        method.addParameter(param3); 
        // 方法body
        method.addBodyLine("try {");
        method.addBodyLine("int currentPage = paginationBean.getPageNum();");
        method.addBodyLine("int pageSize = paginationBean.getNumPerPage(); ");
        method.addBodyLine("");
        method.addBodyLine("if(pageSize < 1){");
        method.addBodyLine("return AjaxResponseBean.Const.PARAMETER_INVALID_ERROR_RESPONSE_BEAN; ");
        method.addBodyLine("}");
        method.addBodyLine("if(currentPage<1){");
        method.addBodyLine("return AjaxResponseBean.Const.PARAMETER_INVALID_ERROR_RESPONSE_BEAN; ");
        method.addBodyLine("}");
        method.addBodyLine("// 构造查询参数");
        method.addBodyLine(""+criteriaName + " param =new " + criteriaName + "();");
        method.addBodyLine("//"+criteriaName + ".Criteria criteria = param.createCriteria();");
        method.addBodyLine("");
        method.addBodyLine("// 根据参数设置查询条件");
        method.addBodyLine("");
        method.addBodyLine("// 取得当前查询的总记录结果");
        method.addBodyLine("int total = this." + businessFieldName + ".count" + beanName + "List(param);");
        method.addBodyLine("if(total == 0){");
        method.addBodyLine("// 没有记录数");
        method.addBodyLine("return AjaxResponseBean.getNoDataReturnValueResponseBean();");
        method.addBodyLine("}");
        method.addBodyLine("paginationBean.setTotalCount(total);");
        method.addBodyLine("// 判断当前请求的页码有没有超过总页数");
        method.addBodyLine("int totalPages = PaginationUtil.getPages(total, pageSize);");
        method.addBodyLine("paginationBean.setTotalPages(totalPages);");
        method.addBodyLine("");
        method.addBodyLine("if(currentPage > totalPages){");
        method.addBodyLine("// 当前页超过总页数,取最大数");
        method.addBodyLine("currentPage = totalPages;");
        method.addBodyLine("paginationBean.setPageNum(currentPage);");
        method.addBodyLine("}");
        method.addBodyLine("");
        method.addBodyLine("// 设置排序");
        method.addBodyLine("// param.setOrderByClause(\" id asc \");");
        method.addBodyLine("");
        method.addBodyLine("int start = (currentPage - 1) * pageSize;");
        method.addBodyLine("param.setStart(start);");
        method.addBodyLine("param.setCount(pageSize);");
        method.addBodyLine("");
        method.addBodyLine("List<" + beanName + "> configList = this." + businessFieldName + ".select" + beanName + "List(param);");
        method.addBodyLine("");
        method.addBodyLine("paginationBean.setResult(configList);  // 返回数据结果");
        method.addBodyLine("return AjaxResponseBean.getReturnValueResponseBean(paginationBean);");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("logger.error(\"查询异常\" + e.getMessage());");
        method.addBodyLine("return AjaxResponseBean.getErrorResponseBean(\"查询异常\" + e.getMessage());");
        method.addBodyLine("}"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        
        // add updateSaveBean
        method = new Method();
        method.addAnnotation("@RequestMapping(value = \"updateSave" + beanName + ".do\")");
        method.addAnnotation("@ResponseBody");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(AjaxResponseBean.class.getName()));
        method.setName("updateSave" + beanName);
        param1 = new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), beanParamName);
        param1.addAnnotation("@ModelAttribute ");
        method.addParameter(param1); 
        // 方法body
        method.addBodyLine("try {");
        method.addBodyLine("if(" + beanParamName + " == null ){");
        method.addBodyLine("// || NumberUtil.isNotPositive(" + beanParamName + ".getId())){");
        method.addBodyLine("return AjaxResponseBean.Const.PARAMETER_INVALID_ERROR_RESPONSE_BEAN;");
        method.addBodyLine("}");
        method.addBodyLine("int updateCount = this." + businessFieldName + ".update(" + beanParamName + ");");
        method.addBodyLine("if(updateCount >0 ){");
        method.addBodyLine("return AjaxResponseBean.Const.SUCCESS_RESPONSE_BEAN;");
        method.addBodyLine("}");
        method.addBodyLine("return AjaxResponseBean.Const.ERROR_RESPONSE_BEAN;");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("logger.error(\"更新异常\" + e.getMessage());");
        method.addBodyLine("return AjaxResponseBean.getErrorResponseBean(\"更新异常\" + e.getMessage());");
        method.addBodyLine("}");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        
        
        String innerClassFullName = controllerType.getFullyQualifiedName() + "." + beanName +"List";
        InnerClass innerClass = new InnerClass(new FullyQualifiedJavaType(innerClassFullName));
        innerClass.setVisibility(JavaVisibility.DEFAULT);
        innerClass.setStatic(true);
        
        // add List<Bean> 内部类
        String beanListFieldName = lowerFirstChar(beanName + "List");
        FullyQualifiedJavaType beanListType = new FullyQualifiedJavaType("java.util.List<"+ beanName +">");
        Field beanList = new Field();
        beanList.setVisibility(JavaVisibility.PRIVATE);
        beanList.setType(beanListType);
        beanList.setName(beanListFieldName);
        commentGenerator.addFieldComment(beanList, introspectedTable);
        innerClass.addField(beanList);
        
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(beanListType);
        method.setName("get"+ beanName + "List");
        method.addBodyLine("return " + beanListFieldName + ";");
        innerClass.addMethod(method);
        
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set"+ beanName + "List");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<"+ beanName +">"), beanListFieldName)); 
        method.addBodyLine("this."+ beanListFieldName + " = "+ beanListFieldName + ";");
        innerClass.addMethod(method);
        
        topLevelClass.addInnerClass(innerClass);
        
        // add batchUpdateSaveBean
        method = new Method();
        method.addAnnotation("@RequestMapping(value = \"batchUpdateSave" + beanName + ".do\")");
        method.addAnnotation("@ResponseBody");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(AjaxResponseBean.class.getName()));
        method.setName("batchUpdateSave" + beanName);
        param1 = new Parameter(new FullyQualifiedJavaType(innerClassFullName), beanListFieldName);
        param1.addAnnotation("@ModelAttribute ");
        method.addParameter(param1); 
        // 方法body
        method.addBodyLine("try {");
        method.addBodyLine("if(" + beanListFieldName + " != null && CollectionUtil.isNotNull(" + beanListFieldName + ".get"+ beanName + "List())){");
        method.addBodyLine("int updateCount = this." + businessFieldName + ".update(" + beanListFieldName + ".get"+ beanName + "List());");
        method.addBodyLine("if(updateCount >0 ){");
        method.addBodyLine("return AjaxResponseBean.Const.SUCCESS_RESPONSE_BEAN;");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("return AjaxResponseBean.Const.ERROR_RESPONSE_BEAN;");
        method.addBodyLine("} catch (Exception e) {");
        method.addBodyLine("logger.error(\"批量更新异常\" + e.getMessage());");
        method.addBodyLine("return AjaxResponseBean.getErrorResponseBean(\"批量更新异常\" + e.getMessage());");
        method.addBodyLine("}");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        
        GeneratedJavaFile gjf2 = new GeneratedJavaFile(topLevelClass,
			context.getJavaClientGeneratorConfiguration().getTargetProject(),
            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
            context.getJavaFormatter());
        
        gifList.add(gjf2);
        
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

	public static String upperFirstChar(String oldString) {
		if (oldString == null || "".equals(oldString.trim())) {
			return oldString;
		}
		String target = new StringBuffer()
				.append(oldString.substring(0, 1).toUpperCase())
				.append(oldString.substring(1)).toString();
		return target;
	}
}
