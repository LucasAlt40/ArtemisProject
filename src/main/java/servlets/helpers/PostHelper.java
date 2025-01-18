package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.PostDao;
import model.dto.PostViewDto;
import model.entity.User;
import model.mapper.MapperPost;
import utils.DataSourceSearcher;

import java.sql.SQLException;

public class PostHelper implements Helper{
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idParam = req.getParameter("id");
        PostViewDto post = null;

        MapperPost mapperPost = new MapperPost();
        PostDao postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());

        User user = (User) req.getSession().getAttribute("user");

        try{
            if(idParam != null && !idParam.isEmpty()){
                int id = Integer.parseInt(idParam);
                post = mapperPost.mapResultSetToPostViewDto(postDao.getPostById(id).get(), postDao, user.getId());
            }

            if(post != null){
                req.setAttribute("post", post);
                return "threads.jsp";
            } else {
                //TODO
            }
        }catch (SQLException e){
            req.setAttribute("error", "ID do post inválido.");
            return "ControllerServlet?action=feed";
        }

        return "ControllerServlet?action=feed";
    }
}
