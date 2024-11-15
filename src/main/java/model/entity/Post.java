package model.entity;

import java.util.Date;
import java.util.List;

public class Post {
    private int id;
    private String content;
    private int likesQuantity;
    private User user;
    private Date postDate;
    private List<Post> threads;

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

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public List<Post> getThreads() {
        return threads;
    }

    public void setThreads(List<Post> threads) {
        this.threads = threads;
    }
}
