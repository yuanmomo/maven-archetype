package net.yuanmomo.framework.util.session;

import javax.servlet.http.HttpSession;

/**
 * Created by Hongbin.Yuan on 2015-11-14 23:41.
 */

public class HttpThreadLocal {
    //创建线程局部变量session，用来保存 HTTP 请求中 的Session
    public static final ThreadLocal<HttpSession> sessionLocal = new ThreadLocal();


    public static void setSession(HttpSession session) {
        sessionLocal.set(session);
    }

    public static HttpSession getSession() {
        return sessionLocal.get();
    }
    public static void remove(){
        sessionLocal.remove();
    }
}
