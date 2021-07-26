# Nginx day01

## 正向代理服务器

* 类似翻墙软件，通过正向代理服务器，获取翻墙资源

## 反向代理服务器

* 提供了根据浏览器请求，反向访问服务器资源
  * 可以根据请求的顺序，决定访问的服务器的顺序(负载均衡)

![image-20210724160825276](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210724160825276.png)

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

   ![image-20210724163433872](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210724163433872.png)

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

  ![image-20210724171239086](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210724171239086.png)

* 分析原因如何得到：
  * 在开启nginx时，会默认加载配置文件/conf/nginx.conf
  * 查看配置文件
  * nginx会拦截当前ip地址的80端口的根目录
  * 只要匹配了当前的请求，就会访问html目录中的index.html
    * 这就是我们的欢迎页面

![image-20210724172134550](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210724172134550.png)

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

    ![image-20210724173121536](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210724173121536.png)

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




# Nginx day02

## 静态资源部署

* 上传ace-master资源到Linux服务器中
* 将ace-master加压缩，放到/opt/static目录中
  * unzip ace-master
  * yum install -y unzip
* 配置文件，nginx.conf
  * 推荐复制一份再编写

```text
# 没有配置项目名称

location / {
	root /opt/static/ace-master;
	index index.html;
}

或

# 配置项目名称

location /ace-master {
	root /opt/static;
	index index.html;
}
```

## 负载均衡

* 概念
  * 将外部的请求，均衡或者按照Nginx的规则，来去进行转发
* Nginx的负载均衡种类
  * 轮询策略`(默认)`
    * 将请求平均分配给每一台服务器
    * 服务器配置，都是均衡
  * 权重策略
    * 将请求根据配置的权重比例，分配给不同的服务器
    * 服务器配置，不均衡，将性能优秀的服务器，尽可能的承载多一些，性能一般或较差的服务器，可以承载少一些
  * 最少连接策略
    * 将请求转发到连接数最少的那台服务器上
  * ip_hash策略
    * 根据浏览器的ip地址，进行hash和取模的运算，得到一个索引值
    * 根据该索引值可以一直访问一台服务器
    * 当ip地址发生变化时，也会重新的进行分配
    * 如果ip地址没有发生变化，就类似于单机使用

* 硬件的负载均衡和软件的负载均衡
  * 哪种提升的性能高呢？
  * 硬件的提升效率最高，但是成本太贵
  * 软件的负载均衡，虽然是提升效果是根据硬件配置，但是开源免费，性能开销小等优势
* 软件负载均衡
  * 服务器端的负载均衡
    * Nginx
      * 负载均衡只有4种
  * 客户端的负载均衡
    * SpringCloud中的ribbon
      * 有7种或以上的负载均衡，并且支持算法
* 实现负载均衡案例

![image-20210726154938751](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210726154938751.png)

* 实现步骤

  * 上传tomcat到Linux服务器中
  * 解压缩并复制两份出来，一共三份
  * 修改配置文件，9001/9002/9003
    * 修改4-5端口
  * 确定tomcat能够被访问
    * 环境没有问题
  * 配置Nginx配置文件
  * ![image-20210726155537815](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210726155537815.png)
    * 根据proxy_pass的配置的节点名称找到相对应的upstream的节点名称
    * 从而匹配到要负载均衡的服务器资源
    * 无需开放9001和9002和9003端口，由80端口进行映射

  ![image-20210726160313363](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210726160313363.png)

* 负载均衡的配置

  * 默认是轮询策略

  ```xml
  upstream www.myweb.com {
      server 127.0.0.1:9001;
      server 127.0.0.1:9002;
      server 127.0.0.1:9003;
  }
  
  server {
      listen       80;
      server_name  localhost;
  
      #charset koi8-r;
  
      #access_log  logs/host.access.log  main;
  
  	location / {
  		proxy_pass http://www.myweb.com;
  	}
  }
  ```

  * 权重配置

  ```text
  upstream www.myweb.com {
      server 127.0.0.1:9001 weight=2;
      server 127.0.0.1:9002 weight=1;
  }
  
  server {
      listen       80;
      server_name  localhost;
  
      #charset koi8-r;
  
      #access_log  logs/host.access.log  main;
  
  	location / {
  		proxy_pass http://www.myweb.com;
  	}
  }
  ```

  * 最少连接

  ```text
  upstream www.myweb.com {
      server 127.0.0.1:9001;
      server 127.0.0.1:9002;
      least_conn;
  }
  
  server {
      listen       80;
      server_name  localhost;
  
      #charset koi8-r;
  
      #access_log  logs/host.access.log  main;
  
  	location / {
  		proxy_pass http://www.myweb.com;
  	}
  }
  ```

  * ip_hash

  ```text
  upstream www.myweb.com {
      server 127.0.0.1:9001;
      server 127.0.0.1:9002;
      ip_hash;
  }
  
  server {
      listen       80;
      server_name  localhost;
  
      #charset koi8-r;
  
      #access_log  logs/host.access.log  main;
  
  	location / {
  		proxy_pass http://www.myweb.com;
  	}
  }
  ```

