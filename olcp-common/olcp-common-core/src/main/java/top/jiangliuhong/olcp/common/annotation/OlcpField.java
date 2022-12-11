package top.jiangliuhong.olcp.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OlcpField {
    String title();

    String type();
}
