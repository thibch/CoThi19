package servlet;

import beans.NotificationBean;
import beans.UserBean;
import servlet.notif.ServletNotif;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class ServletAccueil extends HttpServlet {

    public static final String VUE = "/bienvenue.jsp";

    public ServletAccueil(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setAttribute("notifs", ServletNotif.getNotif((UserBean) req.getSession().getAttribute("userConnected"), 100));
        this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
