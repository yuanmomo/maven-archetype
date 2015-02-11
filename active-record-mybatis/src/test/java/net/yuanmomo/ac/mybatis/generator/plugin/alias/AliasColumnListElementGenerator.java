/**
 * Project Name : Tools
 * File Name    : BaseColumnListElementGenerator.java
 * Package Name : net.yuanmomo.ac.tools.db.orm.mybatis.generator.plugin.alias
 * Created on   : 2013-12-25上午12:51:03
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.ac.mybatis.generator.plugin.alias;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * ClassName : AliasColumnListElementGenerator 
 * Function : TODO ADD FUNCTION.
 * Reason : TODO ADD REASON. Date : 2013-12-25 上午12:51:03
 * 
 * @author : Hongbin Yuan
 * @version
 * @since JDK 1.6
 * @see
 */
public class AliasColumnListElementGenerator {
	private String resultMapId = "Alias_Column_List";
	
	private int tabLength = 4;
	
	/**
	 * addElements: 在当前的parentElement元素中添加别名列元素. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param parentElement			父元素，新的elements将作为子元素添加到该元素
	 * @param introspectedTable		当前表的信息
	 * @since JDK 1.6
	 */
	public void addElements(XmlElement parentElement,IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

		answer.addAttribute(new Attribute("id",resultMapId));

		Iterator<IntrospectedColumn> iter = introspectedTable
				.getNonBLOBColumns().iterator();
		
		// 得到当前表的名字
		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime().toUpperCase();
		
		// 格式化输出，找到别名列最大的长度
		Iterator<IntrospectedColumn> iterForMaxLength = introspectedTable
				.getNonBLOBColumns().iterator();
		int maxLenght = 0;
		while(iterForMaxLength.hasNext()){
			IntrospectedColumn column = iterForMaxLength.next();
			String columnAlias = tableName +"."+ MyBatis3FormattingUtilities.getSelectListPhrase(column).toUpperCase();
			if( columnAlias.length()> maxLenght){
				maxLenght = columnAlias.length();
			}
		}
		// 修改最大长度为4的整倍数
		maxLenght = ( (maxLenght / this.tabLength) +1) * this.tabLength;

		StringBuilder sb = new StringBuilder();
		// 当前别名行
		StringBuilder columnAliasRow= null;
		// 当前别名列的实际长度
		int rowLength = 0;
		while (iter.hasNext()) {
			IntrospectedColumn column = iter.next();
			columnAliasRow = new StringBuilder();
			columnAliasRow.append(tableName +"."+ MyBatis3FormattingUtilities.getSelectListPhrase(column).toUpperCase());
			// 循环添加\t，直到该行的长度大于别名列的最大长度
			rowLength = ( columnAliasRow.length() / this.tabLength) * this.tabLength;
			while( rowLength < maxLenght){
				columnAliasRow.append("\t");
				rowLength += this.tabLength;
			}
			columnAliasRow.append("AS\t").append(tableName +"_"+ MyBatis3FormattingUtilities.getSelectListPhrase(column).toUpperCase());
			
			if(sb.length() > 0){
				sb.append("\t");
			}
			sb.append(columnAliasRow.toString());
			if (iter.hasNext()) {
				sb.append(",\n"); //$NON-NLS-1$
			}
		}

		if (sb.length() > 0) {
			answer.addElement((new TextElement(sb.toString())));
		}

		parentElement.addElement(answer);
	}
}
