package com.yr.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class redis_cluster {
    // private static Jedis jedis;
    private static JedisCluster jedis;

    public static void conn() {
        /*
         * if (jedis == null) { jedis = new Jedis("192.168.1.172", 6379); }
         */

        if (jedis == null) {

            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            jedisClusterNodes.add(new HostAndPort("192.168.1.177", 8001));
            jedisClusterNodes.add(new HostAndPort("192.168.1.177", 8001));
            jedisClusterNodes.add(new HostAndPort("192.168.1.177", 8001));

            jedisClusterNodes.add(new HostAndPort("192.168.1.177", 8002));
            jedisClusterNodes.add(new HostAndPort("192.168.1.177", 8002));
            jedisClusterNodes.add(new HostAndPort("192.168.1.177", 8002));

            jedisClusterNodes.add(new HostAndPort("192.168.1.177", 8003));
            jedisClusterNodes.add(new HostAndPort("192.168.1.177", 8003));
            jedisClusterNodes.add(new HostAndPort("192.168.1.177", 8003));

            jedis = new JedisCluster(jedisClusterNodes);

        }
    }

    public static void setString(String key, String value) {
        conn();
        jedis.set(key , value);
    }

    public static String getString(String key) {
        return jedis.get(key);
    }

    public static void setHash(String key, String field, String value) {
        jedis.hset(key, field, value);
    }

    public static String getHash(String key, String field) {
        return jedis.hget(key, field);
    }

    //1 -  9
    public static void main(String[] args) {
       // setString("sex","ÄÐ");

        conn();
        Set<String> key = new HashSet<String>();
        Map<String, JedisPool> clusterNodes = jedis.getClusterNodes();
        for (String k : clusterNodes.keySet()) {
            JedisPool jp = clusterNodes.get(k);
            Set<String> set = jp.getResource().keys("*");
            key.addAll(set);
        }

        for (String string : key) {
            System.out.println(string);
        }
    }

}
