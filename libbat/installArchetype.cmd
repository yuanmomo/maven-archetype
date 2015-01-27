ECHO 获取当前目录
cd %cd%\%1

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

ECHO 进入项目的根目录
CD ../../../

ECHO 调用清空命令(先清空当前目录的target),清空当前编译生成的文件
ECHO maven clean this directory
SET COMMAND=mvn clean
CALL %COMMAND%

REM 退回上一层目录
cd ..
echo congratulations, %1% install succeed.