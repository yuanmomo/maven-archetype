maven-archetype
===============

maven项目初始化末模板


该项目是一个集成spring,spring mvc,mybatis等框架的web项目快速搭建模板。

将该模板intall到本地的maven仓库后，就可以使用该模板快速的创建一个web项目，该项目集成spring,spring mvc,mybatis三个框架。
并包含一个数据库的简单创建脚本，可以进行JUnit测试，确定项目环境搭建正确。
================

很多时候我们再创建一个项目的时候，再熟练的人也要进行一下几个步骤：
1. 创建一个初始化项目。
2. 配置需要使用的jar包，同时要注意jar包的版本和冲突，比如spring，mybatis，log4j等等。
3. 编写每个框架的配置文件，数据库初始化文件等等。
4. 合理规划项目的package。
5. 编写一个简单的测试用例，分析日志，查看项目环境搭建是否正确。比如：查看事务是否配置成功，日志打印，sql语句的打印。

以上的几个步骤，对于熟练的人我相信还是要花一定的时间。而且基本都是拷贝的体力活。

maven，一个很强大的项目管理，编译，打包，jar包管理工具。运用它，我们就可以简化项目创建的过程。

=================
使用手册：
/************************************************  自动安装  ************************************************/
双击install.bat文件即可

/************************************************  手动安装  ************************************************/
1. 下载整个maven-archetype到本地，例如当前下载到D:盘。
2. 从CMD进入到 spring-springmvc-mybatis目录下：
	ex:D:\maven-archetype-master\maven-archetype-master\spring-springmvc-mybatis
	
3. 在控制台执行如下的命令：
	ex: 1).D:xxxxxxxxxxxxxxxxxx\spring-springmvc-mybatis>mvn clean
	
	    2).D:xxxxxxxxxxxxxxxxxx\spring-springmvc-mybatis>mvn archetype:create-from-project
		
	    3).D:xxxxxxxxxxxxxxxxxx\spring-springmvc-mybatis>cd target/generated-sources/archetype
		
	    4).D:xxxxxxxxxxxxxxxxxx\spring-springmvc-mybatis\target\generated-sources\archetype>mvn clean install
4. 检查用户目录(WIN7,WIN8默认在C:\Users\用户名\.m2下)：
	1. %USER%\.m2文件夹下面是不是多了一个archetype-catalog.xml文件。
	2. 打开archetype-catalog.xml文件，在<archetypes>标签中应该多了如下内容，即表示安装完成。
	<archetype>
      <groupId>net.yuanmomo</groupId>
      <artifactId>spring-springmvc-mybatis-archetype</artifactId>
      <version>0.0.1</version>
      <description>spring-springmvc-mybatis-archetype</description>
    </archetype>
5. 打开eclipse，创建一个maven项目，在<select an Anchetype>页面中：
	1. Catalog中要指向刚刚.m2下面的archetype-catalog.xml文件
	2. 然后在下面的框中选择如下的Anchetype：
		<groupId>net.yuanmomo</groupId>
		<artifactId>spring-springmvc-mybatis-archetype</artifactId>
	然后一路向后，完成项目的创建即可。

	
/************************************************  测试安装  ************************************************/

1. 打开eclipse，创建一个maven项目，在<select an Anchetype>页面中：
	1. Catalog中要指向刚刚.m2下面的archetype-catalog.xml文件
	2. 然后在下面的框中选择如下的Anchetype：
		<groupId>net.yuanmomo</groupId>
		<artifactId>spring-springmvc-mybatis-archetype</artifactId>
	然后一路向后，完成项目的创建即可。
2. 创建成功后，source src/test/resources文件夹下面的DBInit.sql文件，会在本地mysql创建一张test表。
	
3.  在项目创建成功后，删除bean和mybatis.mapper包下面的文件后测试。
	原因：由于git不能保存空的文件夹，所以默认上传了generator自动生成的文件。

4. 修改配置文件。
	修改src/test/resources/ mybatis目录下面的generatorConfig.properties文件。
	参照提示修改targetProject设置。

5. 修改配置文件后，运行src/test/java/文件夹中下mybatis/generator的MyBatisGeneratorTest类。
	5.1 如果成功运行，会在bean包下面生成两个文件，bean和param
	5.2 mybatis.mapper包中生成四个文件，两个java，两个xml，互相对应。
6. 运行src/test/resources/ 下面的BasicTest中的test方法，运行成功后会在数据库test表插入一条记录。
	