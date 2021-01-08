package servlet.admin;

import connexionSQL.SQLConnector;
import exception.ExceptionCoThi19;

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

@WebServlet(name = "ServletAddBDD")
public class ServletAddBDD extends HttpServlet {

    public ServletAddBDD(){
        super();
    }

    public static final String VUE = "/keskecer.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SQLConnector sql = SQLConnector.getInstance();
        String redirect = response.encodeRedirectURL(request.getContextPath() + "/keskecer");
        boolean verification = true;
        switch (request.getParameter("form")) {
            case "userForm":
                List<String> errorMessageUser = new ArrayList<>();
                String idUser, mail, name, surname, password, birthDateString, isAdmin, pathPicture, isInfected;
                idUser = request.getParameter("id_user");
                mail = request.getParameter("mail");
                name = request.getParameter("name");
                surname = request.getParameter("surname");
                password = request.getParameter("password");
                birthDateString = request.getParameter("birthDate");
                isAdmin = request.getParameter("isAdmin");
                pathPicture = request.getParameter("path_picture");
                isInfected = request.getParameter("isInfected");

                Date birthDate = null;
                if (!birthDateString.equals("")) {
                    birthDate = Date.valueOf(birthDateString);
                }

                if (idUser.equals("")){
                    verification = false;
                    errorMessageUser.add("Le champs de idUser est vide");
                }
                if (mail.equals("")){
                    verification = false;
                    errorMessageUser.add("Le champs de mail est vide");
                }
                if (name.equals("")){
                    verification = false;
                    errorMessageUser.add("Le champs de name est vide");
                }
                if (surname.equals("")){
                    verification = false;
                    errorMessageUser.add("Le champs de surnam est vide");
                }
                if (password.equals("")){
                    verification = false;
                    errorMessageUser.add("Le champs de password est vide");
                }

                java.util.Date dateMax = new java.util.Date();

                if (birthDateString.equals("")){
                    verification = false;
                    errorMessageUser.add("Le champs de birth_date est vide");
                }
                if (birthDate != null) {
                    if (birthDate.after(dateMax)) {
                        verification = false;
                        errorMessageUser.add("Le champs de birth_date est dans le future");
                    }
                }
                if (!isAdmin.equals("")){
                    if (Integer.parseInt(isAdmin) != 1 && Integer.parseInt(isAdmin) != 0){
                        verification = false;
                        errorMessageUser.add("Le champs de isAdmin doit être compris entre 0 et 1");
                    }
                }else{
                    verification = false;
                    errorMessageUser.add("Le champs de idAdmin est vide");
                }
                if (pathPicture.equals("")){
                    pathPicture = "NULL";
                }
                if (!isInfected.equals("")){
                    if (Integer.parseInt(isInfected) != 1 && Integer.parseInt(isInfected) != 0){
                        verification = false;
                        errorMessageUser.add("Le champs de isInfected doit être compris entre 0 et 1");
                    }
                }else{
                    verification = false;
                    errorMessageUser.add("Le champs de idInfected est vide");
                }

                if (verification){
                    int i;
                    boolean idCorresponding = false;
                    try {
                        ResultSet resultIdSet = sql.doRequest("Select * from User");
                        if (resultIdSet.next()){
                            i = resultIdSet.getInt("id_user"); // On récupère le résultat
                            if (String.valueOf(i).equals(idUser)){
                                idCorresponding = true;
                            }else{
                                System.err.println("Error");
                            }
                        }
                        if(!idCorresponding) {
                            int resultInsertSet = sql.doInsert("INSERT INTO User (id_user, email, password, name, surname, birth_date, isAdmin, path_picture, isInfected) VALUES (" + Integer.parseInt(idUser) + ", \"" + mail + "\", \"" + password + "\", \"" + name + "\", \"" + surname + "\", '" + birthDate + "', " + Integer.parseInt(isAdmin) + ", " + pathPicture + ", " + Integer.parseInt(isInfected) + ");");
                            if (resultInsertSet == 1) {
                                System.out.println("Insertion réussie");
                            } else {
                                System.err.println("Error");
                            }
                        }else{
                            errorMessageUser.add("L'id entré est déjà utilisé");
                            request.setAttribute("errorMessageUser", errorMessageUser);
                            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                        }
                    } catch (SQLException | ExceptionCoThi19 throwables) {
                        throwables.printStackTrace();
                        errorMessageUser.add(throwables.getMessage());
                    }
                    request.setAttribute("errorMessageUser", errorMessageUser);
                    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                }else{
                    request.setAttribute("errorMessageUser", errorMessageUser);
                    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                }
                break;
            case "activityForm":
                List<String> errorMessageAct = new ArrayList<>();
                String idAct, idPlace, dateString, hourEnd, hourStart, minuteEnd, minuteStart;
                idAct = request.getParameter("id_act");
                idUser = request.getParameter("id_user_act");
                idPlace = request.getParameter("id_place_act");
                dateString = request.getParameter("date");
                hourEnd = request.getParameter("hourEnd");
                minuteEnd = request.getParameter("minuteEnd");
                hourStart = request.getParameter("hourStart");
                minuteStart = request.getParameter("minuteStart");

                Time start = null, end = null;
                Date date;
                if (!hourStart.equals("") && !minuteStart.equals("") &&!hourEnd.equals("") && !minuteEnd.equals("")) {
                    LocalTime startTime = LocalTime.of(Integer.parseInt(hourStart), Integer.parseInt(minuteStart), 0, 0);
                    LocalTime endTime = LocalTime.of(Integer.parseInt(hourEnd), Integer.parseInt(minuteEnd), 0, 0);
                    start = Time.valueOf(startTime);
                    end = Time.valueOf(endTime);
                }

                if (dateString != "") {
                    date = Date.valueOf(dateString);
                }else{
                    date = null;
                }
                if (idAct.equals("")){
                    verification = false;
                    errorMessageAct.add("Le champs de idAct est vide");
                }
                if (idUser.equals("")){
                    verification = false;
                    errorMessageAct.add("Le champs de idUser est vide");
                }
                if (idPlace.equals("")){
                    verification = false;
                    errorMessageAct.add("Le champs de idPlace est vide");
                }
                if (dateString.equals("")){
                    verification = false;
                    errorMessageAct.add("Le champs de dateString est vide");
                }
                if (hourEnd.equals("")){
                    verification = false;
                    errorMessageAct.add("Le champs de hourEnd est vide");
                }
                if (hourStart.equals("")){
                    verification = false;
                    errorMessageAct.add("Le champs de hourStart est vide");
                }
                if (start != null && end != null && start.after(end)){
                    verification = false;
                    errorMessageAct.add("L'heure de début est dans le future'");
                }
                if (end != null && start.after(end) && end.after(start)){
                    verification = false;
                    errorMessageAct.add("L'heure de fin est dans le passé'");
                }

                if (verification){
                    int i;
                    boolean idCorresponding = false;
                    try {
                        ResultSet resultIdSet = sql.doRequest("Select * from Activity");
                        if (resultIdSet.next()){
                            i = resultIdSet.getInt("id_act"); // On récupère le résultat
                            if (String.valueOf(i).equals(idAct)){
                                idCorresponding = true;
                            }else{
                                System.err.println("Error");
                            }
                        }
                        if(!idCorresponding) {
                            int resultInsertSet = sql.doInsert("INSERT INTO Activity (id_act, id_user, id_place, date, hourEnd, hourStart) VALUES (" + Integer.parseInt(idAct) + ", " + Integer.parseInt(idUser) + ", " + Integer.parseInt(idPlace) + ", \"" + date + "\", " + end + ", " + start + ");");
                            if (resultInsertSet == 1) {
                                System.out.println("Insertion réussie");
                            } else {
                                System.err.println("Error");
                            }
                        }else{
                            errorMessageAct.add("L'id entré est déjà utilisé");
                            request.setAttribute("errorMessageAct", errorMessageAct);
                            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                        }
                    } catch (SQLException | ExceptionCoThi19 throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("errorMessageAct", errorMessageAct);
                    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                }else{
                    request.setAttribute("errorMessageAct", errorMessageAct);
                    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                }
                break;
            case "placeForm" :
                List<String> errorMessagePlace = new ArrayList<>();
                String adress, gpsCoordinates;
                idPlace = request.getParameter("id_place");
                name = request.getParameter("namePlace");
                adress = request.getParameter("adress");
                gpsCoordinates = request.getParameter("gps_coordinates");

                if (idPlace.equals("")){
                    verification = false;
                    errorMessagePlace.add("Le champs de idPlace est vide");
                }
                if (name.equals("")){
                    verification = false;
                    errorMessagePlace.add("Le champs de name est vide");
                }
                if (adress.equals("")){
                    verification = false;
                    errorMessagePlace.add("Le champs de adress est vide");
                }
                if (gpsCoordinates.equals("")){
                    verification = false;
                    errorMessagePlace.add("Le champs de gpsCoordinates est vide");
                }

                if (verification){
                    int i;
                    boolean idCorresponding = false;
                    try {
                        ResultSet resultIdSet = sql.doRequest("Select * from Place");
                        if (resultIdSet.next()){
                            i = resultIdSet.getInt("id_place"); // On récupère le résultat
                            if (String.valueOf(i).equals(idPlace)){
                                idCorresponding = true;
                            }else{
                                System.err.println("Error");
                            }
                        }
                        if(!idCorresponding) {
                            int resultInsertSet = sql.doInsert("INSERT INTO Place (id_place, name, adress, gps_coordinates) VALUES (" + Integer.parseInt(idPlace) + ", \"" + name + "\", \"" + adress + "\", \"" + gpsCoordinates + "\");");
                            if (resultInsertSet == 1) {
                                System.out.println("Insertion réussie");
                            } else {
                                System.err.println("Error");
                            }
                        }else {
                            errorMessagePlace.add("L'id entré est déjà utilisé");
                            request.setAttribute("errorMessagePlace", errorMessagePlace);
                            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                        }
                    } catch (SQLException | ExceptionCoThi19 throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("errorMessagePlace", errorMessagePlace);
                    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                }else{
                    request.setAttribute("errorMessagePlace", errorMessagePlace);
                    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                }
                break;
            case "notifForm" :
                List<String> errorMessageNotif = new ArrayList<>();
                String idNotif, idReceive, idAsk, content, seen;
                idNotif = request.getParameter("id_notif");
                idReceive = request.getParameter("id_receive");
                idAsk = request.getParameter("id_ask");
                content = request.getParameter("content");
                seen = request.getParameter("seen");

                if (idNotif.equals("")){
                    verification = false;
                    errorMessageNotif.add("Le champs de idNotif est vide");
                }
                if (idReceive.equals("")){
                    verification = false;
                    errorMessageNotif.add("Le champs de idReceive est vide");
                }
                if (idAsk.equals("")){
                    idAsk = "null";
                }
                if (content.equals("")){
                    verification = false;
                    errorMessageNotif.add("Le champs de content est vide");
                }
                if (!seen.equals("")){
                    if (Integer.parseInt(seen) != 1 && Integer.parseInt(seen) != 0){
                        verification = false;
                        errorMessageNotif.add("Le champs de seen doit être compris entre 0 et 1");
                    }
                }else{
                    verification = false;
                    errorMessageNotif.add("Le champs de seen est vide");
                }

                if (verification){
                    int i;
                    boolean idCorresponding = false;
                    try {
                        ResultSet resultIdSet = sql.doRequest("Select * from Notification");
                        if (resultIdSet.next()){
                            i = resultIdSet.getInt("id_notif"); // On récupère le résultat
                            if (String.valueOf(i).equals(idNotif)){
                                idCorresponding = true;
                            }else{
                                System.err.println("Error");
                            }
                        }
                        if(!idCorresponding) {
                            int resultInsertSet = sql.doInsert("INSERT INTO Notification (id_notif, id_receive, id_ask, content, seen) VALUES (" + idNotif + ", " + Integer.parseInt(idReceive) + ", " + idAsk + ", \"" + content + "\", "+Integer.parseInt(seen)+");");
                            if (resultInsertSet == 1) {
                                System.out.println("Insertion réussie");
                            } else {
                                System.err.println("Error");
                            }
                        }else{
                            errorMessageNotif.add("L'id entré est déjà utilisé");
                            request.setAttribute("errorMessageNotif", errorMessageNotif);
                            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                        }
                    } catch (SQLException | ExceptionCoThi19 throwables) {
                        throwables.printStackTrace();
                    }
                    request.setAttribute("errorMessageNotif", errorMessageNotif);
                    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                }else{
                    request.setAttribute("errorMessageNotif", errorMessageNotif);
                    this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
                }
                break;

        }
    }
}
