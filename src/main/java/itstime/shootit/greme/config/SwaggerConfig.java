package itstime.shootit.greme.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("GREME API")
                        .description("API 명세서")
                        .version("0.1"));
    }

    @Bean
    public GroupedOpenApi userApi(){
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi oauth2Api(){
        return GroupedOpenApi.builder()
                .group("oauth2")
                .pathsToMatch("/oauth2/**")
                .build();
    }

    @Bean
    public GroupedOpenApi challengeApi(){
        return  GroupedOpenApi.builder()
                .group("challenge")
                .pathsToMatch("/challenge/**")
                .build();
    }

    @Bean
    public GroupedOpenApi postApi(){
        return  GroupedOpenApi.builder()
                .group("post")
                .pathsToMatch("/post/**")
                .build();
    }
}
