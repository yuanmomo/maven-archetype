package net.yuanmomo;

import net.yuanmomo.bean.TestBean;
import net.yuanmomo.business.TestBusiness;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/ApplicationContext.xml")
public class BasicTest {
	@Autowired
	protected TestBusiness testBusiness = null;

//	@Before
//	public void setUp() throws Exception {
//		// 绝对路径
//		// beanFactory=new
//		//
//		// ClassPathXmlApplicationContext("file:E:\\workspace\\7e-NewPlatform2.0\\WebContent\\WEB-INF\\applicationContext.xml"
//		// );
//		// 相对路径
//		beanFactory = new ClassPathXmlApplicationContext(
//				"config/ApplicationContext.xml");
//		testBusiness = beanFactory.getBean(TestBusiness.class);
//	}
	@Test
	public void test() {
		try {
			TestBean t = new TestBean();
			t.setNumber(11);
			boolean flag = testBusiness.insert(t);
			if (flag) {
				System.out.println("插入成功");
			} else {
				System.out.println("插入失败");
			}
		} catch (Exception e) {
			System.out.println("插入异常" + e.getMessage());
			e.printStackTrace();
		}
	}
}
