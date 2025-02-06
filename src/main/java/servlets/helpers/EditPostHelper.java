package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.PostDao;
import utils.DataSourceSearcher;

public class EditPostHelper implements Helper{
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer idPost = Integer.parseInt(req.getParameter("idPost"));
        String newContent = req.getParameter("newContent");
        PostDao postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        postDao.editPostContent(idPost, newContent);
        return "ControllerServlet?action=feed";
    }
}
