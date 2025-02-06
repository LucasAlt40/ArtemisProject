package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.PostDao;
import model.entity.User;
import model.mapper.MapperPost;
import utils.DataSourceSearcher;

public class FeedHelper implements Helper{

    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PostDao postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        MapperPost mapperPost = new MapperPost();
        User user = this.getUserFromSession(req);
        if (user != null) {
            req.setAttribute("posts", mapperPost.mapPostListEntityToPostListDto(postDao.getFeed(user.getId())));
        }
        return "feed.jsp";
    }

    public User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (User) session.getAttribute("user");
    }
}
