package com.yang.gateway.service;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author jevon
 */
public class FunnelRateLimiter {

    static class Funnel {
        int capacity;
        float leakingRate;
        int leftQuota;
        long leakingTs;

        public Funnel(int capacity, float leakingRate) {
            this.capacity = capacity;
            this.leakingRate = leakingRate;
            this.leftQuota = capacity;
            this.leakingTs = System.currentTimeMillis();
        }

        void makeSpace() {
            long nowTs = System.currentTimeMillis();
            long deltaTs = nowTs - leakingTs;
            int deltaQuota = (int) (deltaTs * leakingRate);
            if (deltaQuota < 0) {
                // 间隔时间太长，整数数字过大溢出
                this.leftQuota = capacity;
                this.leakingTs = nowTs;
                return;
            }
            if (deltaQuota < 1) {
                // 腾出空间太小，最小单位是1
                return;
            }
            this.leftQuota += deltaQuota;
            this.leakingTs = nowTs;
            if (this.leftQuota > this.capacity) {
                this.leftQuota = this.capacity;
            }
        }

        boolean watering(int quota) {
            makeSpace();
            if (this.leftQuota >= quota) {
                this.leftQuota -= quota;
                return true;
            }
            return false;
        }
    }

    private Map<String, Funnel> funnels = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("%s:%s", userId, actionKey);
        Funnel funnel = funnels.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnels.put(key, funnel);
        }
        return funnel.watering(1);
        // 需要1个quota
    }

    private static int LATCH_SIZE = 8;

    private static String URL = "http://localhost:8080/test";

    private static CountDownLatch countDown;

    private static int count = 0;

    public static void main(String[] args) throws Exception {

        countDown = new CountDownLatch(LATCH_SIZE);

        for (int i = 0; i < LATCH_SIZE; i++) {
            new InnerThread().start();
        }

        countDown.await();
        System.out.println("count: " + count);
    }

    static class InnerThread extends Thread {

        @Override
        public void run() {

            for (int i = 0; i < 1000; i++) {
                try {
                    new MipHttpClient().sendGet(URL);
                    count ++ ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            countDown.countDown();

        }
    }

}
