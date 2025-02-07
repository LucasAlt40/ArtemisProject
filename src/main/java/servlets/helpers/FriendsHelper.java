package servlets.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.UserDao;
import model.dto.FriendsDto;
import model.entity.User;
import utils.DataSourceSearcher;

import java.util.Collection;

public class FriendsHelper implements Helper{
    @Override
    public JsonObject execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        User user = (User) req.getSession().getAttribute("user");
        Collection<FriendsDto> response = userDao.getFriendsList(user.getId());
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.addProperty("friends", gson.toJson(response));
        return json;
    }
}
