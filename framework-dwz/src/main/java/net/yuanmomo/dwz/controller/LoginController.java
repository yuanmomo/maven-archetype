package net.yuanmomo.dwz.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.yuanmomo.dwz.business.LoginBusiness;
import net.yuanmomo.dwz.log.LogFactory;
import net.yuanmomo.dwz.resources.GlobalParam;

import net.yuanmomo.util.generator.AjaxResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired private Producer captchaProducer = null;  
	@Autowired private LoginBusiness loginBusiness = null;
	
	/**
	 * login:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @param request
	 * @param response
	 * @return
	 * @since JDK 1.6
	 */
	@RequestMapping(value = "login.do")
	@ResponseBody
	public AjaxResponseBean login(
			@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			@RequestParam("verificationCode") String verificationCode,
			HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();  
	        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 
	        // 清空session中的验证码
	        session.setAttribute(Constants.KAPTCHA_SESSION_KEY,"");
	        
	        if(!code.equalsIgnoreCase(verificationCode)){
	        	return AjaxResponseBean.getErrorResponseBean("登录错误,验证码错误!");
	        }
			
			Object result = this.loginBusiness.login(userName, password);
			if(result != null){ // 登录成功
				session.setAttribute(GlobalParam.sessionUserKey, result); // 存放到session
				return AjaxResponseBean.getReturnValueResponseBean(result);
			}
			return AjaxResponseBean.Const.ERROR_RESPONSE_BEAN;
		} catch (Exception e) {
			LogFactory.systemLog.error("登录错误" + e.getMessage());
			return AjaxResponseBean.getErrorResponseBean("登录错误:" + e.getMessage());
		}
	}
	

	/**
	 * logout: 注销用户登录. <br/>
	 * 
	 * @author Hongbin Yuan
	 * @param request
	 * @param response
	 * @return
	 * @since JDK 1.6
	 */
	@RequestMapping(value = "logout.do")
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) {
		// 清除session中保存的信息
		request.getSession().invalidate();
		try {
			response.sendRedirect(request.getContextPath() + "/login.html");
		} catch (IOException e) {
			
		}
		return null;
	}
	
	@RequestMapping("/image.do")  
    public String getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		// Set to expire far in the past.
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");

		// return a jpeg
		response.setContentType("image/jpeg");

		// create the text for the image
		String capText = captchaProducer.createText();

		// store the text in the session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,
				capText);

		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);

		ServletOutputStream out = response.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null; 
    }  
}
