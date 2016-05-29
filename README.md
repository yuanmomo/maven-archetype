#framework

maven项目初始化末模板

该框架集成 Spring, Spring MVC, MyBatis, MyBatis Generator, DWZ 等开源框架的项目环境搭建模板。只需要简单的通过Maven创建一个新项目，就集成了以上的开源框架，大大地提高效率。



## 项目结构: ##

###framework-util:###

	工具包，包含常用的日期，MD5，数字，常用类。

###generator-plugin:###
	MyBatis-Generator（MBG） 生成工具封装，提供了一些自定义插件，包括：

	1. 真分页插件。
	2. 单独的 DAO 文件插件，可以单独生成 DAO 的 Java 和 XML 文件，存放自定义 SQL。
	3. 自动生成 Business 和 Controller 文件，并捕捉自定义异常，可以返回 jsp ,ftl等页面模板或者返回 json 响应 AJAX请求。

###framework-init:###
	集成了 Spring, Spring MVC, MyBatis的开发环境并提供国际化支持，以及包含一个 Demo 表操作的代码。

###framework-dwz:###
	集成了 Spring, Spring MVC, MyBatis, DWZ 的开发环境，包含一个可用的管理后台，同时对 Test 表进行界面上的批量操作的代码。

###install.bat 和 install.sh:###
	自动安装 archetype 到本地仓库脚本。

    如果 install.bat 不能正确执行，或者出现乱码。可以在 CMD 窗口中执行，同时使用记事本修改文件的编码为 ASCII 编码后再执行。



## 安装： ##
### 需要环境：###
* JDK 1.7+
* Maven 3.0+
* lombok 环境

###JDK安装###
下载 JDK 安装包，安装 JDK 和 JRE 环境。

