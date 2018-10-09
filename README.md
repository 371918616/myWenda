# myWenda

这个就是用来测试向神的项目的
这次把user的其他三个操作，更新密码，删除，查询写了

这次终于把首页的前端写完了，用的模板引擎是thymeleaf ，和velocity完全不一样，搞了一下午

10.6 早上，把注册登录功能写完了，主要是ticket的应用，以及密码的加密salt

10.6 下午 ，注册登录的 主要是hostHoder来实现页面的浏览，添加敏感词的树形结构，发现前端的问题解决不了，虽然写完了后台的提问，但是前端搞不定

10.9 这几天主要实现了站内信message ，利用redis实现了一些小功能， 比如关注问题 ，点赞以及使用redis自带的阻塞队列list来实现异步功能，异步的实现主要是在

```java
//事件的生产者
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try {
            //将事件序列化为jason串，便于后期反序列化为EventModel
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key , json);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
```



```java
/**
	这个接口定义了基本的时间处理
*/
public interface EventHandler {
    void doHandle(EventModel eventModel);

    List<EventType> getSupportEventTypes();
}
```

```java
public class EventConsumer implements InitializingBean , ApplicationContextAware{
    //这里实现applicationContext,上下文，可以来查找初始化的beans
    private ApplicationContext applicationContext;
    //这个map是用来储存一个EventType会有多少个对应的EventHandler去处理
    private Map<EventType , List<EventHandler>> config = new HashMap<>();
    
     @Override
    public void afterPropertiesSet() throws Exception {
        //得到所有的Eventhandler
        Map<String , EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans != null){
            //将Eventhandler中的关注的事件分贝注册到
            for(Map.Entry<String , EventHandler> entry : beans.entrySet()){
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for(EventType type : eventTypes){
                    if(!config.containsKey(type)){
                        config.put(type , new ArrayList<>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
        Thread thread = new Thread(()-> {

                while (true){
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0 , key);
                    for(String message : events){
                        if(message.equals(key)){
                            continue;
                        }
                        //通过JSON将事件反序列化为对应的EventModel
                        EventModel eventModel = JSON.parseObject(message , EventModel.class);
                        if(!config.containsKey(eventModel.getType())){
                            logger.error("不能识别的事件类型");
                            continue;
                        }
                        for(EventHandler handler : config.get(eventModel.getType())){
                            handler.doHandle(eventModel);
                        }
                    }
                }

        });
        thread.start();
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }
        
        
}
```