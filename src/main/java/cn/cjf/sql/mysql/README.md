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
from Logs a,(select @r1:= NULL) b
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

## 查找重复的电子邮箱
编写一个 SQL 查询，查找 Person 表中所有重复的电子邮箱。
~~~SQL
Create table If Not Exists Person (Id int, Email varchar(255))
Truncate table Person
insert into Person (Id, Email) values ('1', 'a@b.com')
insert into Person (Id, Email) values ('2', 'c@d.com')
insert into Person (Id, Email) values ('3', 'a@b.com')
~~~

~~~SQL
select Email from Person group by Email having count(Email) > 1
~~~

## 删除重复的电子邮箱

~~~SQL
DELETE p1
FROM Person p1, Person p2
WHERE p1.Email = p2.Email AND
p1.Id > p2.Id
~~~

## 从不订购的客户
某网站包含两个表，Customers 表和 Orders 表。编写一个 SQL 查询，找出所有从不订购任何东西的客户。
~~~sql
Create table If Not Exists Customers (Id int, Name varchar(255))
Create table If Not Exists Orders (Id int, CustomerId int)
Truncate table Customers
insert into Customers (Id, Name) values ('1', 'Joe')
insert into Customers (Id, Name) values ('2', 'Henry')
insert into Customers (Id, Name) values ('3', 'Sam')
insert into Customers (Id, Name) values ('4', 'Max')
Truncate table Orders
insert into Orders (Id, CustomerId) values ('1', '3')
insert into Orders (Id, CustomerId) values ('2', '1')
~~~

~~~SQL
select Name as Customers from Customers a where a.id not in (select Distinct CustomerId from Orders)
~~~

## 部门工资最高的员工
Employee 表包含所有员工信息，每个员工有其对应的 Id, salary 和 department Id。
Department 表包含公司所有部门的信息。

~~~SQL
Create table If Not Exists Employee (Id int, Name varchar(255), Salary int, DepartmentId int)
Create table If Not Exists Department (Id int, Name varchar(255))
Truncate table Employee
insert into Employee (Id, Name, Salary, DepartmentId) values ('1', 'Joe', '70000', '1')
insert into Employee (Id, Name, Salary, DepartmentId) values ('2', 'Henry', '80000', '2')
insert into Employee (Id, Name, Salary, DepartmentId) values ('3', 'Sam', '60000', '2')
insert into Employee (Id, Name, Salary, DepartmentId) values ('4', 'Max', '90000', '1')
Truncate table Department
insert into Department (Id, Name) values ('1', 'IT')
insert into Department (Id, Name) values ('2', 'Sales')
~~~

~~~SQL
select d.Name as Department,e.Name as Employee,e.Salary
from Department d,Employee e
where e.DepartmentId=d.Id and e.Salary=(Select max(Salary) from Employee where DepartmentId=d.Id)
~~~

~~~SQL
select d.Name as Department,e.Name as Employee,Salary 
from Employee e join Department d on e.DepartmentId=d.Id 
where (e.Salary,e.DepartmentId) in (select max(Salary),DepartmentId from Employee group by DepartmentId) 
~~~

## 部门工资前三高的员工

~~~SQL
Select d.Name as Department, e.Name as Employee, e.Salary 
from Department d, Employee e 
where b.DepartmentId = d.Id and (
    Select count(distinct Salary) From Employee where DepartmentId=d.Id and Salary > e.Salary
)<3
~~~

~~~SQL
SELECT D1.Name Department, E1.Name Employee,  E1.Salary
FROM Employee E1, Employee E2, Department D1
WHERE E1.DepartmentID = E2.DepartmentID
AND E2.Salary >= E1.Salary 
AND E1.DepartmentID = D1.ID      
GROUP BY E1.Name
HAVING COUNT(DISTINCT E2.Salary) <= 3
ORDER BY D1.Name, E1.Salary DESC;
~~~

