package net.yuanmomo.framework.util.session;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Hongbin.Yuan on 2015-11-14 23:41.
 */

public class HttpThreadLocal {
    //创建线程局部变量session，用来保存 HTTP 请求中 的Session
    public static final ThreadLocal<Map<String,Object>> THREAD_LOCAL= new ThreadLocal();

    private static final String SESSION = "1";


    public static void setSession(HttpSession session) {
        set(SESSION,session);
    }

    public static HttpSession getSession() {
        return (HttpSession) get(SESSION);
    }
    public static void remove(){
        THREAD_LOCAL.remove();
    }

    /**
     * 设置值.
     *
     * @param key
     * @return
     */
    private static Object get(String key) {
        return THREAD_LOCAL.get() == null ? null : THREAD_LOCAL.get().get(key);
    }

    /**
     * 取值.
     *
     * @param key
     * @param object
     */
    private static void set(String key, Object object) {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(new ConcurrentHashMap<String, Object>());
        }
        if (object == null) {
            return;
        }
        THREAD_LOCAL.get().put(key, object);
    }
}
