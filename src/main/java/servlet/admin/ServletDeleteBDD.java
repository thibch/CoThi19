package servlet.admin;

import beans.UserBean;
import connexionSQL.SQLConnector;
import exception.ExceptionCoThi19;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletDeleteBDD")
public class ServletDeleteBDD extends HttpServlet {

    public static final String VUE_INSCRIPTION = "/inscription.jsp";
    public static final String VUE_ADMIN = "/keskecer.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString, table;
        UserBean user = (UserBean) request.getSession().getAttribute("userConnected");
        String redirect = response.encodeRedirectURL(request.getContextPath() + "/keskecer");
        int id;
        if (request.getParameter("idDeleteUser") != null) {
            idString = request.getParameter("idDeleteUser");
            id = Integer.parseInt(idString);
            table = "User";
        }else if (request.getParameter("idDeleteActivity") != null) {
            idString = request.getParameter("idDeleteActivity");
            id = Integer.parseInt(idString);
            table = "Activity";
        }else if (request.getParameter("idDeletePlace") != null) {
            idString = request.getParameter("idDeletePlace");
            id = Integer.parseInt(idString);
            table = "Place";
        }else {
            idString = request.getParameter("idDeleteNotif");
            id = Integer.parseInt(idString);
            table = "Notification";
        }
        SQLConnector sql = SQLConnector.getInstance();

        try {
            switch (table){
                case "User" :
                    List<String> errorMessageUser = new ArrayList<>();
                    ResultSet resultSet = sql.doRequest("Select * from User WHERE id_user = \"" + id + "\"");
                    if (resultSet.next()) {
                        int deletedUser = sql.doInsert("Delete from User WHERE id_user = \"" + id + "\"");
                        int deletedActivity = sql.doInsert("Delete from Activity WHERE id_user = \"" + id + "\"");
                        int deletedNotif = sql.doInsert("Delete from Notification WHERE id_receive = \"" + id + "\"");
                        if (deletedUser == 1 && deletedActivity == 1 && deletedNotif == 1 && resultSet.getString("email").equals(user.getMail())) {
                            request.getSession().invalidate();
                            request.setAttribute("errorMessageUser", errorMessageUser);
                            this.getServletContext().getRequestDispatcher(VUE_INSCRIPTION).forward(request, response);
                        } else if (deletedUser == 1 && deletedActivity == 1 && deletedNotif == 1 && !resultSet.getString("email").equals(user.getMail())){
                            errorMessageUser.add("Un problème est survenu lors de la suppression de l'utilisateur");
                            request.setAttribute("errorMessageUser", errorMessageUser);
                            this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                        } else{
                            errorMessageUser.add("Un problème est survenu lors de la suppression de l'utilisateur");
                            request.setAttribute("errorMessageUser", errorMessageUser);
                            this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                        }
                    }else{
                        request.setAttribute("errorMessageUser", errorMessageUser);
                        this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                    }
                    break;
                case "Activity" :
                    List<String> errorMessageAct = new ArrayList<>();
                    int deletedActivity = sql.doInsert("Delete from Activity WHERE id_act = \"" + id + "\"");
                    if (deletedActivity == 1) {
                        request.setAttribute("errorMessageAct", errorMessageAct);
                        this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                    } else {
                        errorMessageAct.add("Un problème est survenu lors de la suppression de l'activité");
                        request.setAttribute("errorMessageAct", errorMessageAct);
                        this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                    }
                    break;
                case "Place" :
                    List<String> errorMessagePlace = new ArrayList<>();
                    int deletedPlace = sql.doInsert("Delete from Place WHERE id_place = \"" + id + "\"");
                    deletedActivity = sql.doInsert("Delete from Activity WHERE id_place = \"" + id + "\"");
                    if (deletedPlace == 1 && deletedActivity == 1) {
                        request.setAttribute("errorMessagePlace", errorMessagePlace);
                        this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                    } else {
                        errorMessagePlace.add("Un problème est survenu lors de la suppression de l'activité");
                        request.setAttribute("errorMessagePlace", errorMessagePlace);
                        this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                    }
                    break;
                case "Notification" :
                    List<String> errorMessageNotif = new ArrayList<>();
                    int deletednotif = sql.doInsert("Delete from Place WHERE id_notif = \"" + id + "\"");
                    if (deletednotif == 1) {
                        request.setAttribute("errorMessageNotif", errorMessageNotif);
                        this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                    } else {
                        errorMessageNotif.add("Un problème est survenu lors de la suppression de l'activité");
                        request.setAttribute("errorMessageNotif", errorMessageNotif);
                        this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                    }
                    break;
            }
        } catch (ExceptionCoThi19 | SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
