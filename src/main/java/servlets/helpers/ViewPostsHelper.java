package servlets.helpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.PostDao;
import model.dto.PostViewDto;
import model.entity.Post;
import model.entity.User;
import model.mapper.MapperPost;
import model.utils.Utils;
import utils.DataSourceSearcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ViewPostsHelper implements Helper {
    private final PostDao postDao;
    private final MapperPost mapperPost;
    private final Utils utils;

    public ViewPostsHelper() {
        this.postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        this.mapperPost = new MapperPost();
        this.utils = new Utils();
    }

    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        PostViewDto post = null;

        try {
            User user = utils.getUserFromSession(request);
            if (user == null) {
                return "/src/views/feed.jsp";
            }

            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Optional<Post> optionalPost = postDao.getPostById(id);

                if (optionalPost.isPresent()) {
                    post = mapperPost.mapResultSetToPostViewDto(optionalPost.get(), postDao, user.getId());
                }
            }

            if (post != null) {
                request.setAttribute("post", post);
                return "/src/views/threads.jsp";
            } else {
                return "/src/views/feed.jsp";
            }
        } catch (SQLException | NumberFormatException e) {
            return "/src/views/feed.jsp";
        }
    }
}
