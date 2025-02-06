package servlets.helpers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

public class HelperFactory {

    public Helper getHelper(HttpServletRequest request) {
        String action = request.getParameter("action");
        ServletContext context = request.getServletContext();
        String className = context.getInitParameter(action);
        try {
            Class<?> clazz = Class.forName(className);
            return (Helper)clazz.getDeclaredConstructor().newInstance();
        }
        catch(Exception error) {
            throw new RuntimeException(error);
        }
    }

}