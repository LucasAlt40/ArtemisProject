package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.utils.LocalDateTypeAdapter;
import servlets.helpers.Helper;
import servlets.helpers.HelperFactory;

@WebServlet("/ControllerServlet")
public class FrontControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public FrontControllerServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if ((session == null || session.getAttribute("user") == null)
                && (req.getParameter("action") == null || !req.getParameter("action").equals("signIn"))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            Helper helper = new HelperFactory().getHelper(req);
            try {
                Object response = helper.execute(req, resp);
                if (response instanceof JsonObject) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(response.toString());
                } else if (response instanceof List) {
                    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                            .create();
                    resp.setContentType("application/json");
                    resp.getWriter().write(gson.toJson(response));
                } else {
                    RequestDispatcher dispatcher = req.getRequestDispatcher(response.toString());
                    dispatcher.forward(req, resp);
                }
            } catch (Exception error) {
                throw new ServletException(error);
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}