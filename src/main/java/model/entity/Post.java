package model.entity;

import java.util.Date;
import java.util.List;

public class Post {
    private int id;
    private String content;
    private int likesQuantity;
    private int commentsQuantity;
    private User user;
    private Date postDate;
    private List<Post> threads;
    private Boolean user_liked; // provisorio
    private List<String> images; // provisorio

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

    public int getCommentsQuantity() {
        return commentsQuantity;
    }

    public void setCommentsQuantity(int commentsQuantity) {
        this.commentsQuantity = commentsQuantity;
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

    public Boolean getLiked() {
        return user_liked;
    }

    public void setLiked(Boolean liked) {
        this.user_liked = liked;
    }

    public Boolean getUser_liked() {
        return user_liked;
    }

    public void setUser_liked(Boolean user_liked) {
        this.user_liked = user_liked;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
