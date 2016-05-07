#framework-init

集成了 Spring, Spring MVC, MyBatis的开发环境并提供国际化支持，以及包含一个 Demo 表操作的代码。  

## 介绍说明：##

---------------------------------------
## SpringMVC+MyBatis-单表操作Demo ##

    此Demo集成了 Spring, Spring MVC, MyBatis的开发环境，并提供国际化支持。

- 方法介绍  

 1. 按照上述文档成功创建一个项目，导入数据库脚本  
 2. 使用 MyBatis Generator 自动生成工具生成Demo代码  
        * 
        ***注：*** 
        1. Test代码在项目中已生成，若需上手练习生成代码，可另建一张表来自动生成代码，亦可以框架提供的数据库脚本中的表来自动生成代码以覆盖 Test 代码。生成类：src/test/java/GeneratorMain . 
        2. generatorConfig.xml 中的 <table>标签，表的配置，可以通过，src/test/java/XMLGenerator 来自动生成，然后手动拷贝到配置文件。
        
 3. 假定项目名称为 demoPro，添加至tomcat下，默认8080端口启动  
 4. Controller层``net.yuanmomo.framework.controller.mybatis.DemoController``，提供了增、删、改、查方法  
 5. 可使用POSTMAN工具直接访问 ``net.yuanmomo.framework.controller.mybatis.DemoController``，例如我们访问查询方法  
 6. 访问``http://localhost:8080/demoPro/demo/jsp/selectDemoList.do``，提供查询参数，如图所示  
        ![PostMan](http://i.imgur.com/ix7CcYE.png)  
 7. 点``Send``，查询方法执行完毕后跳转到out.jsp，在POSTMAN页面下方显示结果，不出意外的话，会显示HTTP Status 500错误，错误日志其中有  

            javax.servlet.ServletException: javax.servlet.jsp.JspTagException: No message found under code '没有记录。' for locale 'zh_CN'.  

 8. 此时便涉及到国际化资源，查看out.jsp，有如下输出代码  

            <spring:message code="${message}"></spring:message>  
 由于我们从Controller中返回的结果code="没有记录。"，在我们的国际化资源文件message_zh_CN.properties或message_en.properties中没有找到，所以会出现异常，现我们将Controller中selectDemoList方法返回结果"没有记录。"改为"NO_RECORDS"，则返回正常结果  

            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                <title>处理结果...</title>
            </head>
            没有记录!  

 9. 切换语言版本使用 ``http://localhost:8080/demoPro/test/i18n/lang.do?langType=en``切换至英文版（切换国际化语言,langType=zh,中文；langType=en,英文），此时访问查询得到结果  

        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
            <title>Process Result...</title>
        </head>
        no records!  
    
 10. 国际化资源配置在src/main/resources/config/ApplicationContext.xml中，代码如下所示：  

        <!-- ================================ 配置国际化资源文件路径 ============================================= -->
        <bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
        	<!-- 定义消息资源文件的相对路径 -->
        	<property name="basename" value="i18n/message"/>
        </bean>