# OpenResty

## 部署openresty

```shell
mkdir -p /usr/servers  
cd /usr/servers/

yum install -y readline-devel pcre-devel openssl-devel gcc

wget http://openresty.org/download/ngx_openresty-1.7.7.2.tar.gz  
tar -xzvf ngx_openresty-1.7.7.2.tar.gz  
cd /usr/servers/ngx_openresty-1.7.7.2/

cd bundle/LuaJIT-2.1-20150120/  
make clean && make && make install  
ln -sf luajit-2.1.0-alpha /usr/local/bin/luajit

cd bundle  
wget https://github.com/FRiCKLE/ngx_cache_purge/archive/2.3.tar.gz  
tar -xvf 2.3.tar.gz  

cd bundle  
wget https://github.com/yaoweibin/nginx_upstream_check_module/archive/v0.3.0.tar.gz  
tar -xvf v0.3.0.tar.gz  

cd /usr/servers/ngx_openresty-1.7.7.2  
./configure --prefix=/usr/servers --with-http_realip_module  --with-pcre  --with-luajit --add-module=./bundle/ngx_cache_purge-2.3/ --add-module=./bundle/nginx_upstream_check_module-0.3.0/ -j2  

./configure --prefix=/usr/local/app/openresty --with-http_realip_module  --with-pcre  --with-luajit --add-module=./bundle/ngx_cache_purge-2.3/ --add-module=./bundle/nginx_upstream_check_module-0.3.0/ -j2  
make && make install

cd /usr/servers/  
ll

/usr/servers/luajit
/usr/servers/lualib
/usr/servers/nginx
/usr/servers/nginx/sbin/nginx -V

启动nginx: /usr/servers/nginx/sbin/nginx
```

## nginx+lua开发的hello world

vi /usr/servers/nginx/conf/nginx.conf

在http部分添加：

lua_package_path "/usr/servers/lualib/?.lua;;";  
lua_package_cpath "/usr/servers/lualib/?.so;;";  

lua_package_path "/usr/local/app/openresty/lualib/?.lua;;";  
lua_package_cpath "/usr/local/app/openresty/lualib/?.so;;";

/usr/servers/nginx/conf下，创建一个lua.conf

server {  
    listen       80;  
    server_name  _;  
}  

在nginx.conf的http部分添加：

include lua.conf;

验证配置是否正确：

/usr/servers/nginx/sbin/nginx -t
/usr/local/app/openresty/nginx/sbin/nginx -t
在lua.conf的server部分添加：

location /lua {  
    default_type 'text/html';  
    content_by_lua 'ngx.say("hello world")';  
}

/usr/servers/nginx/sbin/nginx -t  

重新nginx加载配置

/usr/servers/nginx/sbin/nginx -s reload  
 /usr/local/app/openresty/nginx/sbin/nginx -s reload  

访问http: http://192.168.31.187/lua

vi /usr/servers/nginx/conf/lua/test.lua

ngx.say("hello world");

修改lua.conf

location /lua {  
    default_type 'text/html';  
    content_by_lua_file conf/lua/test.lua;
}

查看异常日志

tail -f /usr/servers/nginx/logs/error.log

tail   /usr/local/app/openresty/nginx/logs/error.log

## 工程化的nginx+lua项目结构

项目工程结构

hello
    hello.conf
    lua
      hello.lua
    lualib
      *.lua
      *.so

放在/usr/hello目录下

/usr/servers/nginx/conf/nginx.conf

worker_processes  2;  

error_log  logs/error.log;  

events {  
    worker_connections  1024;  
}  

http {  
    include       mime.types;  
    default_type  text/html;  
  
    lua_package_path "/usr/hello/lualib/?.lua;;";  
    lua_package_cpath "/usr/hello/lualib/?.so;;";
    include /usr/hello/hello.conf;  
}  

/usr/hello/hello.conf

/usr/local/app/openresty/nginx/conf/lua

server {  
    listen       80;  
    server_name  _;  
  
    location /lua {  
        default_type 'text/html';  
        lua_code_cache off;  
        content_by_lua_file /usr/example/lua/test.lua;  
    }  
}  

## 基于商品id的定向流量分发的策略，lua脚本来编写和实现

1、获取请求参数，比如productId
2、对productId进行hash
3、hash值对应用服务器数量取模，获取到一个应用服务器
4、利用http发送请求到应用层nginx
5、获取响应后返回

我们作为一个流量分发的nginx，会发送http请求到后端的应用nginx上面去，所以要先引入lua http lib包

cd /usr/hello/lualib/resty/  
wget https://raw.githubusercontent.com/pintsized/lua-resty-http/master/lib/resty/http_headers.lua  
wget https://raw.githubusercontent.com/pintsized/lua-resty-http/master/lib/resty/http.lua 

代码：

