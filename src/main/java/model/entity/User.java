package model.entity;

public class User {
    private int id;
    private String username;
    private String name;
    private String password;
    private String email;
    private String biography;
    private String pathProfilePicture;
    private String pathProfileBanner;
    private Integer friendsQuantity;

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getFriendsQuantity() {
        return friendsQuantity;
    }

    public void setFriendsQuantity(Integer friendsQuantity) {
        this.friendsQuantity = friendsQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathProfileBanner() {
        return pathProfileBanner;
    }

    public void setPathProfileBanner(String pathProfileBanner) {
        this.pathProfileBanner = pathProfileBanner;
    }
}
