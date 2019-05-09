// 自定义组件

// 定义路由
const routes = [
    {path: "/account/list", component: account, name: "account"},
    {path: "/account/add", component: account_add, name: "account_add"},
    {path: "/role/list", component: role, name: "role"},
    {path: "/role/add", component: role_add, name: "role_add"},
    {path: "/privilege/list", component: privilege, name: "privilege"},
    {path: "/privilege/add", component: privilege_add, name: "privilege_add"},
    {path: "/", redirect: '/account/list'}
];

// 创建路由实例
const router = new VueRouter({
    routes
});


// 创建vue实例 ，挂在路由
new Vue({
    // el: "#app",
    data: {
        accountList: [
            {
                "id": "1",
                "username": "zhangsan",
                "password": "123",
                "nickname": "张三"
            },
            {
                "id": "2",
                "username": "lisi",
                "password": "123",
                "nickname": "李四"
            },
            {
                "id": "3",
                "username": "wangwu",
                "password": "123",
                "nickname": "王五"
            }],
        roleList: [
            {
                "id": "1",
                "name": "管理员",
                "code": "admin"
            }
        ],
        privilegeList: [
            {
                "id": "1",
                "url": "/account/add",
                "operate": "add"
            },
            {
                "id": "2",
                "url": "/account/edit",
                "operate": "edit"
            },
            {
                "id": "3",
                "url": "/account/query",
                "operate": "query"
            },
            {
                "id": "4",
                "url": "/account/delete",
                "operate": "delete"
            }
        ]
    },
    router: router
    // 组件已加载完毕，请求网络数据，业务处理
    // mounted: function () {
    //     // 加载账户列表
    //     this.getAccountList();
    // },
    // methods: {
    //     getAccountList() {
    //         this.$http.get("data/account.json").then(response => {
    //             const result = response.body;
    //             this.accountList = result;
    //             console.log(result);
    //         }, reponse => {
    //             console.log("请求处理失败");
    //         })
    //     },
    //     getRoleList: function () {
    //         this.$http.get("data/role.json").then(response => {
    //             const result = response.body;
    //             console.log(result);
    //         }, reponse => {
    //             console.log("请求处理失败");
    //         })
    //     },
    //     getPrivilegeList: function () {
    //         this.$http.get("data/privilege.json").then(response => {
    //             const result = response.body;
    //             console.log(result);
    //         }, reponse => {
    //             console.log("请求处理失败");
    //         })
    //     }
    // }
}).$mount("#app");