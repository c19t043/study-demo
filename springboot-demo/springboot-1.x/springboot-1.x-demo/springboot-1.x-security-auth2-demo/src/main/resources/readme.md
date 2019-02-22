重点关注一下/oauth/token，它是获取的token的endpoint。启动springboot应用之后，使用http工具访问 password模式：

http://localhost:8080/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456

响应如下： {"access_token":"950a7cc9-5a8a-42c9-a693-40e817b1a4b0","token_type":"bearer","refresh_token":"773a0fcd-6023-45f8-8848-e141296cb3cb","expires_in":27036,"scope":"select"}

client模式： http://localhost:8080/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456

响应如下： {"access_token":"56465b41-429d-436c-ad8d-613d476ff322","token_type":"bearer","expires_in":25074,"scope":"select"}

在配置中，我们已经配置了对order资源的保护，如果直接访问:http://localhost:8080/order/1会得到这样的响应:{"error":"unauthorized","error_description":"Full authentication is required to access this resource"} （这样的错误响应可以通过重写配置来修改）

而对于未受保护的product资源http://localhost:8080/product/1则可以直接访问，得到响应product id : 1

携带accessToken参数访问受保护的资源：

使用password模式获得的token:http://localhost:8080/order/1?access_token=950a7cc9-5a8a-42c9-a693-40e817b1a4b0，得到了之前匿名访问无法获取的资源：order id : 1

使用client模式获得的token:http://localhost:8080/order/1?access_token=56465b41-429d-436c-ad8d-613d476ff322，同上的响应order id : 1