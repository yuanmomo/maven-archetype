
/**
 * Project Name : azq-manager
 * File Name    : UserAccountGenerator.java
 * Package Name : com.azq.manager
 * Created on   : 2014-6-9下午3:58:14
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.framework.bean.MUser;
import net.yuanmomo.framework.util.MD5;


/**
 * ClassName : UserAccountGenerator 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : 2014-6-9 下午3:58:14 
 *
 * @author   : Hongbin Yuan
 * @version  
 * @since      JDK 1.6
 * @see 	 
 */

public class UserAccountGenerator {
	public static void main(String[] args) throws Exception{
		List<MUser> list = new ArrayList<MUser>();
		list.add(new MUser("admin","admin"));
		
		for(int i=0;i<list.size();i++){
			MUser user = list.get(i);
			System.out.println("<bean class=\"net.yuanmomo.dwz.bean.MUser\">");
			System.out.println("	<property name=\"id\" value=\""+i+"\"/>");
			System.out.println("	<property name=\"name\" value=\""+user.getName()+"\" />");
			System.out.println("	<property name=\"password\" value=\""+ MD5.getMD5(user.getPassword())+"\" />");
			System.out.println("	<property name=\"privilege\" value=\"1\" />");
			System.out.println("</bean>");
		}
	}
}
