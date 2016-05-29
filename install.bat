@echo off
setlocal EnableDelayedExpansion
ECHO.
COLOR 0a
ECHO. 说 明
ECHO --------------------------------------------------------------------
ECHO @Copyright 2013 Create By Hongbin.Yuan QQ:342398690 日期：2015-10-12 22:59:00
ECHO.
ECHO 重要：直接双击该文件即可进行安装

ECHO 开始编译 framework
SET COMMAND=mvn -X -DskipTests=true install
CALL %COMMAND%
ECHO 清理项目
SET COMMAND=mvn -X clean
CALL %COMMAND%

ECHO 创建 framework-init 项目骨架
cd framework-init
SET COMMAND=mvn -X clean
CALL %COMMAND%
SET COMMAND=mvn -X archetype:create-from-project
CALL %COMMAND%
cd target/generated-sources/archetype
SET COMMAND=mvn -X clean
CALL %COMMAND%
SET COMMAND=mvn -X install
CALL %COMMAND%
cd ../../../../
ECHO 清理项目
SET COMMAND=mvn -X clean
CALL %COMMAND%



ECHO 创建 framework-dwz 项目骨架
cd framework-dwz
SET COMMAND=mvn -X clean
CALL %COMMAND%
SET COMMAND=mvn -X archetype:create-from-project
CALL %COMMAND%
cd target/generated-sources/archetype
SET COMMAND=mvn -X clean
CALL %COMMAND%
SET COMMAND=mvn -X install
CALL %COMMAND%
cd ../../../../
ECHO 清理项目
SET COMMAND=mvn -X clean
CALL %COMMAND%

:_exit
pause
