package it.fartingbrains.ringotalkuser.rest.dto.jwt;

import java.time.LocalDateTime;
import java.util.Date;

public class JWTTokenDTO {
    private String token;
    private Date expirationDate;

    public JWTTokenDTO(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "JWTTokenDTO{" +
                "token='" + token + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
