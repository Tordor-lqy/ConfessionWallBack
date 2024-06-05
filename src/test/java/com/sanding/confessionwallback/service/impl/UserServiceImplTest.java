package com.sanding.confessionwallback.service.impl;

import com.sanding.confessionwallback.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


public class UserServiceImplTest {

    @Test
    public void testName(){
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }
}
