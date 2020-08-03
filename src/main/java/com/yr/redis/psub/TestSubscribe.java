package com.yr.redis.psub;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class TestSubscribe {

    @Test
    public void testSubscribe() {
        Jedis jedis = new Jedis("192.168.1.177",8888);
        RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
        jedis.subscribe(listener, "test");
    }
}