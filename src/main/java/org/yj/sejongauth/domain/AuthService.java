package org.yj.sejongauth.domain;



import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;

public class AuthService {
    private String ssoToken;
    private final String PORTAL_LOGIN_URL = "https://portal.sejong.ac.kr/jsp/login/login_action.jsp";
    private final String INVALID_AUTH = "인증이 실패하였습니다.";
    private final String INVALID_SESSION = "SSO Token 가져오는 것을 실패하였습니다.";
    private final String INVALID_URL = "URL이 유효하지 않습니다.";

    public boolean authenticate(String userId, String password) {
        LoginReq loginReq = new LoginReq(userId, password);
        try {
            System.out.println(attemptLogin(loginReq));
            return attemptLogin(loginReq);
        } catch (IOException e) {
            throw new RuntimeException(INVALID_AUTH);
        }
    }

    private boolean attemptLogin(LoginReq loginReq) throws IOException {
        try {
            URI uri = new URI(PORTAL_LOGIN_URL);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Referer", "https://portal.sejong.ac.kr/jsp/login/loginSSL.jsp");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String postData = "mainLogin=Y" +
                    "&rtUrl=" + URLEncoder.encode("https://classic.sejong.ac.kr/classic/index.do", StandardCharsets.UTF_8.toString()) +
                    "&id=" + URLEncoder.encode(loginReq.getUserId(), StandardCharsets.UTF_8.toString()) +
                    "&password=" + URLEncoder.encode(loginReq.getPassword(), StandardCharsets.UTF_8.toString()) +
                    "&chkNos=on";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return false;
            }

            String cookie = connection.getHeaderField("Set-Cookie");
            if (cookie != null && cookie.contains("ssotoken=")) {
                ssoToken = cookie.substring(cookie.indexOf("ssotoken=") + 9, cookie.indexOf(";", cookie.indexOf("ssotoken=")));
                return true;
            }
            return false;

        } catch (URISyntaxException e) {
            throw new RuntimeException(INVALID_URL);
        }
    }

    public String getSsoToken() {
        return ssoToken;
    }
}
