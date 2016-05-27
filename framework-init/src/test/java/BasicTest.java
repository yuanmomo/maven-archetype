import net.yuanmomo.framework.bean.TestParam;
import net.yuanmomo.framework.business.mybatis.TestBusiness;
import net.yuanmomo.util.NumberUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.net.NetPermission;
import java.util.Date;
import java.util.List;

/**
 * Created by Hongbin.Yuan on 2015-10-19 00:49.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/ApplicationContext.xml")
public class BasicTest {

    @Autowired protected TestBusiness demoBusiness = null;

    @Test
    public void test(){
        testInsert();
        testSelect();
        testUpdate();
    }

    public void testInsert(){
        net.yuanmomo.framework.bean.Test demo = new net.yuanmomo.framework.bean.Test();
        demo.setBytecol((byte) 1);
        demo.setShortcol((short) 2);
        demo.setIntcol(3);
        demo.setFloatcol(4.0f);
        demo.setDoublecol(5.0);
        demo.setStringcol1("A");
        demo.setStringcol2("String column2");
        demo.setDatecol(new Date());
        demo.setBlobcol("BLOB COLUMN".getBytes());

        try {
            int count = this.demoBusiness.insertSelective(demo);
            if(count == 1 && NumberUtil.isPositive(demo.getId())){
                System.out.println("插入成功！");
            }else{
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    public void testSelect(){
        TestParam param = new TestParam();
        param.createCriteria().andBytecolEqualTo((byte) 1);
        try {
            List<net.yuanmomo.framework.bean.Test> demoList= this.demoBusiness.selectTestList(param);
            if(CollectionUtils.isEmpty(demoList) || !demoList.get(0).getShortcol().equals((short)2)){
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    public void testUpdate(){
        TestParam param = new TestParam();
        param.createCriteria().andBytecolEqualTo((byte) 1);
        try {
            List<net.yuanmomo.framework.bean.Test> demoList= this.demoBusiness.selectTestList(param);

            if(CollectionUtils.isEmpty(demoList) || !demoList.get(0).getShortcol().equals((short)2)){
                Assert.fail();
            }

            long key = demoList.get(0).getId();

            net.yuanmomo.framework.bean.Test newTest = new net.yuanmomo.framework.bean.Test();
            newTest.setId(key);
            String newColumn1 = "B";
            String newColumn2 = "String column2 new";
            newTest.setStringcol1(newColumn1);
            newTest.setStringcol2(newColumn2);


            int count = this.demoBusiness.update(newTest);
            if(count != 1 ){
                Assert.fail();
            }

            // 重新查询数据库
            net.yuanmomo.framework.bean.Test afterTest = this.demoBusiness.getTestByKey(key);
            if(afterTest == null || !newColumn1.equals(afterTest.getStringcol1())
                    || !newColumn2.equals(afterTest.getStringcol2())){
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
