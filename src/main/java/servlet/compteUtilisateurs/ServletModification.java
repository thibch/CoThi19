package servlet.compteUtilisateurs;

import beans.UserBean;
import servlet.notif.ServletNotif;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletModification extends HttpServlet {

    public static final String VUE = "/modification.jsp";

    public ServletModification(){
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("notifs", ServletNotif.getNotif((UserBean)request.getSession().getAttribute("userConnected"), 100));
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}
