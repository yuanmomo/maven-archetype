package net.yuanmomo.framework.util.session;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Hongbin.Yuan on 2015-11-14 23:41.
 */

public class HttpThreadLocal {
    // 存储当前线程绑定的变量, 可以构造多个 key 在当前线程绑定多个变量。
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static final String TEMP_KEY = "tempKey";

    public static void setSession(Object object) {
        set(TEMP_KEY,object);
    }

    public static Object getSession() {
        return get(TEMP_KEY);
    }



    public static void remove() {
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

