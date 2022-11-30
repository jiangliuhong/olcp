package top.jiangliuhong.olcp.data.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {
    String value() default "";
}
