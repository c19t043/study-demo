# docker error

##  x509: certificate has expired or is not yet valid.
这种错误，一般都是本地系统时间错误导致报错证书过期，所以先查看本地系统时间

```
date "+%Y-%m-%d"
2015-02-15

yum -y install ntp

#同步一下时间
ntpdate cn.pool.ntp.org

#时间同步成功
date "+%Y-%m-%d"
```
