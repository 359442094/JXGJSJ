package cn.blooming.jxgjsj.model.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ShowLogger {
    public String info() default "";
}
