1. 分布式服务拆分（业务角度切分, 服务与服务之间的循环依赖）
2. 分布式框架
3. 分布式事务
4. 分布式锁
5. 分布式点单登录
6. 分布监控 stagemonitor
7. 分布日志 ELK 日志管理
8. 分布式链路追踪 SkyWalking
9. 分布式任务调度 xxl-job 

面临的问题：
全局 404 500 接口提示  页面
系统日志邮件提醒
gradle 使用
前端页面权限怎么控制 
 
 
规定：
1.文件上传：http  -->  dubbo service --> 数据库
	  oss得到path  -->  传输path  -->  存path
2.vo,po,bo,dto,dao
