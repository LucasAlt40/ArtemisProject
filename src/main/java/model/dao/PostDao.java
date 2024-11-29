package model.dao;

import model.dto.PostDto;
import model.entity.Post;
import model.mapper.MapperPost;

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
    private MapperPost mapperPost;

    public PostDao(DataSource dts){
        this.dataSource = dts;
        this.mapperPost = new MapperPost();
    }

    public Optional<PostDto> getPostById(int id) {
        String sql = "SELECT ID, CONTENT, LIKES_QUANTITY, COMMENTS_QUANTITY, POST_DATE, USER_ID, THREAD_ID FROM POST WHERE ID= ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapperPost.mapResultSetToPostDto(rs, new UserDao(this.dataSource)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<PostDto> getPostsByUsername(String username, Integer userId) {
        String sql = "SELECT P.ID AS ID, P.CONTENT, P.LIKES_QUANTITY, P.COMMENTS_QUANTITY, P.POST_DATE, P.USER_ID, P.THREAD_ID, " +
                "    CASE " +
                "        WHEN PL.ID_USER IS NOT NULL THEN 1 " +
                "        ELSE 0 " +
                "    END AS IS_LIKED " +
                "FROM POST P " +
                "JOIN USER_ARTEMIS U ON P.USER_ID = U.ID " +
                "LEFT JOIN POST_LIKES PL ON P.ID = PL.ID_POST AND PL.ID_USER = ? " +
                "WHERE U.USERNAME = ? AND P.THREAD_ID IS NULL " +
                "ORDER BY P.POST_DATE DESC";

        List<PostDto> posts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId); // ID do usuário logado para verificar se ele curtiu os posts
            stmt.setString(2, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapperPost.mapResultSetToPostDto(rs, new UserDao(this.dataSource)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }


    public Boolean sendPost(Post post, Integer threadId) {
        String insertSql = "INSERT INTO POST (ID, CONTENT, POST_DATE, USER_ID, THREAD_ID) VALUES (?, ?, SYSDATE, ?, ?)";
        String updateCommentsSql = "UPDATE POST SET COMMENTS_QUANTITY = COMMENTS_QUANTITY + 1 WHERE ID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateCommentsSql)) {

            insertStmt.setInt(1, getLastPostId() + 1);
            insertStmt.setString(2, post.getContent());
            insertStmt.setInt(3, post.getUser().getId());

            if (threadId != null && threadId > 0) {
                updateStmt.setInt(1, threadId);
                updateStmt.executeUpdate();

                insertStmt.setInt(4, threadId);
            } else {
                insertStmt.setNull(4, java.sql.Types.INTEGER);
            }

            insertStmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


    public Boolean editPostContent(Integer postId, String newContent) {
        String sql = "UPDATE POST SET CONTENT = ? WHERE ID = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newContent);
            stmt.setInt(2, postId);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deletePost(Integer idPost){
        String sql = "DELETE FROM POST WHERE ID = ?";
        try(Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setLong(1, idPost);
            stmt.executeUpdate();
            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
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

    public Boolean dislikePost(Integer postId, Integer userId) {
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

    public List<Post> getThreadsByPostId(Integer id) {
        String sql = "SELECT ID, CONTENT, LIKES_QUANTITY, COMMENTS_QUANTITY, POST_DATE, USER_ID, THREAD_ID FROM POST WHERE THREAD_ID= ?";
        List<Post> posts = new ArrayList();

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapperPost.mapResultSetToPost(rs, this, new UserDao(this.dataSource)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public List<PostDto> getFeed(Integer id) {
        String sql = "SELECT ID, CONTENT, LIKES_QUANTITY, COMMENTS_QUANTITY, POST_DATE, USER_ID,\n" +
                "    CASE\n" +
                "        WHEN POST_LIKES.ID_USER IS NOT NULL THEN 1\n" +
                "        ELSE 0\n" +
                "    END AS IS_LIKED\n" +
                "FROM POST\n" +
                "LEFT JOIN POST_LIKES ON POST.ID = POST_LIKES.ID_POST AND POST_LIKES.ID_USER = ? WHERE THREAD_ID IS NULL ORDER BY POST_DATE DESC";
        List<PostDto> posts = new ArrayList<>();

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapperPost.mapResultSetToPostDto(rs, new UserDao(this.dataSource)));
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


}
