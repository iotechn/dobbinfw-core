package com.dobbinsoft.fw.core.annotation.param;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-20
 * Time: 上午10:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Documented
public @interface NotNull {

    String message() default "";

    boolean reqScope() default true;

    boolean respScope() default false;

}
