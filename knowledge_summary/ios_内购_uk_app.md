# ios内购优课app业务分析

## IOS内购与其他购买有什么区别

+ ios购买，与ios设备唯一标识有关，
即使用户未登录，用户也能查看购买的信息
+ ios内购，用户购买先走IOS服务内部购买机制，
然后通知IOS设备是否购买成功，若哟修改后台数据，需要
IOS设置主动调用后台接口

## 如何处理区别

+ ios内购流程有别于其他，必须跟ios设备唯一标识绑定
跟服务内部账户系统不同，需要建立，ios设备与系统账户之间的关系
    - 登录APP，初始化为游客账户
    - 如果使用本地账户注册/登录，建立游客与本地账户关系
    - 游客，与本地账户产生的数据，可以分开存，也可以一起存，看设计
+ 内购只能购买配置的产品
    - 如果每上线一个产品，就IOS服务上就配置一个产品，
    那将增加产品上线流程，增加额外工作量，
    并且配置的产品也有数量限制，可配置少量充值产品，
    内购后，生成游客账户余额
    - 用户购买使用游客账户余额购买