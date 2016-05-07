package net.yuanmomo.generator;

public class GeneratorMain {
	public static void main(String args[]) {
		MyBatisGeneratorTool.generate("src/test/resources/config/generatorConfig.xml");
	}
}
