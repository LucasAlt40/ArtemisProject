package servlets.helpers;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.dao.PostDao;
import model.entity.Post;
import model.entity.User;
import utils.DataSourceSearcher;
import utils.FtpClient;

public class AddPostHelper implements Helper{
    @Override
    public Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int threadId = 0;

        String content = req.getParameter("content");
        Part filePart = req.getPart("imagePost");


        if(req.getParameter("threadId") != null && !req.getParameter("threadId").trim().isEmpty()){
            threadId = Integer.parseInt(req.getParameter("threadId"));
        }

        User user = (User) req.getSession().getAttribute("user");

        PostDao postDao = new PostDao(DataSourceSearcher.getInstance().getDataSource());


        Post post = new Post();
        post.setContent(content);
        post.setUser(user);

        System.out.println(filePart.getSubmittedFileName());

        if(postDao.sendPost(post, threadId)){
            if(filePart.getSubmittedFileName() != null || !filePart.getSubmittedFileName().isEmpty()) {
                postDao.uploadPostImage(FtpClient.Upload(filePart).fileName(), postDao.getLastSequenceValue());
            }
            req.setAttribute("success", "Post created");

        }

        return "ControllerServlet?action=feed";
    }
}
