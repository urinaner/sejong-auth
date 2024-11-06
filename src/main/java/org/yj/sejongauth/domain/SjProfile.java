package org.yj.sejongauth.domain;

public class SjProfile {
    private final String major;
    private final String studentCode;
    private final String name;
    private final int gradeLevel;
    private final String userStatus;
    private final int completedSemesters;
    private final int verifiedSemesters;

    public SjProfile(String major, String studentCode, String name,
                     int gradeLevel, String userStatus,
                     int completedSemesters, int verifiedSemesters) {
        this.major = major;
        this.studentCode = studentCode;
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.userStatus = userStatus;
        this.completedSemesters = completedSemesters;
        this.verifiedSemesters = verifiedSemesters;
    }

    public String getMajor() { return major; }
    public String getStudentCode() { return studentCode; }
    public String getName() { return name; }
    public int getGradeLevel() { return gradeLevel; }
    public String getUserStatus() { return userStatus; }
    public int getCompletedSemesters() { return completedSemesters; }
    public int getVerifiedSemesters() { return verifiedSemesters; }
}