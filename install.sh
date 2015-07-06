echo 说 明
echo --------------------------------------------------------------------
echo @Copyright 2015 Create By yuanmomo QQ:342398690 日期：2015-06-07 22:59:00

echo 开始编译，安装MBG自定义插件包
cd generatorPlugin
mvn clean compile package install
mvn clean
cd ..
echo 安装MBG自定义插件包成功

echo --------------------------------------------------------------------
echo 本批处理执行后，将作以下一些设置,确认后继续：
echo 1、请根据README.md指示，创建一个项目来确定是否安装成功
echo --------------------------------------------------------------------
echo         请选择要安装的maven-archetype:
echo         [0] all
declare -i a=1
for file in `ls . | grep -`  
    	do  
   		echo         [${a}]  $file
		((a++))
	done  
echo --------------------------------------------------------------------

# 等待用户输入要安装的archetype
echo "请选择要安装的archetype类型中[]中的编号:0?"
read chooseResult

# 如果直接回车，默认安装所有
if [ ! -n "${chooseResult}" ]; then
	chooseResult=0
fi 
echo 你输入的选项：${chooseResult}


function install(){
	echo 开始编译，安装该项目
	echo enter this project...$1	
	cd $1
	echo clean this project...$1
	mvn clean
	echo create archetype...$1
	mvn archetype:create-from-project
	echo install archetype...$1
	cd target/generated-sources/archetype
	mvn clean install
	cd ../../../
	echo clean this project...$1	
	mvn clean
	cd .. 
	echo congratulations, $1 install succeed.
}

declare -i current=1
if [[ "0" -eq "$chooseResult" ]]; then  
	for temp in `ls | grep -`
        do
		echo $temp
       		install $temp 
	done	
else  
	for temp in `ls | grep -`
        do	
               	if [[ "$current" -eq "$chooseResult" ]]; then
			install $temp	
			break;	
		else
			((current++))
		fi 
        done

fi  

