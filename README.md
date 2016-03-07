# SEWM

2016-03.01 Update
--------------------------------------
未完成：通知短信模板；教师详细信息查询；   
完成：文件上传下载；文件任务类型；单一文件版本控制；   
重新规划工程结构   

2016-02-18 Update
--------------------------------------
课表导入
监考导入
手动添加监考
手动添加特殊监考
监考推荐
监考分配
监考分配短信发送

已完成监考模块主要功能

2016.02.07 Update
------------------------------------
完成admin用户的添加，用户基本信息、权限、通知、推荐的修改及设置  
基于后台数据的checkbox默认状态
默认基于正序的获取  
自定义异常  
修改课表等实体类
文件上传  
读取课表，封装为实体对象，保存
基于enum的常量设计
  

2016.02.01 Update
------------------------
GenericDao: 使用反射获取子类声明的具体泛型类型，使子类无需传入泛型类型参数  
JSON: 基于gson.jar，spring自动完成对象到json字符串的转化，前端ajax返回类型使用text接收json字符串，然后转为json对象  
浏览工程根目录时映射到main，便于当作首页保存，支持拦截器  

Development Environment
-----------------------------------
Server: Tomcat(8.0.26)  
Database: MySQL(5.6)  
Persistence Layer: JPA; Hibernate(5.0.1)  
Business Layer: Spring(4.2.1)  
Controller Layer: SpringMVC  
UI: AJAX; JQuery(2.1.4); Bootstrap(3.3.5); Flat-ui; DateTime Picker

Others  
----------
JSTL
POI
dom4j
