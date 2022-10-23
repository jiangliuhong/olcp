package top.jiangliuhong.olcp.common.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * resolve interceptor
 */
public class EntityInterceptorConfig {
    private final List<IEntityInterceptor> interceptors;
    private final Map<IEntityInterceptor, Class> interceptorsGeneric = new HashMap<>();

    public EntityInterceptorConfig(List<IEntityInterceptor> interceptors) {
        this.interceptors = interceptors;
        for (IEntityInterceptor interceptor : interceptors) {
            Class genericType = getGenericType(interceptor);
            if (genericType != null) {
                this.interceptorsGeneric.put(interceptor, genericType);
            }
        }
    }

    /**
     * loop exec interceptor
     * 
     * @param dest
     * @param consumer
     */
    public void intercept(Object dest, Consumer<IEntityInterceptor> consumer) {
        for (IEntityInterceptor interceptor : interceptors) {
            if (this.checkCall(interceptor, dest)) {
                consumer.accept(interceptor);
            }
        }
    }

    private Class getGenericType(Object ins) {
        ParameterizedType parameterizedType = (ParameterizedType)ins.getClass().getGenericSuperclass();
        if (parameterizedType == null) {
            return null;
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length == 0) {
            return null;
        }
        return (Class)actualTypeArguments[0];
    }

    private boolean checkCall(IEntityInterceptor interceptor, Object object) {
        Class type = this.interceptorsGeneric.get(interceptor);
        if (type == null) {
            return false;
        }
        return type.isAssignableFrom(object.getClass());
    }

}
