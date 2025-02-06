package model.dao;

import model.entity.Post;
import model.mapper.MapperPost;
import oracle.jdbc.OracleTypes;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostDao {
    private DataSource dataSource;
    private MapperPost mapperPost;
    private final String sqlParams = "ID, CONTENT, LIKES_QUANTITY, COMMENTS_QUANTITY, POST_DATE, USER_ID, THREAD_ID";

    public PostDao(DataSource dts) {
        this.dataSource = dts;
        this.mapperPost = new MapperPost();

    }

    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();


        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);

                System.out.print(columnName + ": " + value + " | ");
            }
            System.out.println();
        }
    }

    public List<Post> getFeed(int userID) {
        List<Post> posts = new ArrayList<>();
        String sql = "CALL GET_FEED(?, ?)";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, userID);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(2)) {
                while (rs.next()) {
                    Post post = mapperPost.mapResultSetToPost(rs, this, new UserDao(this.dataSource));
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }


    public List<Post> getThreadsByPostId(Integer userId, Integer threadId) {
        List<Post> posts = new ArrayList<>();
        String procedureCall = "{ call GET_THREADS_BY_POST_ID(?, ?, ?) }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(procedureCall)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, threadId);
            stmt.registerOutParameter(3, Types.REF_CURSOR); // Para Oracle

            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(3)) {
                while (rs.next()) {
                    posts.add(mapperPost.mapResultSetToPost(rs, this, new UserDao(this.dataSource)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public Optional<Post> getPostById(int userId, int postId) {
        String procedureCall = "{ call GET_POST_BY_ID(?, ?, ?) }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(procedureCall)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, postId);

            stmt.registerOutParameter(3, OracleTypes.CURSOR);

            stmt.execute();

            try (ResultSet rs = (ResultSet) stmt.getObject(3)) {
                if (rs.next()) {
                    return Optional.of(mapperPost.mapResultSetToPost(rs, this, new UserDao(this.dataSource)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public List<Post> getPostsByUsername(String username) {
        String sql = "SELECT P.ID AS ID, P.CONTENT, P.LIKES_QUANTITY, P.COMMENTS_QUANTITY, P.POST_DATE, P.USER_ID, P.THREAD_ID\n" +
                "FROM POST P\n" +
                "JOIN USER_ARTEMIS U ON P.USER_ID = U.ID\n" +
                "WHERE U.USERNAME = ? AND P.THREAD_ID IS NULL\n" +
                "ORDER BY P.POST_DATE DESC";

        List<Post> posts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
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

    public Boolean sendPost(Post post, Integer threadId) {
        String procedureAddPost = "CALL ADD_POST(?,?,?)";
        String procedurePlusComment = "CALL PLUS_COMMENT(?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement insertStmt = conn.prepareCall(procedureAddPost);
             PreparedStatement updateStmt = conn.prepareStatement(procedurePlusComment)) {

            insertStmt.setString(1, post.getContent());
            insertStmt.setInt(2, post.getUser().getId());

            if (threadId != null && threadId > 0) {
                insertStmt.setInt(3, threadId);
                insertStmt.executeQuery();

                updateStmt.setInt(1, threadId);
                updateStmt.executeQuery();
            } else {
                insertStmt.setNull(3, java.sql.Types.INTEGER);
            }

            insertStmt.executeQuery();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


    public Boolean editPostContent(Integer postId, String newContent) {
        String sql = "CALL EDIT_POST(?,?)";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setString(2, newContent);
            stmt.executeQuery();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deletePost(Integer idPost){
        String sql = "CALL DELETE_POST(?)";
        try(Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setInt(1, idPost);
            stmt.executeQuery();
            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public Boolean likePost(Integer postId, Integer userId) {
        String sql = "CALL LIKE_POST(?, ?)";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            stmt.executeQuery();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean dislikePost(Integer postId, Integer userId) {
        String sql = "DELETE POST_LIKES WHERE ID_POST = ? AND ID_USER =?";
        String sql2 = "UPDATE POST SET LIKES_QUANTITY = (LIKES_QUANTITY - 1) WHERE ID = ?";

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            stmt2.setInt(1, postId);
            stmt.executeUpdate();
            stmt2.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
