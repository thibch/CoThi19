<%@ page import="beans.UserBean" %><%--
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
                            out.print(request.getAttribute("date"));
                    }%>">

                    <% }else{%>
                        <input type="date" class="form-control is-invalid" id="date" name="date">
                        <div class="invalid-feedback">
                            Please provide a valid date.
                        </div>
                    <%}%>
                    <br>
                    <label for="heureDebut">Heure de début :</label>
                    <% String heureDebut = request.getParameter("heureDebut");
                    out.print("<p> " + heureDebut + "</p>");
                    if(!"-1".equals(heureDebut) && (Integer)request.getAttribute("heureDebut") != -1){ %>
                        <input class="form-control" type="number" id="heureDebut" name="heureDebut" min="0" max="23" value="<%
                               if(heureDebut != null){
                                   out.print(request.getAttribute("heureDebut"));
                               }else{
                                   out.print(0);
                               }
                               %>">
                    <% }else{%>
                        <input class="form-control is-invalid" type="number" id="heureDebut" name="heureDebut" value="0" min="0" max="23">
                        <div class="invalid-feedback">
                            Please provide a valid hour.
                        </div>
                    <%}%>
                    <label for="minuteDebut"></label>
                    <% String minuteDebut = request.getParameter("minuteDebut");
                        if(!"-1".equals(minuteDebut) && (Integer)request.getAttribute("minuteDebut") != -1){ %>
                        <input class="form-control" type="number" id="minuteDebut" name="minuteDebut" min="0" max="59" value="<%
                               if(minuteDebut != null){
                                    out.print(request.getAttribute("minuteDebut"));
                               }else{
                                   out.print(0);
                               }
                               %>">
                    <% }else{%>
                    <input class="form-control is-invalid" type="number" id="minuteDebut" name="minuteDebut" value="0" min="0" max="59">
                    <div class="invalid-feedback">
                        Please provide a valid minute.
                    </div>
                    <%}%>
                    <br>
                    <label for="heureFin">Heure de fin :</label>
                    <% String heureFin = request.getParameter("heureFin");
                        if(!"-1".equals(heureFin) && (Integer)request.getAttribute("heureFin") != -1){ %>
                        <input class="form-control" type="number" id="heureFin" name="heureFin" min="0" max="23" value="<%
                               if(heureFin != null){
                                   out.print(request.getAttribute("heureFin"));
                               }else{
                                   out.print(0);
                               }%>">
                    <% }else{%>
                        <input class="form-control is-invalid" type="number" id="heureFin" name="heureFin" min="0" max="23" value="0">
                        <div class="invalid-feedback">
                            Please provide a valid hour.
                        </div>
                    <%}%>
                    <label for="minuteFin"></label>
                    <% String minuteFin = request.getParameter("minuteFin");
                        if(!"-1".equals(minuteFin) && (Integer)request.getAttribute("minuteFin") != -1){ %>
                        <input class="form-control" type="number" id="minuteFin" name="minuteFin" min="0" max="59"
                               value="<%
                               if(minuteFin != null){
                                   out.print(request.getAttribute("minuteFin"));
                               }else{
                                   out.print(0);
                               }
                               %>">
                    <% }else{%>
                        <input class="form-control is-invalid" type="number" id="minuteFin" name="minuteFin" value="0" min="0" max="59">
                        <div class="invalid-feedback">
                            Please provide a valid hour.
                        </div>
                    <%}%>
                    <br>
                    <!-- Pour l'activité -->

                    <label for="rechercheLieuNom">Entrez le nom du lieux</label>
                    <input class="form-control" type="search" id="rechercheLieuNom" name="rechercheLieu" value=""><br>
                    <label for="rechercheLieuAdresse">Entrez l'adresse du lieu</label>
                    <input class="form-control" type="search" id="rechercheLieuAdresse" name="rechercheLieu" value=""><br>


                    <input class="form-control btn-outline-primary" type="submit" value="Submit">
                </form>
            </div>
        </div>
    </div>
</body>
</html>