## 上升的温度
给定一个 Weather 表，编写一个 SQL 查询，来查找与之前（昨天的）日期相比温度更高的所有日期的 Id。
~~~SQL
Create table If Not Exists Weather (Id int, RecordDate date, Temperature int)
Truncate table Weather
insert into Weather (Id, RecordDate, Temperature) values ('1', '2015-01-01', '10')
insert into Weather (Id, RecordDate, Temperature) values ('2', '2015-01-02', '25')
insert into Weather (Id, RecordDate, Temperature) values ('3', '2015-01-03', '20')
insert into Weather (Id, RecordDate, Temperature) values ('4', '2015-01-04', '30')
~~~

~~~SQL
select a.id from weather a left join weather b on DateDiff(a.recorddate,b.recordDate) = 1
where a.temperature > b.temperature
~~~

## 行程和用户
Trips 表中存所有出租车的行程信息。每段行程有唯一键 Id，Client_Id 和 Driver_Id 是 Users 表中 Users_Id 的外键。Status 是枚举类型，枚举成员为 (‘completed’, ‘cancelled_by_driver’, ‘cancelled_by_client’)。
Users 表存所有用户。每个用户有唯一键 Users_Id。Banned 表示这个用户是否被禁止，Role 则是一个表示（‘client’, ‘driver’, ‘partner’）的枚举类型。
写一段 SQL 语句查出 2013年10月1日 至 2013年10月3日 期间非禁止用户的取消率。基于上表，你的 SQL 语句应返回如下结果，取消率（Cancellation Rate）保留两位小数。

~~~sql
Create table If Not Exists Trips (Id int, Client_Id int, Driver_Id int, City_Id int, Status ENUM('completed', 'cancelled_by_driver', 'cancelled_by_client'), Request_at varchar(50))
Create table If Not Exists Users (Users_Id int, Banned varchar(50), Role ENUM('client', 'driver', 'partner'))
Truncate table Trips
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('1', '1', '10', '1', 'completed', '2013-10-01')
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('2', '2', '11', '1', 'cancelled_by_driver', '2013-10-01')
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('3', '3', '12', '6', 'completed', '2013-10-01')
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('4', '4', '13', '6', 'cancelled_by_client', '2013-10-01')
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('5', '1', '10', '1', 'completed', '2013-10-02')
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('6', '2', '11', '6', 'completed', '2013-10-02')
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('7', '3', '12', '6', 'completed', '2013-10-02')
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('8', '2', '12', '12', 'completed', '2013-10-03')
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('9', '3', '10', '12', 'completed', '2013-10-03')
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values ('10', '4', '13', '12', 'cancelled_by_driver', '2013-10-03')
Truncate table Users
insert into Users (Users_Id, Banned, Role) values ('1', 'No', 'client')
insert into Users (Users_Id, Banned, Role) values ('2', 'Yes', 'client')
insert into Users (Users_Id, Banned, Role) values ('3', 'No', 'client')
insert into Users (Users_Id, Banned, Role) values ('4', 'No', 'client')
insert into Users (Users_Id, Banned, Role) values ('10', 'No', 'driver')
insert into Users (Users_Id, Banned, Role) values ('11', 'No', 'driver')
insert into Users (Users_Id, Banned, Role) values ('12', 'No', 'driver')
insert into Users (Users_Id, Banned, Role) values ('13', 'No', 'driver')
~~~

~~~SQL
select t.Request_at Day,ROUND(sum((case when t.Status like 'cancelled%' then 1 else 0 end))/count(*),2) as'Cancellation Rate'
from Trips t
inner join Users u on u.Users_Id =t.Client_Id and u.Banned = 'No'
where t.Request_at between '2013-10-01'and'2013-10-03' group by t.Request_at;
~~~

## 大的国家
这里有张 World 表
如果一个国家的面积超过300万平方公里，或者人口超过2500万，那么这个国家就是大国家。
编写一个SQL查询，输出表中所有大国家的名称、人口和面积。
~~~SQL
Create table If Not Exists World (name varchar(255), continent varchar(255), area int, population int, gdp int)
Truncate table World
insert into World (name, continent, area, population, gdp) values ('Afghanistan', 'Asia', '652230', '25500100', '20343000000')
insert into World (name, continent, area, population, gdp) values ('Albania', 'Europe', '28748', '2831741', '12960000000')
insert into World (name, continent, area, population, gdp) values ('Algeria', 'Africa', '2381741', '37100000', '188681000000')
insert into World (name, continent, area, population, gdp) values ('Andorra', 'Europe', '468', '78115', '3712000000')
insert into World (name, continent, area, population, gdp) values ('Angola', 'Africa', '1246700', '20609294', '100990000000')
~~~

