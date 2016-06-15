# SEWM 

2016-05.27 Update
--------------------------------------
修改毕设评审逻辑
添加选题教师tel提示

2016-05.26 Update
--------------------------------------
合并毕业设计模块
添加教师主页面
导入学生信息
开启关闭监考各阶段
毕设题目管理
学生选题
阶段评审
指导
选题结果下载

2016-03.01 Update
--------------------------------------
=======

2016-05.08 Update
--------------------------------------
左边侧栏功能过多，取消固定设置   
监考信息按监考时间排序，利于分配时先分配最近的监考，按监考状态分别列出？  
优化页面显示数据，在监考列表中添加星期与周数  
通过设置监考日期第一天属性为星期一，修复周日监考周数计算错误   
添加导出监考信息表格       
优化资源操作   
优化上传文件分析，基于流分析，取消生成本地文件减少IO操作  
等   
    
2016-04.14 Update
--------------------------------------
修改form横向尺寸    
为节约成本，在修改监考人员后应仅向新增加人员发送短信通知，因此分离发送监考通知短信，允许管理员手动确认发送对象  
优化课表信息提取表达式   
取消headers fixed属性，flat-ui switch在header为fixed时，在md宽度超过一屏滚动产生bug，在sm宽度正常     
增加分页    
增加监考短信提醒，无服务器，timer无法运行，暂时基于手动    


2016-03.01 Update
--------------------------------------
>>>>>>> refs/remotes/origin/master
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
