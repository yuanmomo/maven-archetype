import net.yuanmomo.dwz.business.mybatis.TestBusiness;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/ApplicationContext.xml")
// 配置事务管理器,默认执行方法后,回滚事务
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
// 当前测试类使用事务
@Transactional
public class BaseTest {

}
