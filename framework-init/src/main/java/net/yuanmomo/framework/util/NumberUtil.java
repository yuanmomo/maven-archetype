
/**
 * Project Name : azq-manager
 * File Name    : NumberUtils.java
 * Package Name : com.azq.manager.util.json
 * Created on   : 2014-3-22下午3:42:14
 * Author       : Hongbin.Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.framework.util;

import java.math.BigDecimal;

/**
 * ClassName : NumberUtil
 * Date      : 2014-3-22 下午3:42:14
 *
 * @author   : Hongbin.Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
public class NumberUtil {
	public static Double round(Double dou, Integer length) {
		if (dou == null)
			return null;
		if (length == null){
			length = 2;
		}
		BigDecimal b = new BigDecimal(dou);
		Double f1 = b.setScale(length, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}
	
	/**
	 * isPositive: 判断一个数是否是正数. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param obj
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isPositive (Number obj){
		if(obj == null || obj.longValue() <=0){
			return false;
		}
		return true;
	}
	public static boolean isNotPositive (Number obj){
		return !isPositive(obj);
	}
	/**
	 * isNegative: 判断一个数是否是负数. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param obj
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isNegative(Number obj){
		if(obj == null || obj.longValue() >=0){
			return false;
		}
		return true;
	}
	public static boolean isNotNegative (Number obj){
		return !isNegative(obj);
	}
}
