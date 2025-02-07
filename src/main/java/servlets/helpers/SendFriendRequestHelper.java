package servlets.helpers;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.RequestDao;
import model.entity.Request;
import model.entity.User;
import utils.DataSourceSearcher;

import java.util.List;

public class SendFriendRequestHelper implements Helper{
    @Override
    public JsonObject execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        RequestDao requestDao = new RequestDao(DataSourceSearcher.getInstance().getDataSource());
        User user = (User) req.getSession().getAttribute("user");
        int userReceivedId = Integer.parseInt(req.getParameter("userReceivedId"));
        Boolean response = requestDao.sendRequest(user.getId(), userReceivedId);
        JsonObject json = new JsonObject();
        json.addProperty("ok", response);
        return json;
    }
}
