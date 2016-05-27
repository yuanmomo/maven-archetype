echo 说 明
echo --------------------------------------------------------------------
echo @Copyright 2015 Create By Hongbin.Yuan  QQ:342398690 日期：2015-10-12 22:59:00

echo 开始编译 framework
mvn -DskipTests=true install

echo 创建 framework-init 项目骨架
cd framework-init
mvn archetype:create-from-project  -Darchetype.properties=type.properties
cd target/generated-sources/archetype
mvn  install
cd ../../../../

#echo 创建 framework-dwz 项目骨架
#cd framework-dwz
#mvn -X clean archetype:create-from-project
#cd target/generated-sources/archetype
#mvn -X install
#cd ../../../../

echo 清理项目
mvn  clean
