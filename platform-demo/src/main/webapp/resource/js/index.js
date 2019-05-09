// 自定义路由组件

// 自定义路由
const routes = [
    {path: '/account', component: account},
    {path: '/role', component: role},
    {path: '/privilege', component: privilege},
    {path: '/', redirect: "/account"}
];

// 创建router实例
const router = new VueRouter({
    routes: routes
});

new Vue({
    el: "#app",
    router,
    data:{
        
    }
});

