package model.dto;

import model.entity.User;

import java.util.Date;
import java.util.List;

public class PostListDto {
    private int id;
    private String content;
    private int likesQuantity;
    private int commentsQuantity;
    private Date postDate;
    private User user;
    private Boolean isLiked;
    private List<String> images;

    public PostListDto() {
    }

    public PostListDto(int id, String content, int likesQuantity, int commentsQuantity, Date postDate, User user, Boolean liked, List<String> images) {
        this.id = id;
        this.content = content;
        this.likesQuantity = likesQuantity;
        this.commentsQuantity = commentsQuantity;
        this.postDate = postDate;
        this.user = user;
        this.isLiked = liked;
        this.images = images;
    }

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

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
