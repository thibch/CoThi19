package servlet.notif;

import beans.NotificationBean;
import beans.UserBean;
import connexionSQL.SQLConnector;
import exception.ExceptionRequeteSQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class ServletNotif extends HttpServlet {

    private static final String ATT_SESSION_USER = "userConnected";
    public static final String VUE = "/consultNotifs.jsp";
    public static final String VUE_404 = "/404notfound.html";

    public ServletNotif(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public static boolean delNotif(int notif, UserBean usr){
        if(usr != null){
            try {
                SQLConnector.getInstance().delNotif(notif, usr);
                return true;
            } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
                System.err.println("ERREUR Notification : " + exceptionRequeteSQL.getMessage());
            }
        }
        return false;
    }

    public static boolean setNotifAsSeen(Collection<NotificationBean> notifs){
        if(notifs != null){
            try {
                SQLConnector.getInstance().setNotifAsSeen(notifs);
                return true;
            } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
                System.err.println("ERREUR Notification : " + exceptionRequeteSQL.getMessage());
            }
        }
        return false;
    }

    public static Collection<NotificationBean> getNotif(UserBean usr, int max){
        try {
            if(usr != null){
                return SQLConnector.getInstance().getListeNotif(usr.getMail(), max);
            }
        } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
            System.err.println("ERREUR Notification : " + exceptionRequeteSQL.getMessage());
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        UserBean usr = (UserBean) session.getAttribute(ATT_SESSION_USER);
        if(usr != null){
            if(req.getParameter("wantToDelete") != null && req.getParameter("wantToDelete").matches("^[0-9]+$")){
                if(delNotif(Integer.parseInt(req.getParameter("wantToDelete")), usr)){
                    System.out.println("Notification successfully deleted");
                }
            }


            Collection<NotificationBean> notifs = getNotif(usr, 100);

            if(notifs != null){
                if(!setNotifAsSeen(notifs)){
                    System.out.println("Ne peux pas mettre à jour");
                }
            }

            req.setAttribute("notifs", notifs);

            this.getServletContext().getRequestDispatcher("/consultNotifs.jsp").forward(req, resp);
        }else{
            System.err.println("redirect Vue 404");
            this.getServletContext().getRequestDispatcher(VUE_404).forward(req, resp);
        }
    }
}
