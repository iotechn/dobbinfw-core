package com.dobbinsoft.fw.core.exception;

/**
 * Created by rize on 2019/7/1.
 */
public class CoreExceptionDefinition {

    public static final ServiceExceptionDefinition THIRD_PART_SERVICE_EXCEPTION =
            new ServiceExceptionDefinition(0, "第三方服务异常");

    public static final ServiceExceptionDefinition THIRD_PART_IO_EXCEPTION =
            new ServiceExceptionDefinition(0, "第三方服务网络异常");

    public static ServiceExceptionDefinition buildVariableException(ServiceExceptionDefinition definition, String ...args) {
        String msg = definition.getMsg();
        for (int i = 0; i < args.length; i++) {
            msg = msg.replace("${" + i + "}", args[i]);
        }
        return new ServiceExceptionDefinition(definition.getCode(), msg);
    }




}
