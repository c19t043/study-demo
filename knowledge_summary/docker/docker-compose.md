# docker-compose

## build

+ build > 指定Dockerfile文件所在目录的绝对路径或相对路径，默认Dockerfile文件名为Dockerfile ：：：``

```yml
version: '3'
services:
  eureka01:
    build: .
```

+ 指定Dockerfile文件名,设定上下文根目录，然后以该目录为准指定 Dockerfile

```yml
version: '3'
services:
  eureka01:
    build:
      context: .
      dockerfile: dockerfile-eureka
```

## command

+ 覆盖容器启动时默认执行的命令

```yml
version: '3'
services:
  eureka01:
    build:
      context: .
      dockerfile: dockerfile-eureka
    command: --server.port=8080
```  

## ports

+ 指定对外暴露的端口

```yml
version: '3'
services:
  eureka01:
    build: ./eureka
    ports:
      - "8080:80"
```