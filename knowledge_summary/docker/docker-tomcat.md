# docker tomcat

```
docker pull tomcat:8

指定访问端口
docker run -it --rm -p 8888:8080 tomcat:8.0

运行项目
docker run -d --rm --name 项目名 \
-p  外部访问端口:8080 \
-v /usr/local/app/项目名.war:/usr/local/tomcat/webapps/外部访问项目名.war  \
tomcat:8

#sample
docker run -d --rm --name dubbo-admin \
-p  9090:8080 \
-v /usr/local/app/dubbo-admin-2.5.10.war:/usr/local/tomcat/webapps/dubbo-admin.war  \
tomcat:8
```