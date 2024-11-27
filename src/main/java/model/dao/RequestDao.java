package model.dao;

import model.entity.Request;
import model.entity.RequestStatus;
import model.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestDao {
    private DataSource dataSource;

    public RequestDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Request> getRequestsByUserId(int userId) {
        var userDao = new UserDao(this.dataSource);
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT ID_USER_SUBMITTED, STATUS FROM REQUEST WHERE ID_USER_RECEIVED = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Request request = new Request();
                    Optional<User> userSubmitted = userDao.getUserById(rs.getInt("ID_USER_SUBMITTED"));
                    RequestStatus status = RequestStatus.valueOf(rs.getString("STATUS"));
                    request.setStatus(status);
                    request.setUserSubmitted(userSubmitted.get());
                    requests.add(request);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public Boolean sendRequest(int userSubmittedId, int userReceivedId) {
        String sql = "INSERT INTO REQUEST (ID_USER_SUBMITTED, ID_USER_RECEIVED) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userSubmittedId);
            stmt.setInt(2, userReceivedId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean approveRequest(int userSubmittedId, int userReceivedId) {
        String sql = "UPDATE REQUEST SET STATUS = ? WHERE ID_USER_RECEIVED = ? AND ID_USER_SUBMITTED = ?";
        String sql2 = "INSERT INTO FRIENDS VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
            stmt.setString(1, RequestStatus.APPROVED.name());
            stmt.setInt(2, userReceivedId);
            stmt.setInt(3, userSubmittedId);
            stmt.executeUpdate();

            stmt2.setInt(1, userSubmittedId);
            stmt2.setInt(2, userReceivedId);
            stmt2.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean rejectRequest(int userSubmittedId, int userReceivedId) {
        String sql = "UPDATE REQUEST SET STATUS = ? WHERE ID_USER_RECEIVED = ? AND ID_USER_SUBMITTED = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, RequestStatus.REJECTED.name());
            stmt.setInt(2, userReceivedId);
            stmt.setInt(3, userSubmittedId);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
