package org.yj.sejongauth.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ProfileService {
    private final String CLASSIC_URL = "https://classic.sejong.ac.kr/classic/reading/status.do";
    private final String FAILED_PROFILE = "정보 조회에 실패하였습니다.";

    public SjProfile fetchUserProfile(String ssoToken) {
        try {
            URL url = new URL(CLASSIC_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Cookie", "ssotoken=" + ssoToken);
            Document doc = Jsoup.parse(readResponse(connection));
            return parseProfileFromHtml(doc);
        } catch (IOException e) {
            throw new RuntimeException(FAILED_PROFILE);
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
        String major = document.select("th:contains(학과명) + td").text().trim();
        String name = document.select("th:contains(이름) + td").text().trim();
        String userStatus = document.select("th:contains(사용자 상태) + td").text().trim();

        return new SjProfile(major, name, userStatus);
    }
}
