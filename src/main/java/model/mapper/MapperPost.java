package model.mapper;

import jakarta.servlet.http.HttpServletRequest;
import model.dao.PostDao;
import model.dao.UserDao;
import model.dto.PostListDto;
import model.dto.PostViewDto;
import model.entity.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapperPost {

    public Post mapResultSetToPost (ResultSet rs, PostDao postDao, UserDao userDao) throws SQLException {
        Post post = new Post();
        post.setId(rs.getInt("ID"));
        post.setContent(rs.getString("CONTENT"));
        post.setLikesQuantity(rs.getInt("LIKES_QUANTITY"));
        post.setCommentsQuantity(rs.getInt("COMMENTS_QUANTITY"));
        post.setPostDate(rs.getDate("POST_DATE"));
        post.setThreads(postDao.getThreadsByPostId(rs.getInt("USER_ID"), rs.getInt("ID")));
        post.setUser(userDao.getUserById(rs.getInt("USER_ID")).get());
        post.setLiked(rs.getInt("USER_LIKED") == 1);

        String imagePaths = rs.getString("IMAGE_PATHS");
        if (imagePaths != null && !imagePaths.isEmpty()) {
            post.setImages(Arrays.asList(imagePaths.split(",")));
        } else {
            post.setImages(new ArrayList<>());
        }

        return post;
    }

  public PostViewDto mapResultSetToPostViewDto (Post post, PostDao postDao) throws SQLException {
      return new PostViewDto(
              post.getId(),
              post.getContent(),
              post.getLikesQuantity(),
              post.getCommentsQuantity(),
              post.getPostDate(),
              post.getUser(),
              post.getLiked(),
              post.getImages(),
              mapPostListEntityToPostListDto(postDao.getThreadsByPostId(post.getUser().getId(), post.getId()))
      );
  }

    public PostListDto mapPostEntityToPostListDto(Post post) throws SQLException {
        return new PostListDto(
                post.getId(),
                post.getContent(),
                post.getLikesQuantity(),
                post.getCommentsQuantity(),
                post.getPostDate(),
                post.getUser(),
                post.getLiked(),
                post.getImages()
        );
    }

    public List<PostListDto> mapPostListEntityToPostListDto(List<Post> postList) throws SQLException {
        List<PostListDto> postListDtoList = new ArrayList<>();
        for (Post post : postList) {
            PostListDto postListDto = mapPostEntityToPostListDto(post);
            postListDtoList.add(postListDto);
        }
        return postListDtoList;
    }

}
