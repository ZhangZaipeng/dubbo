1. 分布式服务拆分（业务角度切分, 服务与服务之间的循环依赖）
    common : 短息，文件上传，消息
    system : 后台配置，
    pay : 支付通道
    report : 统计报表
    wallet : 钱包
    merchant : 商户
    underwriter : 承兑商
    
2. 分布式框架
    dubbo 微服务
3. 分布式事务
    tcc-transaction, mq
4. 分布式锁
    redis
    zk 锁
5. 分布式点单登录 
6. 分布监控 
    stagemonitor（JAVA）
    zabbix（服务器）
7. 分布日志 ELK 日志管理 
    (service --> kafka --> Logstash --> ES --> kibana) 
    https://blog.csdn.net/zhangruhong168/article/details/76973212
    https://www.jianshu.com/p/d1be3364f32d
8. 分布式链路追踪 
    SkyWalking
9. 分布式任务调度 
    xxl-job 
10.服务熔断、降级
    https://www.cnblogs.com/wx170119/p/10232141.html

面临的问题：
    全局 404 500 接口提示  页面
    系统错误日志邮件提醒
 
规定：
    1.文件上传：http  -->  dubbo service --> 数据库
        oss得到path  -->  传输path  -->  存path
    2.vo,po,bo,dto,dao
    3. docker 每个服务都必须使用静态ip

运维：
    数据迁移: 
      RabbitMq：https://blog.csdn.net/kevinsingapore/article/details/82501131, 
      redis, 
      elasticsearch, 
      Mysql
    stack 网络隔离
