package com.dobbinsoft.fw.core.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
                        if (checkClassType(isInterface, className)) {
                            continue;
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
                } else if (url.getProtocol().equals("file")) {
                    // 文件系统，这是在开发的时候会用到
                    // file:/D:/develop/workspace/ideawork/unierp/unierp-data/target/classes/com/dobbinsoft/unierp/data/dto
                    Map<String, File> classNameFileMap = getClassNameFileMap(new HashMap<>(), new File(url.getFile()), basePackage, subPackage);
                    for (String className : classNameFileMap.keySet()) {
                        if (checkClassType(isInterface, className)) {
                            continue;
                        }
                        InputStream inputStream = null;
                        try {
                            File file = classNameFileMap.get(className);
                            inputStream = new FileInputStream(file);
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
                } else {
                    throw new RuntimeException("不支持本包对象，请不要将接口和实体放入启动器中");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static boolean checkClassType(boolean isInterface, String className) {
        try {
            Class<?> klass = Class.forName(className);
            if (klass.isAnnotation()
                    || klass.isEnum()
                    || klass.isPrimitive()) {
                return true;
            }
            if (isInterface && !klass.isInterface()) {
                return true;
            } else if (!isInterface && klass.isInterface()) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("未找到类：" + className);
        }
        return false;
    }

    /**
     * 递归获取baseDir下的类与文件映射
     * @param map
     * @param baseDir
     * @return
     */
    private static Map<String, File> getClassNameFileMap(Map<String, File> map, File baseDir, String basePackage, boolean subPackage) {
        if (baseDir == null || !baseDir.isDirectory()) {
            return map;
        }
        File[] files = baseDir.listFiles();
        for (File file : files) {
            if (file.isDirectory() && subPackage) {
                // 不这么写 true idea 报黄，看着胀眼睛
                getClassNameFileMap(map, file, basePackage, true);
            } else if (file.getName().endsWith("class")) {
                // 兼容 Windows
                String rawPackage = baseDir.toString().replace("\\", ".").replace("/", ".");
                String className = rawPackage.substring(rawPackage.indexOf(basePackage)) + "." + file.getName().replace(".class", "");
                map.put(className, file);
            }
        }
        return map;
    }


    /**
     * 清理掉对象的空串属性
     * @param object
     */
    public static void clearEmptyString(Object object) {
        try {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                String getter = getMethodName(field.getName(), "get");
                Method getterMethod = clazz.getMethod(getter);
                if (getterMethod != null) {
                    Object res = getterMethod.invoke(object);
                    if (res != null && res instanceof String) {
                        // 若是返回String
                        if (!"".equals(res)) {
                            //设置为空
                            String setter = getMethodName(field.getName(), "set");
                            Method setterMethod = clazz.getMethod(setter);
                            if (setterMethod != null) {
                                setterMethod.invoke(object, null);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
