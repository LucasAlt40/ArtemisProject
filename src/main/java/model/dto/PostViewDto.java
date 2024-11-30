package model.dto;

import model.entity.User;

import java.util.Date;
import java.util.List;

public class PostViewDto extends PostListDto{
    private List<PostListDto> threads;


    public PostViewDto(int id, String content, int likesQuantity, int commentsQuantity, Date postDate, User user, Boolean isLiked, List<PostListDto> threads) {
        super(id, content, likesQuantity, commentsQuantity, postDate, user, isLiked);
        this.threads = threads;
    }

    public List<PostListDto> getThreads() {
        return threads;
    }

    public void setThreads(List<PostListDto> threads) {
        this.threads = threads;
    }
}
