package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.PostDao;
import model.dao.UserDao;
import model.dto.PostListDto;
import model.entity.User;
import model.mapper.MapperPost;
import utils.DataSourceSearcher;
import utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserDao userDao;
    PostDao postDao;
    Utils utils;
    MapperPost mapperPost;

    public UserServlet() {
        super();
        this.userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        this.postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        this.utils = new Utils();
        this.mapperPost = new MapperPost();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            chooseOption(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            chooseOption(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void chooseOption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String action = request.getParameter("action");

        switch (action) {
            case "view":
                viewUser(request, response);
                break;
            case "viewPostsByUser":
                viewPostsByUsername(request, response);
                break;
            case null, default:
               // utils.viewFeed(request, response, postDao);
                break;
        }
    }

    private void viewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String idParam = request.getParameter("id");
        Optional<User> user = Optional.empty();

        try {
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                user = userDao.getUserById(id);
            } else if (username != null && !username.isEmpty()) {
                user = userDao.getUserByUsername(username);
            }

            if (user.isPresent()) {
                request.setAttribute("user", user.get());
                request.getRequestDispatcher("/src/views/profile.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/post?action=feed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/post?action=feed");
        }
    }

    private void viewPostsByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");

        List<PostListDto> posts;
        if(username != null && !username.isEmpty()) {
            posts = mapperPost.mapPostListEntityToPostListDto(postDao.getPostsByUsername(username), postDao, utils.getUserFromSession(request).getId());
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("/src/views/profile.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/post?action=feed");
        }
    }


}
