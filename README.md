# SEWM

2016.02.01 Update
------------------------
GenericDao: 使用反射获取子类声明的具体泛型类型，使子类无需传入泛型类型参数
JSON: 基于gson.jar，spring自动完成对象到json字符串的转化，前端ajax返回类型使用text接收json字符串，然后转为json对象
applicationContext: 浏览工程根目录时映射到main，便于当作首页保存，支持拦截器

Development Environment
-----------------------------------
Server: Tomcat(8.0.26)  
Database: MySQL(5.6)  
Persistence Layer: JPA; Hibernate(5.0.1)  
Business Layer: Spring(4.2.1)  
Web Layer: SpringMVC  
UI: AJAX; JQuery(2.1.4); Bootstrap(3.3.5); Flat-ui; DateTime Picker

Others  
----------
JSTL
POI
dom4j
