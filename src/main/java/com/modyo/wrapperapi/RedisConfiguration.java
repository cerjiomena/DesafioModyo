package com.modyo.wrapperapi;

//@Configuration
//@EnableCaching
public class RedisConfiguration {

	/*@Value("${spring.redis.host}")
	private String REDIS_HOSTNAME;

	@Value("${spring.redis.port}")
	private int REDIST_PORT;

	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		final RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
		
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		
		redisTemplate.setHashKeySerializer(new GenericToStringSerializer<String>(String.class));
		
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(REDIS_HOSTNAME, REDIST_PORT);
		
		JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().build();
		
		JedisConnectionFactory factory = new JedisConnectionFactory(configuration, jedisClientConfiguration);
		
		factory.afterPropertiesSet();
		redisTemplate.setConnectionFactory(factory);

		return redisTemplate;
	}*/
}
