package com.dobbinsoft.fw.core.enums;

import cn.hutool.core.util.StrUtil;

import java.io.Serializable;

/**
 * ClassName: BaseEnums
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-03-19
 */
public interface BaseEnums<T extends Serializable> {

    public Serializable getCode();

    public String getMsg();

    public static <T extends Serializable> BaseEnums getByCode(T t, Class<? extends BaseEnums<T>> clazz) {
        BaseEnums<T>[] enumConstants = clazz.getEnumConstants();
        for (BaseEnums baseEnums : enumConstants) {
            if (baseEnums.getCode().equals(t)) {
                return baseEnums;
            }
        }
        return null;
    }

    public static <T extends Serializable> String getMsgByCode(T t, Class<? extends BaseEnums<T>> clazz) {
        BaseEnums baseEnums = getByCode(t, clazz);
        if (baseEnums == null) {
            return null;
        }
        return baseEnums.getMsg();
    }

    /**
     * 获取前端格式化需要的MAP
     * @return
     */
    public default String getMap() {
        Class<? extends BaseEnums> clazz = this.getClass();
        BaseEnums[] enumConstants = clazz.getEnumConstants();
        StringBuilder sb = new StringBuilder();
        sb.append("const ");
        sb.append(StrUtil.lowerFirst(clazz.getSimpleName()));
        sb.append("Map = {\n");

        for (int i = 0; i < enumConstants.length; i++) {
            sb.append("  ");
            sb.append(enumConstants[i].getCode());
            sb.append(": '");
            sb.append(enumConstants[i].getMsg());
            if (i == enumConstants.length -1) {
                sb.append("'\n}");
            } else {
                sb.append("',\n");
            }
        }
        return sb.toString();
    }

    /**
     * 获取前端格式化需要的过滤器
     * @return
     */
    public default String getFilter() {
        Class<? extends BaseEnums> clazz = this.getClass();
        return
                "    " + StrUtil.lowerFirst(clazz.getSimpleName()) + "Filter(status) {\n" +
                "      return " + StrUtil.lowerFirst(clazz.getSimpleName()) + "Map[status]\n" +
                "    }";
    }

}
