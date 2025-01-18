package utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.PostDao;
import model.entity.User;
import model.mapper.MapperPost;

import java.io.IOException;
import java.sql.SQLException;

public class Utils {

    public Utils() {}

    public User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (User) session.getAttribute("user");
    }

}
