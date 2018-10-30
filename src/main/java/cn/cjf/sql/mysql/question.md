# mysql知识点
## DATEDIFF() 函数
DATEDIFF() 函数返回两个日期之间的天数。

~~~SQL
SELECT DATEDIFF('2008-12-30','2008-12-29') AS DiffDate
~~~
> 1
~~~SQL
SELECT DATEDIFF('2008-12-29','2008-12-30') AS DiffDate
~~~
> -1

## MOD(N,M)
该函数返回N除以M后的余数
~~~
mysql>SELECT MOD(29,3);
+---------------------------------------------------------+
| MOD(29,3)                                               |
+---------------------------------------------------------+
| 2                                                       |
+---------------------------------------------------------+
1 row in set (0.00 sec)
~~~

## IF表达式
IF(expr1,expr2,expr3)
如果 expr1 是TRUE (expr1 <> 0 and expr1 <> NULL)，则 IF()的返回值为expr2; 否则返回值则为 expr3