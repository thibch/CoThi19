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

@WebServlet(name = "ServletAddBDD")
public class ServletAddBDD extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SQLConnector sql = SQLConnector.getInstance();
        boolean verification = true;
        switch (request.getParameter("form")) {
            case "userForm":
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

                Date birthDate = Date.valueOf(birthDateString);

                if (idUser.equals("")){
                    verification = false;
                }
                if (mail.equals("")){
                    verification = false;
                }
                if (name.equals("")){
                    verification = false;
                }
                if (surname.equals("")){
                    verification = false;
                }
                if (password.equals("")){
                    verification = false;
                }
                if (birthDateString.equals("")){
                    verification = false;
                }
                if (isAdmin.equals("")){
                    verification = false;
                }
                if (pathPicture.equals("")){
                    pathPicture = "NULL";
                }
                if (isInfected.equals("")){
                    verification = false;
                }

                if (verification){
                    int i;
                    String bd = null;
                    try {
                        ResultSet resultIdSet = sql.doRequest("Select * from User");
                        if (resultIdSet.next()){
                            i = resultIdSet.getInt("id_user"); // On récupère le résultat
                            if (!String.valueOf(i).equals(idUser)){
                                System.out.println("Error");
                                response.setHeader("Refresh", "10;url=keskecer");
                            }
                        }
                        int resultInsertSet = sql.doInsert("INSERT INTO User (id_user, email, password, name, surname, birth_date, isAdmin, path_picture, isInfected) VALUES ("+Integer.parseInt(idUser)+", \""+mail+"\", \""+password+"\", \""+name+"\", \""+surname+"\", '"+birthDate+"', "+Integer.parseInt(isAdmin)+", "+pathPicture+", "+Integer.parseInt(isInfected)+");");
                        if (resultInsertSet == 1){
                            System.out.println("Insertion réussie");
                        }else{
                            System.out.println("Error");
                        }
                    } catch (SQLException | ExceptionCoThi19 throwables) {
                        throwables.printStackTrace();
                    }
                    response.setHeader("Refresh", "10;url=keskecer");
                }else{
                    response.setHeader("Refresh", "10;url=keskecer");
                }
                break;
            case "activityForm":
                String idAct, idPlace, dateString, hourEnd, hourStart;
                idAct = request.getParameter("id_act");
                idUser = request.getParameter("id_user_act");
                idPlace = request.getParameter("id_place_act");
                dateString = request.getParameter("date");
                hourEnd = request.getParameter("hourEnd");
                hourStart = request.getParameter("hourStart");

                Date date = Date.valueOf(dateString);

                if (idAct.equals("")){
                    verification = false;
                }
                if (idUser.equals("")){
                    verification = false;
                }
                if (idPlace.equals("")){
                    verification = false;
                }
                if (dateString.equals("")){
                    verification = false;
                }
                if (hourEnd.equals("")){
                    verification = false;
                }
                if (hourStart.equals("")){
                    verification = false;
                }

                if (verification){
                    int i;
                    String bd = null;
                    try {
                        ResultSet resultIdSet = sql.doRequest("Select * from Activity");
                        if (resultIdSet.next()){
                            i = resultIdSet.getInt("id_act"); // On récupère le résultat
                            if (!String.valueOf(i).equals(idAct)){
                                System.out.println("Error");
                                response.setHeader("Refresh", "10;url=keskecer");
                            }
                        }
                        int resultInsertSet = sql.doInsert("INSERT INTO Activity (id_act, id_user, id_place, date, hourEnd, hourStart) VALUES ("+Integer.parseInt(idAct)+", "+Integer.parseInt(idUser)+", "+Integer.parseInt(idPlace)+", \""+date+"\", "+Integer.parseInt(hourEnd)+", "+Integer.parseInt(hourStart)+");");
                        if (resultInsertSet == 1){
                            System.out.println("Insertion réussie");
                        }else{
                            System.out.println("Error");
                        }
                    } catch (SQLException | ExceptionCoThi19 throwables) {
                        throwables.printStackTrace();
                    }
                    response.setHeader("Refresh", "10;url=keskecer");
                }else{
                    response.setHeader("Refresh", "10;url=keskecer");
                }
                break;
            case "placeForm" :
                String adress, gpsCoordinates;
                idPlace = request.getParameter("id_place");
                name = request.getParameter("namePlace");
                adress = request.getParameter("adress");
                gpsCoordinates = request.getParameter("gps_coordinates");

                if (idPlace.equals("")){
                    verification = false;
                }
                if (name.equals("")){
                    verification = false;
                }
                if (adress.equals("")){
                    verification = false;
                }
                if (gpsCoordinates.equals("")){
                    verification = false;
                }

                if (verification){
                    int i;
                    String bd = null;
                    try {
                        ResultSet resultIdSet = sql.doRequest("Select * from Place");
                        if (resultIdSet.next()){
                            i = resultIdSet.getInt("id_place"); // On récupère le résultat
                            if (!String.valueOf(i).equals(idPlace)){
                                System.out.println("Error");
                                response.setHeader("Refresh", "10;url=keskecer");
                            }
                        }
                        int resultInsertSet = sql.doInsert("INSERT INTO Place (id_place, name, adress, gps_coordinates) VALUES ("+Integer.parseInt(idPlace)+", \""+name+"\", \""+adress+"\", \""+gpsCoordinates+"\");");
                        if (resultInsertSet == 1){
                            System.out.println("Insertion réussie");
                        }else{
                            System.out.println("Error");
                        }
                    } catch (SQLException | ExceptionCoThi19 throwables) {
                        throwables.printStackTrace();
                    }
                    response.setHeader("Refresh", "10;url=keskecer");
                }else{
                    response.setHeader("Refresh", "10;url=keskecer");
                }
                break;
            case "notifForm" :
                String idNotif, idReceive, idAsk, content;
                idNotif = request.getParameter("id_notif");
                idReceive = request.getParameter("id_receive");
                idAsk = request.getParameter("id_ask");
                content = request.getParameter("content");

                if (idNotif.equals("")){
                    verification = false;
                }
                if (idReceive.equals("")){
                    verification = false;
                }
                if (idAsk.equals("")){
                    verification = false;
                }
                if (content.equals("")){
                    verification = false;
                }

                if (verification){
                    int i;
                    try {
                        ResultSet resultIdSet = sql.doRequest("Select * from Notification");
                        if (resultIdSet.next()){
                            i = resultIdSet.getInt("id_notif"); // On récupère le résultat
                            if (!String.valueOf(i).equals(idNotif)){
                                System.out.println("Error");
                                response.setHeader("Refresh", "10;url=keskecer");
                            }
                        }
                        int resultInsertSet = sql.doInsert("INSERT INTO Notification (id_notif, id_receive, id_ask, content) VALUES ("+Integer.parseInt(idNotif)+", "+Integer.parseInt(idReceive)+", "+Integer.parseInt(idAsk)+", \""+content+"\");");
                        if (resultInsertSet == 1){
                            System.out.println("Insertion réussie");
                        }else{
                            System.out.println("Error");
                        }
                    } catch (SQLException | ExceptionCoThi19 throwables) {
                        throwables.printStackTrace();
                    }
                    response.setHeader("Refresh", "10;url=keskecer");
                }else{
                    response.setHeader("Refresh", "10;url=keskecer");
                }
                break;

        }
    }
}
