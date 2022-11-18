package top.jiangliuhong.olcp.data;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.jiangliuhong.olcp.data.script.DataGroovyClassLoader;
import top.jiangliuhong.olcp.data.config.GroovyConfig;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan({"top.jiangliuhong.olcp.data.context", "top.jiangliuhong.olcp.data.script"})
@Import({GroovyConfig.class})
public class DataContextAutoConfigure {

    public static void main(String[] args) {
        Binding binding = new Binding();

        GroovyClassLoader groovyClassLoader = new DataGroovyClassLoader(Thread.currentThread().getContextClassLoader(), null);
        // 设置配置
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("utf-8");
        // 设置groovy 执行shell
        GroovyShell groovyShell = new GroovyShell(groovyClassLoader, binding, compilerConfiguration);


//        String scriptString = "package com.test.rule \n" +
//                "import com.test.Service;\n" +
//                "def exec(args){ \n" +
//                " def s = new Service() \n" +
//                " print('s1:'+s.res()+'\\n');\n" +
//                " def s2 = new Service() \n" +
//                " print('s2:'+s2.res()+'\\n');\n" +
//                "    return args['a'] + args['b'] + s.res() + s2.res()\n" +
//                "}";
//
//        Script script = groovyShell.parse(scriptString);
//        Map<String, Object> map = new HashMap<>() {{
//            put("a", 1);
//            put("b", 2);
//        }};
//        Object exec = script.invokeMethod("exec", map);
//        System.out.println(exec);
        List<String> scripts = new ArrayList<>();
        String s1 =
                "def exec(args){\n" +
                        "    def s = new com.test.Service() \n" +
                        "    return s.res()\n" +
                        "}";
        scripts.add(s1);
        scripts.add(s1);
        for (String script : scripts) {
            Script parse = groovyShell.parse(script);
            Object exec = parse.invokeMethod("exec", null);
            System.out.println(exec);
        }
    }
}
