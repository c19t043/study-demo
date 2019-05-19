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

## 过滤器

vue.js允许自定义过滤器，**可被用作一些常见的文本格式化**。

过滤器可以用在两个地方：**mustachc插值（插值表达式） 和 v-bind表达式**

### 全局过滤器

> Vue.filter('过滤器的名称',function(data) {
    return data+'123';
  })

### 私有过滤器

> 如果全局过滤器，和私有过滤器名称相同，优先调用私有过滤器

```html
<td>{{item.time | dateFormat('yyyy-MM-dd')}}</td>
<script>
    new Vue({
        filters:{
            dateFormat(dateObj,pattern){
                var dt = new Date(dateObj);
                
                //获取年月日
                var y = dt.getFullYear();
                var m = (dt.getMonth() + 1).toString().padStart(2,'0');
                var d = dt.getDate().toString().padStart(2,'0');
                
                if(pattern && pattern.toLowerCase() === 'yyyy-MM-dd'){
                    //return y+"-"+m+"-"+d;
                    return '${y}-${m}-${d}'
                }else{
                     var hh = dt.getHours().toString().padStart(2,'0');
                     var mm = dt.getMinutes().toString().padStart(2,'0');
                     var ss = dt.getSeconds().toString().padStart(2,'0');
                     return '${y}-${m}-${d} ${hh}:${mm}:${ss}'
                 }
            }
        }
    })
</script>
```

 ## 按键修饰符
 
 + `.enter` 
 
 > enter键
 
 监听enter键（回车键）的按键松开复位事件
 ```html
<input type="button" value="按钮" @keyup.enter="add">
```
 
 + `.delete` 删除键
 
 + `.tab` 制表键 tab键
 
 + `.esc` 退出键 esc键
 
 + `.space` 空格键
 
 + `.up` 向上按键 up键
 
 + `.down`
 
 + `.left`
 
 + `.right`
 
 ### 传入按键的码值
 
 > f1 => 112
 
 直接传入码值不好记
 
  ```html
 <input type="button" value="按钮" @keyup.112="add">
 ```
 
 ### 自定义按键修饰符
 
 > Vue.config.keyCodes.f1 = 112;
 
   ```html
  <input type="button" value="按钮" @keyup.f1="add">
  ```
  
## 自定义指令

### 全局指令

> Vue.directive(),

参数1：指令名称，不要需要加 `v-`前缀 但在调用的时候必须要加 `v-` 前缀

参数2：是一个对象，这个对象由一些指令相关的钩子函数 

```javascript
Vue.directive('focus',{
    bind:function(el){
        el.style.color = 'blue';
    },
    inserted:function(el){
        el.focus();
    },
    updated:function(el){},    
})
```


#### 指令钩子函数

js操作适合在`inserted`钩子函数中处理,

样式适合在`bind`钩子函数中处理

+ `bind`:只调用一次，指令第一次绑定到元素时调用，
用这个钩子函数可以定义一个在绑定时执行一次的初始化动作

时机类似于：在内存中做修改

+ `inserted` 只调用一次,插入到dom中后执行

时机在页面中做修改，

+ `update`: dom节点更新前执行

+ `updated`: dom节点更新后执行

+ `unbind`: 只调用一次，指令与元素解绑时调用
  
##### 钩子函数参数

+ `el` 被绑定的元素，是一个原生的js对象  

+ `binding`：一个对象 包含如下属性：
    
    - `name`: 指令名，不包含 `v-`前缀
    - `value`: 指令的绑定值，如`v-bind='1+1'，value是2，是表达式计算后的值 
    - `expression`: 绑定值的字符串形式,如expression是'1+1'
    - `arg`: 传给指令的参数,如`v-my-directive:foo',arg是foo

### 私有指令

```javascript
new Vue({
    directives:{
        'my-directive':{
            bind:function(el,binding){
                
            }
        }
    }
})
```

### 指令简写

可以需要在bind和update钩子上都做重复动作

相当于，在bind和update钩子函数上做同样的动作

+ 全局指令

> Vue.directive('my-directive',function(el,binding){})

+ 私有指令

> directives:{
    'my-directive':function(el,binding){}
}

## vue实例的生命周期

### 生命周期

从vue实例创建、运行、到销毁期间，总是伴随着各种各样的事件，
这些事件，统称为生命周期

### 生命周期钩子

