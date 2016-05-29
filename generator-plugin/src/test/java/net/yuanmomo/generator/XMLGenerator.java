package net.yuanmomo.generator;

import net.yuanmomo.generator.plugin.TableXMLGenerator;

import java.util.List;

public class XMLGenerator {
	public static void main(String[] args) throws Exception {
		List<String> output = new TableXMLGenerator().generator("src/test/resources/config/generatorConfig.properties");
		for(String str : output){
			System.out.println(str);
		}
	}
}
