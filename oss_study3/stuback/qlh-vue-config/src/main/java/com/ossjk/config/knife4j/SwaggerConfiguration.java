
package com.ossjk.config.knife4j;

import java.util.List;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
//import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ossjk.core.constant.Constant;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.config.knife4j
 * @ClassName: SwaggerConfiguration
 * @Description: knife4j配置文件
 * @author: chair
 * @date: 2020年12月8日 下午4:36:53
 */
@Slf4j
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {

    private final OpenApiExtensionResolver openApiExtensionResolver;


    private ApiInfo apiInfo() {
        Contact contact=new Contact("QLH团队", "", "332039038@qq.com");
        return new ApiInfoBuilder()
                .title("QLH2.0接口文档")
                .description("QLH2.0接口文档").termsOfServiceUrl("https://ossjk.cn/"). contact(contact).version("1.0")
                .build();
    }

    @Autowired
    public SwaggerConfiguration(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean(value = "systemApi")
    public Docket systemApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 分组名称
                .groupName("系统模块").select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.ossjk.qlh.system")).paths(PathSelectors.any()).build()
                .extensions(openApiExtensionResolver.buildSettingExtensions())
                .securityContexts(CollUtil.newArrayList(securityContext()))
                .securitySchemes(CollUtil.newArrayList(apiKey()));
        return docket;
    }

    @Bean(value = "study3Api")
    public Docket study3Api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 分组名称
                .groupName("study3模块").select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.ossjk.qlh.study")).paths(PathSelectors.any()).build()
                .extensions(openApiExtensionResolver.buildSettingExtensions())
                .securityContexts(CollUtil.newArrayList(securityContext()))
                .securitySchemes(CollUtil.newArrayList(apiKey()));
        return docket;
    }

    @Bean(value = "wxappApi")
    public Docket wxappApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 分组名称
                .groupName("小程序模块").select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.ossjk.qlh.wxapp")).paths(PathSelectors.any()).build()
                .extensions(openApiExtensionResolver.buildSettingExtensions())
                .securityContexts(CollUtil.newArrayList(securityContext()))
                .securitySchemes(CollUtil.newArrayList(apiKey()));
        return docket;
    }





    @Bean(value = "monitorApi")
    public Docket monitorApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 分组名称
                .groupName("监控模块").select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.ossjk.qlh.monitor")).paths(PathSelectors.any()).build()
                .securityContexts(CollUtil.newArrayList(securityContext()))
                .extensions(openApiExtensionResolver.buildSettingExtensions())
                .securitySchemes(CollUtil.newArrayList(apiKey()));
        return docket;
    }

    @Bean(value = "scheduleApi")
    public Docket scheduleApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 分组名称
                .groupName("调度器模块").select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.ossjk.qlh.schedule")).paths(PathSelectors.any()).build()
                .securityContexts(CollUtil.newArrayList(securityContext()))
                .extensions(openApiExtensionResolver.buildSettingExtensions())
                .securitySchemes(CollUtil.newArrayList(apiKey()));
        return docket;
    }
    @Bean(value = "smsApi")
    public Docket smsApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 分组名称
                .groupName("站内信模块").select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.ossjk.qlh.sms")).paths(PathSelectors.any()).build()
                .securityContexts(CollUtil.newArrayList(securityContext()))
                .extensions(openApiExtensionResolver.buildSettingExtensions())
                .securitySchemes(CollUtil.newArrayList(apiKey()));
        return docket;
    }





    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
    }

    private ApiKey apiKey() {
        return new ApiKey(Constant.REQUEST_HEADER_TOKEN, Constant.REQUEST_HEADER_TOKEN, "header");
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return CollUtil.newArrayList(new SecurityReference(Constant.REQUEST_HEADER_TOKEN, authorizationScopes));
    }


}
