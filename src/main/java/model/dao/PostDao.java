package model.dao;

import model.entity.Post;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostDao {
    private DataSource dataSource;

    public PostDao(DataSource dts){
        this.dataSource = dts;
    }

    public Optional<Post> getPostById(int id) {
        String sql = "SELECT ID, CONTENT, LIKES_QUANTITY, POST_DATE, USER_ID, THREAD_ID FROM POST WHERE ID= ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Post> getPostsByUsername(String username) {
        String sql = "SELECT P.ID as ID, CONTENT, LIKES_QUANTITY, POST_DATE, USER_ID, THREAD_ID " +
                "FROM POST P JOIN USER_ARTEMIS U ON P.USER_ID = U.ID " +
                "WHERE USERNAME = ? AND P.THREAD_ID IS NULL";
        List<Post> posts = new ArrayList();
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){
                    posts.add(mapResultSetToPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    private List<Post> getThreadsByPostId(Integer id) {
        String sql = "SELECT ID, CONTENT, LIKES_QUANTITY, POST_DATE, USER_ID, THREAD_ID FROM POST WHERE THREAD_ID= ?";
        List<Post> posts = new ArrayList();

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapResultSetToPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public Integer getLastPostId() {
        String sql = "SELECT MAX(ID) FROM POST";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MAX(ID)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Post mapResultSetToPost (ResultSet rs) throws SQLException {
        var userDao = new UserDao(this.dataSource);
        Post post = new Post();
        post.setId(rs.getInt("ID"));
        post.setContent(rs.getString("CONTENT"));
        post.setLikesQuantity(rs.getInt("LIKES_QUANTITY"));
        post.setPostDate(rs.getDate("POST_DATE"));
        post.setThreads(getThreadsByPostId(rs.getInt("ID")));
        post.setUser(userDao.getUserById(rs.getInt("USER_ID")).get());
        return post;
    }
}