```lua
local uri_args = ngx.req.get_uri_args()
local productId = uri_args["productId"]

local host = {"192.168.31.19", "192.168.31.187"}
local hash = ngx.crc32_long(productId)
hash = (hash % 2) + 1  
backend = "http://"..host[hash]

local method = uri_args["method"]
local requestBody = "/"..method.."?productId="..productId

local http = require("resty.http")  
local httpc = http.new()  

local resp, err = httpc:request_uri(backend, {  
    method = "GET",  
    path = requestBody
})

if not resp then  
    ngx.say("request error :", err)  
    return  
end

ngx.say(resp.body)  
  
httpc:close()
```

/usr/servers/nginx/sbin/nginx -s reload

基于商品id的定向流量分发策略的lua脚本就开发完了，而且也测试过了

我们就可以看到，如果你请求的是固定的某一个商品，那么就一定会将流量打到固定的一个应用nginx上面去

## 三级缓存架构执行逻辑

1、应用nginx的lua脚本接收到请求

2、获取请求参数中的商品id，以及商品店铺id

3、根据商品id和商品店铺id，在nginx本地缓存中尝试获取数据

4、如果在nginx本地缓存中没有获取到数据，那么就到redis分布式缓存中获取数据，如果获取到了数据，还要设置到nginx本地缓存中

但是这里有个问题，建议不要用nginx+lua直接去获取redis数据

因为openresty没有太好的redis cluster的支持包，所以建议是发送http请求到缓存数据生产服务，由该服务提供一个http接口

缓存数生产服务可以基于redis cluster api从redis中直接获取数据，并返回给nginx

5、如果缓存数据生产服务没有在redis分布式缓存中没有获取到数据，那么就在自己本地ehcache中获取数据，返回数据给nginx，也要设置到nginx本地缓存中

6、如果ehcache本地缓存都没有数据，那么就需要去原始的服务中拉去数据，该服务会从mysql中查询，拉去到数据之后，返回给nginx，并重新设置到ehcache和redis中

## nginx最终利用获取到的数据，动态渲染网页模板

cd /usr/hello/lualib/resty/
wget https://raw.githubusercontent.com/bungle/lua-resty-template/master/lib/resty/template.lua
mkdir /usr/hello/lualib/resty/html
cd /usr/hello/lualib/resty/html
wget https://raw.githubusercontent.com/bungle/lua-resty-template/master/lib/resty/template/html.lua

在hello.conf的server中配置模板位置

set $template_location "/templates";  
set $template_root "/usr/hello/templates";

mkdir /usr/hello/templates

vi product.html

```html
<html>
        <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>商品详情页</title>
        </head>
<body>
商品id: {* productId *}<br/>
商品名称: {* productName *}<br/>
商品图片列表: {* productPictureList *}<br/>
商品规格: {* productSpecification *}<br/>
商品售后服务: {* productService *}<br/>
商品颜色: {* productColor *}<br/>
商品大小: {* productSize *}<br/>
店铺id: {* shopId *}<br/>
店铺名称: {* shopName *}<br/>
店铺等级: {* shopLevel *}<br/>
店铺好评率: {* shopGoodCommentRate *}<br/>
</body>
</html>
```

## 将渲染后的网页模板作为http响应，返回给分发层nginx

hello.conf中：

lua_shared_dict my_cache 128m;

lua脚本中：

```lua
local uri_args = ngx.req.get_uri_args()
local productId = uri_args["productId"]
local shopId = uri_args["shopId"]

local cache_ngx = ngx.shared.my_cache

local productCacheKey = "product_info_"..productId
local shopCacheKey = "shop_info_"..shopId

local productCache = cache_ngx:get(productCacheKey)
local shopCache = cache_ngx:get(shopCacheKey)

if productCache == "" or productCache == nil then
    local http = require("resty.http")
    local httpc = http.new()

    local resp, err = httpc:request_uri("http://192.168.31.179:8080",{
        method = "GET",
        path = "/getProductInfo?productId="..productId
        })

    productCache = resp.body
    cache_ngx:set(productCacheKey, productCache, 10 * 60)
end

if shopCache == "" or shopCache == nil then
    local http = require("resty.http")
    local httpc = http.new()

    local resp, err = httpc:request_uri("http://192.168.31.179:8080",{
          method = "GET",
          path = "/getShopInfo?shopId="..shopId
    })

    shopCache = resp.body
    cache_ngx:set(shopCacheKey, shopCache, 10 * 60)
end

local cjson = require("cjson")
local productCacheJSON = cjson.decode(productCache)
local shopCacheJSON = cjson.decode(shopCache)

local context = {
    productId = productCacheJSON.id,
    productName = productCacheJSON.name,
    productPrice = productCacheJSON.price,
    productPictureList = productCacheJSON.pictureList,
    productSpecification = productCacheJSON.specification,
    productService = productCacheJSON.service,
    productColor = productCacheJSON.color,
    productSize = productCacheJSON.size,
    shopId = shopCacheJSON.id,
    shopName = shopCacheJSON.name,
    shopLevel = shopCacheJSON.level,
    shopGoodCommentRate = shopCacheJSON.goodCommentRate
}

local template = require("resty.template")
template.render("product.html", context)
```

