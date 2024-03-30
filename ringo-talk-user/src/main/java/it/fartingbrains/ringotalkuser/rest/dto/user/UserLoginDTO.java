package it.fartingbrains.ringotalkuser.rest.dto.user;

import jakarta.validation.constraints.NotNull;

public class UserLoginDTO {
    @NotNull
    private final String username;
    @NotNull
    private final String password;

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserRequestObject{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
