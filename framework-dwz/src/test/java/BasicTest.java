import net.yuanmomo.dwz.business.mybatis.TestBusiness;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/ApplicationContext.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class BasicTest {
	@Autowired
	protected TestBusiness testBusiness = null;

	@Test
	public void test() {
		try {
			net.yuanmomo.dwz.bean.Test t = new net.yuanmomo.dwz.bean.Test();
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
			Assert.fail();
		}
	}
}
