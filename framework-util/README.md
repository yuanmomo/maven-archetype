#framework-util

工具包，包含常用的日期，MD5，数字，银联接口，常用类等常用工具。
  
  
  
## 安装：##
###OpenOffice###
* Window环境+服务  
    * 安装  
    已提供OpenOffice安装包`Apache_OpenOffice_4.0.1_Win_x86_install_zh-CN.exe`  
    按照安装向导完成安装即可  
    * 在命令行下启动服务：
	   1. 开始运行，输入CMD命令，进入命令行界面。切换到openOffice的安装路径下program文件夹下，例如：输入命令cd /d D:/Program Files (x86)/OpenOffice 4/program，回车。  
	   2. 再输入命令：soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard  
       3. 查看openoffice　默认的服务端口是否正常启用，输入命令netstat -ap tcp  
            *eg.(cmd窗口)*  
    
                C:\Windows\System32>cd /d D:/Program Files (x86)/OpenOffice 4/program  
            
                D:\Program Files (x86)\OpenOffice 4\program>soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard  
                  
                D:\Program Files (x86)\OpenOffice 4\program>  
* Linux环境+服务  
    * 安装  
        已提供OpenOffice安装包`Apache_OpenOffice_4.1.1_Linux_x86-64_install-rpm_zh-CN.tar.gz`  
        在命令行下执行：  
    	1. tar -xzvf Apache_OpenOffice_4.1.1_Linux_x86-64_install-rpm_zh-CN.tar.gz  
            解压后的目录是zh-CN，里面有RPMS、readmes、licenses。  
        2. 进入RPMS目录  
        3. 执行 rpm –ivh *rpm（安装所有rpm文件）  
        4. 进入到desktop-integration目录，选择安装相关的套件，选择redhat  
            rpm -ivh openoffice4.1.1-redhat-menus-4.1.1-9775.noarch.rpm  
  
        **这时openOffice己经安装完成，默认会安装在/opt下**  

    * 启动服务
        1. 进入opt下的openofiice4目录：cd /opt/openoffice4
        2. 进入program目录
        3. 执行　soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard & 
        4. 完成启动
        5. 查看openoffice　默认的服务端口是否正常启用，输入命令netstat -tln
        如果启动正常会有　tcp        0      0 127.0.0.1:8100              0.0.0.0:*                   LISTEN

###Windows字体引入###

* 第一步 
    * 在/usr/local/share/下新建文件夹msfonts  
* 第二步 
    * 利用sftp工具把windows系统下的字体文件*(仅中文字体即可)*copy到Linux系统/usr/local/share/msfonts文件夹下
    * windows系统字体文件存放于 C:\Windows\Fonts\ 文件夹下  
* 第三步
    * 修改/etc/fonts/fonts.conf，在以下内容处增加`<dir>/usr/local/share/msfonts</dir>`  
    
            <!-- Font directory list -->
            <dir>/usr/share/fonts</dir>
        	<dir>/usr/share/X11/fonts/Type1</dir> <dir>/usr/share/X11/fonts/TTF</dir> <dir>/usr/local/share/fonts</dir>
        	<dir>~/.fonts</dir><dir>/usr/local/share/msfonts</dir>

	
* 第四步
    * 建立字体缓存
        * cd /usr/local/share/msfonts
        * fc-cache -fv