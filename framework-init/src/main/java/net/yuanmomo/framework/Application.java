package net.yuanmomo.framework;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Created by Hongbin.Yuan on 2017-07-05 13:58.
 */
@ComponentScan(basePackages = "net.yuanmomo.framework")
public class Application implements WebApplicationInitializer {

    private static ApplicationContext applicationContext = null;


    @Override
    public void onStartup(ServletContext container) {
        ServletRegistration.Dynamic registration =
                container.addServlet("example", new DispatcherServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping("/example/*");
    }

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> void register(Class<T> clazz, Object bean) {
        DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext
                .getAutowireCapableBeanFactory();
        fty.registerResolvableDependency(clazz, bean);
    }
}

