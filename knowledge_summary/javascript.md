# javascript

## 注意事项

+ 函数内部引用外部this作用域问题

> lambda 表达式 不用关注 this 作用域问题 , 否则需要 外部定义 let _this = this;

```javascript
var _this = this
var intervalId = setInterval(function() {
    console.log(_this);
},100)

var intervalId2 = setInterval(() => {
    console.log(this);
})
```

