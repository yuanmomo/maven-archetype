@echo off
ECHO.
COLOR 0a
ECHO. 说 明
ECHO --------------------------------------------------------------------
ECHO @Copyright 2013 Create By yuanmomo QQ:342398690 日期：2013-12-10 22:59:00
ECHO.
ECHO 重要：直接双击该文件即可进行安装
ECHO --------------------------------------------------------------------
ECHO 本批处理执行后，将作以下一些设置,确认后继续：
ECHO 1、请根据README.md指示，创建一个项目来确定是否安装成功
ECHO --------------------------------------------------------------------
ECHO.


ECHO 获取当前目录
cd %cd%\spring-springmvc-mybatis

ECHO 调用清空命令(先清空当前目录的target)
ECHO maven clean this directory
SET COMMAND=mvn clean
CALL %COMMAND%

ECHO 编译并生成该项目对应的archetype
ECHO maven create archetype
SET COMMAND=mvn archetype:create-from-project
CALL %COMMAND%

ECHO 进入生成的target/generated-sources/archetype
ECHO cd target to install
CD target/generated-sources/archetype

ECHO 安装当前的archetype到本地的Maven库
ECHO maven install
SET COMMAND=mvn clean install
CALL %COMMAND%

ECHO 进入项目的根目录target/generated-sources/archetype
ECHO cd target to install
CD ../../../

ECHO 调用清空命令(先清空当前目录的target),清空当前编译生成的文件
ECHO maven clean this directory
SET COMMAND=mvn clean
CALL %COMMAND%

echo congratulations, install succeed.
pause