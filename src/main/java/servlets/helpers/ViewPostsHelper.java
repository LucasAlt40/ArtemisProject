package servlets.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.PostDao;
import model.dto.PostListDto;
import model.entity.User;
import model.mapper.MapperPost;
import model.utils.Utils;
import utils.DataSourceSearcher;

import java.util.List;

public class ViewPostsHelper implements Helper {
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PostDao postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        MapperPost mapperPost = new MapperPost();
        Utils utils = new Utils();

        User loggedUser = utils.getUserFromSession(req);
        String username = req.getParameter("username");

        if (username == null && loggedUser != null) {
            username = loggedUser.getUsername();
        }

        if (username != null && !username.isEmpty()) {
            List<PostListDto> posts = mapperPost.mapPostListEntityToPostListDto(
                    postDao.getPostsByUsername(username),
                    postDao,
                    loggedUser.getId()
            );

            req.setAttribute("posts", posts);
            req.setAttribute("user", loggedUser);
            return "profile.jsp";
        }

        return "feed.jsp";
    }
}
