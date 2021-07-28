package com.dobbinsoft.fw.core.annotation;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import com.dobbinsoft.fw.core.enums.EmptyEnums;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-03-25
 * Time: 下午1:12
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpParam {

    String name();

    HttpParamType type() default HttpParamType.COMMON;

    String description() default "";

    String valueDef() default "";

    Class arrayClass() default Object.class;

    /**
     * 绑定枚举
     * @return
     */
    Class<? extends BaseEnums> enums() default EmptyEnums.class;
}
