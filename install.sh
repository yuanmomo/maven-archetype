echo 说 明
echo --------------------------------------------------------------------
echo @Copyright 2015 Create By Hongbin.Yuan  QQ:342398690 日期：2015-10-12 22:59:00

echo 创建 framework-init 项目骨架
cd framework-init
mvn clean archetype:create-from-project
cd target/generated-sources/archetype
mvn  install
cd ../../../../

echo 清理项目
mvn clean
