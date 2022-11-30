package top.jiangliuhong.olcp.data.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Get {
    String value() default "";
}
