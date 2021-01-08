package servlet.notif;

import beans.UserBean;
import connexionSQL.SQLConnector;
import exception.ExceptionRequeteSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletPositif extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean usr = (UserBean) req.getSession().getAttribute("userConnected");
        usr.isInfected();
        resp.sendRedirect(req.getContextPath() + "/accueil");
    }

    public static boolean setUsrPositiv(UserBean usr){
        try {
            return SQLConnector.getInstance().setUserPositive(usr);
        } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
            System.err.println(exceptionRequeteSQL.getMessage());
            return false;
        }
    }
}
