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
        String user_idParam = request.getParameter("user_id");
        Optional<Post> post = Optional.empty();
        List<Post> posts = new ArrayList<>();

        try{
            if(idParam != null && !idParam.isEmpty()){
                Integer id = Integer.parseInt(idParam);
                post = postDao.getPostById(id);
            }else if(user_idParam != null && !user_idParam.isEmpty()){
                Integer user_id = Integer.parseInt(user_idParam);
                posts = postDao.getPostsByUserId(user_id);
            }

            if(post.isPresent()){
                request.setAttribute("post", post.get());
                request.getRequestDispatcher("/pages/profile/postView.jsp").forward(request, response);
            }
        }catch (NumberFormatException e){
            request.setAttribute("error", "ID do post inválido.");
            //TODO
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }

    }


}