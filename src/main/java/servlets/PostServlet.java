package servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.Optional;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;
import model.dao.PostDao;
import model.dao.RequestDao;
import model.dao.UserDao;
import model.dto.PostListDto;
import model.dto.PostViewDto;
import model.entity.Post;
import model.entity.User;
import model.mapper.MapperPost;
import utils.DataSourceSearcher;
import utils.Utils;

@WebServlet("/post")
public class PostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    PostDao postDao;
    UserDao userDao;
    RequestDao requestDao;
    Utils utils;
    MapperPost mapperPost;

    public PostServlet() {
        super();
        this.postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        this.userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        this.requestDao = new RequestDao(DataSourceSearcher.getInstance().getDataSource());
        this.utils = new Utils();
        this.mapperPost = new MapperPost();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            chooseOption(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            chooseOption(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void chooseOption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String action = request.getParameter("action");

        switch (action) {
            case "viewById":
                viewPostById(request, response);
                break;
            case "add":
                createPost(request, response);
                break;
            case "feed":
                utils.viewFeed(request, response, postDao);
                break;
            case "like":
                likePost(request, response);
                break;
            case "dislike":
                dislikePost(request, response);
                break;
            case "delete":
                deletePost(request, response);
                break;
            case "edit":
                editPostContent(request, response);
                break;
            case null, default:
                utils.viewFeed(request, response, postDao);
                break;
        }
    }

    private void viewPostById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idParam = request.getParameter("id");
        PostViewDto post = null;

        try{
            if(idParam != null && !idParam.isEmpty()){
                int id = Integer.parseInt(idParam);
                post = mapperPost.mapResultSetToPostViewDto(postDao.getPostById(id).get(), postDao, utils.getUserFromSession(request).getId());
            }

            if(post != null){
                request.setAttribute("post", post);
                request.getRequestDispatcher("/src/views/threads.jsp").forward(request, response);
            } else {
                //TODO
            }
        }catch (SQLException e){
            request.setAttribute("error", "ID do post inválido.");
            request.getRequestDispatcher("/src/views/feed.jsp").forward(request, response);
        }

    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int threadId = 0;

        String content = request.getParameter("content");
        if(request.getParameter("threadId") != null && !request.getParameter("threadId").trim().isEmpty()){
            threadId = Integer.parseInt(request.getParameter("threadId"));
        }

        User user = utils.getUserFromSession(request);

        Post post = new Post();
        post.setContent(content);
        post.setUser(user);

        if(postDao.sendPost(post, threadId)){
            request.setAttribute("success", "Post created");
            utils.viewFeed(request, response, postDao);
        } else {
            System.out.println("ERRO");
        }

    }

    public void likePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer idPost = Integer.parseInt(request.getParameter("idPost"));
        User user = utils.getUserFromSession(request);

        if(postDao.likePost(idPost, user.getId())) {
            response.sendRedirect(request.getContextPath() + "/post?action=feed");
            //utils.viewFeed(request, response, postDao);
        }
    }
    public void dislikePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer idPost = Integer.parseInt(request.getParameter("idPost"));
        User user = utils.getUserFromSession(request);

        if(postDao.dislikePost(idPost, user.getId())) {
            response.sendRedirect(request.getContextPath() + "/post?action=feed");
            //utils.viewFeed(request, response, postDao);
        }
    }

    public void deletePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer idPost = Integer.parseInt(request.getParameter("idPost"));

        if(postDao.deletePost(idPost)) {
            utils.viewFeed(request, response, postDao);
        }
    }

    public void editPostContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer idPost = Integer.parseInt(request.getParameter("idPost"));
        String newContent = request.getParameter("newContent");

        if (postDao.editPostContent(idPost, newContent)) {
            utils.viewFeed(request, response, postDao);
        }
    }

}