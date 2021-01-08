<%@ page import="beans.UserBean" %>
<%@ page import="beans.NotificationBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="beans.ActiviteBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="fr">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><%
        Object o = request.getAttribute("notifs");
        int nbSeen = 0;
        if(o != null){
            Collection<NotificationBean> notificationBeans = (Collection<NotificationBean>) o;
            for(NotificationBean notif : notificationBeans){
                nbSeen += notif.isSeen()?0:1;
            }
            if(nbSeen > 0){
                out.print("(" + nbSeen + ") ");
            }
        }
        %>CoThi19</title>

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
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
            <%  UserBean user = (UserBean) request.getSession().getAttribute("userConnected");
                if (user != null){%>
                    <li class="nav-item">
                        <a class="btn disabled" style="color: white">Bonjour <% out.print(user.getMail());%></a>
                    </li>
                    <% if (user.isAdmin()){ %>
                    <li class="nav-item">
                        <a class="nav-link" href="keskecer">Administration</a>
                    </li>
                    <% } %>
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home</a>
                    </li>
                    <% if (!user.isInfected()){ %>
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
                <% }else{ %>
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

<!-- Page Content -->
<div class="container">

    <div class="row">
        <div class="col-lg-3">
            <a href="#"><img src="images/cothi19.png" alt="CoThi19" width="240" height="135"></a>
        </div>

        <div class="col-lg-9">
            <div>
                <h2>Liste des activités des 10 derniers jours :</h2>
            </div>
            <br><br>
            <div class="row">
                <%
                    Object ob = request.getAttribute("activities");
                    if(ob != null){
                        Collection<ActiviteBean> col = (Collection<ActiviteBean>) ob;
                        if(col.size() > 0){
                            for(ActiviteBean act : col){
                                out.print("<div class=\"col-lg-4 col-md-6 mb-4\">\n" +
                                        "<div class=\"card h-100\">\n" +
                                        "<a href=\"#\"><img class=\"card-img-top\" src=\"images/onsaitpas2.png\" alt=\"\"></a>\n" +
                                        "<div class=\"card-body\">\n" +
                                        "<h4 class=\"card-title\">");
                                out.print("<a href=\"#\"> " + act.getDate() + " de " + act.getHeureDebut() + " jusqu'à " + act.getHeureFin() + " </a>");
                                out.print("<p class=\"card-text\">Nom : " + act.getLieu().getName() +" <br>Adresse :" + act.getLieu().getAdress() + "</p>\n" +
                                        "</div>\n" +
                                        "</div>\n" +
                                        "</div>");
                            }
                        }else{
                            out.print("<div class=\"col-lg-12 col-md-12 mb-12\"> \n" +
                                    "<h1> Vous n'avez pas d'acitivités. </h1>\n" +
                                    "</div>" +
                                    "<a href=\"#\"><img src=\"images/onsaitpas.png\" alt=\"On sait pas\" width=\"400\" height=\"389\"></a>\n");

                        }
                    }else{
                        out.print("<div class=\"col-lg-12 col-md-12 mb-12\"> \n" +
                                "<h1> Connectez-vous pour afficher vos activités. </h1>\n" +
                                "</div>" +
                                "<a href=\"#\"><img src=\"images/onsaitpas.png\" alt=\"On sait pas\" width=\"400\" height=\"389\"></a>\n");
                    }
                %>

            </div>
            <!-- /.row -->

        </div>
        <!-- /.col-lg-9 -->

    </div>
    <!-- /.row -->

</div>
<!-- /.container -->

<!-- Footer -->
<footer class="py-5 bg-dark">
    <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Your Website 2020</p>
    </div>
    <!-- /.container -->
</footer>

<!-- Bootstrap core JavaScript -->
<script src="bootstrap/jquery/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
