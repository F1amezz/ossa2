package com.ossjk.qlh.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class Config {

@Bean
    public Date getDate(){
        return new Date();

    }
}
