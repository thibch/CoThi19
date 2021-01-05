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

@WebServlet(name = "ServletDeleteBDD")
public class ServletDeleteBDD extends HttpServlet {
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
                    ResultSet resultSet = sql.doRequest("Select * from User WHERE id_user = \"" + id + "\"");
                    if (resultSet.next()) {
                        int deletedUser = sql.doInsert("Delete from User WHERE id_user = \"" + id + "\"");
                        int deletedActivity = sql.doInsert("Delete from Activity WHERE id_user = \"" + id + "\"");
                        int deletedNotif = sql.doInsert("Delete from Notification WHERE id_receive = \"" + id + "\"");
                        if (deletedUser == 1 && deletedActivity == 1 && deletedNotif == 1 && resultSet.getString("email").equals(user.getMail())) {
                            request.getSession().invalidate();
                            response.sendRedirect(request.getContextPath() + "/inscription");
                        } else if (deletedUser == 1 && deletedActivity == 1 && deletedNotif == 1 && !resultSet.getString("email").equals(user.getMail())){
                            response.sendRedirect(request.getContextPath() + "/keskecer");
                        } else{
                            response.sendRedirect(request.getContextPath() + "/keskecer");
                        }
                    }else{
                        response.sendRedirect(request.getContextPath() + "/keskecer");
                    }
                    break;
                case "Activity" :
                    int deletedActivity = sql.doInsert("Delete from Activity WHERE id_act = \"" + id + "\"");
                    if (deletedActivity == 1) {
                        response.sendRedirect(request.getContextPath() + "/keskecer");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/keskecer");
                    }
                    break;
                case "Place" :
                    int deletedPlace = sql.doInsert("Delete from Place WHERE id_place = \"" + id + "\"");
                    deletedActivity = sql.doInsert("Delete from Activity WHERE id_place = \"" + id + "\"");
                    if (deletedPlace == 1 && deletedActivity == 1) {
                        response.sendRedirect(request.getContextPath() + "/keskecer");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/keskecer");
                    }
                    break;
                case "Notification" :
                    int deletednotif = sql.doInsert("Delete from Place WHERE id_notif = \"" + id + "\"");
                    if (deletednotif == 1) {
                        response.sendRedirect(request.getContextPath() + "/keskecer");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/keskecer");
                    }
                    break;
            }
        } catch (ExceptionCoThi19 | SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
