# LiteFlow-Redis-improver
将规则信息初步与外部的redis进行对接，目前已支持通过http请求更改规则信息

## 架构图
![image](https://github.com/nacey5/LiteFlow-Redis-improver/assets/85286598/f2df746c-5ce4-403d-a154-052dfc1b27d7)


规划如下：
1. 成功对接redis 完成
2. redis存入数据并能从redis加载规则 完成
3. 通过http请求完成对redis规则的动态更改 完成
4. 使用监听模式，当key的键值更改时动态更新----->目前正在寻找一种方式对redis客户端直接更改能进行监听 完成
5. 伪监听：通过redis的发布订阅来进行第四个功能的伪装实现 完成
6. 外部传入java代码，完成热更新 完成
7. 动态实现bean类注册，外部完成支持动态更新类之后，要完成对bean类的更新 完成
8. 创建一套基于此套中台的rpc调用流程 搁置
   - a.前期先实现基于反射去调用 类名+方法名完成调用
   - b. 实现rpc调用，泛化调用
9. 代码优化持续中，实现以上基础功能之后，要考虑将上述的功能通用化，目前只考虑功能实现
   - a. 这里有很多细节，例如如何定位到类文件，是否需要设置缓存，redis作为库，是否需要用作二级缓存来实现
   - b. 希望后续使用泛化调用完成这个功能
10. 错误码排期
11. 网关功能
   - a.灰度 完成
   - b.拦截无效ak 进行中:
     - 目前存在bug，无法选择一个固定的redis库来保存数据  DEBUG ->完成
     - ak判断 完成
       - ak map清除，目前是想在某个具体的时间去清除一遍Map，初步的想法是使用另外一个Map去保存记录的时间，当时间超过10分钟的时候，清空CacheMap，需要去到redis中去继续寻找
12. 规则执行器
    - 模型：
    - 结构：
    如下图所示：
![image](https://github.com/nacey5/LiteFlow-Redis-improver/assets/85286598/4db475dd-f1f5-4e0f-81e8-4df715a73899)


bug: 
    1.目前如果单独在一个服务注册bean，在另外一个服务中并不会一起注册，所以需要在网关中去处理，出现需要双份注册的时候，由网关对两个部分的内容一起注册
        - 目前存在问题的接口：【在外部注册的需要全局去执行的】
        1:目前只存在于注册bean，因为分流之后，注册bean是存储在各自的分布器当中，所以我的解决方案是打算引入 规则业务执行，也就是上述引入的规则执行器
         目前存在一个问题，因我不想开启服务发现导致平衡器无法正常的进行服务路由获取，所以有一下两种办法解决：
        
a.
~~~java
RestTemplate restTemplate = new RestTemplate();
String greenRouteUrl = "http://green_route/api/beans/register-bean";
String response = restTemplate.postForObject(greenRouteUrl, requestObject, String.class);
~~~
b. 
~~~java
WebClient webClient = WebClient.create();
String greenRouteUrl = "http://green_route/api/beans/register-bean";
Mono<String> responseMono = webClient.post()
.uri(greenRouteUrl)
.bodyValue(requestObject)
.retrieve()
.bodyToMono(String.class);
~~~
    2.目前在拦截器拦截下没有AK认证的服务请求的时候并不会返回错误和提示信息和日志

