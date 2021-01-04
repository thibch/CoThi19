package servlet.not;

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
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        UserBean usr = (UserBean) session.getAttribute(ATT_SESSION_USER);
        if(usr != null){

            Collection<NotificationBean> notifs = getNotif(usr, 100);

            System.out.println(notifs);
            if(notifs != null){
                if(!setNotifAsSeen(notifs)){
                    System.out.println("Ne peux pas mettre à jour");
                }
            }

            req.setAttribute("notifs", notifs);

            if(req.getParameter("wantToDelete") != null && req.getParameter("wantToDelete").matches("^[0-9]+$")){

            }

            this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
        }else{
            System.out.println("WHY ?");
            this.getServletContext().getRequestDispatcher(VUE_404).forward(req, resp);
        }
    }

    public static boolean delNotif(int notif, UserBean usr){
        try {
            SQLConnector.getInstance().delNotif(notif, usr);
            return true;
        } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
            System.out.println(exceptionRequeteSQL.getMessage());
            System.out.println("ERREUR Notification : " + exceptionRequeteSQL.getMessage());
            return false;
        }
    }

    public static boolean setNotifAsSeen(Collection<NotificationBean> notifs){
        try {
            SQLConnector.getInstance().setNotifAsSeen(notifs);
            return true;
        } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
            System.out.println(exceptionRequeteSQL.getMessage());
            System.out.println("ERREUR Notification : " + exceptionRequeteSQL.getMessage());
            return false;
        }
    }

    public static Collection<NotificationBean> getNotif(UserBean usr, int max){
        try {
            return SQLConnector.getInstance().getListeNotif(usr.getMail(), max);
        } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
            System.out.println("ERREUR Notification : " + exceptionRequeteSQL.getMessage());
            return null;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
