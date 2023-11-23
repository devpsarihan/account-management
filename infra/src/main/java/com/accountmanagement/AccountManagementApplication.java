package com.accountmanagement;

import com.accountmanagement.domain.common.util.DomainComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
@EnableKafka
@EnableCaching
@ComponentScan(
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})
    }
)
public class AccountManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountManagementApplication.class, args);
    }

}
