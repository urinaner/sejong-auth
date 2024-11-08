package org.yj.sejongauth.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yj.sejongauth.config.SjSettingConfig;
import org.yj.sejongauth.controller.Sj;

@SpringBootTest(classes = SjSettingConfig.class)
public class SjTest {

    @Autowired
    protected Sj sj;

    @Disabled
    @Test
    void sjTest(){
        SjProfile sjProfile = sj.login("123","123");
    }


}
