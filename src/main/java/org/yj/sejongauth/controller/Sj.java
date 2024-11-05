package org.yj.sejongauth.controller;

import org.yj.sejongauth.domain.AuthService;
import org.yj.sejongauth.domain.LoginReq;
import org.yj.sejongauth.domain.ProfileRes;
import org.yj.sejongauth.domain.ProfileService;

public class Sj {

    private static final AuthService authService = new AuthService();
    private static final ProfileService PROFILE_SERVICE = new ProfileService();

    public static ProfileRes login(LoginReq loginReq) {
        if (authService.authenticate(loginReq)) {
            String jsessionId = authService.getJsessionId();
            return PROFILE_SERVICE.fetchUserProfile(jsessionId);
        } else {
            throw new RuntimeException("인증에 실패하였습니다.");
        }
    }
}