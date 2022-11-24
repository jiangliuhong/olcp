package top.jiangliuhong.olcp.data.script.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {
    String value() default "";
}
