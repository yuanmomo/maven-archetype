@echo off
setlocal EnableDelayedExpansion
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
cls
ECHO. 
ECHO --------------------------------------------------------------------
echo         请选择要安装的maven-archetype:
echo         [0] all
set a=1
for /D %%s in (*-*) do ( 
	echo         [!a!] %%s
	set /A a+=1
)
ECHO --------------------------------------------------------------------
ECHO.

REM 等待用户输入要安装的archetype
set /p chooseResult=请选择要安装的archetype类型中[]中的编号:0?

REM 如果直接回车，默认安装所有
if /i '%chooseResult%'=='' (
set /A chooseResult=0
)

echo 你输入的选项：%chooseResult%

set current=1
if %chooseResult% EQU 0 (
	for /D %%s in (*-*) do ( 
		rem ./libbat/installArchetype.cmd %%s
		call ./libbat/installArchetype.cmd %%s
	)
	goto _exit
)else if %chooseResult% GTR 0 (
	for /D %%s in (*-*) do (
		if %chooseResult% EQU !current! (
			rem ./libbat/installArchetype.cmd %%s
			call ./libbat/installArchetype.cmd %%s
			goto _exit
		)else (
			set /A current+=1
		)
	)
)

:_exit
pause

