### SpringBoot 整合 MongoDb

首先贴出 pom.xml

```
	<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.2.RELEASE</version>
        <relativePath/>
    </parent>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.joda</groupId>
			<artifactId>joda-money</artifactId>
			<version>RELEASE</version>
		</dependency>
	</dependencies>
```
由于父模块中引用的 springboot 版本是 1.5.6 版本，相对较低，而本模块使用较新的版本与原来的 springcloud 有版本依赖的冲突，所以继承 springboot-parent

###  SpringBoot MongoDb 的自动配置过程

1. 启动类

```
@SpringBootApplication 
@Slf4j
public class Application implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

2. @SpringBootApplication 点进去这个注解：

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
...
}
```

- @SpringBootConfiguration: Spring Boot的配置类，标注在某个类上，表示这是一个Spring Boot的配置类

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {

}
```

- @EnableAutoConfiguration: 开启自动配置类，SpringBoot的精华所在。

- @ComponentScan: 包扫描

以前我们需要配置的东西，Spring Boot帮我们自动配置；@EnableAutoConfiguration告诉SpringBoot开启自动配置功能；这样自动配置才能生效；

3. EnableAutoConfiguration注解：

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {

	String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

	/**
	 * Exclude specific auto-configuration classes such that they will never be applied.
	 * @return the classes to exclude
	 */
	Class<?>[] exclude() default {};

	/**
	 * Exclude specific auto-configuration class names such that they will never be
	 * applied.
	 * @return the class names to exclude
	 * @since 1.3.0
	 */
	String[] excludeName() default {};

}
```

- @AutoConfigurationPackage 自动配置包
- @Import(AutoConfigurationImportSelector.class) 导入自动配置的组件

4. @AutoConfigurationPackage 自动配置包:

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {

}
```

先看这个组件 AutoConfigurationPackages.Registrar.class，看到 register ，很明显是一个bean的定义

```
static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {

		@Override
		public void registerBeanDefinitions(AnnotationMetadata metadata,
				BeanDefinitionRegistry registry) {
			register(registry, new PackageImport(metadata).getPackageName());
		}

		@Override
		public Set<Object> determineImports(AnnotationMetadata metadata) {
			return Collections.singleton(new PackageImport(metadata));
		}

	}
```

new PackageImport(metadata).getPackageName()  它其实返回了当前主程序类 (Application.class) 的**同级以及子级**的包组件。
**所以 Application 要放在项目的最高级中，因为其启动时不会去加载不在其同级以及子级目录下定义的bean。**

5. AutoConfigurationImportSelector.class 类

```
public class AutoConfigurationImportSelector
		implements DeferredImportSelector, BeanClassLoaderAware, ResourceLoaderAware,
		BeanFactoryAware, EnvironmentAware, Ordered 
```

DeferredImportSelector 注意这个接口

```
public interface DeferredImportSelector extends ImportSelector 
```

ImportSelector 

```
public interface ImportSelector {

	/**
	 * Select and return the names of which class(es) should be imported based on
	 * the {@link AnnotationMetadata} of the importing @{@link Configuration} class.
	 */
	String[] selectImports(AnnotationMetadata importingClassMetadata);

}
```

找到 selectImports 的实现：

```
	@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return NO_IMPORTS;
		}
		AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
				.loadMetadata(this.beanClassLoader);
		AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(
				autoConfigurationMetadata, annotationMetadata);
		return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
	}
```

AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
				.loadMetadata(this.beanClassLoader); 先看这个方法
				
```
final class AutoConfigurationMetadataLoader {

	protected static final String PATH = "META-INF/"
			+ "spring-autoconfigure-metadata.properties";

	private AutoConfigurationMetadataLoader() {
	}

	public static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader) {
		return loadMetadata(classLoader, PATH);
	}

	static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader, String path) {
		try {
			Enumeration<URL> urls = (classLoader != null) ? classLoader.getResources(path)
					: ClassLoader.getSystemResources(path);
			Properties properties = new Properties();
			while (urls.hasMoreElements()) {
				properties.putAll(PropertiesLoaderUtils
						.loadProperties(new UrlResource(urls.nextElement())));
			}
			return loadMetadata(properties);
		}
		catch (IOException ex) {
			throw new IllegalArgumentException(
					"Unable to load @ConditionalOnClass location [" + path + "]", ex);
		}
	}
```
其会去 META-INF/spring-autoconfigure-metadata.properties 读取自动配置的元信息

```
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
```

然后看这个方法：
getAutoConfigurationEntry

```
	protected AutoConfigurationEntry getAutoConfigurationEntry(
			AutoConfigurationMetadata autoConfigurationMetadata,
			AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return EMPTY_ENTRY;
		}
		AnnotationAttributes attributes = getAttributes(annotationMetadata);
		List<String> configurations = getCandidateConfigurations(annotationMetadata,
				attributes);
		configurations = removeDuplicates(configurations);
		Set<String> exclusions = getExclusions(annotationMetadata, attributes);
		checkExcludedClasses(configurations, exclusions);
		configurations.removeAll(exclusions);
		configurations = filter(configurations, autoConfigurationMetadata);
		fireAutoConfigurationImportEvents(configurations, exclusions);
		return new AutoConfigurationEntry(configurations, exclusions);
	}
```

getCandidateConfigurations 看这个方法

```
	protected List<String> getCandidateConfigurations(AnnotationMetadata metadata,
			AnnotationAttributes attributes) {
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(
				getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
		Assert.notEmpty(configurations,
				"No auto configuration classes found in META-INF/spring.factories. If you "
						+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}
	
	public static List<String> loadFactoryNames(Class<?> factoryClass, @Nullable ClassLoader classLoader) {
		String factoryClassName = factoryClass.getName();
		return loadSpringFactories(classLoader).getOrDefault(factoryClassName, Collections.emptyList());
	}
	
	private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
		MultiValueMap<String, String> result = cache.get(classLoader);
		if (result != null) {
			return result;
		}

		try {
			Enumeration<URL> urls = (classLoader != null ?
					classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
					ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
			result = new LinkedMultiValueMap<>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				UrlReso
				......
				
	public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
```


