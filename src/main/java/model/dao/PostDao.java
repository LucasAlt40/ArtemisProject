package model.dao;

import model.entity.Post;
import oracle.sql.DATE;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
                "WHERE USERNAME = ? AND P.THREAD_ID IS NULL ORDER BY P.POST_DATE DESC";
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

    public Boolean sendPost(Post post, Integer theadId) {
        String sql = "INSERT INTO POST (ID, CONTENT, POST_DATE, USER_ID, THREAD_ID) VALUES (?, ?, SYSDATE, ?, ?)";

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, getLastPostId() + 1);
            stmt.setString(2, post.getContent());
            stmt.setInt(3, post.getUser().getId());

            if(theadId != null && theadId > 0){
                stmt.setInt(4, theadId);
            } else {
                stmt.setNull(4, 0);
            }
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public Boolean likePost(Integer postId, Integer userId) {
        String sql = "INSERT INTO POST_LIKES VALUES (?,?)";
        String sql2 = "UPDATE POST SET LIKES_QUANTITY = (LIKES_QUANTITY + 1) WHERE ID = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
            stmt2.setInt(1, postId);
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            stmt2.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deslikePost(Integer postId, Integer userId) {
        String sql = "DELETE POST_LIKES WHERE ID_POST = ? AND ID_USER =?";
        String sql2 = "UPDATE POST SET LIKES_QUANTITY = (LIKES_QUANTITY - 1) WHERE ID = ?";

        try(Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); PreparedStatement stmt2 = conn.prepareStatement(sql2)){
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            stmt2.setInt(1, postId);
            stmt.executeUpdate();
            stmt2.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
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

    public List<Post> getFeed(Integer id) {
        String sql = "SELECT ID, CONTENT, LIKES_QUANTITY, POST_DATE, USER_ID,\n" +
                "    CASE\n" +
                "        WHEN POST_LIKES.ID_USER IS NOT NULL THEN 1\n" +
                "        ELSE 0\n" +
                "    END AS user_liked\n" +
                "FROM POST\n" +
                "LEFT JOIN POST_LIKES ON POST.ID = POST_LIKES.ID_POST AND POST_LIKES.ID_USER = ? WHERE THREAD_ID IS NULL";
        List<Post> posts = new ArrayList();

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,id);
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