## 70_基于nginx+lua完成商品详情页访问流量实时上报kafka的开发

在nginx这一层，接收到访问请求的时候，就把请求的流量上报发送给kafka

这样的话，storm才能去消费kafka中的实时的访问日志，然后去进行缓存热数据的统计

用得技术方案非常简单，从lua脚本直接创建一个kafka producer，发送数据到kafka

wget https://github.com/doujiang24/lua-resty-kafka/archive/master.zip

yum install -y unzip

unzip lua-resty-kafka-master.zip

cp -rf /usr/local/lua-resty-kafka-master/lib/resty /usr/hello/lualib

nginx -s reload

```lua
local cjson = require("cjson")  
local producer = require("resty.kafka.producer")  

local broker_list = {  
    { host = "192.168.31.187", port = 9092 },  
    { host = "192.168.31.19", port = 9092 },  
    { host = "192.168.31.227", port = 9092 }
}

local log_json = {}  
log_json["headers"] = ngx.req.get_headers()  
log_json["uri_args"] = ngx.req.get_uri_args()  
log_json["body"] = ngx.req.read_body()  
log_json["http_version"] = ngx.req.http_version()  
log_json["method"] =ngx.req.get_method() 
log_json["raw_reader"] = ngx.req.raw_header()  
log_json["body_data"] = ngx.req.get_body_data()  

local message = cjson.encode(log_json);  

local productId = ngx.req.get_uri_args()["productId"]

local async_producer = producer:new(broker_list, { producer_type = "async" })   
local ok, err = async_producer:send("access-log", productId, message)  

if not ok then  
    ngx.log(ngx.ERR, "kafka send err:", err)  
    return  
end
```

两台机器上都这样做，才能统一上报流量到kafka

bin/kafka-topics.sh --zookeeper 192.168.31.187:2181,192.168.31.19:2181,192.168.31.227:2181 --topic access-log --replication-factor 1 --partitions 1 --create

bin/kafka-console-consumer.sh --zookeeper 192.168.31.187:2181,192.168.31.19:2181,192.168.31.227:2181 --topic access-log --from-beginning

（1）kafka在187上的节点死掉了，可能是虚拟机的问题，杀掉进程，重新启动一下

nohup bin/kafka-server-start.sh config/server.properties &

（2）需要在nginx.conf中，http部分，加入resolver 8.8.8.8;

（3）需要在kafka中加入advertised.host.name = 192.168.31.187，重启三个kafka进程

（4）需要启动eshop-cache缓存服务，因为nginx中的本地缓存可能不在了

## 在流量分发+后端应用双层nginx中加入接收热点缓存数据的接口

流量分发

```lua
local uri_args = ngx.req.get_uri_args()
local product_id = uri_args["productId"]

local cache_ngx = ngx.shared.my_cache

local hot_product_cache_key = "hot_product_"..product_id

cache_ngx:set(hot_product_cache_key, "true", 60 * 60)
```

后端应用

```lua
local uri_args = ngx.req.get_uri_args()
local product_id = uri_args["productId"]
local product_info = uri_args["productInfo"]

local product_cache_key = "product_info_"..product_id

local cache_ngx = ngx.shared.my_cache

cache_ngx:set(product_cache_key,product_info,60 * 60)
```

## 在nginx+lua中实现热点缓存自动降级为负载均衡流量分发策略的逻辑

```lua
local uri_args = ngx.req.get_uri_args()
local productId = uri_args["productId"]
local shopId = uri_args["shopId"]

local hosts = {"192.168.31.187", "192.168.31.19"}
local backend = ""

local hot_product_key = "hot_product_"..productId

local cache_ngx = ngx.shared.my_cache
local hot_product_flag = cache_ngx:get(hot_product_key)

if hot_product_flag == "true" then
  math.randomseed(tostring(os.time()):reverse():sub(1, 7))
  local index = math.random(1, 2)  
  backend = "http://"..hosts[index]
else
  local hash = ngx.crc32_long(productId)
  local index = (hash % 2) + 1
  backend = "http://"..hosts[index]
end

local requestPath = uri_args["requestPath"]
requestPath = "/"..requestPath.."?productId="..productId.."&shopId="..shopId

local http = require("resty.http")
local httpc = http.new()

local resp, err = httpc:request_uri(backend,{
  method = "GET",
  path = requestPath
})

if not resp then
  ngx.say("request error: ", err)
  return
end

ngx.say(resp.body)

httpc:close()
```