package com.guillem.gvilachess.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Created by guillem on 10/02/2019.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.guillem.gvilachess"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(generateApiInfo());
    }

    private ApiInfo generateApiInfo() {
        return new ApiInfo("Chess API sample with engine movements", "Description ", "Version 1.0 - mw",
                "urn:tos", new Contact("guilli15@gmail.com", "", ""), "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
    }
}
