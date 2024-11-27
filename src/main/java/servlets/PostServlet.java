package servlets;

import java.io.*;
import java.util.Optional;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;
import model.dao.PostDao;
import model.dao.RequestDao;
import model.dao.UserDao;
import model.entity.Post;
import model.entity.User;
import utils.DataSourceSearcher;
import utils.Utils;

@WebServlet("/post")
public class PostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    PostDao postDao;
    UserDao userDao;
    RequestDao requestDao;
    Utils utils;

    public PostServlet() {
        super();
        this.postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
        this.userDao = new UserDao(DataSourceSearcher.getInstance().getDataSource());
        this.requestDao = new RequestDao(DataSourceSearcher.getInstance().getDataSource());
        this.utils = new Utils();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        chooseOption(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        chooseOption(req, resp);
    }

    private void chooseOption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            case "deslike":
                deslikePost(request, response);
                break;
            case null, default:
                utils.viewFeed(request, response, postDao);
                break;
        }
    }

    private void viewPostById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idParam = request.getParameter("id");
        Optional<Post> post = Optional.empty();

        try{
            if(idParam != null && !idParam.isEmpty()){
                Integer id = Integer.parseInt(idParam);
                post = postDao.getPostById(id);
            }

            if(post.isPresent()){
                request.setAttribute("post", post.get());
                request.getRequestDispatcher("/src/views/profile.jsp").forward(request, response);
            } else {
                //TODO
            }
        }catch (NumberFormatException e){
            request.setAttribute("error", "ID do post inválido.");
            //TODO
            request.getRequestDispatcher("/src/views/feed.jsp").forward(request, response);
        }

    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
            request.getRequestDispatcher("/src/views/feed.jsp").forward(request, response);
            System.out.println("Executou");
        } else {
            System.out.println("ERRO");
        }

    }

    public Boolean likePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Integer idPost = Integer.parseInt(request.getParameter("idPost"));
        User user = utils.getUserFromSession(request);

        return postDao.likePost(idPost, user.getId());
    }
    public Boolean deslikePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Integer idPost = Integer.parseInt(request.getParameter("idPost"));
        User user = utils.getUserFromSession(request);

        return postDao.deslikePost(idPost, user.getId());
    }



}