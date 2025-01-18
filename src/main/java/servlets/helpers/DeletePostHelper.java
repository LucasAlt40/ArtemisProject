package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.PostDao;
import utils.DataSourceSearcher;

public class DeletePostHelper implements Helper{
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer idPost = Integer.parseInt(req.getParameter("idPost"));
        PostDao postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        postDao.deletePost(idPost);
        return "ControllerServlet?action=feed";
    }
}
