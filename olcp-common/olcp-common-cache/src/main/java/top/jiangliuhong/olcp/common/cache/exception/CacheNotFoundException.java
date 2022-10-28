package top.jiangliuhong.olcp.common.cache.exception;

import top.jiangliuhong.olcp.common.exception.BusinessException;

public class CacheNotFoundException extends BusinessException {

    public CacheNotFoundException(String cacheName) {
        super("cache[" + cacheName + "] not found");
    }

}
