package com.yang.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jevon
 */

@Configuration
@EnableCaching
public class RedisClusterConfig {
	@Value("${redis.cluster.nodes}")
	private String nodes;

	@Value("${redis.cluster.maxActive}")
	private int maxActive;

	@Value("${redis.cluster.maxIdle}")
	private int maxIdle;

	@Value("${redis.cluster.maxWaitMills}")
	private int maxWaitMills;

	@Value("${redis.cluster.minIdle}")
	private int minIdle;

	@Value("${redis.cluster.timeOut}")
	private int timeOut;

	@Value("${redis.cluster.password}")
	private String password;

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWaitMills() {
		return maxWaitMills;
	}

	public void setMaxWaitMills(int maxWaitMills) {
		this.maxWaitMills = maxWaitMills;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Bean
	public JedisCluster getJedisCluster() {
		String[] serverArray = nodes.split(",");
		Set<HostAndPort> nodes = new HashSet<>();

		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));

		}
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
		jedisPoolConfig.setMaxWaitMillis(maxWaitMills);
		// 最大空闲连接数, 默认8个
		jedisPoolConfig.setMaxIdle(maxIdle);
		// 最大连接数, 默认8个
		jedisPoolConfig.setMaxTotal(maxActive);
		// 最小空闲连接数, 默认0
		jedisPoolConfig.setMinIdle(minIdle);

		// 创建集群对象
		return new JedisCluster(nodes, timeOut, 1000, 1, password, jedisPoolConfig);

	}
}