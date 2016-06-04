package net.yuanmomo.dwz.test.mock;

import net.yuanmomo.dwz.business.mybatis.TestBusiness;
import net.yuanmomo.dwz.mybatis.mapper.TestMapper;
import net.yuanmomo.dwz.test.BaseTest;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AopTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类分两类测试:
 * <p>
 * 1. mock mapper 类 模拟数据库数据 来 测试 business.
 * <p>
 * Created by Hongbin.Yuan on 2016-05-29 18:03.
 */
public class DemoMockBusinessTest extends BaseTest {

    @Autowired protected TestBusiness testBusiness = null;

    private TestMapper mockTestMapper;

    //创建一个模拟器
    private IMocksControl control = null;

    /**
     * 实例化之前,设置 mock 类到实体类。
     */
    @Before
    public void before() throws Exception {
        control = EasyMock.createNiceControl();

        mockTestMapper = control.createMock(TestMapper.class);

        ReflectionTestUtils.setField(AopTestUtils.getTargetObject(testBusiness), "testMapper", mockTestMapper);
    }

    /**
     * 1. mock mapper ,测试 business 中的 插入方法。理论上,任何一个分支判断的地方都要测试到。
     */
    @Test
    public void testInsertSelective() {
        try {
            // ######################################################## 测试插入单个对象。########################################################
            /**
             * 设置插入参数为空, 那么返回 0.
             */
            net.yuanmomo.dwz.bean.Test test = null;
            // 因为没有涉及到 mapper 调用,所以直接调用。
            int count = testBusiness.insertSelective(test);
            Assert.assertEquals("数据为空,插入预期返回 0", 0, count);

            /**
             * 设置插入时异常,会抛出异常。
             */
            // 1. 初始化数据
            count = 0;
            test = new net.yuanmomo.dwz.bean.Test();
            // 2. 设置希望, 希望 business 在执行时,调用 mapper.insertSelective 方法时模拟数据库抛出异常。
            EasyMock.expect(mockTestMapper.insertSelective(EasyMock.anyObject(net.yuanmomo.dwz.bean.Test.class))).andThrow(new RuntimeException());
            // 3. 录制
            control.replay();
            // 4. 播放,就是开始调用 business 插入方法。
            try {
                count = testBusiness.insertSelective(test);
                // 如果没有抛异常,则认为错误。
                Assert.fail();
            } catch (Exception e) {
                Assert.assertTrue(true);
            }
            // 5. 校验返回的数据是否是预期的数据.
            Assert.assertEquals("插入一条数据失败", 0, count);
            // 6. 检查 mock 设置的希望是否被调用了。
            control.verify();
            // 7. 重置,进行下一轮测试。
            control.resetToNice();

            // 当 i = 0 时,模拟数据库插入 失败。
            // 当 i = 1 时,模拟数据库插入 成功。
            for (int i = 0; i <= 1; i++) {
                // 1. 初始化数据
                count = 0;
                test = new net.yuanmomo.dwz.bean.Test();
                // 2. 设置希望, 希望 business 在执行时,调用 mapper.insertSelective 方法时的返回的条数, 表示插入失败还是成功。
                EasyMock.expect(mockTestMapper.insertSelective(EasyMock.anyObject(net.yuanmomo.dwz.bean.Test.class))).andReturn(i);
                // 3. 录制
                control.replay();
                // 4. 播放,就是开始调用 business 插入方法。
                count = testBusiness.insertSelective(test);
                // 5. 校验返回的数据是否是预期的数据.
                Assert.assertEquals("插入一条数据失败", i, count);
                // 6. 检查 mock 设置的希望是否被调用了。
                control.verify();
                // 7. 重置,进行下一轮测试。
                control.resetToNice();
            }


            // ######################################################## 测试插入 多个对。########################################################象
            /**
             * 设置插入 列表为空, 那么返回 0.
             */
            List<net.yuanmomo.dwz.bean.Test> testList = null;
            // 因为没有涉及到 mapper 代码,所以直接调用。
            count = testBusiness.insertSelective(testList);
            Assert.assertEquals("数据 列表为空,插入预期返回 0", 0, count);

            /**
             * 设置插入时异常,会抛出异常。
             */
            // 1. 初始化数据
            count = 0;
            testList = new ArrayList<>();
            for (int i1 = 0; i1 < 10; i1++) {
                testList.add(new net.yuanmomo.dwz.bean.Test());
            }
            // 2. 设置希望, 希望 business 在执行时,调用 mapper.insertSelective 方法时模拟数据库抛出异常。
            EasyMock.expect(mockTestMapper.insertSelective(EasyMock.anyObject(net.yuanmomo.dwz.bean.Test.class))).andThrow(new RuntimeException());
            // 3. 录制
            control.replay();
            // 4. 播放,就是开始调用 business 插入方法。
            try {
                count = testBusiness.insertSelective(testList);
                // 如果没有抛异常,则认为错误。
                Assert.fail();
            } catch (Exception e) {
                Assert.assertTrue(true);
            }
            // 5. 校验返回的数据是否是预期的数据.
            Assert.assertEquals(0, count);
            // 6. 检查 mock 设置的希望是否被调用了。
            control.verify();
            // 7. 重置,进行下一轮测试。
            control.resetToNice();


            /**
             * 当 i = 0 时,模拟数据库插入 10 失败。
             * 当 i = 1 时,模拟数据库插入 10 成功。
             */
            for (int i = 0; i <= 1; i++) {
                count = 0;
                // 1. 初始化数据
                testList = new ArrayList<>();
                for (int i1 = 0; i1 < 10; i1++) {
                    testList.add(new net.yuanmomo.dwz.bean.Test());
                }
                // 2. 设置希望, 希望 business 在执行时,调用 mapper.insertSelective 方法时的返回 插入的条数,表示插入成功还是失败。
                EasyMock.expect(mockTestMapper.insertSelective(EasyMock.anyObject(net.yuanmomo.dwz.bean.Test.class))).andReturn(i).times(10);
                // 3. 录制
                control.replay();
                // 4. 播放,就是开始调用 business 插入方法。
                count = testBusiness.insertSelective(testList);
                // 5. 校验返回的数据是否是预期的数据.
                Assert.assertEquals(i == 0 ? 0 : 10, count);
                // 6. 检查 mock 设置的希望是否被调用了。
                control.verify();
                // 7. 重置,进行下一轮测试。
                control.resetToNice();
            }

            /**
             * 模拟数据库插入 10 条, 5条成功, 5条失败。
             */
            // 1. 初始化数据
            count = 0;
            testList = new ArrayList<>();
            for (int i1 = 0; i1 < 5; i1++) {
                testList.add(new net.yuanmomo.dwz.bean.Test());
            }
            // 2. 设置希望, 希望 business 在执行时,调用 mapper.insertSelective 方法时的返回 1 条, 表示插入 成功。
            EasyMock.expect(mockTestMapper.insertSelective(EasyMock.anyObject(net.yuanmomo.dwz.bean.Test.class))).andReturn(1).times(5);
            // 3. 录制
            control.replay();
            // 4. 播放,就是开始调用 business 插入方法。
            count = testBusiness.insertSelective(testList);
            // 5. 校验返回的数据是否是预期的数据.
            Assert.assertEquals(5, count);
            // 6. 检查 mock 设置的希望是否被调用了。
            control.verify();
            // 7. 重置,进行下一轮测试。
            control.resetToNice();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }


    @Test
    public void testSelectGetTestByKey() {
        try {
            /**
             * key 为空的时候。
             */
            // 1. 初始化数据
            // 2. 设置希望, 希望 business 在执行时,调用 mapper.selectByPrimaryKey 方法时抛出异常
            net.yuanmomo.dwz.bean.Test test = null;
            mockTestMapper.selectByPrimaryKey(null);
            EasyMock.expectLastCall().andReturn(null).times(1);
            // 3. 录制
            control.replay();
            // 4. 播放,就是开始调用 business 查询方法。
            test = testBusiness.getTestByKey(null);
            // 5. 校验返回的数据是否是预期的数据.
            Assert.assertNull(test);
            // 6. 检查 mock 设置的希望是否被调用了。
            control.verify();
            // 7. 重置,进行下一轮测试。
            control.resetToNice();

            /**
             * 查询时抛出异常。
             */
            // 1. 初始化数据
            // 2. 设置希望, 希望 business 在执行时,调用 mapper.selectByPrimaryKey 方法时抛出异常
            mockTestMapper.selectByPrimaryKey(EasyMock.anyLong());
            EasyMock.expectLastCall().andThrow(new RuntimeException()).times(1);
            // 3. 录制
            control.replay();
            // 4. 播放,就是开始调用 business 查询方法。
            try {
                test = testBusiness.getTestByKey(0L);
                Assert.fail();
            } catch (Exception e) {
                Assert.assertTrue(true);
            }
            // 5. 校验返回的数据是否是预期的数据.
            Assert.assertNull(test);
            // 6. 检查 mock 设置的希望是否被调用了。
            control.verify();
            // 7. 重置,进行下一轮测试。
            control.resetToNice();

            /**
             * 查询的数据为空。
             */
            // 1. 初始化数据
            // 2. 设置希望, 希望 business 在执行时,调用 mapper.selectByPrimaryKey 方法时的返回空
            mockTestMapper.selectByPrimaryKey(EasyMock.anyLong());
            EasyMock.expectLastCall().andReturn(null).times(1);
            // 3. 录制
            control.replay();
            // 4. 播放,就是开始调用 business 插入方法。
            test = testBusiness.getTestByKey(0L);
            // 5. 校验返回的数据是否是预期的数据.
            Assert.assertNull(test);
            // 6. 检查 mock 设置的希望是否被调用了。
            control.verify();
            // 7. 重置,进行下一轮测试。
            control.resetToNice();


            /**
             * 查询到一条结果。
             */
            // 1. 初始化数据
            // 2. 设置希望, 希望 business 在执行时,调用 mapper.selectByPrimaryKey 方法时的返回空
            mockTestMapper.selectByPrimaryKey(EasyMock.anyLong());
            EasyMock.expectLastCall().andReturn(new net.yuanmomo.dwz.bean.Test()).times(1);
            // 3. 录制
            control.replay();
            // 4. 播放,就是开始调用 business 插入方法。
            test = testBusiness.getTestByKey(0L);
            // 5. 校验返回的数据是否是预期的数据.
            Assert.assertNotNull(test);
            // 6. 检查 mock 设置的希望是否被调用了。
            control.verify();
            // 7. 重置,进行下一轮测试。
            control.resetToNice();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
