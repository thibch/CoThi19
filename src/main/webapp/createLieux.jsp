<%@ page import="beans.UserBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.LieuBean" %>
<%@ page import="java.util.Collection" %><%--
  Created by IntelliJ IDEA.
  User: Thibault
  Date: 29/12/2020
  Time: 19:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Création d'activité</title>

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
                    <a class="nav-link" href="#">Home
                    </a>
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
                <% String dateStr = (String) request.getAttribute("date");
                    Integer heureDebInt = (Integer) request.getAttribute("heureDebut");
                    Integer minuteDebInt = (Integer) request.getAttribute("minuteDebut");
                    Integer heureFinInt = (Integer) request.getAttribute("heureFin");
                    Integer minuteFinInt = (Integer) request.getAttribute("minuteFin");
                %>
                <div class="form-group">
                    <form action="FindPlace" method="GET" id="formActivity">
                        <%
                            out.print("<input type=\"hidden\" id=\"date\" name=\"date\" value=\"" + dateStr + "\">");
                            out.print("<input type=\"hidden\" id=\"heureDebut\" name=\"heureDebut\" value=\"" + heureDebInt + "\">");
                            out.print("<input type=\"hidden\" id=\"minuteDebut\" name=\"minuteDebut\" value=\"" + minuteDebInt + "\">");
                            out.print("<input type=\"hidden\" id=\"heureFin\" name=\"heureFin\" value=\"" + heureFinInt + "\">");
                            out.print("<input type=\"hidden\" id=\"minuteFin\" name=\"minuteFin\" value=\"" + minuteFinInt + "\">");
                        %>
                        <!-- Recherche de Lieux :-->

                        <% String error = (String) request.getAttribute("error");

                            if (error != null) {
                                out.print("<div class=\"alert alert-danger col-lg-9\">"
                                        + "<strong>Erreur ! </strong> " + error
                                        + "</div><br>");
                            }
                            Collection<LieuBean> rechercheLieuListe = (Collection<LieuBean>) request.getAttribute("rechercheLieux");

                            if (rechercheLieuListe == null && request.getParameter("createLieux") == null) {%>
                        <label for="rechercheLieuNom">Entrez le nom du lieux</label>
                        <input class="form-control" type="search" id="rechercheLieuNom" name="rechercheLieuNom"
                               value=""><br>
                        <label for="rechercheLieuAdresse">Entrez l'adresse du lieu</label>
                        <input class="form-control" type="search" id="rechercheLieuAdresse"
                               name="rechercheLieuAdresse" value=""><br>
                        <button class="form-control btn btn-primary" type="submit" form="formActivity"
                                id="RechercheLieu" name="RechercheLieu" value="1">Rechercher un lieux
                        </button>
                        <br>
                        <% } else {
                            if (request.getParameter("createLieux") != null) { // Formulaire de création de lieux
                                String nom = request.getParameter("createPlaceName") == null ? "" : request.getParameter("createPlaceName");
                                out.print(
                                        "<label for=\"createPlaceName\">Entrez le nom du lieu</label>\n" +
                                                "<input class=\"form-control\" type=\"text\" id=\"createPlaceName\" name=\"createPlaceName\" placeholder=\"Entrez le nom du lieu\" value=\"" + nom + "\"><br>");
                                String adress = request.getParameter("createPlaceAdress") == null ? "" : request.getParameter("createPlaceAdress");
                                out.print(
                                        "<label for=\"createPlaceAdress\">Entrez l'adresse du lieu</label>\n" +
                                                "<input class=\"form-control\" type=\"text\" id=\"createPlaceAdress\" name=\"createPlaceAdress\" placeholder=\"Entrez l'adresse du lieu\" value=\"" + adress + "\"><br>");
                                String cityName = request.getParameter("createPlaceCityName") == null ? "" : request.getParameter("createPlaceCityName");
                                out.print(
                                        "<label for=\"createPlaceCityName\">Entrez le nom de la ville</label>\n" +
                                                "<input class=\"form-control\" type=\"text\" id=\"createPlaceCityName\" name=\"createPlaceCityName\" placeholder=\"Entrez le nom de la ville\" value=\"" + cityName + "\"><br>");
                                out.print("<button class=\"form-control btn btn-primary\" type=\"submit\" form=\"formActivity\" id=\"createLieux\" name=\"createLieux\" value=\"2\">Créer un nouveau lieu</button>");
                            } else if (rechercheLieuListe != null) { // Formulaire de selection de lieu
                                int number = 1;
                                for (LieuBean lieu : rechercheLieuListe) {
                                    out.print("<label for=\"choixLieux" + number + "\" >");
                                    out.print("Nom : " + lieu.getName() + " : " + lieu.getAdress());
                                    out.print("</label>");
                                    out.print("<button class=\"btn btn-primary\" type=\"submit\" form=\"formActivity\" id=\"choixLieux" + number + "\" name=\"choixLieux\" value=\"" + number + "\">Choisir ce lieu</button>");
                                    out.print("<input type=\"hidden\" name=\"" + number + "name\" id=\"choixLieux" + number + "\" value=\"" + lieu.getName() + "\">");
                                    out.print("<input type=\"hidden\" name=\"" + number + "adress\" id=\"choixLieux" + number + "\" value=\"" + lieu.getAdress() + "\">");
                                    out.print("<br>");
                                    number++;
                                }
                                out.print("<br>\n" +
                                        "<button class=\"form-control btn btn-primary\" type=\"submit\" form=\"formActivity\" id=\"createLieux\" name=\"createLieux\" value=\"1\">Créer un nouveau lieu</button>");
                            }
                        }%>


                        <input type="hidden" id="estActif" name="estActif" value="1">
                        <!--<input class="form-control btn-outline-primary" type="submit" value="Submit">
                        -->
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>