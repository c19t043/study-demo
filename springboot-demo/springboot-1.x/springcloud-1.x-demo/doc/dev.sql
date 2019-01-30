# 用户密码
CREATE USER 'spring_cloud_demo'@'%' IDENTIFIED BY 'spring_cloud_demo';

# 创建库
CREATE DATABASE IF NOT EXISTS spring_cloud_demo default charset utf8 COLLATE utf8_general_ci;

#权限
grant all privileges on spring_cloud_demo.* to 'spring_cloud_demo'@'%';
#刷新系统权限表
flush privileges;

