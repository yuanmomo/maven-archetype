# maven-archetype #

maven项目初始化末模板

该项目是一个集成 Spring, Spring MVC, MyBatis, MyBatis Generator, DWZ 等开源框架的项目环境搭建模板。只需要简单的通过Maven创建一个新项目，就集成了以上的开源框架，提高效率。



## 项目结构: ##

1. `active-record-mybatis`**(还未完成****)**:
	> MyBatis generator 生成插件，通过继承 HashMap，类似 active-record 的方式，这样可以拜托实体 bean 类的限制，或者修改数据库字段不用修改 bean 的效果。

2. `generatorPlugin`:
	> MyBatis generator 生成工具封装，提供了一些自定义插件，包括：
		
		1. 真分页插件。
		2. 别名插件，在表关联简单查询时使用。
		3. 单独的 DAO 文件插件，可以单独生成 DAO 的 Java 和 XML 文件，存放自定义 SQL。
		4. 自动生成 Business 和 Controller 文件。

3. `spring-springmvc-mybatis`：
	> 集成了 Spring, Spring MVC, MyBatis, MyBatis-Generator 的开发环境。

4. `spring-springmvc-mybatis-dwz`：
	> 集成了 Spring, Spring MVC, MyBatis, MyBatis-Generator, DWZ 的开发环境。

5. `install.bat 和 libbat 目录`：
	> 自动安装 archetype 到本地仓库脚本。 



## 安装： ##
-  需要环境：
	1. JDK 1.6+	
	2. Maven 3.0+
	>	
- git clone 该项目到本地目录。
 
- 双击 install.bat 文件即可。运行中会提示如下选项：
	>
		安装MBG自定义插件包成功
		--------------------------------------------------------------------
		本批处理执行后，将作以下一些设置,确认后继续：
		1、请根据README.md指示，创建一个项目来确定是否安装成功
		--------------------------------------------------------------------
		        请选择要安装的 maven-archetype:
		        [0] all
		        [1] active-record-mybatis
		        [2] spring-springmvc-mybatis
		        [3] spring-springmvc-mybatis-dwz
		--------------------------------------------------------------------
		请选择要安装的archetype类型中[]中的编号:0?
	
	输入相应序号，回车确认安装即可。默认安装所有。

## 使用 ##

- 创建一个新的项目：
	> 1. 打开 Eclipse --New --Other --Maven --Maven Project。
	> 
	> 2. 点击 Next --select an Anchetype 页面。
	> 
	> 3. 在 Catalog --Default Local，则会出现三个，我们已经安装的 Archetype。
	> 
	> 4. 选中其中一个,当前选择 <spring-springmvc-mybatis-dwz> --Next, 
	> 
	> 5. 输入 Group Id --Artifact Id --Package 即可， --Finish，即完成创建。

- 测试是否创建成功：
	> 1. 将 generatorPlugin 中的 DBini.sql 文件 source 到本地的 mysql 数据库中。
	> 
	> 2. 用JUnit运行 scr/test/java 中的 BasicTest.java 的 test 方法。
	> 
	> 3. 如果插入成功，则项目配置成功。 

- WEB启动 spring-springmvc-mybatis 和 spring-springmvc-mybatis-dwz
	> 1. 在新建的项目 右键 --> Properties --> Project Facets, 选中 Dynamic Web Module
	> 
	> 2. 点击下面的 Further configuration available…，弹出Modify Faceted Project窗口
	> 
	> 3. 输入 src/main/webapp,点击OK。
	> 
	> 4. 在 Project Facets 页面，修改 Java 的 Version 到合适的版本，然后点击 Apply，应用。
	> 
	> 5. 在 Properties 左边靠上，点击 Deployment Assembly --> Add --> Java Build Path Entries
	     --> Next --> Maven Dependencies --> Finish。

	> 6. 在 Properties 左边靠上，点击 Java Compiler，选择合适的版本，OK。
	> 
	> 7. 新建 Tomcat 容器，这个时候两个项目都可以 Add 到 Tomcat 中启动。

- 使用 MyBatis Generator 自动生成工具：
	
	> 1. 查看 src/test/resources 下的配置文件。
	> 
	> 2. 修改 generatoerConfig.properties 文件中的对应参数。比如：数据库连接信息，生成的包名等
	> 
	> 3. 修改 generatorConfig.xml 文件，修改自定义插件配置。
	> 
	> 4. 添加要自动生成的表配置。具体配置，请翻阅 MyBatis Generator 官方文档。配置Table标签时，可以运行src/test/java下面的
	> XMLGenerator.java 类，如果jdbc信息配置正确,将会直接生成每一张表的xml配置，可以直接拷贝过去再做修改。
	> 
	> 5. 运行 scr/test/java 中的 GeneratorMain.java 类，查看是否生成文件。



# 备注 #

- 帮助手册
	
	> 1. 默认 Spring MVC 由于和 DWZ 集成，所以默认返回JSON数据。
	> 
	> 2. DWZ默认提供了test表的 增删改查 和 分页操作，默认的登陆用户名密码 admin : admin。
	> 
	> 3. spring-springmvc-mybatis 项目的使用类似。 
	> 	
	> 4. 本教程不适合初学者，适合对 Maven,Spring,MyBatis 有一定了解的人群，如果有任何问题，欢迎交流，QQ:342398690 

	


