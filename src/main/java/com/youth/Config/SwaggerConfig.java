package com.youth.Config;



import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yangsisen
 * @createTime 2021-03-12 10:36
 * @description swagger配置类 - api生成、维护配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.")))
                .paths(Predicates.not(PathSelectors.regex("/error/.")))
                .build();
//                .apiInfo(new ApiInfoBuilder()
//                        .title("SpringBoot整合Swagger")//配置页面标题
//                        .description("SpringBoot整合Swagger，详细信息......")//配置页面描述
//                        .version("9.0")//版本信息
//                        .contact(new Contact("啊啊啊啊","blog.csdn.net","aaa@gmail.com"))//内容
//                        .license("The Apache License")
//                        .licenseUrl("http://www.baidu.com")
//                        .build());
    }
}
