
/**
 * Project Name : Tools
 * File Name    : DAOPlugin.java
 * Package Name : net.yuanmomo.tools.db.orm.mybatis.generator.plugin.dao
 * Created on   : 2014-2-17下午8:23:27
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.mybatis.generator.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
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
public class DAOPlugin extends PluginAdapter {
	private static String typeName = "DAO";
	
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
		CommentGenerator commentGenerator = context.getCommentGenerator();

		// 设置文件类型,DAO文件,java 接口文件
		String  javaMapperName = introspectedTable.getMyBatis3JavaMapperType();
		String typeNameProp = this.getProperties().getProperty("typeName");
		if(typeNameProp == null || "".equals(typeNameProp.trim())){
			typeNameProp = typeName;
		}
		javaMapperName = javaMapperName.replaceAll("Mapper$",typeNameProp);
		
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(javaMapperName);
		Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        String rootInterface = introspectedTable
            .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
                    rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }
		
        GeneratedJavaFile gjf = new GeneratedJavaFile(interfaze,
			context.getJavaClientGeneratorConfiguration().getTargetProject(),
            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
            context.getJavaFormatter());
        List<GeneratedJavaFile>  gifList = new ArrayList<GeneratedJavaFile>();
        
        gifList.add(gjf);
        
		return gifList;
	}
	/**
	 * contextGenerateAdditionalXmlFiles:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param introspectedTable
	 * @return
	 * @see org.mybatis.generator.api.PluginAdapter#contextGenerateAdditionalXmlFiles(org.mybatis.generator.api.IntrospectedTable)
	 */
	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(
			IntrospectedTable introspectedTable) {
		// 设置文件类型,DAO 的xml文件, 原命名空间,用设置的值替换
		String  xmlMapperNamespace = introspectedTable.getMyBatis3SqlMapNamespace();
		String typeNameProp = this.getProperties().getProperty("typeName");
		if(typeNameProp == null || "".equals(typeNameProp.trim())){
			typeNameProp = typeName;
		}
		xmlMapperNamespace = xmlMapperNamespace.replaceAll("Mapper$",typeNameProp);
		
		// 创建mapper文件,
		Document document = new Document(
	                XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
	                XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
		// 创建根 root元素
		XmlElement root = new XmlElement("mapper"); //$NON-NLS-1$
        root.addAttribute(new Attribute("namespace", //$NON-NLS-1$
        		xmlMapperNamespace));
        
        context.getCommentGenerator().addRootComment(root);
        document.setRootElement(root);

        // 像root添加一个空元素
        root.addElement(new TextElement("\n"));
        
        String xmlMapperName = introspectedTable.getMyBatis3XmlMapperFileName();
        xmlMapperName = xmlMapperName.replaceAll("Mapper.xml$",typeNameProp+".xml");
		GeneratedXmlFile gxf = new GeneratedXmlFile(document,
				xmlMapperName, introspectedTable.getMyBatis3XmlMapperPackage(),
                context.getSqlMapGeneratorConfiguration().getTargetProject(),
                true, context.getXmlFormatter());
		List<GeneratedXmlFile>  gxfList = new ArrayList<GeneratedXmlFile>();
        
		gxfList.add(gxf);
        
		return gxfList;
	}

	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
			IntrospectedTable introspectedTable) {
//		AbstractXmlGenerator xmlGenerator = new XMLMapperGenerator();
//		xmlGenerator.setContext(context);
//		xmlGenerator.setIntrospectedTable(introspectedTable);
//		xmlGenerator.setProgressCallback(new NullProgressCallback());
//		xmlGenerator.setWarnings(new ArrayList<String>());
//		
//		GeneratedXmlFile sqlMapNew = new GeneratedXmlFile(xmlGenerator.getDocument(),
//				sqlMap.getFileName(), sqlMap.getTargetPackage(),
//				sqlMap.getTargetProject(),
//                false, context.getXmlFormatter());
//		
//		sqlMap = sqlMapNew;
		return true;
	}
}
