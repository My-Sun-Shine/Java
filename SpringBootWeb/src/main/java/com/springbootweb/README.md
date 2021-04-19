# 启动配置原理

## 启动流程

### 创建SpringApplication对象，并执行run方法

* 由主程序类的main方法，调用SpringApplication.run()方法
* 调用到(new SpringApplication(primarySources)).run(args)

```java
public class SpringApplication {
    public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        this.sources = new LinkedHashSet();
        this.bannerMode = Mode.CONSOLE;
        this.logStartupInfo = true;
        this.addCommandLineProperties = true;
        this.addConversionService = true;
        this.headless = true;
        this.registerShutdownHook = true;
        this.additionalProfiles = Collections.emptySet();
        this.isCustomEnvironment = false;
        this.lazyInitialization = false;
        this.applicationContextFactory = ApplicationContextFactory.DEFAULT;
        this.applicationStartup = ApplicationStartup.DEFAULT;
        this.resourceLoader = resourceLoader;
        Assert.notNull(primarySources, "PrimarySources must not be null");
        //保存主配置类
        this.primarySources = new LinkedHashSet(Arrays.asList(primarySources));
        //检测环境
        //boolean isPresent(String className, ClassLoader classLoader)：判断当前class loader中是否存在对应的类型
        this.webApplicationType = WebApplicationType.deduceFromClasspath();
        this.bootstrappers = new ArrayList(this.getSpringFactoriesInstances(Bootstrapper.class));
        //从类路径下找到META-INF/spring.factories配置的所有ApplicationContextInitializer；然后保存起来
        this.setInitializers(this.getSpringFactoriesInstances(ApplicationContextInitializer.class));
        //从类路径下找到ETA-INF/spring.factories配置的所有ApplicationListener
        this.setListeners(this.getSpringFactoriesInstances(ApplicationListener.class));
        //从多个配置类中找到有main方法的主配置类
        this.mainApplicationClass = this.deduceMainApplicationClass();
    }

    public ConfigurableApplicationContext run(String... args) {
        //计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 初始化启动上下文
        DefaultBootstrapContext bootstrapContext = this.createBootstrapContext();
        ConfigurableApplicationContext context = null;
        //配置headless
        this.configureHeadlessProperty();
        //获取SpringApplicationRunListeners，从类路径下META‐INF/spring.factories
        SpringApplicationRunListeners listeners = this.getRunListeners(args);
        //回调所有的获取SpringApplicationRunListeners.starting()方法
        listeners.starting(bootstrapContext, this.mainApplicationClass);
        try {
            //封装命令行参数
            ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
            //准备环境；创建环境完成后回调SpringApplicationRunListener.environmentPrepared()表示环境准备完成
            ConfigurableEnvironment environment = this.prepareEnvironment(listeners, bootstrapContext, applicationArguments);
            //得到系统属性spring.beaninfo.ignore，如果为空设置为true
            this.configureIgnoreBeanInfo(environment);
            //打印在控制台的banner，spring.banner.location（默认值banner.txt）；spring.banner.image.location（默认值banner.gif/banner.jpg/banner.png）
            Banner printedBanner = this.printBanner(environment);
            //创建ApplicationContext，根据this.webApplicationType决定创建web的ioc还是普通的ioc
            context = this.createApplicationContext();
            context.setApplicationStartup(this.applicationStartup);
            //准备上下文环境
            //context.setEnvironment(environment)：为容器设置environment
            //this.postProcessApplicationContext(context)：设置容器的属性
            //this.applyInitializers(context)：回调之前保存的所有的ApplicationContextInitializer的initialize方法
            //listeners.contextPrepared(context)：回调所有的SpringApplicationRunListener的contextPrepared()；
            //listeners.contextLoaded(context)：回调所有的SpringApplicationRunListener的contextLoaded()
            this.prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);

            //刷新容器，ioc容器初始化（如果是web应用还会创建嵌入式的Tomcat）
            //扫描，创建，加载所有组件的地方（配置类，组件，自动配置）
            this.refreshContext(context);

            this.afterRefresh(context, applicationArguments);
            stopWatch.stop();
            if (this.logStartupInfo) {
                (new StartupInfoLogger(this.mainApplicationClass)).logStarted(this.getApplicationLog(), stopWatch);
            }

            //所有的SpringApplicationRunListener回调started方法
            listeners.started(context);

            //从ioc容器中获取所有的ApplicationRunner和CommandLineRunner进行回调
            this.callRunners(context, applicationArguments);
        } catch (Throwable var10) {
            this.handleRunFailure(context, var10, listeners);
            throw new IllegalStateException(var10);
        }

        try {
            //所有的SpringApplicationRunListener回调running方法
            listeners.running(context);
            //整个SpringBoot应用启动完成以后返回启动的ioc容器；
            return context;
        } catch (Throwable var9) {
            this.handleRunFailure(context, var9, (SpringApplicationRunListeners) null);
            throw new IllegalStateException(var9);
        }
    }
}
```

### 事件监听机制

#### 配置在META-INF/spring.factories

#### ApplicationContextInitializer

```java
public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("ApplicationContextInitializer...initialize..." + applicationContext);
    }
}

```

#### SpringApplicationRunListener

```java
public class HelloSpringApplicationRunListener implements SpringApplicationRunListener {

    //必须有的构造器
    public HelloSpringApplicationRunListener(SpringApplication application, String[] args) {
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        System.out.println("SpringApplicationRunListener...starting...");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener...started...");
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        Object o = environment.getSystemProperties().get("os.name");
        System.out.println("SpringApplicationRunListener...environmentPrepared..." + o);
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener...contextLoaded...");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener...contextPrepared...");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("SpringApplicationRunListener...failed...");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener...running...");
    }
}

```

#### 配置（META-INF/spring.factories）

```properties
org.springframework.context.ApplicationContextInitializer=\
com.springbootweb.listener.HelloApplicationContextInitializer
org.springframework.boot.SpringApplicationRunListener=\
com.springbootweb.listener.HelloSpringApplicationRunListener
```

#### 只需要放在ioc容器中

#### ApplicationRunner

```java

@Component
public class HelloApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner...run....");
    }
}
```

#### ApplicationRunner

```java

@Component
public class HelloCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner...run..." + Arrays.asList(args));
    }
}
```

#### 控制台打印

```
SpringApplicationRunListener...starting...
SpringApplicationRunListener...environmentPrepared...Windows 10
ApplicationContextInitializer...initialize...org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@14555e0a, started on Thu Jan 01 08:00:00 CST 1970
SpringApplicationRunListener...contextPrepared...
SpringApplicationRunListener...contextLoaded...
SpringApplicationRunListener...started...
ApplicationRunner...run....
CommandLineRunner...run...[]
SpringApplicationRunListener...running...
```

