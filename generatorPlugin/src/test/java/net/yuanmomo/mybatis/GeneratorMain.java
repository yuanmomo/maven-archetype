package net.yuanmomo.mybatis;

import net.yuanmomo.mybatis.generator.MyBatisGeneratorTool;

public class GeneratorMain {
	public static void main(String args[]) {
		MyBatisGeneratorTool.generate("src\\main\\resources\\config\\generatorConfig.xml");
	}
}
