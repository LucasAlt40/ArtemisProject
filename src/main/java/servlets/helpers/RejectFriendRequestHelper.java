package servlets.helpers;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.RequestDao;
import model.entity.User;
import utils.DataSourceSearcher;

public class RejectFriendRequestHelper implements Helper{
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        RequestDao requestDao = new RequestDao(DataSourceSearcher.getInstance().getDataSource());
        User user = (User) req.getSession().getAttribute("user");
        int userSubmittedId = Integer.parseInt(req.getParameter("userSubmittedId"));
        Boolean response = requestDao.rejectRequest(userSubmittedId, user.getId());
        JsonObject json = new JsonObject();
        json.addProperty("ok", response);
        return json;
    }
}
