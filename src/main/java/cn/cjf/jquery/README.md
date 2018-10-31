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
> 在<p>的集合中，排除id=selected的p元素，然后对剩余的集合中的元素修改css