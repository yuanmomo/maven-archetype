package net.yuanmomo.mybatis.generator.codegen;

import java.util.ArrayList;
import java.util.List;

public class DefaultBusinessGenerator extends IBusinessGen {
	private static List<String> businessImportStringList = new ArrayList<String>();
	static{
		businessImportStringList.add("java.util.ArrayList");
        businessImportStringList.add("java.util.List");
        businessImportStringList.add("org.slf4j.Logger");
        businessImportStringList.add("org.slf4j.LoggerFactory");
        businessImportStringList.add("org.springframework.beans.factory.annotation.Autowired");
        businessImportStringList.add("org.springframework.stereotype.Service");
        businessImportStringList.add("org.springframework.transaction.annotation.Isolation");
        businessImportStringList.add("org.springframework.transaction.annotation.Propagation");
        businessImportStringList.add("org.springframework.transaction.annotation.Transactional");
	}
	
}
