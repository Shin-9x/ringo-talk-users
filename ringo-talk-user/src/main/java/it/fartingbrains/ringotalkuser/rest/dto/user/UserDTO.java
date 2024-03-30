package it.fartingbrains.ringotalkuser.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UserDTO {
    private String username;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private String biography;

    public UserDTO(
            String username, LocalDateTime createDate, LocalDateTime modifiedDate, String biography
    ) {
        this.username = username;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.biography = biography;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                ", biography='" + biography + '\'' +
                '}';
    }
}
