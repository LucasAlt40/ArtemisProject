
package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.UserDao;
import model.entity.User;
import utils.DataSourceSearcher;

import java.util.ArrayList;
import java.util.List;

public class SearchUserHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        String searchTerm = req.getParameter("searchTerm");
        List<User> users;

        if (searchTerm != null) {
            users =  userDao.searchUsersByUsername(searchTerm.trim());
            req.setAttribute("results", users);
        }

        return "search.jsp";
    }
}