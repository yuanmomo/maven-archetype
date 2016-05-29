package net.yuanmomo.framework.util.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserSessionInterceptor implements HandlerInterceptor{
	private static Logger logger = LoggerFactory.getLogger(UserSessionInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		logger.debug("start to execute UserSessionInterceptor-preHandle.");
		SessionUser sUser = new SessionUser(1,"yuanmomo"); // 设置用户ID，和用户名。
		HttpSession session = request.getSession(true);
		session.setAttribute(SessionFilter.SESSION_USER_KEY, sUser);

        // 将 session 添加到 HttpSessionThreadLocal，方便在 记录操作日志的时候获取。
        HttpThreadLocal.setSession(session);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("start to execute UserSessionInterceptor-postHandle.");
		HttpThreadLocal.remove();
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("start to execute UserSessionInterceptor-afterCompletion.");
	}
	
}
