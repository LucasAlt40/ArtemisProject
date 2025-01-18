package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.PostDao;
import model.entity.User;
import utils.DataSourceSearcher;

public class DislikePostHelper implements Helper{
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer idPost = Integer.parseInt(req.getParameter("idPost"));
        User user = (User) req.getSession().getAttribute("user");
        PostDao postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        postDao.dislikePost(idPost, user.getId());
        return "ControllerServlet?action=feed";
    }
}
