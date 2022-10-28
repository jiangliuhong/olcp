package top.jiangliuhong.olcp.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SpringUtils implements BeanFactoryPostProcessor {

    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        return (T) beanFactory.getBean(clz);
    }

    public static <T> List<T> getBeans(Class<T> clz) {
        List<T> list = new ArrayList<>();
        Map<String, T> beansOfType = beanFactory.getBeansOfType(clz);
        beansOfType.forEach((name, beans) -> {
            list.add(beans);
        });
        // sort by order
        /*list.sort((bean1, bean2) -> {
            Order order1 = bean1.getClass().getAnnotation(Order.class);
            Order order2 = bean2.getClass().getAnnotation(Order.class);
            int orderValue1 = order1 != null ? order1.value() : Integer.MAX_VALUE;
            int orderValue2 = order2 != null ? order2.value() : Integer.MAX_VALUE;
            return orderValue1 - orderValue2;
        });*/
        return list;
    }

    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

}
