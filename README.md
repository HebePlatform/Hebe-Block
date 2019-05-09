----
#欢迎使用HEBE！#

----
## HEBE 是什么 ##
HEBE是基于NXT开发的区块链系统，公链发行，使用HEBE区块链，你可以基于此平台开发自己的业务系统，如ICO、P2P等业务平台。
更多信息请访问：https://hebeblock.com/

----
## 必备条件 ##
	HEBE 是基于Java 8开发的，所以需要Java8的开发环境，至少需要一个JRE8的运行环境

    - *一般情况* - Java 8
    - *Ubuntu* - `http://www.webupd8.org/2012/09/install-oracle-java-8-in-ubuntu-via-ppa.html`
    - *Debian* - `http://www.webupd8.org/2014/03/how-to-install-oracle-java-8-in-debian.html`
    - *FreeBSD* - `pkg install openjdk8`
    - *CentOS  - `yum install java-1.8.0-openjdk.x86_64 java-1.8.0-openjdk-devel.x86_64`

## *源码获取*  ##
    `git clone https://github.com/HebePlatform/Hebe-Block.git`
  
----

## 编译  ##
	进入HEBE目录，下载程序包含已编译的程序，如果需要，可以重新编译
	cd <hebe_dir>
	./compile.sh
	
## 运行! ##
	在HEBE目录中
  - Unix: `./start.sh` (后台运行） 或者 `./run.sh` （前台运行，Ctl+C可终止）
  - Mac: `./start.sh`(启动桌面程序） ，或者 `./run.sh`（前台运行， Ctl+C可终止）
  - Window: `run.bat`

  - 等待JavaFX程序打开，即可登录
  - 如果您的机器没有JavaFX环境，可以用任何浏览器打开地址 `http://localhost:8082/`

----
## 改动说明 ##
	- NXT到HEBE，采用最新的NRS 1.11.5 版本
	- 修改了每个块的交易数量最大为512个
 	- 锻造时间大概60秒
	- block和transaction的版本修改为3，因为是全新的数据链
	- HTTP接口请求增加了RSA公私钥加解密机制
	- 翻译优化
	- 钱包客户端缺省中文
	
----

## 其它 ##
    - USERS-GUIDE.md
    - DEVELOPERS-GUIDE.md
    - OPERATORS-GUIDE.md
