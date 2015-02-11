package net.yuanmomo.ac.business.mybatis;

import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.ac.bean.BeanMap;
import net.yuanmomo.ac.bean.Test;
import net.yuanmomo.ac.bean.TestParam;
import net.yuanmomo.ac.mybatis.mapper.TestDAO;
import net.yuanmomo.ac.mybatis.mapper.TestMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestBusiness {
    private static Logger logger = LoggerFactory.getLogger(TestBusiness.class);

    @Autowired  private TestMapper testMapper;
    @Autowired  private TestDAO testDAO;

    @Transactional(propagation=Propagation.REQUIRED,isolation =Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public int insertSelective(Test obj) throws Exception {
        if(obj  == null ){
            return 0;
        }
        List<Test> list = new ArrayList<Test>();
        list.add(obj);
        return this.insertSelective(list);
    }

    @Transactional(propagation=Propagation.REQUIRED,isolation =Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public int insertSelective(List<Test> list) throws Exception {
        int insertCount = 0;
        if (list == null || list.size() == 0) {
            return insertCount;
        }
        for (Test  obj : list) {
            if (obj == null) {
                continue;
            }
            try {
                insertCount += this.testMapper.insertSelective(obj);
            } catch (Exception e) {
                throw e;
            }
        }
        return insertCount;
    }

    @Transactional(propagation=Propagation.REQUIRED,isolation =Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public int update(Test obj) throws Exception {
        if(obj  == null ){
            return 0;
        }
        List<Test> list = new ArrayList<Test>();
        list.add(obj);
        return this.update(list);
    }

    @Transactional(propagation=Propagation.REQUIRED,isolation =Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public int update(List<Test> list) throws Exception {
        int updateCount = 0;
        if (list == null || list.size() == 0) {
            return updateCount;
        }
        for (Test  obj : list) {
            if (obj == null) {
                continue;
            }
            try {
                updateCount += this.testMapper.updateByPrimaryKeySelective(obj);
            } catch (Exception e) {
                throw e;
            }
        }
        return updateCount;
    }

    public List<Test> selectTestList(TestParam param) throws Exception {
        return this.testMapper.selectByExample(param);
    }

    public int countTestList(TestParam param) throws Exception {
        return this.testMapper.countByExample(param);
    }
    
    
    public List<BeanMap> selectTestList2(TestParam param) throws Exception {
        return this.testDAO.selectByExample(param);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,isolation =Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public int update(BeanMap obj, TestParam param) throws Exception {
        int updateCount = 0;
        if (obj == null || obj.size() == 0) {
            return updateCount;
        }
        try {
            updateCount += this.testDAO.updateByExampleSelective(obj, param);
        } catch (Exception e) {
            throw e;
        }
        return updateCount;
    }
}