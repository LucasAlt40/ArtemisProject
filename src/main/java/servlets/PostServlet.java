package servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;
import model.dao.PostDao;
import model.entity.Post;
import utils.DataSourceSearcher;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    PostDao postDao;

    public PostServlet() {
        super();
        this.postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());
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
            case "view":
                viewPost(request, response);
                break;
            case null, default:
                break;
        }
    }

    private void viewPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idParam = request.getParameter("id");
        Optional<Post> post = Optional.empty();

        try{
            if(idParam != null && !idParam.isEmpty()){
                Integer id = Integer.parseInt(idParam);
                post = postDao.getPostById(id);
            }

            if(post.isPresent()){
                request.setAttribute("post", post.get());
                request.getRequestDispatcher("/pages/profile/postView.jsp").forward(request, response);
            } else {
                //TODO
            }
        }catch (NumberFormatException e){
            request.setAttribute("error", "ID do post inválido.");
            //TODO
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }

    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //String sql = "INSERT INTO POST (CONTENT, POST_DATE, USER_ID, THREAD_ID) VALUES (?, ?, ?, ?)";
        String content = request.getParameter("content");
        Integer threadId = Integer.parseInt(request.getParameter("threadId"));
        Post post = new Post();
        post.setContent(content);

        if(postDao.sendPost(post, threadId)){
            request.setAttribute("success", "Post created");
            request.getRequestDispatcher("/pages/success.jsp").forward(request, response);
        }


    }


}