~~~SQL
select name,population,area from World where area > 3000000 or population > 25000000
~~~

##  超过5名学生的课
请列出所有超过或等于5名学生的课。
~~~SQL
Create table If Not Exists courses (student varchar(255), class varchar(255))
Truncate table courses
insert into courses (student, class) values ('A', 'Math')
insert into courses (student, class) values ('B', 'English')
insert into courses (student, class) values ('C', 'Math')
insert into courses (student, class) values ('D', 'Biology')
insert into courses (student, class) values ('E', 'Math')
insert into courses (student, class) values ('F', 'Computer')
insert into courses (student, class) values ('G', 'Math')
insert into courses (student, class) values ('H', 'Math')
insert into courses (student, class) values ('I', 'Math')
~~~

~~~sql
select class from courses group by class having count(distinct student) >= 5
~~~

## 体育馆的人流量

X 市建了一个新的体育馆，每日人流量信息被记录在这三列信息中：序号 (id)、日期 (date)、 人流量 (people)。

请编写一个查询语句，找出高峰期时段，要求连续三天及以上，并且每天人流量均不少于100。

~~~SQL
Create table If Not Exists stadium (id int, date DATE NULL, people int)
Truncate table stadium
insert into stadium (id, date, people) values ('1', '2017-01-01', '10')
insert into stadium (id, date, people) values ('2', '2017-01-02', '109')
insert into stadium (id, date, people) values ('3', '2017-01-03', '150')
insert into stadium (id, date, people) values ('4', '2017-01-04', '99')
insert into stadium (id, date, people) values ('5', '2017-01-05', '145')
insert into stadium (id, date, people) values ('6', '2017-01-06', '1455')
insert into stadium (id, date, people) values ('7', '2017-01-07', '199')
insert into stadium (id, date, people) values ('8', '2017-01-08', '188')
~~~

~~~SQL
select distinct a.*
from stadium a,stadium b,stadium c
where a.people>=100 and b.people>=100 and c.people>=100
and (
(a.id+1=b.id and b.id+1=c.id)or/*abc*/
(a.id+1=b.id and b.id+1=c.id)or/*acb*/
(a.id-1=b.id and a.id+1=c.id)or/*bac*/
(a.id-2=b.id and a.id-1=c.id)or/*bca*/
(a.id-1=c.id and a.id+1=b.id)or/*cab*/
(a.id-1=b.id and a.id-2=c.id)/*cba*/
)
order by a.id
~~~

~~~SQL
select DISTINCT a.* from stadium a, (
select id from (
SELECT id, date, people,
	case
		when DATEDIFF(@r,date)= -1 and (@r := date) then @count :=  @count + 1
		when (@r := date) then @count := 1
	end cnt
	from stadium,(select @r := null) b
	where people >= 100
)a where a.cnt >= 3
) b where a.id+1 = b.id or a.id + 2 = b.id or a.id = b.id
~~~

## 有趣的电影
某城市开了一家新的电影院，吸引了很多人过来看电影。该电影院特别注意用户体验，专门有个 LED显示板做电影推荐，上面公布着影评和相关电影描述。

作为该电影院的信息部主管，您需要编写一个 SQL查询，找出所有影片描述为非 boring (不无聊) 的并且 id 为奇数 的影片，结果请按等级 rating 排列。

~~~SQL
Create table If Not Exists cinema (id int, movie varchar(255), description varchar(255), rating float(2, 1))
Truncate table cinema
insert into cinema (id, movie, description, rating) values ('1', 'War', 'great 3D', '8.9')
insert into cinema (id, movie, description, rating) values ('2', 'Science', 'fiction', '8.5')
insert into cinema (id, movie, description, rating) values ('3', 'irish', 'boring', '6.2')
insert into cinema (id, movie, description, rating) values ('4', 'Ice song', 'Fantacy', '8.6')
insert into cinema (id, movie, description, rating) values ('5', 'House card', 'Interesting', '9.1')
~~~

~~~SQL
SELECT * FROM cinema where description <> 'boring' and MOD(id,2)=1 order by rating desc
~~~

