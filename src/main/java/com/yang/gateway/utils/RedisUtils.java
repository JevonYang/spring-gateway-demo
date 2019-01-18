package com.yang.gateway.utils;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;

@Component
public class RedisUtils {

    private int DEFAULT_EXPIRED_TIME_SECONDS_OBJECT = 1296000;

    @Autowired
    private JedisCluster jedisCluster;

    public Long getNowTime() {
        String script = "local a=redis.call('TIME'); return a[1]*1000+a[2]";
        return (Long) jedisCluster.eval(script,"time");
    }


    public boolean putObject(String sKey, Serializable object, int expiredInSeconds) {
        boolean result = false;

        if (StringUtils.isEmpty(sKey)) {
            return result;
        }

        if (this.jedisCluster != null) {
            byte[] datas = SerializationUtils.serialize(object);
            byte[] arrKey = sKey.getBytes();

            this.jedisCluster.set(arrKey, datas);
            if (expiredInSeconds > 0) {
                this.jedisCluster.expire(arrKey, expiredInSeconds);
            }

            result = true;
        }

        return result;
    }

    public boolean putObject(String sKey, Serializable object) {
        return putObject(sKey, object, this.DEFAULT_EXPIRED_TIME_SECONDS_OBJECT);
    }

    public Object getObject(String sKey, Class<Object> oclass) {
        Object result = null;
        if (StringUtils.isEmpty(sKey)) {
            return result;
        }

        if (this.jedisCluster != null) {
            byte[] arrKey = sKey.getBytes();

            byte[] datas = this.jedisCluster.get(arrKey);

            if ((datas != null) && (datas.length > 0)) {
                result = SerializationUtils.deserialize(datas);
            }
        }

        return result;
    }

}
