package servlet.compteUtilisateurs;

import connexionSQL.SQLConnector;
import exception.ExceptionCoThi19;
import beans.UserBean;
import servlet.notif.ServletNotif;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ServletConnexionVerification")
public class ServletConnexionVerification extends HttpServlet {

    private static final String ATT_SESSION_USER = "userConnected";
    private UserBean user;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean verification = true;
        boolean recuperation = true;
        int isAdmin = 0;
        String mail, password;
        mail = request.getParameter("mail");
        password = request.getParameter("password");

        if (mail.equals("")){
            verification = false;
        }
        if (password.equals("")){
            verification = false;
        }
        if (verification){
            SQLConnector sql = SQLConnector.getInstance(); // On récupère la connexion sql
            try {
                ResultSet resultIdSet = sql.doRequest("Select * from User WHERE email = \""+mail+"\" AND password = \""+password+"\"");
                if (resultIdSet.next()){
                    recuperation = true;
                    isAdmin = resultIdSet.getInt("isAdmin");
                }else{
                    System.out.println("Error");
                    recuperation = false;
                }
            } catch (SQLException | ExceptionCoThi19 throwables) {
                throwables.printStackTrace();
            }
            if (recuperation) {
                user = new UserBean(mail);
                user.setAdmin(isAdmin);
                HttpSession session = request.getSession();
                session.setAttribute( ATT_SESSION_USER, user);
                request.setAttribute("notifs", ServletNotif.getNotif(user, 100));
                response.sendRedirect(request.getContextPath() + "/accueil");
                String myString = "https://fr.pornhub.com/";
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }else{
                response.sendRedirect(request.getContextPath() + "/connexion");
            }
        }else{
            response.sendRedirect(request.getContextPath() + "/connexion");
        }
    }
}