* 负载均衡的其它参数

  * down
    * 不参与负载均衡访问的服务器
  * backup
    * 备份机，在负载均衡没有故障的时候，是无法提供访问的，当所有的服务器都故障，当前备份机启动可以负载均衡访问

## 静态代理

![image-20210726170538935](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210726170538935.png)

* 实现

* 配置文件

  ```text
  upstream www.myweb.com {
          server 127.0.0.1:9001;
          server 127.0.0.1:9002;
          server 127.0.0.1:9003;
      }
  
  server {
      listen       80;
      server_name  localhost;
  
      #charset koi8-r;
  
      #access_log  logs/host.access.log  main;
  
      location /myweb {
      proxy_pass http://www.myweb.com;
      }
  
      location ~ .*\.(js|css|htm|html|gif|jpg|jpeg|png|bmp|swf|ioc|rar|zip|txt|flv|mid|doc|ppt|pdf|xls|mp3|wma)$ {
      root /opt/static;
  	}
  }
  ```

  

* 测试

  * 页面的状态码为200
  * 图片的状态码为403
    * 没有权限

  ![image-20210726170617440](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210726170617440.png)

* 修改图片的权限

  * 除了图片之外，还需要给/opt/static/myweb和/opt/static/myweb/image授权
    * chmod -R 755 /opt/static/myweb
      * 递归授权

  ![image-20210726170819101](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210726170819101.png)

## 动静分离

* 案例实现
* Nginx80

```text
upstream www.myweb.com {
        server 127.0.0.1:9001;
        server 127.0.0.1:9002;
        server 127.0.0.1:9003;
    }

    upstream static.myweb.com {
        server 127.0.0.1:81;
        server 127.0.0.1:82;
    }

    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        # dyminic
        location /myweb {
           proxy_pass http://www.myweb.com;
        }

        # static
        location ~ .*/(css|js|image) {
           proxy_pass http://static.myweb.com;
        }
    }
```

* Nginx81-82

```text
server {
        listen       81/82;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location ~ .*/(css|js|image) {
            root   /opt/static;
        }
    }
```

## 虚拟主机

* 实现

  * 修改hosts文件`Windows`

    * `C:\Windows\System32\drivers\etc\hosts`

    ![image-20210726173313143](https://raw.githubusercontent.com/Echooee/s6_high/images/image-20210726173313143.png)

    * 配置完成立即生效

  * 之前在部署的时候

    * 将myweb.war放到tomcat的webapps目录中，由Tomcat自动解压缩，进行访问
    * 此时如果想要访问，必须携带项目名称

  * 现在部署，不需要访问项目名称

    * Tom猫的资源在哪里？
      * tomcat/webapps/ROOT目录中
    * 可以将tom猫的资源全部删除，换成自己的
    * ROOT目录中不会解压缩war包
    * 将beijing.war、nanjing.war、tianjin.war拷贝到不同的tomcat上

  * Nginx配置文件

  ```text
      upstream beijing.myweb.com {
          server 127.0.0.1:9001;
      }
  
      upstream nanjing.myweb.com {
          server 127.0.0.1:9002;
      }
  
      upstream tianjin.myweb.com {
          server 127.0.0.1:9003;
      }
  
      server {
          listen 80;
          server_name tianjin.myweb.com;
          location / {
              proxy_pass http://tianjin.myweb.com;
          }
      }
  
      server {
          listen 80;
          server_name nanjing.myweb.com;
          location / {
              proxy_pass http://nanjing.myweb.com;
          }
      }
  
      server {
          listen       80;
          server_name  beijing.myweb.com;
  
          location / {
              proxy_pass http://beijing.myweb.com;
          }
  	}
  ```

  * 测试
    * 浏览器访问
      * beijing.myweb.com
      * nanjing.myweb.com
      * tianjin.myweb.com
