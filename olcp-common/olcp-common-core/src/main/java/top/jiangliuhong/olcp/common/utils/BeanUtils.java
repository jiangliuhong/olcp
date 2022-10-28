package top.jiangliuhong.olcp.common.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class BeanUtils {

    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static void copyNotNullProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullField(source));
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

}
