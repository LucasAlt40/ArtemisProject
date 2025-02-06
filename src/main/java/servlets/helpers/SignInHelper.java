package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.UserDao;
import model.entity.User;
import utils.DataSourceSearcher;

import java.util.Optional;

public class SignInHelper implements Helper{
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        Optional<User> optionalUser = userDao.getUserByEmailAndPassword(email, password);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(600);
            session.setAttribute("user", user);

            return "/ControllerServlet?action=feed";
        } else {
            req.setAttribute("result", "loginError");
            return "signin.jsp";
        }
    }
}
