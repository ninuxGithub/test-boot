
        
	<dependency>
		<groupId>org.mybatis.spring.boot</groupId>
		<artifactId>mybatis-spring-boot-starter</artifactId>
	</dependency>
	
        这些配置是MyBatis 分页插件的正确配置  ，没有必要引入mybatis-spring-boot-starter
	
	<!-- MyBatis support here proper jar is very important  -->
	<dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.3.0</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>1.2.3</version>
    </dependency>
    <!-- 分页插件 -->
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper</artifactId>
        <version>4.1.1</version>
    </dependency>
    <!--通用Mapper插件-->
    <dependency>
        <groupId>tk.mybatis</groupId>
        <artifactId>mapper</artifactId>
        <version>3.3.4</version>
    </dependency>
        
        
        