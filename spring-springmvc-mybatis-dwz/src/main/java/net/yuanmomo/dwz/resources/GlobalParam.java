
/**
 * Project Name : azq-manager
 * File Name    : GlobalParam.java
 * Package Name : com.azq.manager.resource
 * Created on   : 2014-3-19下午5:05:14
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.dwz.resources;

import java.util.List;

import net.yuanmomo.dwz.bean.MUser;


/**
 * ClassName : GlobalParam 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-3-19 下午5:05:14 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
public class GlobalParam {
	public static String sessionUserKey = "user";
	
	
	public static List<MUser> adminUserList;
	
	public static void setAdminUserList(List<MUser> adminUserList) {
		GlobalParam.adminUserList = adminUserList;
	}
}
