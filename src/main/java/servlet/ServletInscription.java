package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Roberge-Mentec Corentin
 */

public class ServletInscription extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        boolean verification = true;
        if (request.getParameter("mail").equals("")){
            verification = false;
        }
        if (request.getParameter("name").equals("")){
            verification = false;
        }
        if (request.getParameter("surname").equals("")){
            verification = false;
        }
        if (request.getParameter("password").equals("")){
            verification = false;
        }
        if (request.getParameter("birthday").equals("")){
            verification = false;
        }
        if (verification){
            response.sendRedirect(request.getContextPath() + "/test");
        }else{
            response.sendRedirect(request.getContextPath() + "/inscription.jsp");
        }
    }
}
