<%@ page import="userBean.UserBean" %><%--
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
    <div class="container">
        <div class="col-lg-3">
            <h1 class="my-4">Creation D'activité</h1>
        </div>

        <div class="col-lg-9">
            <div class = "form-group">
                <form action="CreateActivity" method="GET">
                    <!-- Pour l'activité -->
                    <label for="date">Date Activté :</label>
                    <br>
                    <% String date = request.getParameter("date");
                    if(!"".equals(date)){ %>
                        <input class="form-control" type="date" id="date" name="date" value="<% if(date != null){
                            out.print(request.getParameter("date"));
                    }%>">

                    <% }else{%>
                        <input type="date" class="form-control is-invalid" id="date" name="date">
                        <div class="invalid-feedback">
                            Please provide a valid date.
                        </div>
                    <%}%>
                    <br>
                    <label for="heureDebut">Heure de début :</label>
                    <input class="form-control" type="number" id="heureDebut" name="heureDebut" value="0" min="0" max="23">
                    <label for="minuteDebut"></label>
                    <input class="form-control" type="number" id="minuteDebut" name="minuteDebut" value="0" min="0" max="59"><br>
                    <label for="heureFin">Heure de fin :</label>
                    <input class="form-control" type="number" id="heureFin" name="heureFin" value="0" min="0" max="23">
                    <label for="minuteFin"></label>
                    <input class="form-control" type="number" id="minuteFin" name="minuteFin" value="0" min="0" max="59"><br>
                    <!-- Pour l'activité -->

                    <label for="rechercheLieu">Entrez le nom du lieux</label>
                    <input class="form-control" type="search" id="rechercheLieu" name="rechercheLieu" value=""><br>


                    <input class="form-control" type="submit" value="Submit">
                </form>
            </div>
        </div>
    </div>
</body>
</html>