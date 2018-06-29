package net.ewant.redis.support;

import net.ewant.redis.RedisHelper;
import net.ewant.redis.factory.JedisConnectionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.env.Environment;

import java.util.Set;

public class RedisBeanDefinitionRegistryProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String SCANNER_PACKAGE = RedisHelper.class.getPackage().getName();

    private Environment environment;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registerComponetProcessorBean(registry);
        registerRedisBean(registry);
    }

    private void registerComponetProcessorBean(BeanDefinitionRegistry registry){
        RedisClassPathBeanDefinitionScanner scanner = new RedisClassPathBeanDefinitionScanner(registry, true, environment, null);
        Set<BeanDefinitionHolder> beanDefinitionHolders = scanner.doScan(SCANNER_PACKAGE);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders){
            registry.registerBeanDefinition(beanDefinitionHolder.getBeanName(), beanDefinitionHolder.getBeanDefinition());
        }
    }

    private void registerRedisBean(BeanDefinitionRegistry registry){

        RootBeanDefinition redisConnectionBeanDefinition = new RootBeanDefinition();
        redisConnectionBeanDefinition.setBeanClass(JedisConnectionFactory.class);
        redisConnectionBeanDefinition.setRole(BeanDefinition.ROLE_APPLICATION);
        registry.registerBeanDefinition(JedisConnectionFactory.class.getSimpleName(), redisConnectionBeanDefinition);

        RootBeanDefinition redisHelperBeanDefinition = new RootBeanDefinition();
        redisHelperBeanDefinition.setBeanClass(RedisHelper.class);
        redisHelperBeanDefinition.setRole(BeanDefinition.ROLE_APPLICATION);
        registry.registerBeanDefinition(RedisHelper.class.getSimpleName(), redisHelperBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
