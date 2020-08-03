package com.yr.redis.java;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class redis_java {

    public static void main(String[] args) {
        // 连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.1.177", 8888);
        System.out.println("连接本地的 Redis 服务成功！");
        // 查看服务是否运行
        System.out.println("服务运行状况: " + jedis.ping());

        //list();
        //map();
        //set();
        sort();

    }

        // Redis Java List
         public static void list(){

                // 连接本地的 Redis 服务
                Jedis jedis = new Jedis("192.168.1.177", 8888);

                 // 存储数据到列表中  redis list
                jedis.lpush("list", "diaomao");
                jedis.lpush("list", "17");
                jedis.lpush("list", "hunan");

                // 删除key中的值·
                 //jedis.del("list");

                // 获取存储的数据并输出
                List<String> list = jedis.lrange("list", 0, -1);
                for (int i = 0; i < list.size(); i++) {
                    System.out.println("redis list里面存储的值是:" + list.get(i));
                }
            }


        // Redis Java Map
         public static void map() {

             // 连接本地的 Redis 服务
             Jedis jedis = new Jedis("192.168.1.177", 8888);

            //-----添加数据----------
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "叼毛");
            map.put("age", "18");
            map.put("qq", "123456");

            jedis.hmset("user",map);
            //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
            //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
            List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
            System.out.println(rsmap);

            //删除map中的某个键值
            jedis.hdel("user","age");
            System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null
            System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2
            System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true
            System.out.println(jedis.hkeys("user"));//返回map对象中的所有key
            System.out.println(jedis.hvals("user"));//返回map对象中的所有value

            Iterator<String> iter=jedis.hkeys("user").iterator();
            while (iter.hasNext()){
                String key = iter.next();
                System.out.println(key+":"+jedis.hmget("user",key));
            }
    }

        // Redis Java Set
        public static void set() {

            // 连接本地的 Redis 服务
            Jedis jedis = new Jedis("192.168.1.177", 8888);

            jedis.sadd("users", "a");
            jedis.sadd("users", "b");
            jedis.sadd("users", "c");
            jedis.sadd("users", "d");
            jedis.sadd("users", "e");

            //移除 e
            jedis.srem("users", "e");
            System.out.println(jedis.smembers("users"));//获取所有加入的value
            System.out.println(jedis.sismember("users", "e"));//判断 who 是否是user集合的元素
            System.out.println(jedis.srandmember("users"));
            System.out.println(jedis.scard("users"));//返回集合的元素个数

        }

        // Redis Java Sort
        public static void sort() {
            //jedis 排序
            //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）

            // 连接本地的 Redis 服务
            Jedis jedis = new Jedis("192.168.1.177", 8888);

            jedis.del("a");//先清除数据，再加入数据进行测试
            jedis.rpush("a", "1");
            jedis.lpush("a","2");
            jedis.lpush("a","3");
            jedis.lpush("a","4");
            System.out.println("排序: "+(jedis.sort("a"))); //[1, 2, 3, 4]  //输入排序后结果
            System.out.println("最后一个是: "+(jedis.sort("a","desc")));//[4, 3, 2, 1]  //输入排序后结果
            System.out.println("查询 a: "+(jedis.lrange("a",0,-1)));// [4, 3, 2, 1]
        }
}
