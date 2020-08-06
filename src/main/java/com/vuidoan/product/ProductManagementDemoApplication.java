package com.vuidoan.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {"com.vuidoan.product.*"}
//        exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
@EnableJpaRepositories
public class ProductManagementDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductManagementDemoApplication.class, args);
    }

}
