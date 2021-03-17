package com.dobbinsoft.fw.core.util;



import com.dobbinsoft.fw.core.entiy.inter.PermissionOwner;
import com.dobbinsoft.fw.core.exception.ServiceException;

import java.util.List;

/**
 * Created by rize on 2019/2/27.
 * Edit by rize on 2021/3/16.
 */
public class SessionUtil<U, A extends PermissionOwner> {

    private ThreadLocal<U> userLocal = new ThreadLocal<U>();

    private ThreadLocal<A> adminLocal = new ThreadLocal<A>();

    public void setUser(U userDTO) {
        userLocal.set(userDTO);
    }

    public U getUser() {
        return userLocal.get();
    }

    public void setAdmin(A adminDTO) {
        adminLocal.set(adminDTO);
    }

    public A getAdmin() {
        return adminLocal.get();
    }

    public boolean hasPerm(String permission) throws ServiceException {
        //拥有的权限
        List<String> perms = getAdmin().getPerms();
        boolean hasPerm = false;
        //目标匹配权限
        String[] permissions = permission.split(":");
        outer : for(String item : perms) {
            //拥有的权限点
            String[] hasPer = item.split(":");
            inner : for (int i = 0; i < permissions.length; i++) {
                if ("*".equals(hasPer[i])) {
                    hasPerm = true;
                    break outer;
                } else if (hasPer[i].equals(permissions[i])){
                    //此层合格
                    if (i == permissions.length - 1) {
                        //若是目标层的最后一层。则表示所有层校验通过
                        hasPerm = true;
                    }
                } else {
                    break inner;
                }
            }
        }
        return hasPerm;
    }

}
