package br.com.bnck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "br.com.bnck.beans")
public class SimpleCamelSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCamelSpringbootApplication.class, args);
    }

}
