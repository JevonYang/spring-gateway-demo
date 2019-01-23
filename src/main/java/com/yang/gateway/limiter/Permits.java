package com.yang.gateway.limiter;

import lombok.*;

/**
 *
 * @param maxPermits 最大token数量
 * @param currentPermits 当前token数量
 * @param rate 每秒添加token数量
 * @param lastMilliSecond 上一次更新时间
 * @author jevon
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permits {

    long maxPermits = 1;

    long currentPermits = 2;

    long rate = 3;

    long lastMilliSecond = 4;
}
