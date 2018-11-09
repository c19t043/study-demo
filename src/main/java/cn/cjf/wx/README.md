# 微信
## 用户同意授权
进入这个页面的时候就让用户同意。注意：好像是静默授权的，用户不知道

~~~url
https://open.weixin.qq.com/connect/oauth2/authorize?appid=appid&redirect_uri=url&response_type=code&scope=snsapi_userinfo&state=park#wechat_redirect
　　　　参数：appid:公众号的唯一标识

　　　　　　　redirect_uri:重定向的url,就是授权后要跳转的页面

　　　　　　　scope:应用授权作用域

　　　　　　　　　　snsapi_base:不弹出授权页面，直接跳转，只能获取用户openid

　　　　　　　　　　snsapi_userinfo:弹出授权页面，可通过openid拿到昵称、性别、所在地

　　　　　　   state:重定向后带的参数

　　　　2.用户同意后会产生一个code,只有5分钟时间的有效期。
~~~
