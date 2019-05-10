# vue

## vue 基本格式

```html
<head></head>
<body>
    <div id="app">
        <div>{{value}}</div>
        <button @:click="hello"></button>
    </div>
</body>
<script src="vue.js"></script>
<script >
    new Vue({
        el:'#app'
        data:{
            value:'hello world'
        },
        methods:{
            hello:function(){
                alert(this.value);
            }
        }
    });
</script>
```

## 插入数据

+ `v-text` 
```html
<div v-text="data.property"></div>
```

+ `{{expression}}`
```html
<div>{{ data.property }}</div>
```

## 数据绑定

+ `v-bind`

> `v-bind:属性="{{合法js表达式}}"`

```html
<input type="button" value="按钮" v-bind:title="data.property + '123'">
```

+ 简写 `:属性="{{合法js表达式}}"`

```html
<input type="button" value="按钮" :title="data.property + '123'">
```

## 事件绑定

+ `v-on`

> `v-on:事件="定义的方法"`

```html
<input type="button" value="按钮" v-on:click="submit()">
```

+ 简写 `@事件="定义的方法"`

```html
<input type="button" value="按钮" @click="submit()">
```

## 事件修饰符

+ `.stop` 阻止冒泡

点击子元素，触发点击事件，子元素点击事件会依次向父元素传递，同时也触发父元素的点击事件

加上`.stop` 阻止本身触发的事件向上传递

```html
<div @click="parentHandle">
    <input type="button" @click.stop="btnHandle">
</div>
```

+ `.prevent` 阻止默认事件

阻止标签本身默认行为

如下方 a标签点击事件触发后，会默认跳转向指定链接地址

```html
<a href="http://www.baidu.com" @click.prevent="printHandle"></a>
```

+ `.capture` 添加事件监听器时使用事件捕获模式

子元素事件被触发，被父元素捕获到，先执行父元素函数，再执行子元素函数

```html
<div @click.capture="parentHandle">
    <input type="button" @click="btnHandle">
</div>
```

+ `.self` 只当事件在该元素本身（比如不是子元素）触发时触发回调

只有元素本身事件被触发，才会执行函数

```html
<div @click.self="parentHandle">
    <input type="button" @click="btnHandle">
</div>
```

+ `.once` 事件只触发一次

如下，a标签第一次触发点击事件，默认行为被阻止

但是加了`.once`修饰符，第二次没有阻止a标签的默认行为

```html
<a href="http://www.baidu.com" @click.prevent.once="printHandle"></a>
```

+ 事件修饰符可以链式调用

## 双向数据绑定

数据层数据与视图层数据，双向数据绑定，一方改变，另一方也随之改变

+ `v-model`

```html
<input type="text" name="username" v-model="user.username"></input>
```

## vue中使用样式

### 使用class样式

> 首先要绑定属性 `:class`

+ 数组

使用多个类

```html
<h1 :class="['red','thin']"></h1>
```

+ 数组中使用三元表达式

```html
<h1 :class="['red',flag?'thin':'']"></h1>
```

+ 数组中嵌套对象

```html
<h1 :class="['red',{'thin':true}]"></h1>
```

+ 直接使用对象

```html
<h1 :class="[{'thin':true,'red':true}]"></h1>
```

### 使用内联样式

> 首先要绑定样式 `:style`

+ 直接使用对象

```html
<h1 :style="{color:'red','font-size':'40px'}"></h1>
```

+ 将定义到`data`中的样式，直接应用到`:style`中

```javascript
new Vue({
    data:{
        h1Style:{
            color:'red',
            'font-size':'40px'
        }
    }
})
```
```html
<h1 :style="h1Style"></h1>
```

+ `:style`中通过数组，应用多个`data`上的样式对象

```javascript
new Vue({
    data:{
        h1Style1:{
            color:'red',
            'font-size':'40px'
        },
        h1Style2:{
            'font-weight':'200px',
            'font-size':'40px'
         }
    }
})
```
```html
<h1 :style="[h1Style,h1Style2]"></h1>
```

## 循环迭代

### `v-for`

+ 迭代数组

