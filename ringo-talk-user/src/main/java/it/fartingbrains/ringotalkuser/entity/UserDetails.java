package it.fartingbrains.ringotalkuser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class UserDetails {
    @Id
    @GeneratedValue
    private long id;
    @Size(min=4, message = "Username should be at least 4 characters.")
    @NotNull
    @Column(unique = true)
    private String username;
    @Size(min=2, message = "Password should be at least 8 characters.")
    @NotNull
    //@JsonIgnore
    private String password;
    @JsonIgnore
    private String salt;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private String biography;

    public UserDetails() { }

    public UserDetails(
            long id, String username, String password, LocalDateTime createDate, LocalDateTime modifiedDate,
            String biography
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.biography = biography;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
        return "UserDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                ", biography='" + biography + '\'' +
                '}';
    }
}
