package net.yuanmomo.dwz.mybatis.mapper;

import java.util.List;
import net.yuanmomo.dwz.bean.Test;
import net.yuanmomo.dwz.bean.TestParam;
import org.apache.ibatis.annotations.Param;

public interface TestMapper {
    int countByExample(TestParam example);

    int deleteByExample(TestParam example);

    int deleteByPrimaryKey(Long id);

    int insert(Test record);

    int insertSelective(Test record);

    List<Test> selectByExample(TestParam example);

    Test selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Test record, @Param("example") TestParam example);

    int updateByExample(@Param("record") Test record, @Param("example") TestParam example);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);
}