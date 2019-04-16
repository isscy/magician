# magician

一. 项目说明
    
    基于Spring Cloud Greenwich.SR1 的快速快速开发平台
    

    | 类别                | 框架          |
    | --------------     |----------- |
    | 核心框架             |  springcloud Greenwich |
    | 安全框架             |   Spring Security、 Spring Cloud Oauth2     |
    | 持久层框架           | MyBatis-Plus        |
    | 数据库连接池         | Alibaba Druid       |
    | 消息队列             | RabbitMQ     |
    | 分布式任务调度        | elastic-job       |
    


二. 技术栈及项目结构说明


三. 开发要点
    
    1. 所有接口必须保证幂等，特别是微服务之间调用修改的接口（Feign超时自动重发请求、消息队列重复处理）
    2. gateway使用了路由重试，所以接口必须符合RESTful，尤其是GET只能用来查询！
