package servlet;

import beans.ActiviteBean;
import beans.NotificationBean;
import beans.UserBean;
import connexionSQL.SQLConnector;
import exception.ExceptionRequeteSQL;
import servlet.notif.ServletNotif;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ServletAccueil extends HttpServlet {

    public static final String VUE = "/bienvenue.jsp";

    public ServletAccueil(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        UserBean usr = (UserBean) req.getSession().getAttribute("userConnected");
        if(usr != null){
            req.setAttribute("notifs", ServletNotif.getNotif(usr, 100));

            req.setAttribute("activities", getActivities(usr, 9, 10));
        }
        this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
    }

    public static Collection<ActiviteBean> getActivities(UserBean usr, int maxNumber, int maxDate){
        try {
            return SQLConnector.getInstance().getActivity(usr, maxNumber, maxDate);
        } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
            System.err.println(exceptionRequeteSQL.getMessage());
        }
        return new ArrayList<>();
    }
}
