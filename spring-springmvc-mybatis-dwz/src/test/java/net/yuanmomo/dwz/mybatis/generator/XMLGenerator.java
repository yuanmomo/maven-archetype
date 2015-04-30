package net.yuanmomo.dwz.mybatis.generator;

import java.util.List;

import net.yuanmomo.mybatis.generator.plugin.TableXMLGenerator;

public class XMLGenerator {
	public static void main(String[] args) throws Exception {
		List<String> output = new TableXMLGenerator().generator("src/test/resources/mybatis/generatorConfig.properties");
		for(String str : output){
			System.out.println(str);
		}
	}
}
