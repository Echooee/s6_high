# Nginx

## 正向代理服务器

* 类似翻墙软件，通过正向代理服务器，获取翻墙资源

## 反向代理服务器

* 提供了根据浏览器请求，反向访问服务器资源
  * 可以根据请求的顺序，决定访问的服务器的顺序(负载均衡)

![image-20210724160825276](https://github.com/Echooee/s6_high/tree/images/image-20210724160825276.png)

## Nginx安装

1. 安装前环境准备

```shell
yum install gcc openssl openssl-devel pcre pcre-devel zlib zlib-devel -y
```

2. 上传资源到虚拟机中
   * Nginx资源
   * Tomcat资源
3. 加压缩Nginx资源到local目录中

```shell
tar -zxvf nginx-1.14.2.tar.gz -C /usr/local
```

4. 指定编译安装后的文件位置
   * 进入`nginx-1.14.2`目录中，然后执行命令

```shell
./configure --prefix=/usr/local/nginx
```

5. 编译和安装

   * `make`   
   * `make install`
   * 安装命令执行完成后，就会在/usr/local目录中多出nginx的文件夹

6. 启动Nginx

   * 进入编译后的nginx目录(/usr/loca/nginx)
   * 进入sbin目录，启动
     * ./nginx
     * /usr/local/nginx/sbin/nginx

7. 测试

   * 访问Nginx的欢迎页面
   * 在虚拟机中输入
     * curl localhost:80

   ![image-20210724163433872](https://gitee.com/lmx1989/sz2103/raw/images/image-20210724163433872.png)

   * 通过window的浏览器访问是访问不了
     * 因为防火墙问题，没有关闭，导致无法被访问，无法访问80端口

## 防火墙管理

* 在企业中，对于防火墙的管理绝对不是关闭的

  * 必须是开启的

* 命令

  * 关闭

  `systemctl stop firewalld`

  * 开启

  `systemctl start firewalld`

  * 查看防火墙状态

  `systemctl status firewalld`

  * 开放防火墙的端口访问

  `firewall-cmd --add-port=80/tcp --permanent`

  * 重新加载防火墙，立即生效

  `firewall-cmd --reload`

  * 查询以开放的防火墙端口

  `firewall-cmd --list-ports`

  * 关闭防火墙的端口访问

  `firewall-cmd --remove-port=80/tcp --permanent`

## Nginx加载流程

* 浏览器发送请求

  * `http://192.168.206.129:80`

* 得到Nginx的响应结果

  ![image-20210724171239086](https://gitee.com/lmx1989/sz2103/raw/images/image-20210724171239086.png)

* 分析原因如何得到：
  * 在开启nginx时，会默认加载配置文件/conf/nginx.conf
  * 查看配置文件
  * nginx会拦截当前ip地址的80端口的根目录
  * 只要匹配了当前的请求，就会访问html目录中的index.html
    * 这就是我们的欢迎页面

![image-20210724172134550](https://gitee.com/lmx1989/sz2103/raw/images/image-20210724172134550.png)

## Nginx的启动和关闭

* 启动Nginx
  * 默认启动

    * ./nginx
    * /usr/local/nginx/sbin/nginx
      * 加载默认配置文件，/usr/local/nginx/conf/nginx.conf
      * 配置文件，是有多个的
        * /usr/local/nginx/conf/nginx.conf
        * /usr/local/nginx-1.14.2/conf/nginx.conf

  * 指定配置文件启动`(常用)`

    * `/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf`

  * 启动前检查配置文件的语法

    * `/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf -t`

  * 在vim、vi编辑器中查看行号

    * `:set number`
    * 上方语法有问题，下方没问题

    ![image-20210724173121536](https://gitee.com/lmx1989/sz2103/raw/images/image-20210724173121536.png)

* 关闭

  * 强制关闭
    * kill -9 PID
  * 优雅关闭
    * kill -QUIT PID
  * 快速关闭
    * kill -TERM PID

* 重启`(了解)`

  * 单台Nginx可以使用重启命令，但是多台，就无法指定重启哪一台了
    * `./nginx -s reload`

## Nginx核心配置文件

```text
#配置worker进程运行用户 nobody也是一个linux用户，一般用于启动程序，没有密码
user  nobody;  
#配置工作进程数目，根据硬件调整，通常等于CPU数量或者2倍于CPU数量
worker_processes  1;  

#配置全局错误日志及类型，[debug | info | notice | warn | error | crit]，默认是error
error_log  logs/error.log;  
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

pid        logs/nginx.pid;  #配置进程pid文件 


###====================================================


#配置工作模式和连接数
events {
    worker_connections  1024;  #配置每个worker进程连接数上限，nginx支持的总连接数就等于worker_processes * worker_connections
}

###===================================================


#配置http服务器,利用它的反向代理功能提供负载均衡支持
http {
    #配置nginx支持哪些多媒体类型，可以在conf/mime.types查看支持哪些多媒体类型
    include       mime.types;  
    #默认文件类型 流类型，可以理解为支持任意类型
    default_type  application/octet-stream;  
    #配置日志格式 
    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #配置access.log日志及存放路径，并使用上面定义的main日志格式
    #access_log  logs/access.log  main;

    sendfile        on;  #开启高效文件传输模式
    #tcp_nopush     on;  #防止网络阻塞

    #keepalive_timeout  0;
    keepalive_timeout  65;  #长连接超时时间，单位是秒

    #gzip  on;  #开启gzip压缩输出
	
	###-----------------------------------------------
	

    #配置虚拟主机
    server {
        listen       80;  #配置监听端口
        server_name  localhost;  #配置服务名

        #charset koi8-r;  #配置字符集

        #access_log  logs/host.access.log  main;  #配置本虚拟主机的访问日志

		#默认的匹配斜杠/的请求，当访问路径中有斜杠/，会被该location匹配到并进行处理
        location / {
	    #root是配置服务器的默认网站根目录位置，默认为nginx安装主目录下的html目录
            root   html;  
	    #配置首页文件的名称
            index  index.html index.htm;  
        }		

        #error_page  404              /404.html;  #配置404页面
        # redirect server error pages to the static page /50x.html
        #error_page   500 502 503 504  /50x.html;  #配置50x错误页面
        
	#精确匹配
	location = /50x.html {
            root   html;
        }

		#PHP 脚本请求全部转发到Apache处理
        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

		#PHP 脚本请求全部转发到FastCGI处理
        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

		#禁止访问 .htaccess 文件
        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }

	
	#配置另一个虚拟主机
    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

	
	#配置https服务，安全的网络传输协议，加密传输，端口443，运维来配置
	#
    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}
}
```

* 主要配置信息
  * server
    * listen
    * server_name
  * http
    * 当中的负载均衡配置
