/*-
Copyright [2020] Karthik.V

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.alpha.visitor.listener;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisPoolFactory {

	private JedisPool jedisPool;

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private Integer redisPort;

	private JedisPool createJedisPool() throws IOException {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(128);
		return new JedisPool(poolConfig, redisHost, redisPort);
	}

	@PostConstruct
	public void init() {
		try {
			jedisPool = createJedisPool();
		} catch (IOException e) {
		}
	}

	@PreDestroy
	public void cleanUp() {
		if (jedisPool != null) {
			jedisPool.destroy();
		}
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

}