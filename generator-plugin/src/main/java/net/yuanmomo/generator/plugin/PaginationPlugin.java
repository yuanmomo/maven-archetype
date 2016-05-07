/** 
 * Project Name : dooliuManager
 * Package Name : com.clou.douliu.server.mybatis.generator
 * Created on   : 2013-9-6下午3:08:21
 * File Name    : PaginationPlugin.java
 *
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.generator.plugin;
/**
 * ClassName : PaginationPlugin 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2013-9-6 下午3:08:21 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author Hongbin Yuan
 * @date 2011-11-30 下午08:36:11
 */
public class PaginationPlugin extends PluginAdapter {
    /**
     * modelExampleClassGenerated: bean类的criteria类中加入start和count两个属性. <br/>
     *
     * @author Hongbin Yuan
     * @param topLevelClass
     * @param introspectedTable
     * @return
     * @see PluginAdapter#modelExampleClassGenerated(TopLevelClass, IntrospectedTable)
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        // add field, getter, setter for limit start clause
        this.addFiled(topLevelClass, introspectedTable,
                FullyQualifiedJavaType.getIntInstance(), "start", "-1");
        // add field, getter, setter for limit count clause
        this.addFiled(topLevelClass, introspectedTable,
                FullyQualifiedJavaType.getIntInstance(), "count", "-1");
        return super.modelExampleClassGenerated(topLevelClass,
                introspectedTable);
    }

    /**
     * sqlMapSelectByExampleWithoutBLOBsElementGenerated: 写入xml文件. <br/>
     *
     * @author Hongbin Yuan
     * @param element
     * @param introspectedTable
     * @return
     * @see PluginAdapter#sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement, IntrospectedTable)
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        // 向mapper xml中添加limit的start字句
        XmlElement limitStartElement = new XmlElement("if"); //$NON-NLS-1$
        limitStartElement.addAttribute(new Attribute("test", "start &gt;= 0 ")); //$NON-NLS-1$ //$NON-NLS-2$
        limitStartElement.addElement(new TextElement("limit ${start}"));
        element.addElement(limitStartElement);

        // 向mapper xml中添加limit的count字句
        XmlElement limitEndElement = new XmlElement("if"); //$NON-NLS-1$
        limitEndElement.addAttribute(new Attribute("test", "count &gt;= 0 ")); //$NON-NLS-1$ //$NON-NLS-2$
        limitEndElement.addElement(new TextElement(",${count}"));
        element.addElement(limitEndElement);

        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,
                introspectedTable);
    }

    /**
     * addFiled: 添加一个类的成员变量，同时添加相应的 setter 和 getter. <br/>
     *
     * @author Hongbin Yuan
     * @param topLevelClass
     * @param introspectedTable
     * @param fieldType
     * @param name
     * @param initializationString
     * @since JDK 1.6
     */
    private void addFiled(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable,
            FullyQualifiedJavaType fieldType, String name,
            String initializationString) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        // 添加属性
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(fieldType);
        field.setName(name);
        field.setInitializationString(initializationString);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);

        // 添加getter和setter时候方法大写属性首字母
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);

        // 添加setter方法
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(fieldType, name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        // 添加getter方法
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(fieldType);
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }
    /**
     * This plugin is always valid - no properties are required
     */
    public boolean validate(List<String> warnings) {
        return true;
    }
}
