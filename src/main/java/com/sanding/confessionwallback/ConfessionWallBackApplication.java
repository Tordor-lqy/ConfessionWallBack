package com.sanding.confessionwallback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
public class ConfessionWallBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfessionWallBackApplication.class, args);
    }

}
