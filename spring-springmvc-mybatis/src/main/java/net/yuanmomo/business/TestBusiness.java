package net.yuanmomo.business;

import net.yuanmomo.bean.TestBean;
import net.yuanmomo.mybatis.mapper.TestMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestBusiness{
	private Logger logger=LoggerFactory.getLogger(TestBusiness.class);
	
	@Autowired
	private TestMapper testMapper=null;

	@Transactional(propagation=Propagation.REQUIRED,isolation =Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public boolean insert(TestBean t) throws Exception {
		logger.debug("Start to insert record.......");
		int count=testMapper.insert(t);
		logger.debug("Insert record finished.......");
		return count>0 ? true : false;
	}
}
