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

    public UserServlet() {
        super();
        this.userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        this.postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
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
            case "createUser":
                createUser(request, response);
                break;
            case "login":
                login(request, response);
                break;
            case null, default:
                Utils.viewFeed(request, response, postDao);
                break;
        }
    }
    private void error(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String pathProfilePicture = req.getParameter("pathProfilePicture");
        String biografy = req.getParameter("biografy");
        String password = req.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPathProfilePicture(pathProfilePicture);
        user.setBiografy(biografy);
        user.setPassword(PasswordEncoder.encode(password));

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());

        if (userDao.save(user)) {
            req.setAttribute("result", "registered");
            req.getRequestDispatcher("/login-teste.jsp").forward(req, resp);
        } else {
            req.setAttribute("result", "notRegistered");
            req.getRequestDispatcher("/user-teste.jsp").forward(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<User> optionalUser = userDao.getUserByEmailAndPassword(email, password);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(600);
            session.setAttribute("user", user);

            req.getRequestDispatcher("/user.jsp").forward(req, resp); //mudar para perfil ou tl dps
        } else {
            req.setAttribute("result", "loginError");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

}
