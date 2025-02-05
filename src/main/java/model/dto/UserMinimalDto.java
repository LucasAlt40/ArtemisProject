package model.dto;

public class UserMinimalDto {
    private String username;
    private String name;
    private String pathProfilePicture;

    public UserMinimalDto() {}

    public UserMinimalDto(String username, String name, String pathProfilePicture) {
        this.username = username;
        this.name = name;
        this.pathProfilePicture = pathProfilePicture;
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

    public String getPathProfilePicture() {
        return pathProfilePicture;
    }

    public void setPathProfilePicture(String pathProfilePicture) {
        this.pathProfilePicture = pathProfilePicture;
    }
}
