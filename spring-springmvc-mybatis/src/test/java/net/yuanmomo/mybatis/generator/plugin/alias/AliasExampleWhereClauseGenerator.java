
/**
 * Project Name : Tools
 * File Name    : AliasExampleWhereClauseGenerator.java
 * Package Name : net.yuanmomo.tools.db.orm.mybatis.generator.plugin.alias
 * Created on   : 2014-2-18上午11:49:55
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.mybatis.generator.plugin.alias;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * ClassName : AliasExampleWhereClauseGenerator 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-2-18 上午11:49:55 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
public class AliasExampleWhereClauseGenerator{

	private boolean isForUpdateByExample;
	
	public static final String ALIAS_EXAMPLE_WHERE_CLAUSE_ID= "Alias_Example_Where_Clause";
	public static final String ALIAS_UPDATE_BY_EXAMPLE_WHERE_CLAUSE_ID= "Alias_Update_By_Example_Where_Clause";

	public AliasExampleWhereClauseGenerator(boolean isForUpdateByExample) {
		this.isForUpdateByExample = isForUpdateByExample;
	}

	public void addElements(XmlElement parentElement,IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

		if (isForUpdateByExample) {
			answer.addAttribute(new Attribute(
					"id", ALIAS_UPDATE_BY_EXAMPLE_WHERE_CLAUSE_ID)); //$NON-NLS-1$
		} else {
			answer.addAttribute(new Attribute(
					"id", ALIAS_EXAMPLE_WHERE_CLAUSE_ID)); //$NON-NLS-1$
		}

		introspectedTable.getContext().getCommentGenerator().addComment(answer);

		XmlElement whereElement = new XmlElement("where"); //$NON-NLS-1$
		answer.addElement(whereElement);

		XmlElement outerForEachElement = new XmlElement("foreach"); //$NON-NLS-1$
		if (isForUpdateByExample) {
			outerForEachElement.addAttribute(new Attribute(
					"collection", "example.oredCriteria")); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			outerForEachElement.addAttribute(new Attribute(
					"collection", "oredCriteria")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		outerForEachElement.addAttribute(new Attribute("item", "criteria")); //$NON-NLS-1$ //$NON-NLS-2$
		outerForEachElement.addAttribute(new Attribute("separator", "or")); //$NON-NLS-1$ //$NON-NLS-2$
		whereElement.addElement(outerForEachElement);

		XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
		ifElement.addAttribute(new Attribute("test", "criteria.valid")); //$NON-NLS-1$ //$NON-NLS-2$
		outerForEachElement.addElement(ifElement);

		XmlElement trimElement = new XmlElement("trim"); //$NON-NLS-1$
		trimElement.addAttribute(new Attribute("prefix", "(")); //$NON-NLS-1$ //$NON-NLS-2$
		trimElement.addAttribute(new Attribute("suffix", ")")); //$NON-NLS-1$ //$NON-NLS-2$
		trimElement.addAttribute(new Attribute("prefixOverrides", "and")); //$NON-NLS-1$ //$NON-NLS-2$

		ifElement.addElement(trimElement);

		trimElement.addElement(getMiddleForEachElement(null,introspectedTable));

		for (IntrospectedColumn introspectedColumn : introspectedTable
				.getNonBLOBColumns()) {
			if (stringHasValue(introspectedColumn.getTypeHandler())) {
				trimElement
						.addElement(getMiddleForEachElement(introspectedColumn,introspectedTable));
			}
		}

		parentElement.addElement(answer);
	}

	private XmlElement getMiddleForEachElement(
			IntrospectedColumn introspectedColumn,IntrospectedTable introspectedTable) {
		// 得到当前表的名字
		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
		if(tableName !=null){
			tableName = tableName.toUpperCase().trim();
		}
		
		StringBuilder sb = new StringBuilder();
		String criteriaAttribute;
		boolean typeHandled;
		String typeHandlerString;
		if (introspectedColumn == null) {
			criteriaAttribute = "criteria.criteria"; //$NON-NLS-1$
			typeHandled = false;
			typeHandlerString = null;
		} else {
			sb.setLength(0);
			sb.append("criteria."); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.append("Criteria"); //$NON-NLS-1$
			criteriaAttribute = sb.toString();

			typeHandled = true;

			sb.setLength(0);
			sb.append(",typeHandler="); //$NON-NLS-1$
			sb.append(introspectedColumn.getTypeHandler());
			typeHandlerString = sb.toString();
		}

		XmlElement middleForEachElement = new XmlElement("foreach"); //$NON-NLS-1$
		middleForEachElement.addAttribute(new Attribute(
				"collection", criteriaAttribute)); //$NON-NLS-1$
		middleForEachElement.addAttribute(new Attribute("item", "criterion")); //$NON-NLS-1$ //$NON-NLS-2$

		XmlElement chooseElement = new XmlElement("choose"); //$NON-NLS-1$
		middleForEachElement.addElement(chooseElement);

		XmlElement when = new XmlElement("when"); //$NON-NLS-1$
		when.addAttribute(new Attribute("test", "criterion.noValue")); //$NON-NLS-1$ //$NON-NLS-2$
		// 添加别名支持
		when.addElement(new TextElement("and "+ tableName +".${criterion.condition}")); //$NON-NLS-1$
		chooseElement.addElement(when);

		when = new XmlElement("when"); //$NON-NLS-1$
		when.addAttribute(new Attribute("test", "criterion.singleValue")); //$NON-NLS-1$ //$NON-NLS-2$
		sb.setLength(0);
		sb.append("and "+ tableName +".${criterion.condition} #{criterion.value"); //$NON-NLS-1$
		if (typeHandled) {
			sb.append(typeHandlerString);
		}
		sb.append('}');
		when.addElement(new TextElement(sb.toString()));
		chooseElement.addElement(when);

		when = new XmlElement("when"); //$NON-NLS-1$
		when.addAttribute(new Attribute("test", "criterion.betweenValue")); //$NON-NLS-1$ //$NON-NLS-2$
		sb.setLength(0);
		sb.append("and "+ tableName +".${criterion.condition} #{criterion.value"); //$NON-NLS-1$
		if (typeHandled) {
			sb.append(typeHandlerString);
		}
		sb.append("} and #{criterion.secondValue"); //$NON-NLS-1$
		if (typeHandled) {
			sb.append(typeHandlerString);
		}
		sb.append('}');
		when.addElement(new TextElement(sb.toString()));
		chooseElement.addElement(when);

		when = new XmlElement("when"); //$NON-NLS-1$
		when.addAttribute(new Attribute("test", "criterion.listValue")); //$NON-NLS-1$ //$NON-NLS-2$
		when.addElement(new TextElement("and "+ tableName +".${criterion.condition}")); //$NON-NLS-1$
		XmlElement innerForEach = new XmlElement("foreach"); //$NON-NLS-1$
		innerForEach
				.addAttribute(new Attribute("collection", "criterion.value")); //$NON-NLS-1$ //$NON-NLS-2$
		innerForEach.addAttribute(new Attribute("item", "listItem")); //$NON-NLS-1$ //$NON-NLS-2$
		innerForEach.addAttribute(new Attribute("open", "(")); //$NON-NLS-1$ //$NON-NLS-2$
		innerForEach.addAttribute(new Attribute("close", ")")); //$NON-NLS-1$ //$NON-NLS-2$
		innerForEach.addAttribute(new Attribute("separator", ",")); //$NON-NLS-1$ //$NON-NLS-2$
		sb.setLength(0);
		sb.append("#{listItem"); //$NON-NLS-1$
		if (typeHandled) {
			sb.append(typeHandlerString);
		}
		sb.append('}');
		innerForEach.addElement(new TextElement(sb.toString()));
		when.addElement(innerForEach);
		chooseElement.addElement(when);

		return middleForEachElement;
	}
}
