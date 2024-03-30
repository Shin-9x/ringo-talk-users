package it.fartingbrains.ringotalkuser.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import it.fartingbrains.ringotalkuser.entity.UserDetails;
import it.fartingbrains.ringotalkuser.rest.dto.jwt.JWTTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTUtils {
    private static final Logger _logger = LoggerFactory.getLogger(JWTUtils.class);

    //INJECTED BY SPRING
    @Value("${jwt.secret.key.env.param.name}")
    private String SECRET_KEY;
    @Value("${jwt.expiration.time}")
    private long EXPIRATION_TIME_SECONDS;
    @Value("${jwt.issuer}")
    private String ISSUER;
    private JWTKeyLoader jwtKeyLoader;

    public JWTUtils(JWTKeyLoader jwtKeyLoader) {
        this.jwtKeyLoader = jwtKeyLoader;
    }

    public JWTTokenDTO generateToken(UserDetails userDetails) {
        Date expDate = Date.from(Instant.now(Clock.systemUTC()).plusSeconds(EXPIRATION_TIME_SECONDS));
        String token = Jwts.builder()
                .issuer(ISSUER)
                .claim("id", userDetails.getId())
                .claim("username", userDetails.getUsername())
                .expiration(Date.from(Instant.now(Clock.systemUTC()).plusSeconds(EXPIRATION_TIME_SECONDS)))
                .signWith(jwtKeyLoader.getSecretKey())
                .compact();

        return new JWTTokenDTO(token, expDate);
    }

    public boolean validateToken(String token, String userName) {
        Jws<Claims> jwsClaims;

        try {
            jwsClaims = Jwts.parser().verifyWith(jwtKeyLoader.getSecretKey()).build().parseSignedClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            if(_logger.isErrorEnabled()) {
                _logger.error("[validateToken] Invalid JWT token: {}", e.getMessage());
            }
            return false;
        }

        Claims claims = jwsClaims.getPayload();

        //Verify if token is emitted by ISSUER
        if(!claims.getIssuer().equals(ISSUER)) {
            if(_logger.isErrorEnabled()) {
                _logger.error("[validateToken] Token not issued by authorized source");
            }
            return false;
        }

        if(!claims.get("username").equals(userName)) {
            if(_logger.isErrorEnabled()) {
                _logger.error("[validateToken] Username doesn't match.");
            }
            return false;
        }

        //Verify if token is expired
        if(Instant.now(Clock.systemUTC()).isAfter(claims.getExpiration().toInstant())) {
            _logger.error("[validateToken] Token expired");
            return false;
        }

        return true;
    }
}
