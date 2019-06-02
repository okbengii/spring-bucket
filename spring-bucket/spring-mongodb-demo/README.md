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
