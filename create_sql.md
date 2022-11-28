# 创建 OneOffWord 的数据库  

### 创建名叫 `oneoffword` 的库  
```
create database if not exists OneOffWord;
```
(应该会失败,因为没有权限,创建失败可以去  phpMyAdmin 创建) 

### 查看当前 有哪些库  
```
show databases;
```

### 使用 `OneOffWord` 库 
```
use oneoffword;
```

### 创建名为 `messagedata` 的表   
```
create table if not exists messagedata(`id` char(5) not null, `word` varchar(10000) not null, `date` datetime not null, `timelimit` smallint not null, primary key(id)) engine=InnoDB default charset=utf8; 
```  
### 查看该库有哪些表
```
show tables;
```

### 查看 `messagedata` 表中的数据
```
select * from messagedata;
```
