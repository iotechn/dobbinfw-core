package com.dobbinsoft.fw.core.entiy.inter;

import java.util.List;

/**
 * ClassName: PermissionOwner
 * Description: 表示一个有权限的身份
 *
 * @author: e-weichaozheng
 * @date: 2021-03-16
 */
public interface PermissionOwner {

    public List<String> getPerms();

}
