## Dobbin Framework Logo

#### 一、项目背景

> 为了快速落地项目、快速搭建脚手架，dobbinsoft开发一套基于SpringBoot MyBatis的框架，并手搓了如参数校验、文档生成、限流、鉴权等等常用功能。core包中包括工具类、注解、模型等。

#### 二、快速开始

##### 2.1. 下载代码

您可以在国内开源社区Gitee下载（推荐）：https://gitee.com/iotechn/dobbinfw-core

您可以在国际开源社区Github下载：https://github.com/iotechn/dobbinfw-core

##### 2.2. maven引入

请确定您已经将 JAVA_HOME 配置，并将mvn命令配置到PATH中，若出现找不到命令，或找不到JAVA_HOME，[请参考此文档](https://blog.csdn.net/weixin_44548718/article/details/108635409)

在项目根目录，打开命令行。并执行 ：

```shell
mvn install -Dmaven.test.skip=true
```

引入maven坐标到工程pom.xml文件中。

```xml
<groupId>com.dobbinsoft</groupId>
<artifactId>fw-core</artifactId>
<version>1.0-SNAPSHOT</version>
```

#### 三、常见问题

##### 3.1. 为何分离core包？

部分项目，需要拆分微服务，这类项目需要将接口暴露给其他微服务系统，通常提供一个api包，api包不需要引入过重依赖，使用此框架则只需要引入core包即可。

#### 四、贡献 & 社区
您可以直接在仓库中发布Pull Request。本项目欢迎所有开发者一起维护，并永久开源。