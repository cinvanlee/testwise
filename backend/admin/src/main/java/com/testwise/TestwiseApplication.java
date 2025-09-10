package com.testwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class TestwiseApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TestwiseApplication.class, args);
    }
}