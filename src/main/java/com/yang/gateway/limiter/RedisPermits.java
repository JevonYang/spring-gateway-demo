package com.yang.gateway.limiter;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author jevon
 */

@Data
public class RedisPermits {

//    /**
//     * maxPermits 最大存储令牌数
//     */
//    private Long maxPermits;
//    /**
//     * storedPermits 当前存储令牌数
//     */
//    private Long storedPermits;
//    /**
//     * intervalMillis 添加令牌时间间隔
//     */
//    private Long intervalMillis;
//    /**
//     * nextFreeTicketMillis 下次请求可以获取令牌的起始时间，默认当前系统时间
//     */
//    private Long nextFreeTicketMillis;

//    private PermitsEntity.Permits permits;
//
//    public PermitsEntity.Permits getPermits() {
//        return permits;
//    }
//
//    public void setPermits(PermitsEntity.Permits permits) {
//        this.permits = permits;
//    }

    /**
     * @param permitsPerSecond 每秒放入的令牌数
     * @param maxBurstSeconds  maxPermits由此字段计算，最大存储maxBurstSeconds秒生成的令牌
     */

    public static PermitsEntity.Permits getPermits(double permitsPerSecond,  int maxBurstSeconds) {

        PermitsEntity.Permits.Builder builder = PermitsEntity.Permits.newBuilder();
        if (maxBurstSeconds == 0) {
            maxBurstSeconds = 60;
        }

        builder.setMaxPermits((long) (permitsPerSecond * maxBurstSeconds));
        builder.setStoredPermits((long) permitsPerSecond);
        builder.setIntervalMillis((long) (TimeUnit.SECONDS.toMillis(1) / permitsPerSecond));
        builder.setNextFreeTicketMillis(System.currentTimeMillis());
        return builder.build();
    }

    /**
     * redis的过期时长
     * @return
     */
    public static long expires(PermitsEntity.Permits permits) {
        long now = System.currentTimeMillis();
        return 2 * TimeUnit.MINUTES.toSeconds(1)
                + TimeUnit.MILLISECONDS.toSeconds(Math.max(permits.getNextFreeTicketMillis(), now) - now);
    }

    /**
     * 异步更新当前持有的令牌数
     * 若当前时间晚于nextFreeTicketMicros，则计算该段时间内可以生成多少令牌，将生成的令牌加入令牌桶中并更新数据
     * @param now
     * @return
     */
    public static boolean resync(PermitsEntity.Permits permits, long now){
        if (now > permits.getNextFreeTicketMillis()) {
            PermitsEntity.Permits.Builder builder = permits.toBuilder().setStoredPermits(Math.min(permits.getMaxPermits(), permits.getStoredPermits() + (now - permits.getNextFreeTicketMillis()) / permits.getIntervalMillis()));
            builder.setNextFreeTicketMillis(now);
            permits = builder.build();
            return true;
        }
        return false;
    }

    @Deprecated
    public static PermitsEntity.Permits update(PermitsEntity.Permits permits, long now) {
        if (now > permits.getNextFreeTicketMillis()) {
            permits.toBuilder().setStoredPermits(Math.min(permits.getMaxPermits(), permits.getStoredPermits() + (now - permits.getNextFreeTicketMillis()) / permits.getIntervalMillis()));
            permits.toBuilder().setNextFreeTicketMillis(now);
            return permits;
        }
        return permits;
    }

}