就是生命周期事件的别名

生命周期钩子 = 生命周期函数 = 生命周期事件

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

## vue动画

vue动画分为两部分：enter（进入），leave（离开）

+ enter
以下属于`v-enter-active`阶段
    - `v-enter` 进入前的状态
    - `v-enter-to` 进入后的状态
+ leave
以下属于`v-leave-active`阶段
    - `v-leave` 离开前的状态
    - `v-leave-to` 离开后的状态

### 动画执行

+ `transition` 标签包裹需要被动画控制的元素    

+ 加上动画样式

```css
/* v-enter 这是一个时间点，是进入之前，元素的起始状态，此时还没有开始进入
    v-leave-to 这是一个时间点，是动画离开后，离开的终止状态，此时元素动画已经结束了
    
    起始状态
 */
 .v-enter,
 .v-leave-to{
    opacity: 0;
 }
 
 /* v-enter-active 入场动画时间段 
    v-leave-active 离场动画时间段
    
    起始状态过度需要的时间
  */
  .v-enter-active,
  .v-leave-active{
    transition: all 0.4s ease;
  }
```

```html
<transition>
    <h5 v-if="flag">h5</h5>
</transition>
```

+ 自定义动画类前缀

```html
<transition name="my">
    <h5 v-if="flag">h5</h5>
</transition>
```

```css
/* v-enter 这是一个时间点，是进入之前，元素的起始状态，此时还没有开始进入
    v-leave-to 这是一个时间点，是动画离开后，离开的终止状态，此时元素动画已经结束了
    
    起始状态
 */
 .my-enter,
 .my-leave-to{
    opacity: 0;
 }
 
 /* v-enter-active 入场动画时间段 
    v-leave-active 离场动画时间段
    
    起始状态过度需要的时间
  */
  .my-enter-active,
  .my-leave-active{
    transition: all 0.4s ease;
  }
  
```

+ `transitionGroup`

在实现列表过渡的时候，如果需要过渡的元素，是通过v-for循环渲染出来的，
不能使用`transition`包裹，需要使用 `transitionGroup`

如果要为`v-for`循环创建的元素设置动画，必须为每一个元素设置 `:key` 属性

    - `.v-move` 设置过渡的切换时间和过渡曲线
    
    - `appear` 添加属性 ，实现页面刚展示出来的时候，入场效果
    
    - `tag="ul"` 通过tag属性指定前来要动画标签要渲染为的标签，不指定默认为span标签

### animate.css第三方动画库

```html
<!-- :duration="毫秒值" 统一设置入场 和 离场 动画时长 -->
<!-- :duration="{enter:200,leave:400}" 分别设置入场 和 离场 动画时长 -->
<transition name="my" 
enter-active-class="animated bounceIn"
leave-active-class="animated bounceOut"
:duration="400"
>
    <h5 v-if="flag">h5</h5>
</transition>
```
### 动画javascript钩子

+ `beforeEnter`

表示动画入场之前，此时，动画尚未开始，可以设置元素开始动画之前的其实样式

+ `enter`

el.offsetWidth;这句话，没有实际的作用，但是，如果不写，出不来动画效果

表示动画开始之后的样式，这里可以设置完成动画之后的样式

参数2：done ，表示下个动画的函数引用, enter结束后必须调用

默认加上done()，表示本次动画结束后，下个动画立即执行

+ `afterEnter`

完成动画之后

## 组件

### 全局组件

+ 分别创建模板和组件 

> `template` 模板属性，有且只有一个根元素

```javascript
// 使用 Vue.extend 创建全局的模板
var tpl = Vue.extend({
    template: '<h3></h3>'
});

// 使用Vue.component('组件名',组件模板对象)
Vue.component('myTpl',tpl);

```
```html
<!-- 使用组件,直接以html标签形式引用 
    创建组件时，创建名可以是驼峰命令
    使用时名字必须小写，然后使用横线`-`连接
-->
<my-tpl></my-tpl>
```

+ 创建组件时，直接传入模板对象

```javascript
Vue.component('myTpl',{
  template: '<h3></h3>'
});
```

+ 使用`template`标签创建模板

```html
<template id="temp">
    <div>
        <div></div>
        <div></div>    
    </div>
</template>
```
```javascript
Vue.component('myTpl',{
  template: '#temp'
});
```

### 私有组件

