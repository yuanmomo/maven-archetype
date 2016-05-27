echo 说 明
echo --------------------------------------------------------------------
echo @Copyright 2015 Create By Hongbin.Yuan  QQ:342398690 日期：2015-10-12 22:59:00

echo 开始编译 framework
mvn -DskipTests=true install

function createArchetype {
    cd framework-init
    mvn archetype:create-from-project -Darchetype.properties=type.properties
    cd target/generated-sources/archetype
    mvn  install
    cd ../../../../
}

git fetch --all

echo 创建 framework-common 项目骨架, 不带 DWZ 的项目
git checkout master
git pull
createArchetype

echo 创建 framework-dwz 项目骨架
git checkout dwz
git pull
createArchetype

echo 清理项目
mvn  clean
