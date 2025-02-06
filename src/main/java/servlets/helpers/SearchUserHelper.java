// No arquivo SearchUserHelper.java
package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.UserDao;
import model.entity.User;
import utils.DataSourceSearcher;

import java.util.List;

public class SearchUserHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String searchTerm = req.getParameter("searchTerm");

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return List.of();
        }

        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        return userDao.searchUsersByUsername(searchTerm.trim());
    }
}