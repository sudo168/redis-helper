package net.ewant.redis.support;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RedisContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {


	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		RedisBeanDefinitionRegistryProcessor redisBeanDefinitionProcessor = new RedisBeanDefinitionRegistryProcessor();
		redisBeanDefinitionProcessor.setEnvironment(applicationContext.getEnvironment());
		applicationContext.addBeanFactoryPostProcessor(redisBeanDefinitionProcessor);
	}
}
