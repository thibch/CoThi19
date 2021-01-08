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
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletModificationVerification")
public class ServletModificationVerification extends HttpServlet {

    public ServletModificationVerification(){
        super();
    }

    public static final String VUE_USER = "/modification.jsp";
    public static final String VUE_ADMIN = "/keskecer.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idUser, name, surname, password, newPassword, confirmedPassword, birthDateString, isAdmin, isInfected, seen;
        String pathPicture, idAct, idPlace, idNotif, dateString, hourEnd, hourStart, namePlace, adress, minuteStart, minuteEnd, bdSeen;
        String gpsCoordinates, content;
        String bdName, bdSurname, bdPassword, bdBirthdate, bdIsAdmin, bdPathPicture, bdContent, bdIsInfected;
        String bdDate, bdHourEnd, bdHourStart, bdAdress, bdGpsCooridnates;

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
        isInfected = request.getParameter("isInfected");
        minuteEnd = request.getParameter("minuteEnd");
        minuteStart = request.getParameter("minuteStart");
        seen = request.getParameter("seen");

        Date birthDate = null, date = null;


        if (birthDateString != null && !birthDateString.equals("")) {
            birthDate = Date.valueOf(birthDateString);
        }
        if (dateString != null && !dateString.equals("")) {
            date = Date.valueOf(dateString);
        }

        UserBean user = (UserBean) request.getSession().getAttribute("userConnected");
        request.setAttribute("notifs", ServletNotif.getNotif(user, 100));

        SQLConnector sql = SQLConnector.getInstance();

