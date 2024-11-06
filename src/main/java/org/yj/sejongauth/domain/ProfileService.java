package org.yj.sejongauth.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ProfileService {
    private final String PROFILE_URL = "http://classic.sejong.ac.kr/userCertStatus.do?menuInfoId=MAIN_02_05";
    private final String FAIDED_PROFILE = "정보 조회에 실패하였습니다.";

    public SjProfile fetchUserProfile(String jsessionId) {
        try {
            URL url = new URL(PROFILE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", "JSESSIONID=" + jsessionId);

            Document doc = Jsoup.parse(readResponse(connection));
            return parseProfileFromHtml(doc);
        } catch (IOException e) {
            throw new RuntimeException(FAIDED_PROFILE);
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private SjProfile parseProfileFromHtml(Document document) {
        String major = document.select("div.contentWrap li dl dd").get(0).text();
        String studentCode = document.select("div.contentWrap li dl dd").get(1).text();
        String name = document.select("div.contentWrap li dl dd").get(2).text();
        int gradeLevel = Integer.parseInt(document.select("div.contentWrap li dl dd").get(3).text().split(" ")[0]);
        String userStatus = document.select("div.contentWrap li dl dd").get(4).text();
        int completedSemesters = Integer.parseInt(document.select("div.contentWrap li dl dd").get(5).text().split(" ")[0]);
        int verifiedSemesters = Integer.parseInt(document.select("div.contentWrap li dl dd").get(6).text().split(" ")[0]);

        return new SjProfile(major, studentCode, name, gradeLevel, userStatus, completedSemesters, verifiedSemesters);
    }
}