## 换座位
~~~
小美是一所中学的信息科技老师，她有一张 seat 座位表，平时用来储存学生名字和与他们相对应的座位 id。

其中纵列的 id 是连续递增的

小美想改变相邻俩学生的座位。

你能不能帮她写一个 SQL query 来输出小美想要的结果呢？



示例：

+---------+---------+
|    id   | student |
+---------+---------+
|    1    | Abbot   |
|    2    | Doris   |
|    3    | Emerson |
|    4    | Green   |
|    5    | Jeames  |
+---------+---------+
假如数据输入的是上表，则输出结果如下：

+---------+---------+
|    id   | student |
+---------+---------+
|    1    | Doris   |
|    2    | Abbot   |
|    3    | Green   |
|    4    | Emerson |
|    5    | Jeames  |
+---------+---------+
注意：

如果学生人数是奇数，则不需要改变最后一个同学的座位。
~~~

~~~
Create table If Not Exists seat(id int, student varchar(255))
Truncate table seat
insert into seat (id, student) values ('1', 'Abbot')
insert into seat (id, student) values ('2', 'Doris')
insert into seat (id, student) values ('3', 'Emerson')
insert into seat (id, student) values ('4', 'Green')
insert into seat (id, student) values ('5', 'Jeames')
~~~

~~~
方法一：按题目的要求，对所有数据进行拆分，1、2互换，3、4互换，最后一个是奇数的不动，然后就分成三块来写，第一块就是id为偶数的，id-1就相当于和奇数的互换了，第二块是id为奇数的，id+1就相当于和偶数的互换了，最后一块是最后一个为奇数的，不换，然后三块合并排序就出来结果了。
---------------------
select s.id , s.student from
(
select id-1 as id ,student from seat where mod(id,2)=0
union
select id+1 as id,student from seat where mod(id,2)=1 and id !=(select count(*) from seat)
union
select id,student from seat where mod(id,2)=1 and id = (select count(*) from seat)
) s order by id;
~~~

~~~
方法二：此种方法是上面一种方法的一个拓展简化，思路差不多，也有区别

对照上表及其查询结果可以得知，当原id为奇数时，交换座位后的id变为id+1,当原id为偶数时，交换座位后的id变为id-1,另一个方面需要考虑的是，学生人数为奇数时，最后一个学生的id不变，故应当用子查询确定学生的人数，然后分情况讨论即可。
---------------------
select (case
      when mod(id,2)!=0 and id!=counts then id+1
      when mod(id,2)!=0 and id=counts then id
      else id-1 end)as id,student
      from seat,(select count(*)as counts from seat)as seat_counts
                order by id;
~~~

## 交换工资

~~~
给定一个 salary表，如下所示，有m=男性 和 f=女性的值 。交换所有的 f 和 m 值(例如，将所有 f 值更改为 m，反之亦然)。要求使用一个更新查询，并且没有中间临时表。

例如:

| id | name | sex | salary |
|----|------|-----|--------|
| 1  | A    | m   | 2500   |
| 2  | B    | f   | 1500   |
| 3  | C    | m   | 5500   |
| 4  | D    | f   | 500    |
运行你所编写的查询语句之后，将会得到以下表:

| id | name | sex | salary |
|----|------|-----|--------|
| 1  | A    | f   | 2500   |
| 2  | B    | m   | 1500   |
| 3  | C    | f   | 5500   |
| 4  | D    | m   | 500    |
~~~

~~~
create table if not exists salary(id int, name varchar(100), sex char(1), salary int)
Truncate table salary
insert into salary (id, name, sex, salary) values ('1', 'A', 'm', '2500')
insert into salary (id, name, sex, salary) values ('2', 'B', 'f', '1500')
insert into salary (id, name, sex, salary) values ('3', 'C', 'm', '5500')
insert into salary (id, name, sex, salary) values ('4', 'D', 'f', '500')
~~~

~~~
方法一：使用if函数
update salary set sex = if(sex = 'm', 'f','m');
~~~

~~~
方法二：使用case...when..then..else..end
UPDATE salary SET sex  = (CASE WHEN sex = 'm' THEN 'f' ELSE 'm' END)
~~~