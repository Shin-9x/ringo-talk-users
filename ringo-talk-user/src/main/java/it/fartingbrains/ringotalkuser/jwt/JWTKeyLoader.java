package it.fartingbrains.ringotalkuser.jwt;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JWTKeyLoader {
    @Value("${jwt.secret.key.env.param.name}")
    private String SECRET_KEY;
    private static final Logger _logger = LoggerFactory.getLogger(JWTUtils.class);
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        String errorMessage = "";

        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            errorMessage = "[init] The JWT Secret Key application properties cannot be empty or null.";
            _logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        String envSecret = System.getenv(SECRET_KEY);
        if(envSecret == null || envSecret.isEmpty()) {
            errorMessage = "[init] The JWT Secret Key environment variable cannot be empty or null.";
            _logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        this.secretKey = Keys.hmacShaKeyFor(envSecret.getBytes(StandardCharsets.UTF_8));
        _logger.info("[init] JWT Secret Key correctly loaded.");
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
