package it.fartingbrains.ringotalkuser.rest.dto.user;

public class UserBiographyDTO {
    private String biography;

    public UserBiographyDTO() { }

    public UserBiographyDTO(String biography) {
        this.biography = biography;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "UserBiographyDTO{" +
                "biography='" + biography + '\'' +
                '}';
    }
}
