package edu.swu.cs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket custumDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.swu.cs"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo(){
        Contact contact=new Contact("西南大学刘剑","https://ljkk23.github.io/HexoBlog/","2156718743@qq.com");
        return new ApiInfoBuilder()
                .title("springboot 的博客练手项目")
                .description("只是作为一个新手java程序员在springboot的练手项目")
                .contact(contact)//联系方式
                .version("1.0")
                .build();
    }
}
