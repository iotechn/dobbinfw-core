package com.dobbinsoft.fw.core.enums;

import java.io.Serializable;

/**
 * ClassName: EmptyEnums
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-03-19
 */
public enum EmptyEnums implements BaseEnums<Integer> {
    ;

    public Integer getCode() {
        return 0;
    }

    public String getMsg() {
        return null;
    }
}
