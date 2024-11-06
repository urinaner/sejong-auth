package org.yj.sejongauth.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yj.sejongauth.config.AutoConfig;
import org.yj.sejongauth.controller.Sj;

@SpringBootTest(classes = AutoConfig.class)
public class SjTest {

    @Autowired
    protected Sj sj;

    @Test
    void sjTest(){
        SjProfile sjProfile = sj.login("123","123");
    }


}
