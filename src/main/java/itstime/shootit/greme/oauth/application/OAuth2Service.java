package itstime.shootit.greme.oauth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itstime.shootit.greme.oauth.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2Service {
    private final String NAVER_URL = "https://openapi.naver.com/v1/nid/me";
    private final String KAKAO_URL = "https://kapi.kakao.com/v2/user/me";

    public LoginResponse findEmailByAccessToken(String domain, String accessToken) {
        String header = "Bearer " + accessToken; //Bearer 다음에 공백 추가

        String apiURL = domain.equals("naver") ? NAVER_URL : KAKAO_URL;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        String responseBody = getBody(apiURL, requestHeaders);

        System.out.println("RESPONSE BODY: " + responseBody);
        return new LoginResponse(domain.equals("naver")
                ? getNaverEmail(responseBody)
                : getKakaoEmail(responseBody));
    }

    private String getNaverEmail(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(responseBody, Map.class);
            Map<String, String> profile = (Map<String, String>) map.get("response");
            String email = profile.get("email");
            System.out.println("NAVER_" + email);
            return email;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getKakaoEmail(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(responseBody, Map.class);
            Map<String, String> profile = (Map<String, String>) map.get("kakao_account");
            String email = profile.get("email");
            System.out.println("KAKAO_" + email);
            return email;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getBody(String apiURL, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiURL);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { //정상 호출
                return readBody(con.getInputStream());
            } else { //에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiURL) {

        try {
            URL url = new URL(apiURL);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiURL, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiURL, e);
        }
    }

    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}