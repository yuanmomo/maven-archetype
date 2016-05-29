package net.yuanmomo.framework.util.session;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Hongbin.Yuan on 2015-11-14 23:48.
 */

public class SessionFilter implements Filter {

    public static final String SESSION_USER_KEY = "SessionUser";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req =(HttpServletRequest) servletRequest;

        SessionUser sUser = new SessionUser(1,"yuanmomo"); // 设置用户ID，和用户名。
        req.getSession().setAttribute(SessionFilter.SESSION_USER_KEY, sUser);

        // 将 session 添加到 HttpSessionThreadLocal，方便在 记录操作日志的时候获取。
        HttpThreadLocal.setSession(req.getSession(true));
        try{
            filterChain.doFilter(servletRequest, servletResponse);
        }finally{
            HttpThreadLocal.remove();
        }
    }

    @Override
    public void destroy() {

    }
}
