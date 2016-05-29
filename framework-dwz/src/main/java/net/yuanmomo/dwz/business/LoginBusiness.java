
/**
 * Project Name : azq-manager
 * File Name    : LoginBusiness.java
 * Package Name : com.azq.manager.business.manage
 * Created on   : 2014-4-3下午4:07:01
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.dwz.business;

import net.yuanmomo.dwz.bean.MUser;
import net.yuanmomo.dwz.resources.GlobalParam;
import net.yuanmomo.dwz.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * ClassName : LoginBusiness 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-4-3 下午4:07:01 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */
@Service
public class LoginBusiness{
	/**
	 * login: 登录操作. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param userName
	 * @param password
	 * @return
	 * @since JDK 1.6
	 */
	public Object login(String userName, String password) throws Exception{
		if(StringUtils.isBlank(userName)){
			throw new Exception("用户名不能为空");
		}
		if(StringUtils.isBlank(password)){
			throw new Exception("密码不能为空");
		}
		
		for(MUser user: GlobalParam.adminUserList){
			if(userName.equals(user.getName()) 
					&& MD5.getMD5(password).equals(user.getPassword())){
				return user;
			}
		}
		// 登录失败
		throw new Exception("用户名密码错误");
	}
}