###MAVEN安装###
* 安装参考 [开源中国 Maven 库使用帮助](http://maven.oschina.net/help.html)。
* 在官网下载 maven的 安装包[点击此处下载](http://apache.fayea.com/maven/maven-3/3.3.3/* binaries/apache-maven-3.3.3-bin.zip)。
* 解压到任意目录。
* 配置 apache-maven-3.3.3  PATH 环境变量。
* 打开一个 CMD (Terminal) 窗口，输入: mvn -v，如果输出 Apache Maven 3.3.3 则安装成功。

>1. apache Maven 3.3.3 (7994120775791599e205a5524ec3e0dfe41d4a06; 2015-04-22T19:57:37+08:00)
2. Maven home: /usr/local/apache-maven-3.3.3
3. Java version: 1.7.0_76, vendor: Oracle Corporation
4. Java home: /Library/Java/JavaVirtualMachines/jdk1.7.0_76.jdk/Contents/Home/jre
5. Default locale: en_US, platform encoding: UTF-8
6. OS name: "mac os x", version: "10.11", arch: "x86_64", family: "mac"

* 添加 oschina 的仓库以及三方仓库:
* 打开 maven 目录中的 config/setting.xml 文件。
* 找到 mirrors 标签，修改 mirros标签，改为如下代码：

> ~~~markup
<mirrors>
	<!-- mirror | Specifies a repository mirror site to use instead of a given
		repository. The repository that | this mirror serves has an ID that matches
		the mirrorOf element of this mirror. IDs are used | for inheritance and direct
		lookup purposes, and must be unique across the set of mirrors. | -->
	<mirror>
		<id>nexus-osc</id>
		<mirrorOf>central</mirrorOf>
		<name>Nexus osc</name>
		<url>http://maven.oschina.net/content/groups/public/</url>
	</mirror>
	<mirror>
		<id>nexus-osc-thirdparty</id>
		<mirrorOf>thirdparty</mirrorOf>
		<name>Nexus osc thirdparty</name>
		<url>http://maven.oschina.net/content/repositories/thirdparty/</url>
	</mirror>
</mirrors>
~~~



###lombok环境安装###
* [点击下载 lombok](https://projectlombok.org/downloads/lombok.jar)。
*  自动安装 [参考链接](http://www.cnblogs.com/liqiu/p/3398868.html):
	- 双击下载的 lombok.jar 文件。
	- 如果 IDEs 中没有找到本地的 eclipse，点击 specify locations，然后选中 eclipse 的安装目录。
	- 然后点击 Install/Update。
* 手动安装 [参考链接](http://www.blogjava.net/fancydeepin/archive/2012/07/12/382933.html):
	- 将 lombok.jar 复制到 myeclipse.ini / eclipse.ini 所在的文件夹目录下。
	- 打开 eclipse.ini / myeclipse.ini，在最后面插入以下两行并保存：

        > -Xbootclasspath/a:lombok.jar
        > -javaagent:lombok.jar
	- 重启 eclipse / myeclipse


###抓取代码###
	git clone 该项目到本地目录。

###运行 install脚本###
	双击 install.bat(install.sh) 文件安装。

## 使用 ##

- 创建一个新的项目（framework-init 和 framework-dwz 类似）：
	* 打开 Eclipse --> New --> Other --> Maven --> Maven Project。
	* 点击 Next --> select an Anchetype 页面。
	* 在 Catalog --> Default Local，则会出现一个 net.yuanmomo.framework 的 Archetype。
	* 选中 framework-init-archetype --> Next,
	* 输入 Group Id --> Artifact Id --> Version（建议初始 1.0.0） --> Package，--> Finish，完成创建。

- 导入数据库脚本：
	* 将 新创建的项目 中 src/test/resources/mybatis 目录下的 demo.sql 文件 source 到本地的 mysql 中，会自动创建一个 test 的数据库。
	* 在 src/main/test 目录运行测试类 DemoTest 的 test()方法，检查是否全部执行成功。

- 使用 MyBatis Generator 自动生成工具：

	* 查看 src/test/resources 下的配置文件。
	* 根据文件的提示修改 generatoerConfig.properties 文件中的对应参数。比如：数据库连接信息，生成的包名。
	* 根据文件内的提示修改 generatorConfig.xml 文件，修改自定义插件配置。
	* 添加要自动生成的表配置。具体配置，请翻阅 MyBatis Generator 官方文档，[传送门](http://mybatis.github.io/generator/configreference/xmlconfig.html)。

	* **提示：**
		* 配置Table标签时，可以运行src/test/java下面的XMLGenerator.java 类。
		* 如果jdbc信息配置正确,将会直接生成每一张表的xml配置，拷贝控制台输出的 xml 内容到 generatorConfig.xml 的 </context> 标签前，然后再做对应的详细修改。

	* 运行 scr/test/java 中的 GeneratorMain.java 类，查看是否生成文件。如果没有生成，检查控制台是否找不到配置文件等错误。注意文件路径。
	* 运行 src/test/java下面的GenerateBeanMain.java 类生成 Bean 对象。
	* 更详细的文档参考 [generator-plugin 文档](https://github.com/yuanmomo/maven-archetype/blob/master/generator-plugin/README.md)。

- 部署新创建的项目到 Tomcat
	* 在新建的项目 右键 --> Properties --> Project Facets, 选中 Dynamic Web Module。
	* 点击下面的 Further configuration available…，弹出Modify Faceted Project窗口。
	* 输入 src/main/webapp,点击OK。
	* 在 Project Facets 页面，修改 Java 的 Version 到合适的版本，然后点击 Apply，应用。
	* 在 Properties 左边靠上，点击 Deployment Assembly --> Add --> Java Build Path Entries
	     --> Next --> Maven Dependencies --> Finish。
	* 在 Properties 左边靠上，点击 Java Compiler，选择合适的版本，OK。
	* 新建 Tomcat 容器，这个时候新创建的项目就可以 Add 到 Tomcat 中启动。


# 开发手册 #

# 项目更新操作 #


# 备注 #

- 帮助手册

	* 默认 Spring MVC 返回到 JSP, ftl 页面模板数据，如果需要配置请修改 dispatcher-servlet.xml 文件。
	* 本教程需要有一定的基础， 需要熟练使用eclipse，并对 maven, Spring, Spring MVC, MyBatis, Mybatis-Generator有一定的基础。如果有任何问题，欢迎交流，QQ:342398690





