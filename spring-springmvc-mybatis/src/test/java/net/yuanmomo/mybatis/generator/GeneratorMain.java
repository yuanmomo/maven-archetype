/**
 * Project Name : Tools
 * File Name    : MyBatisGeneratorTest.java
 * Package Name : net.yuanmomo.tools.db.orm.mybatis
 * Created on   : 2013-12-24下午5:13:58
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.mybatis.generator;


/**
 * ClassName : MyBatisGeneratorTest Function : TODO ADD FUNCTION. Reason : TODO
 * ADD REASON. Date : 2013-12-24 下午5:13:58
 * 
 * @author : Hongbin Yuan
 * @version
 * @since JDK 1.6
 * @see
 */
public class GeneratorMain {
	public static void main(String args[]) {
		MyBatisGeneratorTool.generate("src\\test\\resources\\mybatis\\generatorConfig.xml");
	}
}
