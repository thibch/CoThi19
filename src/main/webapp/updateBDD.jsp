<%@ page import="java.sql.ResultSet" %>
<%@ page import="beans.UserBean" %>
<%@ page import="connexionSQL.SQLConnector" %>
<%@ page import="exception.ExceptionCoThi19" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="beans.NotificationBean" %>
<%@ page import="java.util.Collection" %><%--
  Created by IntelliJ IDEA.
  User: Khozo
  Date: 31/12/2020
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%
        Collection<NotificationBean> notificationBeans = (Collection<NotificationBean>) request.getAttribute("notifs");
        int nbSeen = 0;
        if (notificationBeans != null) {
            for(NotificationBean notif : notificationBeans){
                nbSeen += notif.isSeen()?0:1;
            }
            if(nbSeen > 0){
                out.print("(" + nbSeen + ") ");
            }
        }%>Modification BDD</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="bootstrap/css/shop-homepage.css" rel="stylesheet">
</head>
<body>
<%
    if (request.getParameter("idUpdateUser") != null){
        String idUser = "";
        String email = "";
        String password = "";
        String name = "";
        String surname = "";
        String birthDate = "";
        String isAdmin = "";
        String pathPicture = "";
        String isInfected = "";
        SQLConnector sql = SQLConnector.getInstance();
        try {
            ResultSet resultSet = sql.doRequest("Select * from User WHERE id_user = \"" + request.getParameter("idUpdateUser") + "\"");
            if (resultSet.next()){
                idUser = resultSet.getString("id_user");
                email = resultSet.getString("email");
                password = resultSet.getString("password");
                name = resultSet.getString("name");
                surname = resultSet.getString("surname");
                birthDate = resultSet.getString("birth_date");
                isAdmin = resultSet.getString("isAdmin");
                pathPicture = resultSet.getString("path_picture");
                isInfected = resultSet.getString("isInfected");
            }else{

            }
        }catch (ExceptionCoThi19 | SQLException throwables){
            throwables.printStackTrace();
        }
    %>
    <div class="col-lg-9">
        <br>
        <div class = "form-group">
            <form action="modificationVerification" method="GET">
                <label for="password">Password :</label>
                <input class="form-control" type="text" id="password" name="password" value="<%out.print(password);%>"><br>
                <label for="name">Name :</label>
                <input class="form-control" type="text" id="name" name="name" value="<%out.print(name);%>"><br>
                <label for="surname">Surname :</label>
                <input class="form-control" type="text" id="surname" name="surname" value="<%out.print(surname);%>"><br>
                <label for="birthDate">Birth_date :</label>
                <input class="form-control" type="date" id="birthDate" name="birthDate" value="<%out.print(birthDate);%>"><br>
                <label for="isAdmin">IsAdmin :</label>
                <input class="form-control" type="text" id="isAdmin" name="isAdmin" value="<%out.print(isAdmin);%>"><br>
                <label for="pathPicture">Path_picture :</label>
                <input class="form-control" type="text" id="pathPicture" name="pathPicture" value="<%out.print(pathPicture);%>"><br>
                <label for="isInfected">IsInfected :</label>
                <input class="form-control" type="text" id="isInfected" name="isInfected" value="<%out.print(isInfected);%>"><br>
                <input type="hidden" value="userForm" name="form" />
                <input type="hidden" value="<% out.print(email); %>" name="email" id="email"/>
                <input type="hidden" value="<% out.print(idUser);%>" name="id_user" id="id_user"/>
                <input class="form-control" type="submit" value="MODIFIER">
            </form>
        </div>
    </div>
<% } else if (request.getParameter("idUpdateActivity") != null) {
    String idAct = "";
    String date = "";
    String hourEnd = "";
    String hourStart = "";
    SQLConnector sql = SQLConnector.getInstance();
    System.out.println(request.getParameter("idUpdateActivity"));
    try {
        ResultSet resultSet = sql.doRequest("Select * from Activity WHERE id_act = " + request.getParameter("idUpdateActivity")+";");
        if (resultSet.next()){
            idAct = resultSet.getString("id_act");
            date = resultSet.getString("date");
            hourEnd = resultSet.getString("hourEnd");
            hourStart = resultSet.getString("hourStart");
        }
    }catch (ExceptionCoThi19 | SQLException throwables){
        throwables.printStackTrace();
    }
    System.out.println("id act dans updateBDD : "+idAct);
    %>
    <div class="col-lg-9">
        <br>
        <div class = "form-group">
            <form action="modificationVerification" method="GET">
                <label for="actDate">Date :</label>
                <input class="form-control" type="date" id="actDate" name="actDate" value="<%out.print(date);%>"><br>
                <label for="hourEnd">HourEnd :</label>
                <input class="form-control" type="text" id="hourEnd" name="hourEnd" value="<%out.print(hourEnd);%>"><br>
                <label for="minuteEnd">MinuteEnd :</label>
                <input class="form-control" type="text" id="minuteEnd" name="minuteEnd" value="<%out.print(hourEnd);%>"><br>
                <label for="hourStart">HourStart :</label>
                <input class="form-control" type="text" id="hourStart" name="hourStart" value="<%out.print(hourStart);%>"><br>
                <label for="minuteStart">MinuteStart :</label>
                <input class="form-control" type="text" id="minuteStart" name="minuteStart" value="<%out.print(hourStart);%>"><br>
                <input type="hidden" value="activityForm" name="form" />
                <input type="hidden" value="<% out.print(idAct);%>" name="id_activity" id="id_activity"/>
                <input class="form-control" type="submit" value="MODIFIER">
            </form>
        </div>
    </div>
<% } else if (request.getParameter("idUpdatePlace") != null) {
    String idPlace = "";
    String namePlace = "";
    String adress = "";
    String gpsCoordinates = "";

    SQLConnector sql = SQLConnector.getInstance();
    try {
        ResultSet resultSet = sql.doRequest("Select * from Place WHERE id_place = \"" + request.getParameter("idUpdatePlace") + "\"");
        if (resultSet.next()){
            idPlace = resultSet.getString("id_place");
            namePlace = resultSet.getString("name");
            adress = resultSet.getString("adress");
            gpsCoordinates = resultSet.getString("gps_coordinates");
        }
    }catch (ExceptionCoThi19 | SQLException throwables){
        throwables.printStackTrace();
    }
    %>
    <div class="col-lg-9">
        <br>
        <div class = "form-group">
            <form action="modificationVerification" method="GET">
                <label for="namePlace">Name :</label>
                <input class="form-control" type="text" id="namePlace" name="namePlace" value="<%out.print(namePlace);%>"><br>
                <label for="adress">Adress :</label>
                <input class="form-control" type="text" id="adress" name="adress" value="<%out.print(adress);%>"><br>
                <label for="gpsCoordinates">Gps_coordinates :</label>
                <input class="form-control" type="text" id="gpsCoordinates" name="gpsCoordinates" value="<%out.print(gpsCoordinates);%>"><br>
                <input type="hidden" value="placeForm" name="form" />
                <input type="hidden" value="<% out.print(idPlace);%>" name="id_place" id="id_place"/>
                <input class="form-control" type="submit" value="MODIFIER">
            </form>
        </div>
    </div>
<% } else if (request.getParameter("idUpdateNotif") != null) {
    String idNotif = "";
    String content = "", seen = "";
    SQLConnector sql = SQLConnector.getInstance();
    try {
        ResultSet resultSet = sql.doRequest("Select * from Notification WHERE id_notif = \"" + request.getParameter("idUpdateNotif") + "\"");
        if (resultSet.next()){
            idNotif = resultSet.getString("id_notif");
            content = resultSet.getString("content");
            seen = resultSet.getString("seen");
        }
    }catch (ExceptionCoThi19 | SQLException throwables){
        throwables.printStackTrace();
    }
    %>
    <div class="col-lg-9">
        <br>
        <div class = "form-group">
            <form action="modificationVerification" method="GET">
                <label for="content">Content :</label>
                <input class="form-control" type="text" id="content" name="content" value="<%out.print(content);%>"><br>
                <label for="seen">Seen :</label>
                <input class="form-control" type="text" id="seen" name="seen" value="<%out.print(seen);%>"><br>
                <input type="hidden" value="notifForm" name="form" />
                <input type="hidden" value="<% out.print(idNotif);%>" name="id_notif" id="id_notif"/>
                <input class="form-control" type="submit" value="MODIFIER">
            </form>
        </div>
    </div>
<% } %>
</body>
</html>
