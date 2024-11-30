package model.dto;

import model.entity.User;

import java.util.Date;

public class PostListDto {
    private int id;
    private String content;
    private int likesQuantity;
    private int commentsQuantity;
    private Date postDate;
    private User user;
    private Boolean isLiked;

    public PostListDto() {
    }

    public PostListDto(int id, String content, int likesQuantity, int commentsQuantity, Date postDate, User user, Boolean isLiked) {
        this.id = id;
        this.content = content;
        this.likesQuantity = likesQuantity;
        this.commentsQuantity = commentsQuantity;
        this.postDate = postDate;
        this.user = user;
        this.isLiked = isLiked;
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

}
