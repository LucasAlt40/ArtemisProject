package utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.PostDao;
import model.entity.User;

import java.io.IOException;

public class Utils {

    public  void viewFeed(HttpServletRequest request, HttpServletResponse response, PostDao postDao) throws ServletException, IOException {
       User user = this.getUserFromSession(request);
        if (user != null) {
            request.setAttribute("posts", postDao.getFeed(user.getId()));
        }
        request.getRequestDispatcher("/src/views/feed.jsp").forward(request, response);
    }

    public User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (User) session.getAttribute("user");
    }

}
