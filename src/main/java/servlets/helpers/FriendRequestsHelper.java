package servlets.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.RequestDao;
import model.entity.Request;
import model.entity.User;
import utils.DataSourceSearcher;

import java.util.List;

public class FriendRequestsHelper implements Helper{
    @Override
    public JsonObject execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        RequestDao requestDao = new RequestDao(DataSourceSearcher.getInstance().getDataSource());
        User user = (User) req.getSession().getAttribute("user");
        List<Request> response = requestDao.getRequestsByUserId(user.getId());
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.addProperty("requests", gson.toJson(response));
        return json;
    }
}
