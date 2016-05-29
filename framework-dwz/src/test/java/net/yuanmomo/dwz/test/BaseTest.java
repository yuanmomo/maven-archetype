package net.yuanmomo.dwz.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:config/ApplicationContext.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:config/dispatcher-servlet.xml")
})
@Transactional
public class BaseTest {

}
