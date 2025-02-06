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

    private MapperPost mapperPost;

    public Utils() {
        mapperPost = new MapperPost();
    }

    public void viewFeed(HttpServletRequest request, HttpServletResponse response, PostDao postDao) throws ServletException, IOException, SQLException {
       User user = this.getUserFromSession(request);
        if (user != null) {
            request.setAttribute("posts", mapperPost.mapPostListEntityToPostListDto(postDao.getFeed(user.getId())));
        }
        request.getRequestDispatcher("/src/views/feed.jsp").forward(request, response);
    }

    public User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (User) session.getAttribute("user");
    }


}
