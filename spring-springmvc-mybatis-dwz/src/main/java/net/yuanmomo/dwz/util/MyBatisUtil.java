
/**
 * Project Name : Tools
 * File Name    : MyBatisUtil.java
 * Package Name : net.yuanmomo.tools.db.orm.mybatis
 * Created on   : 2014-4-25上午10:15:37
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.dwz.util;

import java.util.List;

import org.apache.ibatis.exceptions.TooManyResultsException;

/**
 * ClassName : MyBatisUtil 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-4-25 上午10:15:37 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.7
 * @see 	 
 */
public class MyBatisUtil {
	/**
	 *	mybatis自带的条件查询返回的是一个list, 但是某些时候我们需要的是返回单条记录. <br/>
	 *	该方法从mybatis的查询结果list中,取出为一个的第一条记录	
	 * @author Hongbin Yuan
	 * @param list		
	 * @return	list 为空或者size为0,返回空; 
	 * 			list 元素超过一个.
	 * 			list 只包含单独一个元素, 返回该元素
	 * @throws Exception	list 元素超过一个.
	 */
	public static <T> T getOneFromList(List<T> list) throws Exception{
		if(list == null || list.isEmpty()){
			return null;
		}
		if(list.size() > 1){
			 throw new TooManyResultsException("Expected one result (or null) to be returned, but found: " + list.size());
		}
		return list.get(0);
	}
}
