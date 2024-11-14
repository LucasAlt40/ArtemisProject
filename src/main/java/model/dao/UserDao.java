package model.dao;

import model.entity.User;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<User> getUserById(int id) {
        String sql = "SELECT ID, USERNAME, PASSWORD, EMAIL, BIOGRAFY, PATH_PROFILE_PICTURE FROM USER_ARTEMIS WHERE ID = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> getUserByUsername(String username) {
        String sql = "SELECT ID, USERNAME, PASSWORD, EMAIL, BIOGRAFY, PATH_PROFILE_PICTURE FROM USER_ARTEMIS WHERE USERNAME = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("ID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setEmail(rs.getString("EMAIL"));
        user.setBiografy(rs.getString("BIOGRAFY"));
        user.setPathProfilePicture(rs.getString("PATH_PROFILE_PICTURE"));
        return user;
    }
}
