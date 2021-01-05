<%@ page import="beans.UserBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="beans.NotificationBean" %><%--
  Created by IntelliJ IDEA.
  User: Thibault
  Date: 22/12/2020
  Time: 14:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
        }%>Création d'activité</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="bootstrap/css/shop-homepage.css" rel="stylesheet">
</head>
<body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="#">CoThi19</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <% UserBean user = (UserBean) request.getSession().getAttribute("userConnected");
                    if (user != null) {%>
                <li class="nav-item">
                    <a class="btn disabled" style="color: white">Bonjour <% out.print(user.getMail());%></a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="accueil">Home
                    </a>
                </li>
                <% if (user.isAdmin()){ %>
                <li class="nav-item">
                    <a class="nav-link" href="keskecer">Administration</a>
                </li>
                <% } %>
                <li class="nav-item">
                    <a class="nav-link" href="consultNotifications">Notifications<%if(nbSeen > 0){out.print("<span style=\"color:red\">(" + nbSeen + ")</span>");}%></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="monCompte">Mon compte</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="deconnexion">Déconnexion</a>
                </li>
                <% } else { %>
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="connexion">Connexion</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="inscription">Inscription</a>
                </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-lg-3">

            <img src="images/cothi19.png" alt="CoThi19" width="240" height="135">

        </div>
        <div class="col-lg-9">
            <h1 class="my-4">Creation D'activité</h1>

            <div class="col-lg-9">
                <% String date = request.getParameter("date");
                    String dateStr = (String) request.getAttribute("date");
                    boolean errorDate = "".equals(date) || dateStr != null && dateStr.equals("");
                    List<String> list = new ArrayList<>();
                    if (errorDate) {
                        list.add("Veuillez rentrer une date correcte.");
                    }

                    String heureDebut = request.getParameter("heureDebut");
                    Integer heureDebInt = (Integer) request.getAttribute("heureDebut");
                    boolean errorHeureDebut = "".equals(heureDebut) || heureDebInt != null && heureDebInt == -1;
                    if (errorHeureDebut) {
                        list.add("Veuillez rentrer une heure de début correcte.");
                    }

                    String minuteDebut = request.getParameter("minuteDebut");
                    Integer minuteDebInt = (Integer) request.getAttribute("minuteDebut");
                    boolean errorMinuteDebut = "".equals(minuteDebut) || minuteDebInt != null && minuteDebInt == -1;
                    if (errorMinuteDebut) {
                        list.add("Veuillez rentrer une minute de début correcte.");
                    }

                    String heureFin = request.getParameter("heureFin");
                    Integer heureFinInt = (Integer) request.getAttribute("heureFin");
                    boolean errorHeureFin = "".equals(heureFin) || heureFinInt != null && heureFinInt == -1;
                    if (errorHeureFin) {
                        list.add("Veuillez rentrer une heure de fin correcte.");
                    }

                    String minuteFin = request.getParameter("minuteFin");
                    Integer minuteFinInt = (Integer) request.getAttribute("minuteFin");
                    boolean errorminuteFin = "".equals(minuteFin) || minuteFinInt != null && minuteFinInt == -1;
                    if (errorminuteFin) {
                        list.add("Veuillez rentrer une minute de fin correcte.");
                    }

                    for (String message : list) {
                        out.print("<div class=\"alert alert-danger col-lg-9\">"
                                + "<strong>Erreur ! </strong> " + message
                                + "</div>");
                    }
                %>
                <div class="form-group">
                    <form action="CreateActivity" method="GET" id="formActivity">
                        <!-- Pour l'activité -->
                        <label for="date">Date Activté :</label>
                        <br>
                        <% if (!errorDate) { %>
                        <input class="form-control" type="date" id="date" name="date" value="<%out.print(dateStr);%>">
                        <% } else {%>
                        <input type="date" class="form-control is-invalid" id="date" name="date">
                        <%}%>
                        <br>
                        <label for="heureDebut">Heure de début :</label>
                        <% if (!errorHeureDebut) { %>
                        <input class="form-control" type="number" id="heureDebut" name="heureDebut" min="0" max="23"
                               value="<%out.print(heureDebInt);%>">
                        <% } else {%>
                        <input class="form-control is-invalid" type="number" id="heureDebut" name="heureDebut" value="0"
                               min="0" max="23">
                        <%}%>
                        <label for="minuteDebut"></label>
                        <% if (!errorMinuteDebut) { %>
                        <input class="form-control" type="number" id="minuteDebut" name="minuteDebut" min="0" max="59"
                               value="<%out.print(minuteDebInt);%>">
                        <% } else {%>
                        <input class="form-control is-invalid" type="number" id="minuteDebut" name="minuteDebut"
                               value="0" min="0" max="59">
                        <%}%>
                        <br>
                        <label for="heureFin">Heure de fin :</label>
                        <% if (!errorHeureFin) { %>
                        <input class="form-control" type="number" id="heureFin" name="heureFin" min="0" max="23"
                               value="<%out.print(heureFinInt);%>">
                        <% } else {%>
                        <input class="form-control is-invalid" type="number" id="heureFin" name="heureFin" min="0"
                               max="23" value="0">
                        <%}%>
                        <label for="minuteFin"></label>
                        <% if (!errorminuteFin) { %>
                        <input class="form-control" type="number" id="minuteFin" name="minuteFin" min="0" max="59"
                               value="<%out.print(minuteFinInt);%>">
                        <% } else {%>
                        <input class="form-control is-invalid" type="number" id="minuteFin" name="minuteFin" value="0"
                               min="0" max="59">
                        <%}%>
                        <br>
                        <!-- Pour l'activité -->

                        <input type="hidden" id="estActif" name="estActif" value="1">
                        <input class="form-control btn-outline-primary" type="submit" value="Rechercher un lieux">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>