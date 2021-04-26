package com.ng0cth1nh.management.configuration;

import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config{
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                    .paths(PathSelectors.any())
                    .build()
                    .apiInfo(apiInfo())
                    .securitySchemes(Collections.singletonList(apiKey()))
             //       .securityContexts(Collections.singletonList(securityContext()))
                    .useDefaultResponseMessages(false);
    }

    @Bean
    public SecurityConfiguration security() {
            return SecurityConfigurationBuilder.builder()
                    .clientId("test-app-client-id")
                    .clientSecret("test-app-client-secret")
                    .realm("test-app-realm")
                    .appName("test-app")
                    .scopeSeparator(",")
                    .additionalQueryStringParams(null)
                    .useBasicAuthenticationWithAccessCodeGrant(false)
                    .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
                .description("User Management REST API")
                .contact(new Contact("Ngoc Thinh", "https://www.instagram.com/ng0cth1nh/", "vnt462@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

    private SecurityContext securityContext() { 
        return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
    } 
    
    private List<SecurityReference> defaultAuth() { 
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
        authorizationScopes[0] = authorizationScope; 
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
    }

    
    private ApiKey apiKey(){
        return new ApiKey("JWT","Authorization","header");
    }
}