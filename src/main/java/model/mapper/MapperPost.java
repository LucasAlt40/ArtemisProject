package model.mapper;

import model.dao.PostDao;
import model.dao.UserDao;
import model.dto.PostDto;
import model.entity.Post;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperPost {
    private DataSource dataSource;
    private PostDao postDao;

    public Post mapResultSetToPost (ResultSet rs) throws SQLException {
        var userDao = new UserDao(this.dataSource);
        Post post = new Post();
        post.setId(rs.getInt("ID"));
        post.setContent(rs.getString("CONTENT"));
        post.setLikesQuantity(rs.getInt("LIKES_QUANTITY"));
        post.setPostDate(rs.getDate("POST_DATE"));
        post.setThreads(postDao.getThreadsByPostId(rs.getInt("ID")));
        post.setUser(userDao.getUserById(rs.getInt("USER_ID")).get());
        return post;
    }

    public PostDto mapResultSetToPostDto (ResultSet rs) throws SQLException {
        var userDao = new UserDao(this.dataSource);
        PostDto post = new PostDto(
                rs.getInt("ID"),
                rs.getString("CONTENT"),
                rs.getInt("LIKES_QUANTITY"),
                rs.getDate("POST_DATE"),
                userDao.getUserById(rs.getInt("USER_ID")).get(),
                rs.getBoolean("IS_LIKED")
        );

        return post;
    }

}
