package model.dto;

import model.entity.Post;
import model.entity.User;

import java.util.Date;
import java.util.List;

public record PostDto(int id, String content, int likesQuantity, Date postDate, User user, Boolean isLiked) {
}
