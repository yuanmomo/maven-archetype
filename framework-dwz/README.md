#framework-init

集成了 Spring, Spring MVC, MyBatis的开发环境并提供国际化支持，以及包含一个 Demo 表操作的代码。  

## 介绍说明：##

---------------------------------------
## SpringMVC+MyBatis-单表操作Demo ##

    此Demo集成了 Spring, Spring MVC, MyBatis的开发环境，并提供国际化支持。

- 方法介绍  

 1. 按照上述文档成功创建一个项目，导入数据库脚本  
 2. 使用 MyBatis Generator 自动生成工具生成Demo代码  
 
        ***注：*** 
        1. Test代码在项目中已生成，若需上手练习生成代码，可另建一张表来自动生成代码，亦可以框架提供的数据库脚本中的表来自动生成代码以覆盖 Test 代码。生成类：net.yuanmomo.dwz.mybatis.generator.GeneratorMain . 
        2. generatorConfig.xml 中的 <table>标签，表的配置，可以通过，net.yuanmomo.dwz.mybatis.generator.XMLGenerator 来自动生成，然后手动拷贝到配置文件。
        	
        
 3. 假定项目名称为 TestPro，添加至tomcat下，默认8080端口启动  
 4. Controller层``net.yuanmomo.dwz.controller.mybatis.TestController``，提供了增、删、改、查方法  
 5. 在 eclipse 中，找到 ``net.yuanmomo.dwz.BasicTest``类，并运行 JUnit 测试，注意启动数据库，数据库库名和表明
 6. 如果成功运行该 BasicTest 后，部署项目到 Tomcat 中,并启动项目。
 7. 打开浏览器，输入: http://localhost:8080/login.html 打开登录页面，然后输入 admin:admin，用户名和密码都是 admin。登录到后台管理平台。
 8. 左边选择配置列表。然后在右边任意修改输入框中的值，点击 [批量保存] 按钮，然后插件数据库是否成功写入。

