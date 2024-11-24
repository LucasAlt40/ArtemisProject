package utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.PostDao;

import java.io.IOException;

public class Utils {

    public static void viewFeed(HttpServletRequest request, HttpServletResponse response, PostDao postDao) throws ServletException, IOException {
//        request.setAttribute("posts", postDao.getFeed());
//        //TODO
//        request.getRequestDispatcher("/src/views/feed.jsp").forward(request, response);
    }

}
