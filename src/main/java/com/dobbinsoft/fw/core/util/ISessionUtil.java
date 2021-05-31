package com.dobbinsoft.fw.core.util;

import com.dobbinsoft.fw.core.entiy.inter.IdentityOwner;
import com.dobbinsoft.fw.core.entiy.inter.PermissionOwner;
import com.dobbinsoft.fw.core.exception.ServiceException;

import java.util.List;

/**
 * Session 相关抽取接口
 */
public interface ISessionUtil<U extends IdentityOwner, A extends PermissionOwner> {

    public void setUser(U userDTO);

    public U getUser();

    public void setAdmin(A adminDTO);

    public A getAdmin();

    public Class<U> getUserClass();

    public Class<A> getAdminClass();

    public boolean hasPerm(String permission) throws ServiceException;

}
