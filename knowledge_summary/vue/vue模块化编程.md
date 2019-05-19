# vue模块化编程


```js
// 总结梳理： webpack 中如何使用 vue :
// 1. 安装vue的包：  cnpm i vue -S
// 2. 由于 在 webpack 中，推荐使用 .vue 这个组件模板文件定义组件，所以，需要安装 能解析这种文件的 loader    cnpm i vue-loader vue-template-complier -D
// 3. 在 main.js 中，导入 vue 模块  import Vue from 'vue'
// 4. 定义一个 .vue 结尾的组件，其中，组件有三部分组成： template script style
// 5. 使用 import login from './login.vue' 导入这个组件
// 6. 创建 vm 的实例 var vm = new Vue({ el: '#app', render: c => c(login) })
// 7. 在页面中创建一个 id 为 app 的 div 元素，作为我们 vm 实例要控制的区域；
```

## 在webpack中配置.vue组件页面的解析

1. 运行`cnpm i vue -S`将vue安装为运行依赖；

2. 运行`cnpm i vue-loader vue-template-compiler -D`将解析转换vue的包安装为开发依赖；

3. 运行`cnpm i style-loader css-loader -D`将解析转换CSS的包安装为开发依赖，因为.vue文件中会写CSS样式；

4. 在`webpack.config.js`中，添加如下`module`规则：

```
module: {
    rules: [
      { test: /\.css$/, use: ['style-loader', 'css-loader'] },
      { test: /\.vue$/, use: 'vue-loader' }
    ]
  }
```

## 创建`App.js`组件页面：

```text
    <template>
      <!-- 注意：在 .vue 的组件中，template 中必须有且只有唯一的根元素进行包裹，一般都用 div 当作唯一的根元素 -->
      <div>
        <h1>这是APP组件 - {{msg}}</h1>
        <h3>我是h3</h3>
      </div>
    </template>

    <script>
    // 注意：在 .vue 的组件中，通过 script 标签来定义组件的行为，需要使用 ES6 中提供的 export default 方式，导出一个vue实例对象
    export default {
      data() {
        return {
          msg: 'OK'
        }
      }
    }

    </script>
/* 普通的 style 标签只支持 普通的 样式，如果想要启用 scss 或 less ，需要为 style 元素，设置 lang 属性 */
// 只要 咱们的 style 标签， 是在 .vue 组件中定义的，那么，推荐都为 style 开启 scoped 属性
    <style scoped>
        h1 {
          color: red;
        }
    </style>
```

## 创建`main.js`入口文件

```
    // 导入 Vue 组件
    import Vue from 'vue'
    // 功能最全的vue
    // import Vue from 'vue/dist/vue.js'
    // 导入 App组件
    import App from './components/App.vue'
    // 创建一个 Vue 实例，使用 render 函数，渲染指定的组件
    var vm = new Vue({
      el: '#app',
      render: c => c(App)
    });
```

## 在使用webpack构建的Vue项目中使用模板对象？

vue 导入包方式

直接使用 `import Vue from 'vue'` 导入的vue 功能不全 runtime-only

可以导入 `import Vue from 'vue/dist/vue.js'`

也可以在`webpack.config.js`中添加`resolve`属性：
```
resolve: {
    alias: {
      'vue$': 'vue/dist/vue.esm.js'
    }
  }
```

## 在vue组件页面中，集成vue-router路由模块

[vue-router官网](https://router.vuejs.org/)

cnpm i vue-router -S

1. 导入路由模块：

```
import VueRouter from 'vue-router'
```

2. 安装路由模块：

```
Vue.use(VueRouter);
```

3. 导入需要展示的组件:

```
import login from './components/account/login.vue'
import register from './components/account/register.vue'
```

4. 创建路由对象:

```
var router = new VueRouter({
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: login },
    { path: '/register', component: register }
  ]
});
```

5. 将路由对象，挂载到 Vue 实例上:

```
var vm = new Vue({
  el: '#app',
  // render: c => { return c(App) }
  render(c) {
    return c(App);
  },
  router // 将路由对象，挂载到 Vue 实例上
});
```

6. 改造App.vue组件，在 template 中，添加`router-link`和`router-view`：

```
    <router-link to="/login">登录</router-link>
    <router-link to="/register">注册</router-link>
    <router-view></router-view>
```

## 组件中的css作用域问题



## 抽离路由为单独的模块
