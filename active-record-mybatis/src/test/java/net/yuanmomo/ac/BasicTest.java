package net.yuanmomo.ac;

import java.util.List;

import net.yuanmomo.ac.bean.BeanMap;
import net.yuanmomo.ac.bean.TestParam;
import net.yuanmomo.ac.business.mybatis.TestBusiness;

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
			net.yuanmomo.ac.bean.Test t = new net.yuanmomo.ac.bean.Test();
			t.setNumber(11);
			int count = testBusiness.insertSelective(t);
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
	@Test
	public void testSelectList() {
		try {
			TestParam param = new TestParam();
			param.createCriteria().andIdGreaterThan(0L).andIdLessThan(100L);
			List<BeanMap> result= testBusiness.selectTestList2(param);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("查询异常" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
		try {
			TestParam param = new TestParam();
			param.createCriteria().andIdGreaterThan(1L).andIdLessThan(4L);
			
			BeanMap bm = new BeanMap();
			bm.put("number", 110);
			bm.put("version", 119);
			
			int  result= testBusiness.update(bm, param);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("更新异常" + e.getMessage());
			e.printStackTrace();
		}
	}
}
