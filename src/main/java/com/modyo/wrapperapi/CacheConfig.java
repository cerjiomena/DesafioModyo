package com.modyo.wrapperapi;

//@Configuration
//@EnableConfigurationProperties(RedisProperties.class)
public class CacheConfig {
  /*@Bean
  public JedisConnectionFactory jedisConnectionFactory() {
	  JedisConnectionFactory factory = new JedisConnectionFactory();
	  factory.getPoolConfig().setMaxIdle(30);
	  factory.getPoolConfig().setMinIdle(10);
      return factory;
  }
   
	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<?, ?> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}*/
	
	 /*@Bean
	    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
	        RedisTemplate<?, ?> template = new RedisTemplate<>();
	        template.setConnectionFactory(connectionFactory);
	        // Add some specific configuration here. Key serializers, etc.
	        return template;
	    }*/

}
