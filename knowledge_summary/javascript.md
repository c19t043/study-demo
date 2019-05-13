# javascript

## 模块化 和 组件化

+ 模块化

是从代码逻辑的角度进行划分的，方便代码分层开发，保证每个功能模块的职能单一

+ 组件化

是从ui界面的角度进行划分的，前端的组件化，方便ui组件的重用

## 什么是路由

+ **后端路由** 对于普通的网站，所有的超链接都是URL地址，
所有的URL地址都对应服务器上的资源

+ **前端路由** 对于单页面应用程序来说，主要通过URL中的hash(#号)来实现
不同页面之间的切换，同时，hash有一个特点：http请求中不会包含hash相关的内容
所以，单页面程序中的页面跳转主要用hash实现

+ 在单页面应用程序中，这通通过hash改变来切换页面的方式，称作前端路由

## localStorage

+ `localStorage.setItem('key','value')`

localStorage 只支持存放字符串数据，要先调用JSON.stringify

+ `localStorage.setItem('key')`

获取后要JSON.parse将字符串转为json对象

## 数组操作

### 删除,插入

+ `splice`

> `splice(index,删除个数,索引位置插入数据)`

```javascript
let arr = [1,2,3,4];
arr.splice(1,1,3)
```
### 查找

+ `some`

```javascript
let arr = [1,2,3,4];
arr.some((num) => {
    if(num === 2){
        // 返回true 就停止后序操作
        return true;
    }
})
```

+ `findIndex`

> 查找索引

```javascript
let arr = [1,2,3,4];
const index = arr.findIndex((num) => {
    if(num === 2){
        // 返回true 就停止后序操作
        return true;
    }
})
```

+ `indexOf`

> 查找索引

```javascript
let name = 'zhangsan';
const index = name.indexOf('zhangsan');
console.log(index);
```

+ `foreach`

> 遍历

```javascript
const arr = [1,2,3,4];
arr.forEach(num => {
    console.log(num);
})
```

+ `includes`

> 包含,es6语法

```javascript
const name = 'zhangsan';
if(name.includes('zhang')){
    console.log(true)
}else{
    console.log(false)
}
```

+ `filter`

> 过滤

```javascript
let arr = [1,2,3,4];
this.arr.filter(num =>{
   if(num > 3){
      return num;
   }
})
```
## 替换

+ `replace`

> `replace(表达式，要替换成的字符串)`
表达式可以是字符串，也可以是正则表达式

```javascript
let name = 'zhangsan';
name = name.replace('san','si');

name = name.replace(/n/g,'s');
```

## 模板字符串

```javascript
dateFormat(dateObj,pattern=''){
    const dt = new Date(dateObj);
    
    //获取年月日
    const y = dt.getFullYear();
    const m = (dt.getMonth() + 1).toString().padStart(2,'0');
    const d = dt.getDate().toString().padStart(2,'0');
    
    if(pattern.toLowerCase() === 'yyyy-mm-dd'){
        //return y+"-"+m+"-"+d;
        return '${y}-${m}-${d}'
    }else{
        const hh = dt.getHours().toString().padStart(2,'0');
        const mm = dt.getMinutes().toString().padStart(2,'0');
        const ss = dt.getSeconds().toString().padStart(2,'0');
        return '${y}-${m}-${d} ${hh}:${mm}:${ss}'
    }
}
```

## 字符串长度不足，填充数据

### 头部填充

+ `padStart`

> `padStart(maxLength,filleString='')` es6语法, fillString 不足最大长度，用来填充长度的字符串

### 尾部填充

+ `padEnd`

> `padEnd(maxLength,filleString='')` es6语法, fillString 不足最大长度，用来填充长度的字符串

## 注意事项

+ 函数内部引用外部this作用域问题

> lambda 表达式 不用关注 this 作用域问题 , 否则需要 外部定义 let _this = this;

```javascript
let _this = this;
let intervalId = setInterval(function() {
    console.log(_this);
},100);

let intervalId2 = setInterval(() => {
    console.log(this);
});
```

