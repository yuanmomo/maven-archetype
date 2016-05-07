/**
 * Project Name : Tools
 * File Name    : AliasPlugin.java
 * Package Name : net.yuanmomo.tools.db.orm.mybatis.generator.plugin
 * Created on   : 2013-12-24下午10:09:08
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.mybatis.generator.plugin.alias;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * ClassName : AliasPlugin 
 * Function  : TODO 别名插件,为每张表生成一个别名列和别名的resultMap，供别的mapper.xml文件引用，实现简单的关联查询. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2013-12-24 下午10:09:08 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	
 * Plugin Lifecycle
 *	Plugins have a lifecycle. Plugins are created during the initialization of the code generation process <br/>
 *	and are called, in order, in different phases of the process. The following list shows the basic lifecycle of a plugin: <br/>
 *		
 *		1. Plugin created through the default constructor <br/>
 *		2. setContext method called <br/>
 *		3. setProperties method called <br/>
 *		4. validate method called. If this method returns false, then no further methods in the plugin will be called <br/>
 *		5. For each table in the configuration: <br/>
 *			5.1.1 initialized method called <br/>
 *			5.1.2 Java Client Methods:1,2 <br/>
 *				5.1.2.1 clientXXXMethodGenerated(Method, TopLevelClass, IntrospectedTable) - these methods are called as each method of a Java client implementation class is generated.
 *				5.1.2.2 clientXXXMethodGenerated(Method, Interface, IntrospectedTable) - these methods are called as each method of the Java client interface is generated.
 *				5.1.2.3 clientGenerated(Interface, TopLevelClass, IntrospectedTable) method called
 *			5.1.3 Model Methods:1
 *				5.1.3.1 modelFieldGenerated, modelGetterMethodGenerated, modelSetterMethodGenerated for each field in the class
 *				5.1.3.2 modelExampleClassGenerated(TopLevelClass, IntrospectedTable)
 *				5.1.3.3 modelPrimaryKeyClassGenerated(TopLevelClass, IntrospectedTable)
 *				5.1.3.4 modelBaseRecordClassGenerated(TopLevelClass, IntrospectedTable)
 *				5.1.3.5 modelRecordWithBLOBsClassGenerated(TopLevelClass, IntrospectedTable)
 *			5.1.4 SQL Map Methods:1
 *				5.1.4.1 sqlMapXXXElementGenerated(XmlElement, IntrospectedTable) - these methods are called as each element of the SQL map is generated
 *				5.1.4.2 sqlMapDocumentGenerated(Document, IntrospectedTable)
 *				5.1.4.3 sqlMapDocument(GeneratedXmlFile, IntrospectedTable)
 *			5.1.5 contextGenerateAdditionalJavaFiles(IntrospectedTable) method called
 *			5.1.6 contextGenerateAdditionalXmlFiles(IntrospectedTable) method called
 *		6. contextGenerateAdditionalJavaFiles() method called
 *		7. contextGenerateAdditionalXmlFiles() method called
 *		
 *	Notes:
 *		1 - These methods will be called by the packaged code generators. If you supply a custom code generator, then these methods will only be called if the custom code generator calls them.
 *		2 - The Java client methods will only be called is a Java client generator is configured. 
 * 
 */



public class AliasPlugin extends PluginAdapter {

	/**
	 * validate: validate method called. 
	 * If this method returns false,<br />
	 * then no further methods in the plugin will be called. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param arg0
	 * @return
	 * @see org.mybatis.generator.api.Plugin#validate(java.util.List)
	 */
	public boolean validate(List<String> arg0) {
		return true;
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document,
			IntrospectedTable introspectedTable) {
			XmlElement  xmlElement = document.getRootElement();
			// 生成别名的AliasResultMap
			new AliasResultMapWithoutBLOBsElementGenerator().addElements(xmlElement, introspectedTable);
			
			// 生成别名的 AliasColumnList
			new AliasColumnListElementGenerator().addElements(xmlElement, introspectedTable);
			
			new AliasExampleWhereClauseGenerator(true).addElements(xmlElement,introspectedTable);
			
			new AliasExampleWhereClauseGenerator(false).addElements(xmlElement,introspectedTable);
		return true;
	}
	
	/**
	 * getElementById: 在指定的xmlElement的一级子元素中查找id值为指定value的Element. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param xmlElement	指定的xmlElement元素  <br/>
	 * @param value			查找id值为指定value的Element  <br/>
	 * @return
	 * @since JDK 1.6
	 */
	public XmlElement getElementById(XmlElement xmlElement,String value){
		if(xmlElement == null){
			return null;
		}
		if(value == null || "".equals(value.trim())){
			return xmlElement;
		}
		List<Element> elementList = xmlElement.getElements();
		if(elementList != null && elementList.size() > 0){
			for(Element e : elementList){
				if(e instanceof XmlElement){
					XmlElement eXML = (XmlElement)e;
					List<Attribute> eXMLAttrList = eXML.getAttributes();
					for(Attribute a : eXMLAttrList){
						if(a!=null && "id".equals(a.getName()) && value.equals(a.getValue())){
							return eXML;
						}
					}
				}
			}
		}
		return null;
	}
}
