package servlets.helpers;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.PostDao;
import model.entity.User;
import utils.DataSourceSearcher;

public class LikePostHelper implements Helper{
    @Override
    public JsonObject execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer idPost = Integer.parseInt(req.getParameter("idPost"));
        User user = (User) req.getSession().getAttribute("user");
        PostDao postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        Boolean response = postDao.likePost(idPost, user.getId());
        JsonObject json = new JsonObject();
        json.addProperty("ok", response);
        return json;
    }
}
