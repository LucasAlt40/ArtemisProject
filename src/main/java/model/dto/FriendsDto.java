package model.dto;

public class FriendsDto {
    Integer id;
    String username;
    String name;
    String path;

    public FriendsDto() {}

    public FriendsDto(Integer id, String username, String name, String path) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
