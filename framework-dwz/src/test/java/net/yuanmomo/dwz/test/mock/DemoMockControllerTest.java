package net.yuanmomo.dwz.test.mock;

import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.JsonPath;
import net.yuanmomo.dwz.business.mybatis.TestBusiness;
import net.yuanmomo.dwz.controller.mybatis.TestController;
import net.yuanmomo.dwz.mybatis.mapper.TestMapper;
import net.yuanmomo.dwz.test.BaseTest;
import net.yuanmomo.util.generator.AjaxResponseBean;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.AopTestUtils;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 该类分两类测试:
 * <p>
 * 2. mock business 类 模拟业务类 来 测试 controller.
 * <p>
 * Created by Hongbin.Yuan on 2016-05-29 18:03.
 */
public class DemoMockControllerTest extends BaseTest {

    @Autowired protected TestController testController = null;

    private MockMvc mockMvc;

    private TestBusiness mockTestBusiness;

    //创建一个模拟器
    private IMocksControl control = null;

    /**
     * 实例化之前,设置 mock 类到实体类。
     */
    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();

//        control = EasyMock.createNiceControl();

//        mockTestBusiness = control.createMock(TestBusiness.class);

//        ReflectionTestUtils.setField(AopTestUtils.getTargetObject(testController), "testBusiness", mockTestBusiness);
    }

    /**
     * 1. 测试 controller 里面的 insert 方法。
     */
    @Test
    public void testGetTestByKey() {
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/backend/test/getTestByKey.do",0))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //验证响应contentType
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            AjaxResponseBean responseBean = getJson(result);
            Assert.assertEquals(responseBean.getStatusCode(),AjaxResponseBean.ERROR);
            Assert.assertEquals(responseBean.getMessage(),AjaxResponseBean.ERROR_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
