/** 
 * Project Name : dooliuManager
 * Package Name : com.clou.douliu.server.controller
 * Created on   : 2013-8-14下午7:20:26
 * File Name    : GlobalController.java
 *
 * Author       : Hongbin Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.util.controller;

import net.yuanmomo.util.generator.AjaxResponseBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName : GlobalController
 * Function : 进行页面的全局跳转控制等等，比如跳转到登录页面，跳转到后台管理页面等等
 * Reason : 在处理ajax的请求是，服务器端不能进行客户端的跳转，需要传给客户端一个标志，又客户端控制跳转。
 * Date : 2013-8-14
 * 下午7:20:26
 *
 * @author : Hongbin Yuan
 * @see
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/")
public class GlobalController {
	/**
	 * toLoginPage:返回客户端一个用户登录超时的信息，跳转至login.html. <br/>
	 * 
	 * @author Hongbin Yuan
	 * @param request
	 * @param response
	 * @return <code>AjaxResponseBean</code> 一个用户超时的ajax response bean
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "toLoginPage.do")
	@ResponseBody
	public AjaxResponseBean toLoginPage(HttpServletRequest request,
			HttpServletResponse response) {
		return AjaxResponseBean.Const.TIMEOUT_RESPONSE_BEAN;
	}
}
