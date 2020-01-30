package com.lynda.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {

    @Bean
    public String sayHey(){
        return "Hello world!";
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(HelloConfig.class);
        String text = (String) applicationContext.getBean("sayHey");

        System.out.println("Hey you! " + text);
    }
}
