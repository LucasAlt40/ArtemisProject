package model.dto;

public class UserDetailsDto {
    private String username;
    private String name;
    private String biography;
    private String pathProfilePicture;
    private String pathProfileBanner;
    private Integer friendsQuantity;

    public UserDetailsDto() {}

    public UserDetailsDto(String username, String name, String biography, String pathProfilePicture, String pathProfileBanner, Integer friendsQuantity) {
        this.username = username;
        this.name = name;
        this.biography = biography;
        this.pathProfilePicture = pathProfilePicture;
        this.pathProfileBanner = pathProfileBanner;
        this.friendsQuantity = friendsQuantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPathProfilePicture() {
        return pathProfilePicture;
    }

    public void setPathProfilePicture(String pathProfilePicture) {
        this.pathProfilePicture = pathProfilePicture;
    }

    public String getPathProfileBanner() {
        return pathProfileBanner;
    }

    public void setPathProfileBanner(String pathProfileBanner) {
        this.pathProfileBanner = pathProfileBanner;
    }

    public Integer getFriendsQuantity() {
        return friendsQuantity;
    }

    public void setFriendsQuantity(Integer friendsQuantity) {
        this.friendsQuantity = friendsQuantity;
    }
}
