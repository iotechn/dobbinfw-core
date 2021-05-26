package com.dobbinsoft.fw.core.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Description:
 * User: rize
 * Date: 2020/8/15
 * Time: 11:01
 */
public class ReflectUtil {

    /**
     * 通过Getter方法名称获取属性
     * @param getterName
     * @return
     */
    public static String getField(String getterName) {
        char[] dst = new char[getterName.length() - 3];
        getterName.getChars(3, getterName.length(), dst, 0);
        if ('A' <= dst[0] && 'Z' >= dst[0]) {
            dst[0] = (char) (dst[0] + 32);
        }
        return new String(dst);
    }

    /**
     * 通过属性名获取 Getter 或 Setter
     * @param fieldName
     * @param prefix "get" | "set"
     * @return
     */
    public static String getMethodName(String fieldName, String prefix) {
        char[] dst = new char[fieldName.length() + 3];
        // 1. 将prefix搞进去
        prefix.getChars(0, 3, dst, 0);
        // 2. 跟随考进去
        fieldName.getChars(0, fieldName.length(), dst, 3);
        if ('a' <= dst[3] && 'z' >= dst[3]) {
            dst[3] = (char) (dst[3] - 32);
        }
        return new String(dst);
    }

    /**
     * 加载包字节码
     * @return
     */
    public static Map<String, byte[]> loadPackageBytes(String basePackage, boolean subPackage, boolean isInterface) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String fsBasePackage = basePackage.replace(".","/");
        Map<String, byte[]> map = new HashMap<>();
        try {
            Enumeration<URL> resources = classLoader.getResources(fsBasePackage);
            while(resources.hasMoreElements()) {
                //先获得本类的所在位置
                URL url = resources.nextElement();
                //url.getProtocol()是获取URL的HTTP协议。
                if(url.getProtocol().equals("jar")) {
                    //判断是不是jar包
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    JarFile jarfile = urlConnection.getJarFile();
                    Enumeration<JarEntry> jarEntries = jarfile.entries();
                    while(jarEntries.hasMoreElements()) {
                        JarEntry jarEntry = jarEntries.nextElement();
                        String jarName = jarEntry.getName();
                        if(!jarName.endsWith(".class")) {
                            continue;
                        }
                        String className = jarName.replace(".class", "").replaceAll("/", ".");
                        if (!className.startsWith(basePackage)) {
                            continue;
                        }
                        if (!subPackage) {
                            if (!className.substring(0, className.lastIndexOf(".")).equals(basePackage)) {
                                continue;
                            }
                        }
                        try {
                            Class<?> klass = Class.forName(className);
                            if (klass.isAnnotation()
                                    || klass.isEnum()
                                    || klass.isPrimitive()) {
                                continue;
                            }
                            if (isInterface && !klass.isInterface()) {
                                continue;
                            } else if (!isInterface && klass.isInterface()) {
                                continue;
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            throw new RuntimeException("未找到类：" + className);
                        }
                        InputStream inputStream = null;
                        try {
                            String newUrl = url.toString().substring(0, url.toString().lastIndexOf("!") + 2) + jarName;
                            URL classUrl = new URL(newUrl);
                            URLConnection classUrlConnection = classUrl.openConnection();
                            inputStream = classUrlConnection.getInputStream();
                            byte[] bytes = IoUtil.readBytes(inputStream);
                            map.put(className, bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        }
                    }
                }else {
                    throw new RuntimeException("不支持本包对象，请不要将接口和实体放入启动器中");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
