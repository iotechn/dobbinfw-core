package com.dobbinsoft.fw.core;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-11
 * Time: 下午11:43
 */
public class Const {

    public static final Set<Class> IGNORE_PARAM_LIST = new HashSet<Class>();

    static {
        IGNORE_PARAM_LIST.add(boolean.class);
        IGNORE_PARAM_LIST.add(byte.class);
        IGNORE_PARAM_LIST.add(char.class);
        IGNORE_PARAM_LIST.add(short.class);
        IGNORE_PARAM_LIST.add(int.class);
        IGNORE_PARAM_LIST.add(long.class);
        IGNORE_PARAM_LIST.add(float.class);
        IGNORE_PARAM_LIST.add(double.class);
        IGNORE_PARAM_LIST.add(Byte.class);
        IGNORE_PARAM_LIST.add(Character.class);
        IGNORE_PARAM_LIST.add(Short.class);
        IGNORE_PARAM_LIST.add(Integer.class);
        IGNORE_PARAM_LIST.add(Long.class);
        IGNORE_PARAM_LIST.add(String.class);
        IGNORE_PARAM_LIST.add(Float.class);
        IGNORE_PARAM_LIST.add(Double.class);
        IGNORE_PARAM_LIST.add(Boolean.class);
    }

    public static final int CACHE_ONE_DAY = 60 * 60 * 24;

    public static final int CACHE_ONE_YEAR = 60 * 60 * 24 * 365;


    public static final String USER_ACCESS_TOKEN = "ACCESSTOKEN";

    public static final String USER_REDIS_PREFIX = "USER_SESSION_";

    public static final String ADMIN_ACCESS_TOKEN = "ADMINTOKEN";

    public static final String ADMIN_REDIS_PREFIX = "ADMIN_SESSION_";

}
