package org.yj.sejongauth.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

public class AuthService {
    private String jsessionId;
    private final String SJ_LOGIN_URL = "https://classic.sejong.ac.kr/userLogin.do";
    private final String SJ_POTAL_URL = "https://classic.sejong.ac.kr";
    private final String INVALID_AUTH = "인증이 실패하였습니다.";
    private final String INVALID_SESSION = "SESSION_ID 가져오는 것을 실패하였습니다.";
    private final String INVALID_URL = "URL이 유효하지 않습니다.";
    private final String CONTAINS_HTML = "로그인 정보가 올바르지 않습니다.";

    public boolean authenticate(String userId, String password) {
        LoginReq loginReq = new LoginReq(userId, password);
        try {
            fetchJsessionId();
            return attemptLogin(loginReq);
        } catch (IOException e) {
            throw new RuntimeException(INVALID_AUTH);
        }
    }

    void fetchJsessionId() throws IOException {
        try {
            URI uri = new URI(SJ_POTAL_URL);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            Map<String, List<String>> headers = connection.getHeaderFields();
            List<String> cookies = headers.get("Set-Cookie");

            if (cookies != null) {
                for (String cookie : cookies) {
                    if (cookie.startsWith("JSESSIONID")) {
                        jsessionId = cookie.split(";")[0].split("=")[1];
                        break;
                    }
                }
            }

            if (jsessionId == null) {
                throw new RuntimeException(INVALID_SESSION);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(INVALID_URL);
        }
    }

    private boolean attemptLogin(LoginReq loginReq) throws IOException {
        try {
            URI uri = new URI(SJ_LOGIN_URL);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Cookie", "JSESSIONID=" + jsessionId);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String postData = "userId=" + loginReq.getUserId() + "&password=" + loginReq.getPassword();
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            String responseMessage = readResponse(connection);
            return responseCode == 302 && !responseMessage.contains(CONTAINS_HTML);
        } catch (URISyntaxException e) {
            throw new RuntimeException(INVALID_URL);
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

    public String getJsessionId() {
        return jsessionId;
    }
}
