import net.yuanmomo.dwz.bean.TestParam;
import net.yuanmomo.dwz.business.mybatis.TestBusiness;
import net.yuanmomo.util.CollectionUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 继承 BaseTest, 测试期间对数据库的 CRUD 操作,最终都会回滚,不会影响数据库中的数据。
 */
@Sql("classpath:sql/test.sql")
public class DemoTest extends BaseTest{
	@Autowired protected TestBusiness testBusiness = null;

	/**
	 *  测试插入逻辑,是否正确。插入一条数据,并获取数据,校验数据是否与插入的数据是否一致。
	 */
	@Test
	public void testInsertBusinessLogic() {
		try {
			net.yuanmomo.dwz.bean.Test t = new net.yuanmomo.dwz.bean.Test();
			t.setNumber(999);
			int count = testBusiness.insertSelective(t);
			Assert.assertTrue("插入数据失败",count  > 0 );

			// 查询一次数据库,校验数据
			net.yuanmomo.dwz.bean.Test newT = this.testBusiness.getTestByKey(t.getId());
			Assert.assertEquals("插入前后的值不相等。",newT.getNumber(),t.getNumber());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 *  测试查询逻辑,是否正确。查询 sql 脚本中执行的数据的数据。
	 */
	@Test
	public void testSelectBusinessLogic() {
		try {
			Integer number = 10000;
			TestParam param = new TestParam();
			param.createCriteria().andNumberEqualTo(number);
			List<net.yuanmomo.dwz.bean.Test> testList = this.testBusiness.selectTestList(param);
			Assert.assertTrue("查询数据返回只能有一条数据",testList.size() == 1 );
			Assert.assertEquals("插入前后的值不相等。",testList.get(0).getNumber(),number);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	/**
	 *  测试更新逻辑,是否正确。更新 sql 脚本中执行的数据的数据。
	 */
	@Test
	public void testUpdateBusinessLogic() {
		try {
			Integer number = 10000;
			TestParam param = new TestParam();
			param.createCriteria().andNumberEqualTo(number);
			List<net.yuanmomo.dwz.bean.Test> testList = this.testBusiness.selectTestList(param);

			Assert.assertTrue("查询数据返回只能有一条数据",testList.size() == 1 );
			Assert.assertEquals("插入前后的值不相等。",testList.get(0).getNumber(),number);

			net.yuanmomo.dwz.bean.Test test = testList.get(0);

			// 更新数据
			Integer newNumber = 20000;
			Long version = 11111L;
			test.setNumber(newNumber);
			test.setVersion(version);
			int count = this.testBusiness.update(test);
			Assert.assertTrue("插入数据失败",count  > 0 );

			// 查询一次数据库,校验数据
			net.yuanmomo.dwz.bean.Test newT = this.testBusiness.getTestByKey(test.getId());
			Assert.assertEquals("更新后的值和预期的值不相等。",testList.get(0).getNumber(),newNumber);
			Assert.assertEquals("更新后的值和预期的值不相等。",testList.get(0).getVersion(),version);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
