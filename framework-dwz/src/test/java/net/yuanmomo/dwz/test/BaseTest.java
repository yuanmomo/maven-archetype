package net.yuanmomo.dwz.test;

import com.alibaba.fastjson.JSON;
import net.yuanmomo.util.generator.AjaxResponseBean;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:config/ApplicationContext.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:config/dispatcher-servlet.xml")
})
@Transactional
public class BaseTest {
    protected MockMvc mockMvc;


    protected AjaxResponseBean getJson(MvcResult result) throws UnsupportedEncodingException {
        return JSON.parseObject(result.getResponse().getContentAsString(),AjaxResponseBean.class);
    }
}