```javascript
new Vue({
    components:{
        myTpl:{
            template: '<h3></h3>'
        }
    }
})
```

### 组件中的data

组件可以有自己的data数据

组件的data 和实例的data 有点不一样，

实例中的data 可以为一个对象，

但是组件中的data必须是一个方法

data方法内部还必须返回一个对象才行

```javascript
Vue.component('myTpl',{
     template: '<h3></h3>',
     data:function() {
        return {
            msg: ''
        };
     }
})
```

### 组件切换

绑定标签必须挨着使用

+ `v-if`
+ `v-else-if`
+ `v-else`

+ `:is='组件名称'`

不直接使用组件标签，通过组件名切换组件

```html
<component :is="register"></component>
```

### 组件切换+动画

用 `transition` 包裹 `component`

### 父组件向子组件传递数据

+ 子组件默认无法访问到父组件中data上的数据和methods中的方法

+ 父组件在引用子组件时，可以通过属性绑定（v-bind:）的形式，把需要传递给子组件的数据

以属性绑定的形式，传递到子组件内部，供子组件使用

+ 子组件使用`props`属性中，定义的属性接受数据

并且`props`中的数据是只读的

```html
<div id="app">
    <sub-com :subProperty="property"></sub-com>
</div>
```
```javascript
new Vue({
    el:'#app',
    data:{
        property:'123'
    },
    components:{
        subCom:{
            template:'<h5></h5>',
            props:['subProperty']
        }
    }
})
```

### 父组件把方法传递给子组件，子组件触发父组件传递的方法

> `$emit('子组件事件名','参数')`

```html
<div id="app">
    <sub-com @func="show"></sub-com>
</div>
```
```javascript
new Vue({
    el:'#app',
    data:{
        property:'123'
    },
    methods:{
        show(data){
            
        }
    },
    components:{
        subCom:{
            template:'<h5 @click="myClick"></h5>',
            methods:{
                myClick(){
                    this.$emit('func','123');
                }
            }
        }
    }
})
```


## vue获取dom元素 或 组件的引用

### `ref` 属性

+ 获取dom元素

```html
<h3 ref="h3"></h3>
```
```javascript

let h3 = this.$refs.h3;

h3.outerHTML;
```

+ 获取组件引用

> 获取组件的属性和方法

```html
<div id="app">
    <mylogin ref="mylogin"></mylogin>
</div>

<template id="tpl">
    <div>
        <input type="text">
        <button></button>
    </div>
</template>
```
```javascript
Vue.component('mylogin',{
    template:'#tpl',
    data(){
        return {
          msg:''  
        };
    },
    methods:{
        submit(){
            
        }
    }
})
new Vue({
    el:'app',
    methods:{
        console.log(this.$refs.mylogin.msg);
        
        console.log(this.$refs.mylogin.submit());
    }
})
```

## 路由

### 基本使用

```html
<div id="app">
    <!-- 路由标签 -->
    <router-link to="/login">登录</router-link>
    
    <!-- 路由匹配的组件展示的位置 -->
    <router-view></router-view>
</div>
```
```javascript
// 自定义组件模板对象
var login = {
    template:'<h1>登录组件</h1>'
}

// 创建路由实例
var routerObj = new VueRouter({
    routes:[
        {path:'/login',component:login}
    ]
})

// 挂在路由
new Vue({
    router:routerObj
}).$mount("#app")

```
### 路由属性

+ `path` 表示监听的路由链接地址

+ `component` 组件模板对象

不能使用`Vue.component('login',login)`

错误：`{path:'/path',component:'login'}`

正确：不使用`Vue.component`

`{path:'/path',component:login}`

+ `redirect` 重定向路由地址

`{path:'/',redirect:'/path'}`

### 路由传参

如果在路由中，使用查询字符串，给路由传递参数，
则不需要修改路由规则的path属性

+ 路由+请求参数

```html
<route-link to="/login?id=1"></route-link>
```
+ 获取路由参数

```javascript
// get /login?id=1

this.$route.path // /login

this.$route.query // {id:1}

this.$route.query.id

```

+ 路径参数

```html
<route-link to="/login/1"></route-link>
```
```javascript
var login = {
    template:'<h1></h1>'
}
new VueRouter({
    routes:[
        {path:'/login/:id',component:login}
    ]
})
```

+ 获取路径参数

