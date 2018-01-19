package com.ambition.rcsss.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Created by hhu on 2017/8/2.
 */
@Configuration
public class InitTestBean {

    @Bean
    public FlywayMigrationInitializer flywayInitializer() {
        return null;
    }

    @Bean(name = "session")
    public HttpSession session() {
        return request().getSession();
    }

    @Bean(name = "request")
    public HttpServletRequest request() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        return request;
    }

}
