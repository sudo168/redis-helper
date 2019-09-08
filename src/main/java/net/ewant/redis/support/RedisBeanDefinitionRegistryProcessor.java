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
        registerComponentProcessorBean(registry);
    }

    private void registerComponentProcessorBean(BeanDefinitionRegistry registry){
        RedisClassPathBeanDefinitionScanner scanner = new RedisClassPathBeanDefinitionScanner(registry, true, environment, null);
        scanner.doScan(SCANNER_PACKAGE);
        /*Set<BeanDefinitionHolder> beanDefinitionHolders = scanner.doScan(SCANNER_PACKAGE);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders){
            registry.registerBeanDefinition(beanDefinitionHolder.getBeanName(), beanDefinitionHolder.getBeanDefinition());
        }*/
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
