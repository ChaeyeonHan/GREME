package itstime.shootit.greme.oauth.application;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {
    private String secretKey = "asdfghjqweralujeijfakdjfajekjfladjflakdjasdjdflajiejlfakdaasdefqadfaeqf";

    @Spy
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(secretKey);

    @Test
    @DisplayName("jwt email 가져오기")
    void getUserIdSuccess() {
        String accessToken = jwtTokenProvider.createAccessToken("email");
        String email = jwtTokenProvider.getEmail(accessToken);
        assertThat(email).isEqualTo("email");
    }

    @Test
    @DisplayName("jwt 유효기간 만료")
    void vaildateSuccess() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String accessToken = Jwts.builder()
                .setSubject("email")
                .setExpiration(null)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        assertThat(jwtTokenProvider.validate(accessToken)).isFalse();
    }
}