package org.yj.sejongauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.yj.sejongauth.controller.Sj;
import org.yj.sejongauth.domain.AuthService;
import org.yj.sejongauth.domain.ProfileService;

@Configuration
@ComponentScan("org.yj.sejongauth")
public class SjSettingConfig {
    @Bean
    public AuthService authService() {
        return new AuthService();
    }

    @Bean
    public ProfileService profileService() {
        return new ProfileService();
    }
    @Bean
    public Sj sj(AuthService authService, ProfileService profileService) {
        return new Sj(authService, profileService);
    }
}