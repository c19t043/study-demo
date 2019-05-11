# javascript

## 数组操作

### 删除,插入

+ `splice`

> `splice(index,删除个数,索引位置插入数据)`

```javascript
let arr = [1,2,3,4]
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
var index = arr.findIndex((num) => {
    if(num === 2){
        // 返回true 就停止后序操作
        return true;
    }
})
```

+ `indexOf`

> 查找索引

```javascript
var name = 'zhangsan';
var index = name.indexOf('zhangsan');
console.log(index);
```

+ `foreach`

> 遍历

```javascript
var arr = [1,2,3,4];
arr.forEach(num => {
    console.log(num);
})
```

+ `includes`

> 包含,es6语法

```javascript
var name = 'zhangsan';
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
var name = 'zhangsan';
name = name.replace('san','si');

name = name.replace(/n/g,'s');
```

## 模板字符串

```javascript
dateFormat(dateObj,pattern=''){
    var dt = new Date(dateObj);
    
    //获取年月日
    var y = dt.getFullYear();
    var m = (dt.getMonth() + 1).toString().padStart(2,'0');
    var d = dt.getDate().toString().padStart(2,'0');
    
    if(pattern.toLowerCase() === 'yyyy-mm-dd'){
        //return y+"-"+m+"-"+d;
        return '${y}-${m}-${d}'
    }else{
        var hh = dt.getHours().toString().padStart(2,'0');
        var mm = dt.getMinutes().toString().padStart(2,'0');
        var ss = dt.getSeconds().toString().padStart(2,'0');
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
let _this = this
let intervalId = setInterval(function() {
    console.log(_this);
},100);

let intervalId2 = setInterval(() => {
    console.log(this);
});
```

