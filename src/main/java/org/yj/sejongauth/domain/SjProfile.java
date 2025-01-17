package org.yj.sejongauth.domain;

public class SjProfile {
    private final String major;
    private final String name;
    private final String userStatus;

    public SjProfile(String major, String name, String userStatus) {
        this.major = major;
        this.name = name;
        this.userStatus = userStatus;
    }

    public String getMajor() { return major; }
    public String getName() { return name; }
    public String getUserStatus() { return userStatus; }
}