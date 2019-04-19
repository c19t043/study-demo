# 互联网+教育业务

##  课程、老师、工作人员关系

+ 课程由老师创建，发布上架，下架
+ 工作人员有权，强制下架课程
+ 工作人员有审核，强制下架权限，没有修改权，强制下架课程，需要经过工作人员审核通过后，才能重新上架
+ 课程上线后，课程相关所有信息不的更改,需要更改课程信息，需要重新下架才能更改

### 课程

+ 表

```sql
create table course_info(
    id int primary key auto_increment comment '主键',
    name varchar(100) not null comment '课程名',
    teacher_id int not null comment '老师id',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '修改时间'
) comment ='课程表'
```

### 老师

+ 表

```
create table teacher_info(
    id int primary key auto_increment comment '主键',
    name varchar(100) not null comment '老师名',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '修改时间'
) comment ='老师表'
```

### 工作人员

+ 表

```
create table manager_info(
    id int primary key auto_increment comment '主键',
    name varchar(100) not null comment '工作人员名',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '修改时间'
) comment ='工作人员表'
```

### 课程发布表

+ 表

```
create table course_release(
    id int primary key auto_increment comment '主键',
    course_id int not null comment '课程名',
    teacher_id int default null comment '老师id',
    manager_id int default null comment '管理员id',
    status int not null comment '课程发布状态',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '修改时间'
) comment ='课程发布表'
```