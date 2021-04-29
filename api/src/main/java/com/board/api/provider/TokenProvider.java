package com.board.api.provider;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Component
public class TokenProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 토큰 암호화키
    private final int tokenExpirationMsec = 1800000;  // 만료시간 30분

    public String createToken() {

        // setExpiration 매개변수가 Date로 되어있어 LocalDateTime를 사용하지 못함
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, tokenExpirationMsec);
        Date expiryDate =  calendar.getTime();

        return Jwts.builder()
                .setSubject("Jaeho")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String authToken) throws JwtException {

        Boolean result = Boolean.FALSE;

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            result = Boolean.TRUE;
        } catch (JwtException e) {
            logger.error("Validate Token Failed -> ", e);
        }
        return result;
    }
}
