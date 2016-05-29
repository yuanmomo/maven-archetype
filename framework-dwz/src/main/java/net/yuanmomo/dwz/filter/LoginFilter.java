package net.yuanmomo.dwz.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yuanmomo.dwz.resources.GlobalParam;

import net.yuanmomo.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class LoginFilter extends OncePerRequestFilter {
	private static List<String>  notFilter = null;
	
	/**
	 * initFilterBean:. <br/>
	 *
	 * @author Hongbin Yuan
	 * @throws ServletException
	 * @see org.springframework.web.filter.GenericFilterBean#initFilterBean()
	 */
	@Override
	protected void initFilterBean() throws ServletException {
		String notFilterStr = this.getFilterConfig().getInitParameter("notFilter");
		if(StringUtils.isNotBlank(notFilterStr)){
			notFilter = CollectionUtil.toList( notFilterStr , ";");
		}
	}

	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Object obj = request.getSession().getAttribute(GlobalParam.sessionUserKey);
		if (obj != null) {
			filterChain.doFilter(request, response);
			return;
		}
		String uri = request.getRequestURI();
		boolean doFilter = true;
		for (String s : notFilter) {
			if (uri != null && !uri.endsWith(s) && !s.equals(uri)) {
				continue;
			}
			doFilter = false;
			break;
		}
		if (!doFilter) {
			filterChain.doFilter(request, response);
			return;
		}
		if (isAjax(request)) {
			request.getRequestDispatcher( "/toLoginPage.do").forward(
					request, response);
		} else{
			response.sendRedirect(request.getContextPath() + "/login.html");
		}
	}

	// 工具类判断是否ajax请求
	public static boolean isAjax(HttpServletRequest request) {
		if (request != null && "XMLHttpRequest".equalsIgnoreCase(request
		.getHeader("X-Requested-With"))){
			return true;
		}
		return false;
	}
}
