<%@ page import="beans.UserBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="beans.NotificationBean" %>
<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: Thibault
  Date: 04/01/2021
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Notifications</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="bootstrap/css/shop-homepage.css" rel="stylesheet">
</head>
<%Collection<NotificationBean> notificationBeans = (Collection<NotificationBean>) request.getAttribute("notifs");%>
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
                    <% if (user.isAdmin()){ %>
                    <li class="nav-item">
                        <a class="nav-link" href="keskecer">Administration</a>
                    </li>
                    <% } %>
                    <li class="nav-item active">
                        <a class="nav-link" href="accueil">Home
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="createActivity">Créer une activité</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="monCompte">Mon compte</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="deconnexion">Déconnexion</a>
                    </li>
                    <% }%>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <img src="images/cothi19.png" alt="CoThi19" width="240" height="135">
            </div>
            <div class="col-lg-8">
                <form action="consultnotifications" method="GET" id="formNotif">
                    <h1 class="my-4">Notifications</h1>
                    <% String isSeen = "";
                        if(notificationBeans != null){
                        for(NotificationBean notif : notificationBeans){
                            out.print("<div class=\"col-lg-9 clearfix\">");
                            if(!notif.isSeen()) {
                                isSeen = "class=\"font-weight-bold\"";
                            }else{
                                isSeen = "";
                            }
                            out.print("<span class=\"col-lg-6\" " + isSeen + " >");
                            out.print(notif.getContent());
                            out.print("</span>");
                            out.print("<div class=\"col-lg-2 float-right\" >");
                            out.print("<button type=\"submit\" class=\"btn btn-danger\" name=\"wantToDelete\" id=\"wantToDelete\" value=\"" + notif.getIdNotif() + "\">Supprimer</button>");
                            out.print("</div>");
                            out.print("</div><br>");
                        }
                    }%>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

