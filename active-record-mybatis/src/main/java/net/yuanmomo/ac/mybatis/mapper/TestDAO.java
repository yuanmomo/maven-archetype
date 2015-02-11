package net.yuanmomo.ac.mybatis.mapper;

import java.util.List;

import net.yuanmomo.ac.bean.BeanMap;
import net.yuanmomo.ac.bean.Test;
import net.yuanmomo.ac.bean.TestParam;

import org.apache.ibatis.annotations.Param;

public interface TestDAO {
	
	List<BeanMap> selectByExample(TestParam example);
	
	int updateByExampleSelective(@Param("record") BeanMap obj, @Param("example") TestParam example);
	
//	int updateByExampleSelective(@Param("record") Test    record, @Param("example") TestParam example);
}