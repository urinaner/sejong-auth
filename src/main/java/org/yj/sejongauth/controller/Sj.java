package org.yj.sejongauth.controller;

import org.yj.sejongauth.domain.AuthService;
import org.yj.sejongauth.domain.SjProfile;
import org.yj.sejongauth.domain.ProfileService;

public class Sj {

    private final AuthService authService;
    private final ProfileService profileService;

    public Sj(AuthService authService, ProfileService profileService){
        this.authService = authService;
        this.profileService = profileService;
    }

    public SjProfile login(String userId, String password) {
        if (authService.authenticate(userId, password)) {
            String jsessionId = authService.getJsessionId();
            return profileService.fetchUserProfile(jsessionId);
        } else {
            throw new RuntimeException("인증에 실패하였습니다.");
        }
    }
}