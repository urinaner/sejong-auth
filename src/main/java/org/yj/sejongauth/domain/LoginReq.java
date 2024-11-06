package org.yj.sejongauth.domain;

public class LoginReq {
    private final String userId;
    private final String password;

    public LoginReq(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}