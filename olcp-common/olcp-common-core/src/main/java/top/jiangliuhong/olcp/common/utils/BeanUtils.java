package top.jiangliuhong.olcp.common.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.function.Consumer;

public final class BeanUtils {

    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static void copyNotNullProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullField(source));
    }

    public static void copyProperties(Object source, Object target, String... ignores) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignores);
    }

    public static void copyNotNullProperties(Object source, Object target, String... ignores) {
        String[] nullField = getNullField(source);
        if (ignores.length == 0) {
            org.springframework.beans.BeanUtils.copyProperties(source, target, nullField);
        } else {
            String[] strings = ArrayUtils.addAll(nullField, ignores);
            org.springframework.beans.BeanUtils.copyProperties(source, target, strings);
        }
    }

    public static String[] getNullField(Object target) {
        return getNullStatusField(target, true);
    }

    public static String[] getNotNullField(Object target) {
        return getNullStatusField(target, false);
    }

    public static String[] getNullStatusField(Object target, boolean nullStatus) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> fieldSet = new HashSet<>();
        if (propertyDescriptors.length > 0) {
            for (PropertyDescriptor p : propertyDescriptors) {
                String name = p.getName();
                Object value = beanWrapper.getPropertyValue(name);
                if (nullStatus && Objects.isNull(value)) {
                    fieldSet.add(name);
                }
                if (!nullStatus && !Objects.isNull(value)) {
                    fieldSet.add(name);
                }
            }
        }
        return fieldSet.toArray(new String[0]);
    }

    public static <T> T copyBean(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            T dest = clazz.getDeclaredConstructor().newInstance();
            copyProperties(source, dest);
            return dest;
        } catch (Exception e) {
            throw new RuntimeException("copy bean error:" + e.getMessage(), e);
        }
    }

    public static <T> List<T> copyBean(List<?> objects, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (objects == null || objects.size() == 0) {
            return list;
        }
        objects.forEach(object -> list.add(copyBean(object, clazz)));
        return list;
    }

}
