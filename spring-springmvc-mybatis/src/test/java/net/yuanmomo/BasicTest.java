package net.yuanmomo;

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

	@Test
	public void test() {
		try {
			net.yuanmomo.bean.Test t = new net.yuanmomo.bean.Test();
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
