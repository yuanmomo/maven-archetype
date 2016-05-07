package net.yuanmomo;

import net.yuanmomo.bean.Temp;
import net.yuanmomo.business.mybatis.TempBusiness;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/ApplicationContext.xml")
public class BasicTest {
	@Autowired
	protected TempBusiness TempBusiness = null;

	@Test
	public void Temp() {
		try {
			Temp t = new Temp();
			t.setNumber(11);
			int count = TempBusiness.insertSelective(t);
			if (count > 0) {
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
