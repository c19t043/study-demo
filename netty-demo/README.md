# 仿微信聊天v0.4
v0.4主要解决v0.3性能问题,增加webSocket功能

## 系统功能
+ 单聊  
    - 1、指定用户聊天
+ 群聊（待开发）   
    - 1、建立群组  
    - 2、拉人加入指定群组
    - 3、申请加入指定群组
    - 4、多人聊天
+ 广播（待开发）  
    - 1、向所有人发送消息  
    - 2、向指定人发送消息    
+ 登录认证

## 遇到的问题
+ 1、（**解决**）服务端响应指定客户端请求，存在问题
    - 0、(**目前解决方案**)父类定义messageId的Setter/getter方法,子类定义字段，重写父类方法
    - 1、数据包重新定义：客户端与服务端请求响应：一个请求数据包对象，对应一个响应数据包对象
    - 2、客户端发送消息给另外一个客户端，使用同一个数据包对象 
        
## v.4版本解决问题
+ 1、客户端连接服务器失败重试，断线重连
+ 2、心跳检查功能
+ 3、解决拆包、粘包问题
+ 4、服务端性能问题
+ 5、webSocket

## 待开发功能
+ 1、群组命令：创建群组
+ 2、群组命令：展示客户端自己拥有的群组
+ 3、群组命令：将指定用户加入到指定群组中
+ 4、群组命令：用户申请加入指定群组
+ 5、群聊命令（多对多）：开始
    - 1、客户端输入要进入聊天的群组，然后发送客户端发送消息
    - 2、服务端接收到客户端发送过来的消息，转发给群组内其他成员
    - 3、客户端收到服务端发送过来的消息，直接打印在控制台
+ 6、群聊命令（多对多）：退出


## v0.3版本总结
+ 1、客户端在控制台录入命令和消息
+ 2、命令格式：【@命令】，
+ 3、单聊：**userId:[userId]:消息**，群聊：**groupId:[groupId]:消息**
+ 4、单聊回复：**!@userId:[userId]:消息**，群聊回复：**!@groupId:[groupId]:消息** 
+ 5、定义了传输协议，包含数据类型，消息id，数据包
+ 6、完成用户登录命令，单聊命令，以及其功能
+ 7、同一时间只能执行一组命令，除回复消息，如果要执行其他命令，必须先退出组命令，再切换到其他组命令