```html
<ul>
    <li v-for="(item,i) in list ">
        index:{{i}}--name:{{item.name}}
    </li>
</ul>
<ul>
    <li v-for="item in list ">
        name:{{item.name}}
    </li>
</ul>
```

+ 迭代对象

```html
<ul>
    <li v-for="(value,key,i) in item ">
        value:{{value}}--key:{{key}}
    </li>
</ul>
```

+ 迭代数字

> 迭代数字，从1开始

```html
<p v-for="i in 10">第{{i}}个p标签</p>
```

+ 注意事项：属性`key`

> 2.20+版本里，当组件中使用v-for时，key是必须的

当`v-for`正在更新已渲染过的元素列表时，它默认用**就地复用**策略，

如果数据项的顺序被改变，vue将**不是移动dom元素来匹配数据项的顺序**，

而是**简单服用此处每个元素**,并且确保它在**特定索引下**显示已被渲染过的每个元素

为了给vue一个提示，**以便它能跟踪每个节点的身份，从而重用和重新排序现有元素**，

你需要为每项提供一个唯一key属性

> 现有数组数据arr:[{id:1,name:'zhangsan'},{id:2,name:'lisi'}]被渲染为
```html
<div>
    <div><input type="checkbox" name="1">zhangsan</div>
    <div><input type="checkbox" name="2" checked="checked">lisi</div>
</div>
``` 
> 其中 name=2的checkbox被选中，然后，向数组arr头部插入一条记录{id:3,name:'wangwu'}，
结果重新渲染后，name=1被选中
```html
<div>
    <div><input type="checkbox" name="3">wangwu</div>
    <div><input type="checkbox" name="1" checked="checked">zhangsan</div>
    <div><input type="checkbox" name="2">lisi</div>
</div>
```
> 如果在`v-for`时，加上key属性，将不存在此问题
```html
<div>
    <div v-for="item in arr" :key="item.id">
        <input type="checkbox" :name="item.id">{{item.name}}
    </div>
</div>
``` 

## 控制元素显示 ，隐藏

+ `v-if`

> `v-if:'expression'` 表达式为true，创建dom,显示元素，false，销毁dom，隐藏元素



+ `v-show`

如果元素涉及频繁的切换，推荐使用`v-show`

>  `v-show:'expression'` 修改元素样式,表达式为false,`display:none`，true,去掉`display`样式, 


## vue实例的生命周期

### 生命周期

从vue实例创建、运行、到销毁期间，总是伴随着各种各样的事件，
这些事件，统称为生命周期

### 生命周期钩子

就是生命周期事件的别名

### 生命周期函数分类

#### 创建期间的生命周期

+ beforeCreate

实例刚在内存中被创建出来，此时，还没有初始化好data，和methods属性

+ created

实例已经在内存中创建OK，此时data和methods属性已创建OK，
此时还没有开始编译模板

+ beforeMount

此时已经完成了模板的编译，但是还没有挂在到页面中

+ mounted

此时将编译好的模板，挂在到了页面指定的容器中显示

#### 运行期间的生命周期函数

+ beforeUpdate

状态更新之前执行此函数，此时data的状态值是最新的，但是界面上显示的数据还是旧的，

因为此时还没有开始重新渲染DOM节点

+ updated

实例更新完毕调用此函数，此时data中的状态值和界面上显示的数据，

都已经完成了更新，界面已经被重新渲染好了

#### 销毁期间的生命周期函数

+ beforeDestroy

实例销毁之前调用。在这一步，实例仍然完全可用

+ destroyed

实例销毁后调用。调用后，vue实例指示的所有东西都会解除绑定，

所有的事件监听器都会被移除，所有的子实例也会被销毁

## 注意事项

+ 插值表达式闪烁问题

当网络请求速度慢，数据加载未完成，插值表达式中值一直得不到替换，页面显示具体的插值表达式{{xx}}

>解决方案1：在插值表达式标签上加属性 `v-cloak`
 
并添加样式 
```css
[v-cloak]{
    display:none;
}
```
当插值表达式中值，加载未完成时，隐藏插值表达式

> 解决方案2：`v-text` 默认没有插值闪烁问题

+ `v-text` 与 `{{expression}}`

`v-text` 标签中的内容会不覆盖

`{{expression}}` 插值表达式可以与其他字符组合