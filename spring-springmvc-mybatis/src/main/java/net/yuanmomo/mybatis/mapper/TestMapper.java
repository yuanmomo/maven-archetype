package net.yuanmomo.mybatis.mapper;

import net.yuanmomo.bean.TestBean;

public interface TestMapper {
	public int update(TestBean t) throws Exception;
	public TestBean get(int id) throws Exception;
	public int insert(TestBean t);
}
