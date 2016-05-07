package net.yuanmomo;

import net.yuanmomo.mybatis.generator.MyBatisGeneratorTool;

public class GeneratorMain {
	public static void main(String args[]) {
		MyBatisGeneratorTool.generate("src\\test\\resources\\config\\generatorConfig.xml");
	}
}
