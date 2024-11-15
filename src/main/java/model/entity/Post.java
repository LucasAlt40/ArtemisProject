package model.entity;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private String content;
    private int likesQuantity;
    private User user;
    private LocalDateTime postDate;
    private Post thread;

    public Post() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikesQuantity() {
        return likesQuantity;
    }

    public void setLikesQuantity(int likesQuantity) {
        this.likesQuantity = likesQuantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public Post getThread() {
        return thread;
    }

    public void setThread(Post thread) {
        this.thread = thread;
    }
}
