package top.jiangliuhong.olcp.data.config;

import groovy.lang.GroovyClassLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class DataGroovyClassLoader extends GroovyClassLoader {

    public DataGroovyClassLoader(ClassLoader loader) {
        super(loader);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //System.out.println("find class:" + name);
        String source = this.getStringByte(name);
        if (source != null) {
            return super.parseClass(source);
        } else {
            return super.findClass(name);
        }
    }

    @Override
    protected boolean isRecompilable(Class cls) {
        if (cls == null) {
            return true;
        }
        if (cls.getName().equals("com.test.Service")) {
            return true;
        }

        return super.isRecompilable(cls);
    }

    private String getStringByte(String name) {
        if (StringUtils.equals(name, "com.test.Service")) {
            int a = new Random().nextInt(100);
            System.out.println("do :com.test.Service:" + a);
            String tpl = "package com.test;\n" +
                    "class Service{\n" +
                    "    def res(){\n" +
                    "        return " + a + ";\n" +
                    "    }\n" +
                    "}";
            return tpl;
        } else {
            return null;
        }
    }
}
