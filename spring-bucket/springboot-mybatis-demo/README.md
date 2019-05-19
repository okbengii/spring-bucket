### SpringBoot 整合 Mybatis

涉及的依赖：

- lombok

	这个依赖通过使用注解，可以简化 model 的编码，如果使用 ide，比如 eclipse，需要安装相关的插件才能使用 
	
	- @Data 可以简化 get/set 方法
	- @Builder 帮你构建 builder 方法生成实例对象
	- @AllArgsConstructor // 构造方法
	- @NoArgsConstructor // 无参构造方法
	
- org.joda
  
	这是一个处理数值的jar包，尤其是在金融领域。
	
- h2database
 
	这是一个数据库，h2 数据库 @link: https://www.cnblogs.com/langtianya/p/3807639.html
	
- druid
	
	alibaba 开源的数据源，以前经常用 c3p0 和 dhcp，现在看大多数都在用 druid
	
- mybatis-enhance-actable

	根据 model 类，自己创建数据库表（懒得手动创建数据库表了，所以找了这么一个开源的项目）
	@link: https://github.com/sunchenbin/A.CTable-Frame
	
	```
	<dependency>
			<groupId>net.sf.json-lib</groupId>
			<artifactId>json-lib</artifactId>
			<version>2.4</version>
			<classifier>jdk15</classifier>
			<exclusions>
				<exclusion>
					<artifactId>commons-logging</artifactId>
					<groupId>commons-logging</groupId>
				</exclusion>
			</exclusions>
	</dependency>
	<dependency>
		<groupId>org.apache.commons</groupId>
		<artifactId>commons-lang3</artifactId>
		<version>3.4</version>
	</dependency>
	<dependency>
			<groupId>com.gitee.sunchenbin.mybatis.actable</groupId>
			<artifactId>mybatis-enhance-actable</artifactId>
			<version>1.0.3</version>
	</dependency>
	```
	