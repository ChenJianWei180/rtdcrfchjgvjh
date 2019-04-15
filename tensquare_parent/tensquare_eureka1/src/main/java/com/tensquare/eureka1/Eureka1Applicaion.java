package com.tensquare.eureka1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: I Need A Dr.
 *
 * @Date: 2019/4/12 13:42
 * @Description: tensquare_parent
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka1Applicaion {
    public static void main(String[] args) {
        SpringApplication.run(Eureka1Applicaion.class);
    }

}
