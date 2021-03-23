package com.dobbinsoft.fw.core.annotation.param;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 自定义参数校验
 * User: rize
 * Date: 2021-03-17
 * Time: 上午9:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Documented
public @interface CustomCheck {

    /**
     * 校验器Class对象，会从IoC中拿取
     * @return
     */
    Class beanClass();

    boolean reqScope() default true;

    boolean respScope() default false;

}
