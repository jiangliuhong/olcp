package top.jiangliuhong.olcp.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ClusterDBContext {

    private static final ThreadLocal<String> dbContext = new ThreadLocal<>();
    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static final Map<Object, Object> dataSources = new HashMap<>();

    private static final List<String> masterKeys = new ArrayList<>();
    private static final List<String> slaveKeys = new ArrayList<>();

    public static void registry(String key, Object obj) {
        dataSources.put(key, obj);
        if (StringUtils.startsWith(key, "master")) {
            masterKeys.add(key);
        } else {
            slaveKeys.add(key);
        }
    }

    public static Object getDefaultMaster() {
        if (masterKeys.isEmpty()) {
            throw new RuntimeException("not found master datasource");
        }
        return dataSources.get(masterKeys.get(0));
    }

    public static void set(String dbType) {
        dbContext.set(dbType);
    }

    public static String get() {
        return dbContext.get();
    }

    public static void master() {
        int index = counter.getAndIncrement() % masterKeys.size();
        String slaveKey = masterKeys.get(index);
        log.debug("change master datasource ,use {}", slaveKey);
        set(slaveKey);
    }

    public static void slave() {
        if (slaveKeys.isEmpty()) {
            log.warn("not found slave datasource,use master");
            master();
            return;
        }
        //  读库负载均衡(轮询方式)
        int index = counter.getAndIncrement() % slaveKeys.size();
        String slaveKey = slaveKeys.get(index);
        log.debug("change slave datasource ,use {}", slaveKey);
        set(slaveKey);
    }
}
