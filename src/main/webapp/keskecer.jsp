<%@ page import="beans.UserBean" %>
<%@ page import="connexionSQL.SQLConnector" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="exception.ExceptionCoThi19" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="beans.NotificationBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Khozo
  Date: 30/12/2020
  Time: 12:51
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
        }%>Administration</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="bootstrap/css/shop-homepage.css" rel="stylesheet">

    <script>
        window.onload = function() {
            document.getElementById("addUser").style.display = "none";
            document.getElementById("upUser").style.display = "none";
            document.getElementById("delUser").style.display = "none";

            document.getElementById("addActivity").style.display = "none";
            document.getElementById("upActivity").style.display = "none";
            document.getElementById("delActivity").style.display = "none";

            document.getElementById("addPlace").style.display = "none";
            document.getElementById("upPlace").style.display = "none";
            document.getElementById("delPlace").style.display = "none";

            document.getElementById("addNotif").style.display = "none";
            document.getElementById("upNotif").style.display = "none";
            document.getElementById("delNotif").style.display = "none";
        };
    </script>
</head>

<body>
    <%
    UserBean user = (UserBean) request.getSession().getAttribute("userConnected");
    if (user != null){
        if (!user.isAdmin()){ %>
            <script type="text/javascript">
                <!--
                window.location = "https://www.youtube.com/watch?v=NP9NI8xX9nY"
                //-->
            </script>
        <% }
    } else { %>
        <script type="text/javascript">
            <!--
            window.location = "https://www.youtube.com/watch?v=NP9NI8xX9nY"
            //-->
        </script>
    <% } %>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="#">CoThi19</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="accueil">Home</a>
                    </li>
                    <% if (user != null && !user.isInfected()){ %>
                    <li class="nav-item">
                        <a class="nav-link" href="positif/">Je suis positif :)</a>
                    </li>
                    <%}%>
                    <li class="nav-item">
                        <a class="nav-link" href="consultNotifications">Notifications<%if(nbSeen > 0){out.print("<span style=\"color:red\">(" + nbSeen + ")</span>");}%></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="CreateActivity">Créer une activité</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="monCompte">Mon compte</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="deconnexion">Déconnexion</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <%
        SQLConnector sql = SQLConnector.getInstance();
    %>
    <div class="container">

        <div class="row">

            <div class="col-lg-3">

                <a href="accueil"><img src="images/cothi19.png" alt="CoThi19" width="240" height="135"></a>

            </div>

            <div class="col-lg-9">
                <div class="col-md-9">
                    <h2>Les tables de la bdd</h2>
                </div>
                <br><br>
                <div class="row">

                    <!-- Création des différentes tables de la BDD -->

                    <div class="col">
                        <% if (request.getAttribute("errorMessageUser") != null) {
                            List<String> messages = (List<String>) request.getAttribute("errorMessageUser");
                            System.out.println(messages);
                            for (String s : messages) {
                                out.print("<div class=\"alert alert-danger col-lg-9\">" +
                                        "<strong>Erreur ! </strong> " + s +
                                        "</div>");
                            }
                        } %>

                        <% if (request.getAttribute("errorMessageAct") != null) {
                            List<String> messages = (List<String>) request.getAttribute("errorMessageAct");
                            System.out.println(messages);
                            for (String s : messages) {
                                out.print("<div class=\"alert alert-danger col-lg-9\">" +
                                        "<strong>Erreur ! </strong> " + s +
                                        "</div>");
                            }
                        } %>

                        <% if (request.getAttribute("errorMessagePlace") != null) {
                            List<String> messages = (List<String>) request.getAttribute("errorMessagePlace");
                            System.out.println(messages);
                            for (String s : messages) {
                                out.print("<div class=\"alert alert-danger col-lg-9\">" +
                                        "<strong>Erreur ! </strong> " + s +
                                        "</div>");
                            }
                        } %>

                        <% if (request.getAttribute("errorMessageNotif") != null) {
                            List<String> messages = (List<String>) request.getAttribute("errorMessageNotif");
                            System.out.println(messages);
                            for (String s : messages) {
                                out.print("<div class=\"alert alert-danger col-lg-9\">" +
                                        "<strong>Erreur ! </strong> " + s +
                                        "</div>");
                            }
                        } %>
                        <!-- Table User -->

                        <div class="col-md-9">
                            <h3>Table user</h3>
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">Id_user</th>
                                <th scope="col">Email</th>
                                <th scope="col">Password</th>
                                <th scope="col">Name</th>
                                <th scope="col">Surname</th>
                                <th scope="col">Birth_date</th>
                                <th scope="col">IsAdmin</th>
                                <th scope="col">Path_picture</th>
                                <th scope="col">IsInfected</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%

                                try {
                                    ResultSet resultSet = sql .doRequest("Select * from User");
                                    while (resultSet.next()) { %>
                                        <tr>
                                            <th scope="row"><% out.print(resultSet.getString("id_user")); %></th>
                                            <td><% out.print(resultSet.getString("email")); %></td>
                                            <td><% out.print(resultSet.getString("password")); %></td>
                                            <td><% out.print(resultSet.getString("name")); %></td>
                                            <td><% out.print(resultSet.getString("surname")); %></td>
                                            <td><% out.print(resultSet.getString("birth_date")); %></td>
                                            <td><% out.print(resultSet.getString("isAdmin")); %></td>
                                            <td><% out.print(resultSet.getString("path_picture")); %></td>
                                            <td><% out.print(resultSet.getString("isInfected")); %></td>
                                        </tr>
                                    <% }
                                }catch (ExceptionCoThi19 | SQLException throwable){
                                    throwable.printStackTrace();
                                }
                            %>
                            </tbody>
                        </table>

                        <div class="row-cols-lg-4">
                            <button type="button" class="btn btn-dark" onclick="displayUser(1)" id="insertUser" value="Display AddForm">Ajouter</button>
                            <button type="button" class="btn btn-dark" onclick="displayUser(2)" id="updateUser" value="Display UpdateForm">Modifier</button>
                            <button type="button" class="btn btn-dark" onclick="displayUser(3)" id="deleteUser" value="Display DeleteForm">Supprimer</button>
                            <button type="button" class="btn btn-dark" onclick="displayUser(0)" id="hideUser" value="Hide Form">Hide Form</button>
                        </div>
                        <br>

                        <form id="addUser" action="addBDD">
                            <h1>Ajouter une ligne :</h1>
                            <label for="id_user">Id_user :</label>
                            <input class="form-control" type="number" id="id_user" name="id_user"><br>
                            <label for="mail">e-mail :</label>
                            <input class="form-control" type="email" id="mail" name="mail"><br>
                            <label for="password">Mot de passe :</label>
                            <input class="form-control" type="password" id="password" name="password"><br>
                            <label for="name">Nom :</label>
                            <input class="form-control" type="text" id="name" name="name"><br>
                            <label for="surname">Prenom :</label>
                            <input class="form-control" type="text" id="surname" name="surname"><br>
                            <label for="birthDate">Date de naissance :</label>
                            <input class="form-control" type="date" id="birthDate" name="birthDate"><br>
                            <label for="isAdmin">IsAdmin :</label>
                            <input class="form-control" type="number" id="isAdmin" name="isAdmin"><br>
                            <label for="path_picture">Path_picture :</label>
                            <input class="form-control" type="text" id="path_picture" name="path_picture"><br>
                            <label for="isInfected">IsInfected :</label>
                            <input class="form-control" type="number" id="isInfected" name="isInfected"><br>
                            <input type="hidden" value="userForm" name="form" />
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>
                        <form id="upUser" action="updateBDD">
                            <h1>Modifier une ligne :</h1>
                            <label for="idUpdateUser">Id de la ligne à modifier :</label>
                            <input class="form-control" type="number" id="idUpdateUser" name="idUpdateUser"><br>
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>
                        <form id="delUser" action="deleteBDD">
                            <h1>Supprimer une ligne :</h1>
                            <label for="idDeleteUser">Id de la ligne à supprimer :</label>
                            <input class="form-control" type="number" id="idDeleteUser" name="idDeleteUser"><br>
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>

                        <script type="text/javascript">

                            function displayUser(a) {
                                if (a === 1) {
                                    document.getElementById("addUser").style.display = "block";
                                    document.getElementById("upUser").style.display = "none";
                                    document.getElementById("delUser").style.display = "none";
                                } else if( a === 2) {
                                    document.getElementById("upUser").style.display = "block";
                                    document.getElementById("addUser").style.display = "none";
                                    document.getElementById("delUser").style.display = "none";
                                } else if( a === 3) {
                                    document.getElementById("delUser").style.display = "block";
                                    document.getElementById("addUser").style.display = "none";
                                    document.getElementById("upUser").style.display = "none";
                                } else {
                                    document.getElementById("addUser").style.display = "none";
                                    document.getElementById("upUser").style.display = "none";
                                    document.getElementById("delUser").style.display = "none";
                                }
                            }
                        </script>

                        <br><br>
                        <!-- Table Activity -->

                        <div class="col-md-9">
                            <h3>Table Activité</h3>
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">Id_act</th>
                                <th scope="col">Id_user</th>
                                <th scope="col">Id_place</th>
                                <th scope="col">date</th>
                                <th scope="col">HourEnd</th>
                                <th scope="col">HourStart</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%

                                try {
                                    ResultSet resultSet = sql .doRequest("Select * from Activity");
                                    while (resultSet.next()) { %>
                            <tr>
                                <th scope="row"><% out.print(resultSet.getString("id_act")); %></th>
                                <td><% out.print(resultSet.getString("id_user")); %></td>
                                <td><% out.print(resultSet.getString("id_place")); %></td>
                                <td><% out.print(resultSet.getString("date")); %></td>
                                <td><% out.print(resultSet.getString("hourEnd")); %></td>
                                <td><% out.print(resultSet.getString("hourStart")); %></td>
                            </tr>
                            <% }
                            }catch (ExceptionCoThi19 | SQLException throwable){
                                throwable.printStackTrace();
                            }
                            %>
                            </tbody>
                        </table>

                        <div class="row-cols-lg-4">
                            <button type="button" class="btn btn-dark" onclick="displayActivity(1)" id="insertActivity">Ajouter</button>
                            <button type="button" class="btn btn-dark" onclick="displayActivity(2)" id="updateActivity">Modifier</button>
                            <button type="button" class="btn btn-dark" onclick="displayActivity(3)" id="deleteActivity">Supprimer</button>
                            <button type="button" class="btn btn-dark" onclick="displayActivity(0)" id="hideActivity" value="Hide Form">Hide Form</button>
                        </div>
                        <br>

                        <form id="addActivity" action="addBDD">
                            <h1>Ajouter une ligne :</h1>
                            <label for="id_act">Id_act :</label>
                            <input class="form-control" type="number" id="id_act" name="id_act"><br>
                            <label for="id_user_act">Id_user :</label>
                            <input class="form-control" type="number" id="id_user_act" name="id_user_act"><br>
                            <label for="id_place_act">Id_place :</label>
                            <input class="form-control" type="number" id="id_place_act" name="id_place_act"><br>
                            <label for="date">Date :</label>
                            <input class="form-control" type="date" id="date" name="date"><br>
                            <label for="hourEnd">HourEnd :</label>
                            <input class="form-control" type="number" id="hourEnd" name="hourEnd"><br>
                            <label for="minuteEnd">MinuteEnd :</label>
                            <input class="form-control" type="number" id="minuteEnd" name="minuteEnd"><br>
                            <label for="hourStart">HourStart :</label>
                            <input class="form-control" type="number" id="hourStart" name="hourStart"><br>
                            <label for="minuteStart">MinuteStart :</label>
                            <input class="form-control" type="number" id="minuteStart" name="minuteStart"><br>
                            <input type="hidden" value="activityForm" name="form" />
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>
                        <form id="upActivity" action="updateBDD">
                            <h1>Modifier une ligne :</h1>
                            <label for="idUpdateActivity">Id de la ligne à modifier :</label>
                            <input class="form-control" type="number" id="idUpdateActivity" name="idUpdateActivity"><br>
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>
                        <form id="delActivity" action="deleteBDD">
                            <h1>Supprimer une ligne :</h1>
                            <label for="idDeleteActivity">Id de la ligne à supprimer :</label>
                            <input class="form-control" type="number" id="idDeleteActivity" name="idDeleteActivity"><br>
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>

                        <script type="text/javascript">

                            function displayActivity(a) {
                                if (a === 1) {
                                    document.getElementById("addActivity").style.display = "block";
                                    document.getElementById("upActivity").style.display = "none";
                                    document.getElementById("delActivity").style.display = "none";
                                } else if( a === 2) {
                                    document.getElementById("upActivity").style.display = "block";
                                    document.getElementById("addActivity").style.display = "none";
                                    document.getElementById("delActivity").style.display = "none";
                                } else if( a === 3) {
                                    document.getElementById("delActivity").style.display = "block";
                                    document.getElementById("addActivity").style.display = "none";
                                    document.getElementById("upActivity").style.display = "none";
                                } else {
                                    document.getElementById("addActivity").style.display = "none";
                                    document.getElementById("upActivity").style.display = "none";
                                    document.getElementById("delActivity").style.display = "none";
                                }
                            }
                        </script>

                        <br><br>
                        <!-- Table Place -->

                        <div class="col-md-9">
                            <h3>Table Place</h3>
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">Id_place</th>
                                <th scope="col">Name</th>
                                <th scope="col">Adress</th>
                                <th scope="col">Gps_coordinates</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%

                                try {
                                    ResultSet resultSet = sql .doRequest("Select * from Place");
                                    while (resultSet.next()) { %>
                            <tr>
                                <th scope="row"><% out.print(resultSet.getString("id_place")); %></th>
                                <td><% out.print(resultSet.getString("name")); %></td>
                                <td><% out.print(resultSet.getString("adress")); %></td>
                                <td><% out.print(resultSet.getString("gps_coordinates")); %></td>
                            </tr>
                            <% }
                            }catch (ExceptionCoThi19 | SQLException throwable){
                                throwable.printStackTrace();
                            }
                            %>
                            </tbody>
                        </table>

                        <div class="row-cols-lg-4">
                            <button type="button" class="btn btn-dark" onclick="displayPlace(1)" id="insertPlace">Ajouter</button>
                            <button type="button" class="btn btn-dark" onclick="displayPlace(2)" id="updatePlace">Modifier</button>
                            <button type="button" class="btn btn-dark" onclick="displayPlace(3)" id="deletePlace">Supprimer</button>
                            <button type="button" class="btn btn-dark" onclick="displayPlace(0)" id="hidePlace" value="Hide Form">Hide Form</button>
                        </div>
                        <br>

                        <form id="addPlace" action="addBDD">
                            <h1>Ajouter une ligne :</h1>
                            <label for="id_place">Id_place :</label>
                            <input class="form-control" type="number" id="id_place" name="id_place"><br>
                            <label for="namePlace">Name :</label>
                            <input class="form-control" type="text" id="namePlace" name="namePlace"><br>
                            <label for="adress">adress :</label>
                            <input class="form-control" type="text" id="adress" name="adress"><br>
                            <label for="gps_coordinates">Gps_coordinates :</label>
                            <input class="form-control" type="text" id="gps_coordinates" name="gps_coordinates"><br>
                            <input type="hidden" value="placeForm" name="form" />
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>
                        <form id="upPlace" action="updateBDD">
                            <h1>Modifier une ligne :</h1>
                            <label for="idUpdatePlace">Id de la ligne à modifier :</label>
                            <input class="form-control" type="number" id="idUpdatePlace" name="idUpdatePlace"><br>
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>
                        <form id="delPlace" action="deleteBDD">
                            <h1>Supprimer une ligne :</h1>
                            <label for="idDeletePlace">Id de la ligne à supprimer :</label>
                            <input class="form-control" type="number" id="idDeletePlace" name="idDeletePlace"><br>
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>

                        <script type="text/javascript">

                            function displayPlace(a) {
                                if (a === 1) {
                                    document.getElementById("addPlace").style.display = "block";
                                    document.getElementById("upPlace").style.display = "none";
                                    document.getElementById("delPlace").style.display = "none";
                                } else if( a === 2) {
                                    document.getElementById("upPlace").style.display = "block";
                                    document.getElementById("addPlace").style.display = "none";
                                    document.getElementById("delPlace").style.display = "none";
                                } else if( a === 3) {
                                    document.getElementById("delPlace").style.display = "block";
                                    document.getElementById("addPlace").style.display = "none";
                                    document.getElementById("upPlace").style.display = "none";
                                } else {
                                    document.getElementById("addPlace").style.display = "none";
                                    document.getElementById("upPlace").style.display = "none";
                                    document.getElementById("delPlace").style.display = "none";
                                }
                            }
                        </script>

                        <br><br>
                        <!-- Table Notification -->

                        <div class="col-md-9">
                            <h3>Table Notification</h3>
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">Id_notif</th>
                                <th scope="col">Id_receive</th>
                                <th scope="col">Id_ask</th>
                                <th scope="col">Content</th>
                                <th scope="col">Seen</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%

                                try {
                                    ResultSet resultSet = sql .doRequest("Select * from Notification");
                                    while (resultSet.next()) { %>
                            <tr>
                                <th scope="row"><% out.print(resultSet.getString("id_notif")); %></th>
                                <td><% out.print(resultSet.getString("id_receive")); %></td>
                                <td><% out.print(resultSet.getString("id_ask")); %></td>
                                <td><% out.print(resultSet.getString("content")); %></td>
                                <td><% out.print(resultSet.getString("seen")); %></td>
                            </tr>
                            <% }
                            }catch (ExceptionCoThi19 | SQLException throwable){
                                throwable.printStackTrace();
                            }
                            %>
                            </tbody>
                        </table>

                        <div class="row-cols-lg-4">
                            <button type="button" class="btn btn-dark" onclick="displayNotif(1)" id="insertNotif">Ajouter</button>
                            <button type="button" class="btn btn-dark" onclick="displayNotif(2)" id="updateNotif">Modifier</button>
                            <button type="button" class="btn btn-dark" onclick="displayNotif(3)" id="deleteNotif">Supprimer</button>
                            <button type="button" class="btn btn-dark" onclick="displayNotif(0)" id="hideNotif" value="Hide Form">Hide Form</button>
                        </div>
                        <br>
                        <form id="addNotif" action="addBDD">
                            <h1>Ajouter une ligne :</h1>
                            <label for="id_notif">Id_notif :</label>
                            <input class="form-control" type="number" id="id_notif" name="id_notif"><br>
                            <label for="id_receive">Id_receive :</label>
                            <input class="form-control" type="number" id="id_receive" name="id_receive"><br>
                            <label for="id_ask">Id_ask :</label>
                            <input class="form-control" type="number" id="id_ask" name="id_ask"><br>
                            <label for="content">Content :</label>
                            <input class="form-control" type="text" id="content" name="content"><br>
                            <label for="seen">Seen :</label>
                            <input class="form-control" type="number" id="seen" name="seen"><br>
                            <input type="hidden" value="notifForm" name="form" />
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>
                        <form id="upNotif" action="updateBDD">
                            <h1>Modifier une ligne :</h1>
                            <label for="idUpdateNotif">Id de la ligne à modifier :</label>
                            <input class="form-control" type="number" id="idUpdateNotif" name="idUpdateNotif"><br>
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>
                        <form id="delNotif" action="deleteBDD">
                            <h1>Supprimer une ligne :</h1>
                            <label for="idDeleteNotif">Id de la ligne à supprimer :</label>
                            <input class="form-control" type="number" id="idDeleteNotif" name="idDeleteNotif"><br>
                            <input class="btn btn-dark" type="submit" value="VALIDER">
                        </form>

                        <script type="text/javascript">


                            function displayNotif(a) {
                                if (a === 1) {
                                    document.getElementById("addNotif").style.display = "block";
                                    document.getElementById("upNotif").style.display = "none";
                                    document.getElementById("delNotif").style.display = "none";
                                } else if( a === 2) {
                                    document.getElementById("upNotif").style.display = "block";
                                    document.getElementById("addNotif").style.display = "none";
                                    document.getElementById("delNotif").style.display = "none";
                                } else if( a === 3) {
                                    document.getElementById("delNotif").style.display = "block";
                                    document.getElementById("addNotif").style.display = "none";
                                    document.getElementById("upNotif").style.display = "none";
                                } else {
                                    document.getElementById("addNotif").style.display = "none";
                                    document.getElementById("upNotif").style.display = "none";
                                    document.getElementById("delNotif").style.display = "none";
                                }
                            }
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
