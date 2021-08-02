package br.com.cvccorp.hotelgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class HotelGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelGatewayApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.cvccorp.hotelgateway.resource"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo(
                        "Hotel Gateway",
                        "from https://github.com/cvc-corp/Teste-API/tree/master/soa_osb_test-master",
                        "1",
                        "Terms of service",
                        new Contact(
                                "santiagoalves",
                                "https://github.com/santiagoalves",
                                null
                        ),
                        "license - 4free",
                        "",
                        Collections.emptyList()
                ));
    }
}
