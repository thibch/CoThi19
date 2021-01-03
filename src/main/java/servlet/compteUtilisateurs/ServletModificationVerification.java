package servlet.compteUtilisateurs;

import connexionSQL.SQLConnector;
import exception.ExceptionCoThi19;
import beans.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ServletModificationVerification")
public class ServletModificationVerification extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idUser, name, surname, password, newPassword, confirmedPassword, birthDateString, isAdmin;
        String pathPicture, idAct, idPlace, idNotif, dateString, hourEnd, hourStart, namePlace, adress;
        String gpsCoordinates, content;
        String bdName, bdSurname, bdPassword, bdBirthdate, bdIsAdmin, bdPathPicture, bdContent;
        String bdDate, bdHourEnd, bdHourStart, bdAdress, bdGpsCooridnates;

        System.out.println("id act : "+request.getParameter("id_activity"));

        idUser = request.getParameter("id_user");
        idAct = request.getParameter("id_activity");
        idPlace = request.getParameter("id_place");
        idNotif = request.getParameter("id_notif");
        name = request.getParameter("name");
        surname = request.getParameter("surname");
        password = request.getParameter("password");
        newPassword = request.getParameter("newPassword");
        confirmedPassword = request.getParameter("confirmedPassword");
        birthDateString = request.getParameter("birthDate");
        dateString = request.getParameter("actDate");
        isAdmin = request.getParameter("isAdmin");
        hourEnd = request.getParameter("hourEnd");
        hourStart = request.getParameter("hourStart");
        namePlace = request.getParameter("namePlace");
        adress = request.getParameter("adress");
        gpsCoordinates = request.getParameter("gpsCoordinates");
        content = request.getParameter("content");
        pathPicture = request.getParameter("pathPicture");

        Date birthDate = null, date = null;

        System.out.println(dateString);

        if (birthDateString != null && !birthDateString.equals("")) {
            birthDate = Date.valueOf(birthDateString);
        }
        if (dateString != null && !dateString.equals("")) {
            date = Date.valueOf(dateString);
        }

        UserBean user = (UserBean) request.getSession().getAttribute("userConnected");

        SQLConnector sql = SQLConnector.getInstance();

        switch (request.getParameter("form")) {
            case "userForm":
                /* Ici on vérifie que la modification se fait par La table utilisateur admin */
                try {
                    ResultSet resultSet = sql.doRequest("Select * from User WHERE id_user = \"" + idUser + "\"");
                    if (resultSet.next()) {
                        bdPassword = resultSet.getString("password");
                        bdName = resultSet.getString("name");
                        bdSurname = resultSet.getString("surname");
                        bdBirthdate = resultSet.getString("birth_date");
                        bdIsAdmin = resultSet.getString("isAdmin");
                        bdPathPicture = resultSet.getString("path_picture");
                    } else {
                        bdName = "";
                        bdSurname = "";
                        bdPassword = "";
                        bdBirthdate = "";
                        bdIsAdmin = "";
                        bdPathPicture = "";
                    }
                    if (!bdName.equals(name) && !name.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE User SET name = \"" + name + "\" where id_user = \"" + idUser + "\";");
                    }
                    if (!bdSurname.equals(surname) && !surname.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE User SET surname = \"" + surname + "\" where id_user = \"" + idUser + "\";");
                    }
                    if (!bdBirthdate.equals(birthDateString) && !birthDateString.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE User SET birth_date = '" + birthDate + "' where id_user = \"" + idUser + "\";");
                    }
                    if (!bdPassword.equals(password) && !password.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE User SET password = \"" + password + "\" where id_user = \"" + idUser + "\";");
                    }
                    if (!bdIsAdmin.equals(isAdmin) && !isAdmin.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE User SET isAdmin = \"" + isAdmin + "\" where id_user = \"" + idUser + "\";");
                    }
                    if (bdPathPicture == null) {
                        bdPathPicture = "NULL";
                    }
                    if (!bdPathPicture.equals(pathPicture) && !pathPicture.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE User SET path_picture = \"" + pathPicture + "\" where id_user = \"" + idUser + "\";");
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                response.sendRedirect(request.getContextPath() + "/keskecer");
                break;
            case "activityForm":
                /* Ici on vérifie que la modification se fait par La table Activity admin */
                try {
                    ResultSet resultSet = sql.doRequest("Select * from Activity WHERE id_act = " + idAct + ";");
                    if (resultSet.next()) {
                        bdDate = resultSet.getString("date");
                        bdHourEnd = resultSet.getString("hourEnd");
                        bdHourStart = resultSet.getString("hourStart");
                    } else {
                        bdDate = "";
                        bdHourEnd = "";
                        bdHourStart = "";
                    }
                    if (!bdDate.equals(dateString) && !dateString.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE Activity SET date = \"" + date + "\" where id_act = \"" + idAct + "\";");
                    }
                    if (!bdHourEnd.equals(hourEnd) && !hourEnd.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE Activity SET hourEnd = \"" + hourEnd + "\" where id_act = \"" + idAct + "\";");
                    }
                    if (!bdHourStart.equals(hourStart) && !hourStart.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE Activity SET hourStart = \"" + hourStart + "\" where id_act = \"" + idAct + "\";");
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                response.sendRedirect(request.getContextPath() + "/keskecer");
                break;
            case "placeForm":
                /* Ici on vérifie que la modification se fait par La table Place admin */
                try {
                    ResultSet resultSet = sql.doRequest("Select * from Place WHERE id_place = \"" + idPlace + "\"");
                    if (resultSet.next()) {
                        bdName = resultSet.getString("name");
                        bdAdress = resultSet.getString("adress");
                        bdGpsCooridnates = resultSet.getString("gps_coordinates");
                    } else {
                        bdName = "";
                        bdAdress = "";
                        bdGpsCooridnates = "";
                    }
                    if (!bdName.equals(namePlace) && !namePlace.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE Place SET name = \"" + namePlace + "\" where id_place = \"" + idPlace + "\";");
                    }
                    if (!bdAdress.equals(adress) && !adress.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE Place SET adress = \"" + adress + "\" where id_place = \"" + idPlace + "\";");
                    }
                    if (!bdGpsCooridnates.equals(gpsCoordinates) && !gpsCoordinates.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE Place SET gps_coordinates = \"" + gpsCoordinates + "\" where id_place = \"" + idPlace + "\";");
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                response.sendRedirect(request.getContextPath() + "/keskecer");
                break;

            case "notifForm":
                /* Ici on vérifie que la modification se fait par La table Notification admin */
                try {
                    ResultSet resultSet = sql.doRequest("Select * from Notification WHERE id_notif = \"" + idNotif + "\"");
                    if (resultSet.next()) {
                        bdContent = resultSet.getString("content");
                    } else {
                        bdContent = "";
                    }
                    if (!bdContent.equals(content) && !content.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE Notification SET content = \"" + content + "\" where id_notif = \"" + idNotif + "\";");
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                response.sendRedirect(request.getContextPath() + "/keskecer");
                break;

            default:
                /* Ici on vérifie que la modification se fait par le compte utilisateur */
                try {
                    ResultSet resultSet = sql.doRequest("Select * from User WHERE email = \"" + user.getMail() + "\"");
                    if (resultSet.next()) {
                        bdName = resultSet.getString("name");
                        bdSurname = resultSet.getString("surname");
                        bdPassword = resultSet.getString("password");
                        bdBirthdate = resultSet.getString("birth_date");
                    } else {
                        bdName = "";
                        bdSurname = "";
                        bdPassword = "";
                        bdBirthdate = "";
                    }
                    if (!bdName.equals(name) && !name.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE User SET name = \"" + name + "\" where email = \"" + user.getMail() + "\";");
                    }
                    if (!bdSurname.equals(surname) && !surname.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE User SET surname = \"" + surname + "\" where email = \"" + user.getMail() + "\";");
                    }
                    if (!bdBirthdate.equals(birthDateString) && !birthDateString.equals("")) {
                        int resultInsertSet = sql.doInsert("UPDATE User SET birth_date = '" + birthDate + "' where email = \"" + user.getMail() + "\";");
                    }
                    if (bdPassword.equals(password) && !password.equals("")) {
                        if (!newPassword.equals(password) && !newPassword.equals("")) {
                            if (confirmedPassword.equals(newPassword)) {
                                int resultInsertSet = sql.doInsert("UPDATE User SET password = \"" + confirmedPassword + "\" where email = \"" + user.getMail() + "\";");
                            }
                        }
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                response.sendRedirect(request.getContextPath() + "/monCompte");
                break;
        }
    }
}
