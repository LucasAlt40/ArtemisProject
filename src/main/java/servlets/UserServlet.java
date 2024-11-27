package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.PostDao;
import model.dao.UserDao;
import model.entity.Post;
import model.entity.User;
import utils.DataSourceSearcher;
import utils.PasswordEncoder;
import utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserDao userDao;
    PostDao postDao;
    Utils utils;

    public UserServlet() {
        super();
        this.userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        this.postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        this.utils = new Utils();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        chooseOption(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        chooseOption(req, resp);
    }

    private void chooseOption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "view":
                viewUser(request, response);
                break;
            case "viewPostsByUser":
                viewPostsByUsername(request, response);
                break;
            case null, default:
                utils.viewFeed(request, response, postDao);
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
                request.setAttribute("error", "Usuário não encontrado.");
                request.getRequestDispatcher("/src/views/feed.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de usuário inválido.");
            request.getRequestDispatcher("/src/views/feed.jsp").forward(request, response);
        }
    }

    private void viewPostsByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        List<Post> posts;
        if(username != null && !username.isEmpty()) {
            posts = postDao.getPostsByUsername(username);
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("/src/views/postsUser.jsp").forward(request, response);
        }
    }

}
