package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.UserDao;
import model.entity.User;
import utils.DataSourceSearcher;

import java.util.Optional;

public class ViewUserHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());

        String username = req.getParameter("username");
        String idParam = req.getParameter("id");
        Optional<User> user = Optional.empty();

        try {
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                user = userDao.getUserById(id);
            } else if (username != null && !username.isEmpty()) {
                user = userDao.getUserByUsername(username);
            }

            if (user.isPresent()) {
                req.setAttribute("profileUser", user.get());
                return null;
            }else {
                return "feed.jsp";
            }
        } catch (NumberFormatException e) {
            return "feed.jsp";
        }
    }
}
