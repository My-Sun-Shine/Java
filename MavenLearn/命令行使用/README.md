### 安装Maven
1. 下载地址：http://maven.apache.org/download.cgi
2. 添加环境Path环境变量，其中必须添加JDK的环境变量JAVA_HOME，否则mvn会报错
```
# 检测安装的Maven的版本
mvn -v
```
### 建立一个Maven工程
1. 新建文件夹Maven文件结构体系
2. 新建pom.xml配置文件
```
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
					  http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.mavenlearn</groupId>
	<artifactId>maven-java-001</artifactId>
	<version>1.0.0</version>
</project>
```
3. 在src/main/java/com/mavenlearn中创建一个类HelloMaven.java
4. 在工程的根目录下运行mvn compile命令，编译项目，同时会在电脑C:\Users\Administrator\.m2目录下创建一个repository的目录，这就是本地的仓库
5. 在src/test/java/com/mavenlearn中创建一个类HelloMavenTest.java测试类
6. 在pom.xml添加单元测试需要的junit.jar
```
<dependencies>
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit</artifactId>
		<version>4.12</version>
	</dependency>
</dependencies>
```
7. 在工程的根目录下运行maven test命令，运行测试类
8. 在工程的根目录下运行maven clean命令，可以删除target目录下的内容
9. 在工程的根目录下运行maven package命令，对项目进行打包，可以发现在target目录下多出maven-java-001-1.0.0.jar包，命名规则是artifactId-version.jar；如果是普通的java项目，打jar包，如果是web项目，打war包
10. 在工程的根目录下运行maven install命令，这样可以将打好的包放在本地仓库中，其它项目可以使用配置依赖来引入该jar包
11. 新建项目maven-java-02，修改配置文件，添加依赖
```
<dependencies>
	<dependency>
		<groupId>com.mavenlearn</groupId>
		<artifactId>maven-java-001</artifactId>
		<version>1.0.0</version>
	</dependency>
</dependencies>
```
12. 在maven-java-02目录下执行mvn compile命令，执行编译
13. 在命令行使用目录中执行mvn archetype:generate，自动创建Maven项目maven-java-03，创建时直接回车，遇到groupId、artifactId、vension、packages时输入对应的参数

### 修改Maven本地仓库位置
* 找到maven安装目录下的conf文件中settings.xml，修改localRepository属性
```
<!-- localRepository
   | The path to the local repository maven will use to store artifacts.
   |
   | Default: ${user.home}/.m2/repository
  <localRepository>/path/to/local/repo</localRepository>
-->
<localRepository>D:\\MvnRepo</localRepository>
```

### 修改Maven远程仓库位置
* 找到maven安装目录下的conf文件中settings.xml，修改mirror属性
```
<mirrors>
	<mirror>  
		<id>nexus-aliyun</id>  
		<mirrorOf>central</mirrorOf>    
		<name>Nexus aliyun</name>  
		<url>https://maven.aliyun.com/repository/public</url>  
	</mirror> 
    <!-- mirror
     | Specifies a repository mirror site to use instead of a given repository. The repository that
     | this mirror serves has an ID that matches the mirrorOf element of this mirror. IDs are used
     | for inheritance and direct lookup purposes, and must be unique across the set of mirrors.
     |
    <mirror>
      <id>mirrorId</id>
      <mirrorOf>repositoryId</mirrorOf>
      <name>Human Readable Name for this Mirror.</name>
      <url>http://my.repository.com/repo/path</url>
    </mirror>
     -->
  </mirrors>
```

### 插件
* 编译插件
```
<!-- 编译插件 -->
<plugin>
	<artifactId>maven-compiler-plugin</artifactId>
	<!-- 插件的版本 -->
	<version>3.5.1</version>
	<!-- 编译级别 -->
	<configuration>
		<source>1.8</source>
		<target>1.8</target>
		<!-- 编码格式 -->
		<encoding>UTF-8</encoding>
	</configuration>
</plugin>
```
* 打jar包插件
```
<!-- 打jar包的插件 -->
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-jar-plugin</artifactId>
	<version>2.6</version>
</plugin>
```
* 打war包插件
```
<!-- 打war包的插件 -->
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-war-plugin</artifactId>
	<version>2.6</version>
</plugin>
```
* jetty插件：提供Web应用运行环境，用来替代独立的Tomcat中间件
* jetty插件只能配置一个
* jetty插件8.1，会导致web.xml中的welcome配置不起作用的bug
```
<!-- jetty插件：提供web应用运行的环境（tomcat） -->
<plugin>
	<groupId>org.mortbay.jetty</groupId>
	<artifactId>jetty-maven-plugin</artifactId>
	<version>8.1.15.v20140411</version>
	<configuration>
		<scanIntervalSeconds>1</scanIntervalSeconds>
		<webApp>
			<!-- 浏览器访问上下文根 -->
			<contextPath>/</contextPath>
		</webApp>
		<connectors>
			<connector implementation="org.eclipse.jetty.server.nio.SelectChannelConnector">
				<port>9090</port>
			</connector>
		</connectors>
	</configuration>
</plugin>
```
* jetty插件9.3.7，解决上述bug，但必须是jdk1.8
```
<!--jetty插件9.3.7：提供web应用运行的环境（tomcat） -->
<plugin>
	<groupId>org.eclipse.jetty</groupId>
	<artifactId>jetty-maven-plugin</artifactId>
	<version>9.3.7.v20160115</version>
	<configuration>
		<!-- 扫描间隔 -->
		<scanIntervalSeconds>1</scanIntervalSeconds>
		<httpConnector>
			<!-- 端口号 -->
			<port>9090</port>
		</httpConnector>
		<webApp>
			<!-- 上下文根 -->
			<contextPath>/</contextPath>
		</webApp>
	</configuration>
</plugin>
```

