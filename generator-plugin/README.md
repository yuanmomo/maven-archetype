# 1. MBG - MybBtis Generator 自动生成文件
	作者：罗浩 # haolllllllll@qq.com
	时间：2015-10-14
	版本：1.0.0	

## 1.1 MBG自动生成文件
### 1.1.1 概述
	根据配置文件由数据表自动生成底层模型类、Dao接口类以及Mapping映射文件
	自行选择是否生成控制层Controller文件和业务层Business文件
![生成文件Package展示](http://i.imgur.com/MzOz7yz.png)
	
### 1.1.2 建立表结构

CREATE TABLE IF NOT EXISTS `test` (  
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,  
  `number` int(11) NOT NULL,  
  `version` bigint(20) unsigned NOT NULL DEFAULT '0',  
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;  

### 1.1.3 配置文件generatorConfig：properties + xml

#### 1.1.3.1 generatorConfig.properties

***注***：
	配置基本参数，供generatorConfig.xml读取

    ## targetProject 要修改为当前项目的目录，路径之间必须使用 \\ 或者 / 作为分隔符，不然不会生成文件
    #生成文件的存放路径，相对路径，根据项目的根路径来算
    targetProject=src/test/java
    
    #数据库连接信息    
    driver=com.mysql.jdbc.Driver
    url=jdbc:mysql://localhost:3306/test
    user=root
    password=root
    
    #MyBatis 的插件包路径
    pluginBasePackage=net.yuanmomo.framework.mybatis.generator.plugin
    
    #生成的bean类包名，存放路径
    modelPackage=net.yuanmomo.bean
    
    #生成的xml文件存放路径
    sqlMapPackage=net.yuanmomo.mybatis.mapper
    
    #生成的Java mapper文件包名，存放路径
    javaClientPackage=net.yuanmomo.mybatis.mapper
    
    #生成的business文件包名，存放路径
    businessPackage=net.yuanmomo.business.mybatis
    
    #生成的controller类包名，存放路径
    controllerPackage=net.yuanmomo.controller.mybatis

    #自定义异常类(全路径)
    exceptionClass=net.yuanmomo.util.exception.BaseException

#### 1.1.3.2 generatorConfig.xml

##### 1.1.3.2.1 批量抓取数据表
配置文件中配置的自动生成的表，如`<table tableName="test" domainObjectName="Test" >...`
可由 net.yuanmomo.framework.mybatis.generator.XMLGenerator 类从数据库里批量抓取生成（执行main方法即可）

***注***：类路径为 src/test/java/net/yuanmomo/framework/mybatis/generator/XMLGenerator.java

    e.g:假如数据库里总共有两张表emp和test
		<table tableName="emp" domainObjectName="Emp">
			<property name="generatedBusinessName" value="EmpBusiness"/>
			<property name="generatedControllerName" value="EmpController"/>
			<generatedKey column="id" sqlStatement="MySql" identity="true"/>
		</table>
		<table tableName="test" domainObjectName="Test">
			<property name="generatedBusinessName" value="TestBusiness"/>
			<property name="generatedControllerName" value="TestController"/>
			<generatedKey column="id" sqlStatement="MySql" identity="true"/>
		</table>

##### 1.1.3.2.2 xml文件配置信息

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE generatorConfiguration
    		PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
    		"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
    <generatorConfiguration>
    	<!-- propertis 配置文件 -->
    	<properties resource="config/generatorConfig.properties" />
    	<!-- MBG根配置 -->
    	<context id="MBG" targetRuntime="MyBatis3" defaultModelType="conditional">
    		<!-- 生成hashCode()和equals()方法的插件 -->
    		<plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin" />
    		
    		<!-- bean类序列化插件 -->
    		<plugin type="org.mybatis.generator.plugins.SerializablePlugin" />
    		
    		<!-- sql like 查询是否区分大小写插件 -->
    		<plugin type="org.mybatis.generator.plugins.CaseInsensitiveLikePlugin" />
    		
    		<!-- 生成toString() 方法插件 -->
    		<plugin type="org.mybatis.generator.plugins.ToStringPlugin" />
    		
    		<!-- 重命名Bean查询条件类的类名，默认命名Example -->
    		<plugin type="org.mybatis.generator.plugins.RenameExampleClassPlugin">
    			<property name="searchString" value="Example$" />
    			<property name="replaceString" value="Param" />
    		</plugin>
    		
    		<!-- *******************************下面的插件都是自定义插件******************************* -->
    		<!-- 分页插件， 自带的是假分页，效率很低  -->
    		<plugin type="${pluginBasePackage}.PaginationPlugin" />
    		
    		<!-- 生成别名，在表关联简单查询时使用  -->
    		<plugin type="${pluginBasePackage}.alias.AliasPlugin" />
    		
    		<!-- 单独生成一个DAO的JAVA文件和XML文件，存放自定义SQL，将自动生成和自定义分开 -->
    		<plugin type="${pluginBasePackage}.DAOPlugin" >
    			<property name="typeName" value="DAO"/>
    		</plugin>
    		
    		<!-- 生成business，简单的增删改查业务 -->
    		<plugin type="${pluginBasePackage}.business.BusinessPlugin" >
    			<property name="businessPackage" value="${businessPackage}"/>
    		</plugin>
    		
    		<!-- 
                生成controller, 不再需要单独配置生成business插件，会默认自动生成business
    			type="${pluginBasePackage}.controller.jsp.ControllerPlugin" ==> 生成返回视图的controller
    			type="${pluginBasePackage}.controller.json.ControllerPlugin" ==> 生成返回json的controller
    		 -->
    		<plugin type="${pluginBasePackage}.controller.jsp.ControllerPlugin" >
    			<property name="businessPackage" value="${businessPackage}"/>
    			<property name="controllerPackage" value="${controllerPackage}"/>
                <property name="exceptionClass" value="${exceptionClass}"/>
    		</plugin>
    		<!-- *******************************上面的插件都是自定义插件******************************* -->
    		
    		
    		<!-- 生成的注释配置，不输入注释中的时间 -->		
    		<commentGenerator>
    			<property name="suppressDate" value="true" />
    		</commentGenerator>
    		
    		<!-- 数据库连接信息 -->
    		<jdbcConnection driverClass="${driver}" connectionURL="${url}"
    			userId="${user}" password="${password}">
    		</jdbcConnection>
    		
    		<!-- Java 生成的bean中字段类型配置 -->
    		<javaTypeResolver>
    			<property name="forceBigDecimals" value="false" />
    		</javaTypeResolver>
    		
    		<!-- java 生成Bean类的配置 -->
    		<javaModelGenerator targetPackage="${modelPackage}"
    			targetProject="${targetProject}">
    			<property name="enableSubPackages" value="true" />
    		</javaModelGenerator>
    		
    		<!-- java 生成xml文件的配置 -->
    		<sqlMapGenerator targetPackage="${sqlMapPackage}"
    			targetProject="${targetProject}">
    			<property name="enableSubPackages" value="true" />
    		</sqlMapGenerator>
    		
    		<!-- java 生成 mapper类的配置 -->
    		<javaClientGenerator type="XMLMAPPER"
    			targetPackage="${javaClientPackage}" targetProject="${targetProject}">
    			<property name="enableSubPackages" value="true" />
    		</javaClientGenerator>
    		
    		
    		<!-- 配置需要自动生成的表，可配置多个table标签 -->
    		<table tableName="test" domainObjectName="Test" >
    		
    			<!-- 生成的business文件名称,不配置，则不会生成business  -->
    			<property name="generatedBusinessName" value="TestBusiness"/>
    			
    			<!-- 生成的controller文件名称，不配置，则不会生成controller -->
    			<property name="generatedControllerName" value="TestController"/>
    			
    			<!-- 是否生成自增长主键 -->
    			<generatedKey column="id" sqlStatement="MySql" identity="true"/>
    		</table>
    	</context>
    </generatorConfiguration>


### 1.1.4 运行 

执行 net.yuanmomo.framework.mybatis.generator.GeneratorMain 类的main方法即可

***注***：类路径为 src/test/java/net/yuanmomo/framework/mybatis/generator/GeneratorMain.java
		
### 1.1.5 运行结果

***注***：执行结果的包路径以上述properties文件为例  

![生成文件Package展示](http://i.imgur.com/MzOz7yz.png)
  
---------------------------------------  
# 2. 总述
 


---------------------------------------

# 3. 备注


---------------------------------------
# 4. 版本历史

版本号|时间|备注|
----|---|---|
1.0.0|2015-10-14|初始文档|
