package com.dobbinsoft.fw.core.annotation.param;

import com.dobbinsoft.fw.core.exception.ServiceException;

/**
 * ClassName: Validator
 * Description: 校验器
 *
 * @author: e-weichaozheng
 * @date: 2021-03-17
 */
public interface Validator<T> {

    public boolean check(T param) throws ServiceException;

}