        switch (request.getParameter("form")) {
            case "userForm":
                /* Ici on vérifie que la modification se fait par La table utilisateur admin */
                List<String> errorMessageUser = new ArrayList<>();
                try {
                    ResultSet resultSet = sql.doRequest("Select * from User WHERE id_user = \"" + idUser + "\"");
                    if (resultSet.next()) {
                        bdPassword = resultSet.getString("password");
                        bdName = resultSet.getString("name");
                        bdSurname = resultSet.getString("surname");
                        bdBirthdate = resultSet.getString("birth_date");
                        bdIsAdmin = resultSet.getString("isAdmin");
                        bdPathPicture = resultSet.getString("path_picture");
                        bdIsInfected = resultSet.getString("isinfected");
                    } else {
                        bdName = "";
                        bdSurname = "";
                        bdPassword = "";
                        bdBirthdate = "";
                        bdIsAdmin = "";
                        bdPathPicture = "";
                        bdIsInfected = "";
                    }
                    if (!bdName.equals(name)) {
                        if (!name.equals("")) {
                            int resultInsertSet = sql.doInsert("UPDATE User SET name = \"" + name + "\" where id_user = \"" + idUser + "\";");
                        } else {
                            errorMessageUser.add("Le champs name est vide");
                        }
                    }
                    if (!bdSurname.equals(surname)) {
                        if (!surname.equals("")) {
                            int resultInsertSet = sql.doInsert("UPDATE User SET surname = \"" + surname + "\" where id_user = \"" + idUser + "\";");
                        } else {
                            errorMessageUser.add("Le champs surname est vide");
                        }
                    }
                    java.util.Date dateMax = new java.util.Date();
                    if (!bdBirthdate.equals(birthDateString)) {
                        if (!birthDateString.equals("")) {
                            if (birthDate.before(dateMax)) {
                                int resultInsertSet = sql.doInsert("UPDATE User SET birth_date = '" + birthDate + "' where id_user = \"" + idUser + "\";");
                            } else {
                                errorMessageUser.add("Le champs birthDate est dans le futur");
                            }
                        }else{
                            errorMessageUser.add("Le champs birthDate est vide");
                        }
                    }
                    if (!bdPassword.equals(password)) {
                        if (!password.equals("")) {
                            int resultInsertSet = sql.doInsert("UPDATE User SET password = \"" + password + "\" where id_user = \"" + idUser + "\";");
                        } else {
                            errorMessageUser.add("Le champs password est vide");
                        }
                    }
                    if (!bdIsAdmin.equals(isAdmin)) {
                        if( !isAdmin.equals("")) {
                            if (Integer.parseInt(isAdmin) == 0 && Integer.parseInt(isAdmin) == 1) {
                                int resultInsertSet = sql.doInsert("UPDATE User SET isAdmin = " + isAdmin + " where id_user = \"" + idUser + "\";");
                            }else{
                                errorMessageUser.add("Le champs isAdmin doit être compris entre 0 et 1");
                            }
                        }else{
                            errorMessageUser.add("Le champs isAdmin est vide");
                        }
                    }
                    if (bdPathPicture == null) {
                        bdPathPicture = "NULL";
                    }
                    if (!bdPathPicture.equals(pathPicture)) {
                        if (!pathPicture.equals("")) {
                            int resultInsertSet = sql.doInsert("UPDATE User SET path_picture = \"" + pathPicture + "\" where id_user = \"" + idUser + "\";");
                        } else {
                            errorMessageUser.add("Le champs pathPicture est vide");
                        }
                    }
                    if (!bdIsInfected.equals(isInfected)) {
                        if (!isInfected.equals("")){
                            if (Integer.parseInt(isInfected) == 0 && Integer.parseInt(isInfected) == 1) {
                                int resultInsertSet = sql.doInsert("UPDATE User SET isInfected = " + isInfected + " where id_user = \"" + idUser + "\";");
                            }else{
                                errorMessageUser.add("Le champs isInfected doit être compris entre 0 et 1");
                            }
                        }else{
                            errorMessageUser.add("Le champs isInfected est vide");
                        }
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                request.setAttribute("errorMessageUser", errorMessageUser);
                this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                break;
            case "activityForm":
                /* Ici on vérifie que la modification se fait par La table Activity admin */
                List<String> errorMessageAct = new ArrayList<>();

                Time start, end;
                LocalTime startTime = LocalTime.of(Integer.parseInt(hourStart), Integer.parseInt(minuteStart), 0, 0);
                LocalTime endTime = LocalTime.of(Integer.parseInt(hourEnd), Integer.parseInt(minuteEnd), 0, 0);

                start = Time.valueOf(startTime);
                end = Time.valueOf(endTime);

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
                    if (!bdDate.equals(dateString)) {
                        if (!dateString.equals("")) {
                            int resultInsertSet = sql.doInsert("UPDATE Activity SET date = \"" + date + "\" where id_act = \"" + idAct + "\";");
                        }else{
                            errorMessageAct.add("Le champs date est vide");
                        }
                    }
                    if (!bdHourEnd.equals(hourEnd)) {
                        if (!hourEnd.equals("")) {
                            if (end.after(start)) {
                                int resultInsertSet = sql.doInsert("UPDATE Activity SET hourEnd = \"" + end + "\" where id_act = \"" + idAct + "\";");
                            }else{
                                errorMessageAct.add("Le début est dans le future");
                            }
                        }else{
                            errorMessageAct.add("Le champs date est vide");
                        }
                    }
                    if (!bdHourStart.equals(hourStart)) {
                        if (!hourStart.equals("")) {
                            if (start.after(end)) {
                                int resultInsertSet = sql.doInsert("UPDATE Activity SET hourStart = \"" + start + "\" where id_act = \"" + idAct + "\";");
                            }else {
                                errorMessageAct.add("La fin est dans le passé");
                            }
                        } else{
                            errorMessageAct.add("Le champs date est vide");
                        }
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                request.setAttribute("errorMessageAct", errorMessageAct);
                this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                break;
            case "placeForm":
                List<String> errorMessagePlace = new ArrayList<>();
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
                    if (!bdName.equals(namePlace)) {
                        if (!namePlace.equals("")) {
                            int resultInsertSet = sql.doInsert("UPDATE Place SET name = \"" + namePlace + "\" where id_place = \"" + idPlace + "\";");
                        }else{
                            errorMessagePlace.add("Le champs name est vide");
                        }
                    }
                    if (!bdAdress.equals(adress)) {
                        if (!adress.equals("")) {
                            int resultInsertSet = sql.doInsert("UPDATE Place SET adress = \"" + adress + "\" where id_place = \"" + idPlace + "\";");
                        }else{
                            errorMessagePlace.add("Le champs adress est vide");
                        }
                    }
                    if (!bdGpsCooridnates.equals(gpsCoordinates)) {
                        if (!gpsCoordinates.equals("")) {
                            int resultInsertSet = sql.doInsert("UPDATE Place SET gps_coordinates = \"" + gpsCoordinates + "\" where id_place = \"" + idPlace + "\";");
                        }else{
                            errorMessagePlace.add("Le champs gpsCoordinates est vide");
                        }
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                request.setAttribute("errorMessagePlace", errorMessagePlace);
                this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                break;

            case "notifForm":
                /* Ici on vérifie que la modification se fait par La table Notification admin */
                List<String> errorMessageNotif = new ArrayList<>();
                try {
                    ResultSet resultSet = sql.doRequest("Select * from Notification WHERE id_notif = \"" + idNotif + "\"");
                    if (resultSet.next()) {
                        bdContent = resultSet.getString("content");
                        bdSeen = resultSet.getString("bdSeen");
                    } else {
                        bdContent = "";
                        bdSeen = "";
                    }
                    if (!bdContent.equals(content)) {
                        if (!content.equals("")) {
                            int resultInsertSet = sql.doInsert("UPDATE Notification SET content = \"" + content + "\" where id_notif = \"" + idNotif + "\";");
                        }else{
                            errorMessageNotif.add("Le champs content est vide");
                        }
                    }
                    if (!bdSeen.equals(seen)){
                        if (!seen.equals("")){
                            if (Integer.parseInt(seen) == 0 || Integer.parseInt(seen) == 1){
                                int resultInsertSet = sql.doInsert("UPDATE Notification SET seen = \"" + Integer.parseInt(seen) + "\" where id_notif = \"" + idNotif + "\";");
                            }else{
                                errorMessageNotif.add("Le champs seen doit être compris entre 0 et 1");
                            }
                        }else{
                            errorMessageNotif.add("Le champs seen est vide");
                        }
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                request.setAttribute("errorMessageNotif", errorMessageNotif);
                this.getServletContext().getRequestDispatcher(VUE_ADMIN).forward(request, response);
                break;

            default:
                /* Ici on vérifie que la modification se fait par le compte utilisateur */
                List<String> errorMessage = new ArrayList<>();
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

                    java.util.Date dateMax = new java.util.Date();

                    if (!bdBirthdate.equals(birthDateString) && !birthDateString.equals("")) {
                        if (birthDate.before(dateMax)) {
                            int resultInsertSet = sql.doInsert("UPDATE User SET birth_date = '" + birthDate + "' where email = \"" + user.getMail() + "\";");
                        }else{
                            errorMessage.add("Veuillez choisir une date de naissance antérieure à al date actuelle.");
                        }
                    }
                    if (!password.equals("")) {
                        if (bdPassword.equals(password)) {
                            if (!newPassword.equals("")) {
                                if (!newPassword.equals(password)) {
                                    if (confirmedPassword.equals(newPassword)) {
                                        int resultInsertSet = sql.doInsert("UPDATE User SET password = \"" + confirmedPassword + "\" where email = \"" + user.getMail() + "\";");
                                    } else {
                                        errorMessage.add("Le nouveau mot de passe et le mot de passe de confirmation sont différents.");
                                    }
                                } else {
                                    errorMessage.add("Le nouveau mot de passe entré n'est pas différents du précédent .");
                                }
                            }else{
                                errorMessage.add("Le nouveau mot de passe entré est vide.");
                            }
                        }else{
                            errorMessage.add("Le mot de passe actuel entré n'est pas le bon .");
                        }
                    }
                } catch (SQLException | ExceptionCoThi19 throwables) {
                    throwables.printStackTrace();
                }
                request.setAttribute("errorMessage", errorMessage);
                this.getServletContext().getRequestDispatcher(VUE_USER).forward(request, response);
                break;
        }
    }
}
