package it.fartingbrains.ringotalkuser.rest.dto.user;

public class UserPasswordDTO {
    private String password;

    public UserPasswordDTO() { }

    public UserPasswordDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserPasswordDTO{" +
                "password='" + password + '\'' +
                '}';
    }
}
