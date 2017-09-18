package net.yuanmomo.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by Hongbin.Yuan on 2017-06-14 11:42.
 */

@Component
public class SpringUtils implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
    }

}
