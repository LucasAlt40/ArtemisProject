package model.dao;

import model.dto.FriendsDto;
import model.dto.FriendsListDto;
import model.entity.User;
import utils.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserDao {
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<User> getUserById(int id) {
        String sql = "SELECT ID, USERNAME, PASSWORD, EMAIL, NAME, FRIENDS_QUANTITY, BIOGRAPHY, PATH_PROFILE_PICTURE FROM USER_ARTEMIS WHERE ID = ?";
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
        String sql = "SELECT ID, USERNAME, PASSWORD, NAME, FRIENDS_QUANTITY, EMAIL, BIOGRAPHY, PATH_PROFILE_PICTURE FROM USER_ARTEMIS WHERE USERNAME = ?";
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

    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT ID, USERNAME, PASSWORD, EMAIL, NAME, FRIENDS_QUANTITY, BIOGRAPHY, PATH_PROFILE_PICTURE FROM USER_ARTEMIS WHERE EMAIL = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
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

    public Integer getLastUserId() {
        String sql = "SELECT MAX(ID) FROM USER_ARTEMIS";
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




    public Boolean save(User user){
        Optional<User> optional = getUserByEmail(user.getEmail());
        if(optional.isPresent()) {
            return false;
        }

        String sql = "INSERT INTO USER_ARTEMIS (ID, USERNAME, PASSWORD, NAME, EMAIL, BIOGRAPHY, PATH_PROFILE_PICTURE) VALUES (? ,?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, getLastUserId()+1);
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getBiography());
            ps.setString(7, user.getPathProfilePicture());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir no banco", e);
        }
        return true;
    }

    public Collection<FriendsDto> getFriendsList(Integer userId){
        String sql = "CALL GET_FRENDS_INFO(?)";

        Collection<FriendsDto> friendsDtos = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    FriendsDto fq = new FriendsDto();
                    fq.setId(rs.getInt("FRIENDS_ID"));
                    fq.setUsername(rs.getString("USERNAME"));
                    fq.setName(rs.getString("NAME"));
                    fq.setPath(rs.getString("PATH_PROFILE_PICTURE"));
                    friendsDtos.add(fq);
                }

            }
            return friendsDtos;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }

    }


    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        String passwordEncripted = PasswordEncoder.encode(password);

        String sql = "SELECT ID, NAME, USERNAME, EMAIL FROM USER_ARTEMIS WHERE email = ? AND password = ?";
        Optional<User> optional = Optional.empty();

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, passwordEncripted);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setUsername(rs.getString(3));
                    user.setEmail(rs.getString(4));

                    optional = Optional.of(user);
                }
            }
            return optional;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Erro durante a consulta no BD", sqlException);
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("ID"));
        user.setName(rs.getString("NAME"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setEmail(rs.getString("EMAIL"));
        user.setBiography(rs.getString("BIOGRAPHY"));
        user.setPathProfilePicture(rs.getString("PATH_PROFILE_PICTURE"));
        user.setFriendsQuantity(rs.getInt("FRIENDS_QUANTITY"));
        return user;
    }

    public List<User> searchUsersByUsername(String usernameSubstring) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT ID, USERNAME, PASSWORD, EMAIL, NAME, FRIENDS_QUANTITY, BIOGRAPHY, PATH_PROFILE_PICTURE " +
                "FROM USER_ARTEMIS WHERE USERNAME LIKE ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + usernameSubstring + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
