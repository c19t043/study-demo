## 组合两个表
编写一个 SQL 查询，满足条件：无论 person 是否有地址信息，都需要基于上述两表提供 person 的以下信息：FirstName, LastName, City, State
~~~SQL
Create table Person (PersonId int, FirstName varchar(255), LastName varchar(255))
Create table Address (AddressId int, PersonId int, City varchar(255), State varchar(255))
Truncate table Person
insert into Person (PersonId, LastName, FirstName) values ('1', 'Wang', 'Allen')
Truncate table Address
insert into Address (AddressId, PersonId, City, State) values ('1', '2', 'New York City', 'New York')
~~~

~~~SQL
select FirstName, LastName, City, State from Person p left join Address a on p.PersonId  = a.PersonId
~~~  

## 第二高的薪水
编写一个 SQL 查询，获取 Employee 表中第二高的薪水（Salary） 。
~~~SQL
Create table If Not Exists Employee (Id int, Salary int)
Truncate table Employee
insert into Employee (Id, Salary) values ('1', '100')
insert into Employee (Id, Salary) values ('2', '200')
insert into Employee (Id, Salary) values ('3', '300')
~~~

~~~SQL
select ifnull((select Distinct Salary from Employee order by Salary desc limit 1,1),null) as SecondHighestSalary 
~~~

## 第N高的薪水
编写一个 SQL 查询，获取 Employee 表中第 n 高的薪水（Salary）。
~~~
+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
~~~
~~~sql
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    set n = n-1;
  RETURN (
      # Write your MySQL query statement below.
      select ifnull((select distinct Salary from Employee order by Salary desc limit 1 offset n),null)
  );
END
~~~

## 分数排名
编写一个 SQL 查询来实现分数排名。如果两个分数相同，则两个分数排名（Rank）相同。请注意，平分后的下一个名次应该是下一个连续的整数值。换句话说，名次之间不应该有“间隔”。
~~~SQL
Create table If Not Exists Scores (Id int, Score DECIMAL(3,2))
Truncate table Scores
insert into Scores (Id, Score) values ('1', '3.5')
insert into Scores (Id, Score) values ('2', '3.65')
insert into Scores (Id, Score) values ('3', '4.0')
insert into Scores (Id, Score) values ('4', '3.85')
insert into Scores (Id, Score) values ('5', '4.0')
insert into Scores (Id, Score) values ('6', '3.65')
~~~ 

~~~SQL
select Score,(select count(distinct Score) from Scores where Score >= s.Score) as Rank from Scores s order by Score desc
~~~

## 连续出现的数字
编写一个 SQL 查询，查找所有至少连续出现三次的数字。

~~~SQL
Create table If Not Exists Logs (Id int, Num int)
Truncate table Logs
insert into Logs (Id, Num) values ('1', '1')
insert into Logs (Id, Num) values ('2', '1')
insert into Logs (Id, Num) values ('3', '1')
insert into Logs (Id, Num) values ('4', '2')
insert into Logs (Id, Num) values ('5', '1')
insert into Logs (Id, Num) values ('6', '2')
insert into Logs (Id, Num) values ('7', '2')
~~~

~~~SQL
select DISTINCT ConsecutiveNums FROM
(
select NUM AS ConsecutiveNums,
 case 
		when @r1 = NUM then @count := @count+1 
		WHEN (@r1 := NUM) IS NOT NULL then @count := 1
	end cnt
from Logs a
) a where a.cnt >= 3 
~~~

~~~SQL
select DISTINCT ConsecutiveNums FROM
(
select NUM AS ConsecutiveNums,
 case 
		when @r1 = NUM then @count := @count+1 
		WHEN (@r1 := NUM) IS NOT NULL then @count := 1
	end cnt
from Logs a,(select @r1:= NULL) b ,(select @count:= 1) d
) a where a.cnt >= 3 
~~~

## 超过经理收入的员工
Employee 表包含所有员工，他们的经理也属于员工。每个员工都有一个 Id，此外还有一列对应员工的经理的 Id。
给定 Employee 表，编写一个 SQL 查询，该查询可以获取收入超过他们经理的员工的姓名。在上面的表格中，Joe 是唯一一个收入超过他的经理的员工。

~~~SQL
Create table If Not Exists Employee (Id int, Name varchar(255), Salary int, ManagerId int)
Truncate table Employee
insert into Employee (Id, Name, Salary, ManagerId) values ('1', 'Joe', '70000', '3')
insert into Employee (Id, Name, Salary, ManagerId) values ('2', 'Henry', '80000', '4')
insert into Employee (Id, Name, Salary, ManagerId) values ('3', 'Sam', '60000', 'None')
insert into Employee (Id, Name, Salary, ManagerId) values ('4', 'Max', '90000', 'None')
~~~

~~~SQL
select a.Name  as "Employee" from Employee a left join Employee b on a.ManagerId = b.id 
where a.Salary > b.Salary and a.ManagerId is not null
~~~