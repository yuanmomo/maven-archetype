# maven-archetype #

maven项目初始化末模板

该项目是一个集成 Spring, Spring MVC, MyBatis, MyBatis Generator, DWZ 等开源框架的项目环境搭建模板。只需要简单的通过Maven创建一个新项目，就集成了以上的开源框架，提供效率。



## 项目结构: ##

1. `active-record-mybatis`**(还未完成****)**:
	> MyBatis generator 生成插件，通过继承 HashMap，类似 active-record 的方式，这样可以拜托实体 bean 类的限制，或者修改数据库字段不用修改 bean 的效果。

2. `generatorPlugin`:
	> MyBatis generator生成工具封装，提供了一些自定义插件，包括：
		
		1. 真分页插件。
		2. 别名插件，在表关联简单查询时使用。
		3. 单独的 DAO 文件插件，可以单独生成DAO的Java和XML文件，存放自定义SQL。
		4. 自动生成Business 和 Controller文件。

3. `spring-springmvc-mybatis`：
	> 集成了Spring, Spring MVC, MyBatis, MyBatis-Generator的开发环境。

4. `spring-springmvc-mybatis-dwz`：
	> 集成了Spring, Spring MVC, MyBatis, MyBatis-Generator, DWZ的开发环境。

5. `install.bat 和 libbat 目录`：
	> 自动安装archetype到本地仓库脚本。 



## 安装： ##
-  需要环境：
	1. JDK 1.6+	
	2. Maven 3.0+
	>	
- git clone 该项目到本地目录。
 
- 双击 install.bat 文件即可。运行中会提示如下选项：
	>
		请选择要安装的maven-archetype:
		[0] all
		[1] active-record-mybatis
		[2] spring-springmvc-mybatis
		[3] spring-springmvc-mybatis-dwz
	输入相应序号，回车确认安装即可。默认安装所有。

## 使用 ##
	

