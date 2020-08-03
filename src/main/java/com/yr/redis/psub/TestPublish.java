package com.yr.redis.psub;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class TestPublish {

    @Test
    public void testPublish() {
        Jedis jedis = new Jedis("192.168.1.177",8888);
        jedis.publish("test","李杰交流群");
        jedis.publish("test","请加");
        jedis.publish("test","123456789");

    }
}