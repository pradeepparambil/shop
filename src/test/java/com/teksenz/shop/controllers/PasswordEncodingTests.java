package com.teksenz.shop.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodingTests {
    static final String PASSWORD = "password";

    @Test
    void testBcrypt(){
        PasswordEncoder bcrypt = new BCryptPasswordEncoder();
        System.out.println(bcrypt.encode("john123"));
        System.out.println(bcrypt.encode("maria123"));
        System.out.println(bcrypt.encode("david123"));

    }
}
