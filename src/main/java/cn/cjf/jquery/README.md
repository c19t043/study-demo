# Jquery
## not()函数
not() 从匹配元素集合中排除元素
~~~html
<p>This is a paragragh.</p>
<p>This is a paragragh.</p>
<p>This is a paragragh.</p>
<p id="selected">This is a paragragh.</p>
<p>This is a paragragh.</p>
<p>This is a paragragh.</p>

<script>
$("p").not("#selected").css('background-color', 'red');
</script>
~~~
> 在<p>的集中，排除id=selected的p元素，然后对剩余的集合中的元素修改css
## 添加元素
~~~jquery
添加新内容的四个 jQuery 方法：
append() - 在被选元素的结尾插入内容
prepend() - 在被选元素的开头插入内容
after() - 在被选元素之后插入内容
before() - 在被选元素之前插入内容
~~~
## 查找所有同胞元素：
~~~
$("p").siblings(".selected")
~~~