```javascript
// /login/1

this.$route.path // /login/1
this.$route.query // {}
this.$route.param // {id:'1'}

```

## vue 属性监听

### `watch`

+ 监视属性

使用这个属性可以监视data中指定数据的变化，
然后出发这个watch中对应function处理函数

```javascript
new Vue({
    data:{
        firstName:'',
        lastName:'',
        fullName:''
    },
    watch:{
        'firstName':function(newVal,oldVal) {
          this.fullName = newVal+ "-" + this.lastName;
        },
        lastName:function(newVal) {
          this.fullName = this.firstName + newVal;
        }
    }
});
```

+ 监视路由地址

```javascript
new Vue({
    watch:{
        // this.$route.path
        // 监听非dom属性变化
        '$route.path':function(newVal,oldVal) {
          if(newVal === '/login'){
              
          }else if(newVal === '/register'){
              
          }
        }
    }
});
```

## 计算属性

### `computed`

在computed中，可以定义一些属性，这些属性，叫做**计算属性**，
计算属性的本质，就是一个方法，只不过，我们在使用这些计算属性的时候，
是把他们的名称，直接当做属性来使用，并不会把计算属性，当做方法来调用

```html
<input type="text" v-model="firstName">+
<input type="text" v-model="lastName">=
<input type="text" v-model="fullName">
```
```javascript
new Vue({
    data:{
        firstName:'',
        lastName:'',
    },
    watch:{
        'fullName':function() {
          return this.firstName+ "-" + this.lastName;
        }
    }
});
```

注意：计算属性在引用的时候，一定不要加()调用，
直接把它当做普通属性去使用就好

只要计算属性，这个function内部所用的到任何data中的数据发送了变化，
就会立即重新计算这个计算属性的值

计算属性的求值结果，会被缓存起来，方便下次直接使用，
如果计算属性方法中，所以来的任何数据，都没有发送变化，
则不会重新对计算属性求值

## watch、computed和methods之间对比

1、`computed`属性的结果会被缓存，除非依赖的响应式属性变化才会重新计算，主要当作属性来使用

2、`methods`方法表示一个具体的操作，主要书写业务逻辑

3、`watch`一个对象，键是需要观察的表达式，值是对应回调函数。
主要用来监听某些特定数据的变化，从而进行某些具体的业务逻辑操作；
可以看做是`computed`和`methods`的结合体

## 组件渲染

### `render`

```html
  <div id="app">
    <p>444444</p>
  </div>

  <script>

    var login = {
      template: '<h1>这是登录组件</h1>'
    }

    // 创建 Vue 实例，得到 ViewModel
    var vm = new Vue({
      el: '#app',
      data: {},
      methods: {},
      render: function (createElements) { // createElements 是一个 方法，调用它，能够把 指定的 组件模板，渲染为 html 结构
        return createElements(login)
        // 注意：这里 return 的结果，会 替换页面中 el 指定的那个 容器
      }
    });
  </script>
```
### 基本组件component使用，和render区别

+ `component` 相当于插值表达式，只替换 组件标签对应的部分

+ `render` 替换页面中 el 指定的那个 容器

render 会把 el 指定的容器中，所有的内容都清空覆盖，所以 不要 把 路由的 router-view 和 router-link 直接写到 el 所控制的元素中

注意： App 这个组件，是通过 VM 实例的 render 函数，渲染出来的， render 函数如果要渲染 组件， 渲染出来的组件，只能放到 el: '#app' 所指定的 元素中；
Account 和 GoodsList 组件， 是通过 路由匹配监听到的，所以， 这两个组件，只能展示到 属于 路由的 <router-view></router-view> 中去；

## 注意事项

### 绑定属性中表达式 'xx'  与 xx 的区别

+ 'xx' 表示字符串

+ xx 表示变量，回去`data`中找 

### `v-for`可以组合方法使用

```html
<div id="app">
    <div v-for="item in search(keyword)">
        {{item.id}}--{{item.name}}    
    </div>
</div>
<script>
    new Vue({
        el:"#app",
        data:{
            list:[
                {id:1,name:'zhangsan'},
                {id:2,name:'lisi'},
            ],
            keyword:''
        },
        methods:{
            search(keyword){
               return this.list.filter(item =>{
                   if(item.name.includes(keyword)){
                      return item;
                   }
               })
            }
        }
    })
</script>
``` 

### 插值表达式闪烁问题

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