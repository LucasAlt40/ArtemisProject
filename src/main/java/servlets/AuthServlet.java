package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.UserDao;
import model.entity.User;
import utils.DataSourceSearcher;
import utils.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserDao userDao;

    public AuthServlet() {
        super();
        this.userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
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
            case "signup":
                signup(request, response);
                break;
            case "signin":
                signin(request, response);
                break;
            case "signout":
                signout(request, response);
                break;
            case null, default:
                break;
        }
    }

    private void signup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(PasswordEncoder.encode(password));

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());

        if (userDao.save(user)) {
            req.setAttribute("result", "registered");
            req.getRequestDispatcher("/src/views/signin.jsp").forward(req, resp);
        } else {
            req.setAttribute("result", "notRegistered");
            req.getRequestDispatcher("/src/views/signup.jsp").forward(req, resp);
        }
    }

    private void signin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<User> optionalUser = userDao.getUserByEmailAndPassword(email, password);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(600);
            session.setAttribute("user", user);

            req.setAttribute("result", "loginSuccess");
            req.getRequestDispatcher("/post?action=feed").forward(req, resp);
        } else {
            req.setAttribute("result", "loginError");
            req.getRequestDispatcher("/src/views/signin.jsp").forward(req, resp);
        }
    }

    private void signout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
