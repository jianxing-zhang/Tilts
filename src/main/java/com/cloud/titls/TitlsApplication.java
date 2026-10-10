package com.cloud.titls;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.cloud.titls.Mapper")
@SpringBootApplication
public class TitlsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TitlsApplication.class, args);
    }

}
