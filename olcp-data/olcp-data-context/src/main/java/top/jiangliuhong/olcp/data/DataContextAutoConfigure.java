package top.jiangliuhong.olcp.data;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.jiangliuhong.olcp.data.config.DataGroovyClassLoader;
import top.jiangliuhong.olcp.data.config.GroovyConfig;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("top.jiangliuhong.olcp.data.context")
@Import({GroovyConfig.class})
public class DataContextAutoConfigure {

    public static void main(String[] args) {
        String scriptString = "package com.test.rule \n" +
                "def exec(args){ \n" +
                " def s = new Service() \n"+
                "    return args['a'] + args['b'] + s.res()\n" +
                "}";
        Binding binding = new Binding();

        GroovyClassLoader groovyClassLoader = new DataGroovyClassLoader(Thread.currentThread().getContextClassLoader());
        // 设置配置
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("utf-8");
        // 设置groovy 执行shell
        GroovyShell groovyShell = new GroovyShell(groovyClassLoader, binding, compilerConfiguration);
        Script script = groovyShell.parse(scriptString);
        Map<String, Object> map = new HashMap<>() {{
            put("a", 1);
            put("b", 2);
        }};
        Object exec = script.invokeMethod("exec", map);
        System.out.println(exec);
    }
}
