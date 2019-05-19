# ES6中语法使用总结

1. 使用 `export default` 和 `export` 导出模块中的成员; 对应ES5中的 `module.exports` 和 `export`

var info = {
  name: 'zs',
  age: 20
}

export default info

import m222 from './test.js'

注意： export default 向外暴露的成员，可以使用任意的变量来接收

注意： 在一个模块中，export default 只允许向外暴露1次

注意： 在一个模块中，可以同时使用 export default 和 export 向外暴露成员

export var title = '小星星'
export var content = '哈哈哈'

注意： 使用 export 向外暴露的成员，只能使用 { } 的形式来接收，这种形式，叫做 【按需导出】

注意： export 可以向外暴露多个成员， 同时，如果某些成员，我们在 import 的时候，不需要，则可以 不在 {}  中定义

注意： 使用 export 导出的成员，必须严格按照 导出时候的名称，来使用  {}  按需接收；

注意： 使用 export 导出的成员，如果 就想 换个 名称来接收，可以使用 as 来起别名；

import m222, { title as title123, content } from './test.js'

2. 使用 `import ** from **` 和 `import '路径'` 还有 `import {a, b} from '模块标识'` 导入其他模块

3. 使用箭头函数：`(a, b)=> { return a-b; }`

// class 关键字，是ES6中提供的新语法，是用来 实现 ES6 中面向对象编程的方式
class Person {
  // 使用 static 关键字，可以定义静态属性
  // 所谓的静态属性，就是 可以直接通过 类名， 直接访问的属性
  // 实例属性： 只能通过类的实例，来访问的属性，叫做实例属性
  static info = { name: 'zs', age: 20 }
}

