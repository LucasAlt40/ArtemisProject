package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.UserDao;
import model.entity.User;
import utils.DataSourceSearcher;
import utils.PasswordEncoder;

public class SignUpHelper implements Helper{

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(PasswordEncoder.encode(password));

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());

        if (userDao.save(user)) {
            req.setAttribute("result", "registered");
            return "signin.jsp";
        } else {
            req.setAttribute("result", "notRegistered");
            return "signup.jsp";
        }

    }
}
