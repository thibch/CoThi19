<%@ page import="beans.UserBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.LieuBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="beans.ActiviteBean" %>
<%@ page import="beans.NotificationBean" %><%--
  Created by IntelliJ IDEA.
  User: Thibault
  Date: 30/12/2020
  Time: 20:03
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
        <a class="navbar-brand" href="accueil">CoThi19</a>
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
                <% if (user.isAdmin()){ %>
                <li class="nav-item">
                    <a class="nav-link" href="keskecer">Administration</a>
                </li>
                <% } %>
                <li class="nav-item active">
                    <a class="nav-link" href="accueil">Home
                    </a>
                </li>
                <% if (!user.isInfected()){ %>
                <li class="nav-item">
                    <a class="nav-link" href="positif/" style="color:red">Je suis positif</a>
                </li>
                <%}%>
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
            <h1 class="my-4">Creation D'activité : Récap'</h1>

            <%
                ActiviteBean act = (ActiviteBean) request.getAttribute("activite");
                LieuBean lieu = (LieuBean) request.getAttribute("lieu");
            %>
            <div class="card-text">
                <div class="col-lg-4">
                    <h4>
                        Activité :
                    </h4>
                </div>
                <div class="col-lg-4">
                    <p>Date : <%
                        out.println(act.getDate());
                    %></p>
                </div>
                <div class="col-lg-4">
                    <p>Heure de début : <%
                        out.println(act.getHeureDebut());
                    %></p>
                </div>
                <div class="col-lg-4">
                    <p>Heure de fin : <%
                        out.println(act.getHeureFin());
                    %></p>
                </div>
                <div class="col-lg-4">
                    <h4>
                        Lieu :
                    </h4>
                </div>
                <div class="col-lg-4">
                    <p>Nom : <%
                        out.println(lieu.getName());
                    %></p>
                </div>
                <div class="col-lg-4">
                    <p>Adresse : <%
                        out.println(lieu.getAdress());
                    %></p>
                </div>
                <div class="col-lg-9">
                    <h3>L'activité a été créée avec succès !</h3>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
