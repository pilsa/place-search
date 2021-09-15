package com.pilsa.place.framework.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 테스트를 위함.
 *
 * @author pilsa_home1
 * @since 2021-09-12 오후 1:53
 */
@Profile("!prd")
@Configuration
public class SwaggerConfig {

    private static final String API_NAME = "장소검색 API 서비스 ";
    private static final String API_VERSION = "1.0";
    private static final String API_DESCRIPTION = "KakaoBank 코딩테스트 - 이진영";

    @Bean
    public Docket api(){

        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder()
                .in(ParameterType.HEADER)
                .name("Authorization")
                .required(true)
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)));


        return new Docket(DocumentationType.OAS_30)
                /*
                .globalRequestParameters(
                        Collections.singletonList(new RequestParameterBuilder()
                                .name(ApiConstant.X_USER_ID)
                                .description("사용자 식별값")
                                .in(ParameterType.HEADER)
                                .required(false)
                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                                .build()))
                */
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .build();
    }
}
