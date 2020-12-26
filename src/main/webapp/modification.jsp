<%@ page import="connexionSQL.SQLConnector" %>
<%@ page import="beans.UserBean" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="exception.ExceptionCoThi19" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Modification des informations personnelles</title>

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
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="accueil">Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="monCompte">Mon compte</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<%
    String mail = "";
    String name = "";
    String surname = "";
    String birthDate = "";
    SQLConnector sql = SQLConnector.getInstance();
    UserBean user = (UserBean) request.getSession().getAttribute("userConnected");
    try {
        ResultSet resultSet = sql.doRequest("Select * from User WHERE email = \"" + user.getMail() + "\"");
        if (resultSet.next()){
            mail = resultSet.getString("email");
            name = resultSet.getString("name");
            surname = resultSet.getString("surname");
            birthDate = resultSet.getString("birth_date");
        }
    }catch (ExceptionCoThi19 | SQLException throwables){
        throwables.printStackTrace();
    }
%>
<div class="container">

    <div class="col-lg-9">
        <h1>Modification des informations personnelles</h1>
    </div>

    <div class="col-lg-9">
        <p>Mail : <%out.print(mail);%></p>
        <div class = "form-group">
            <form action="modificationVerification" method="GET">
                <label for="surname">Prenom :</label>
                <input class="form-control" type="text" id="surname" name="surname" value="<%out.print(surname);%>"><br>
                <label for="name">Nom :</label>
                <input class="form-control" type="text" id="name" name="name" value="<%out.print(name);%>"><br>
                <label for="birthDate">Date de naissance :</label>
                <input class="form-control" type="date" id="birthDate" name="birthDate" value="<%out.print(birthDate);%>"><br>
                <label for="password">Mot de passe actuel :</label>
                <input class="form-control" type="password" id="password" name="password"><br>
                <label for="newPassword">Nouveau mot de passe :</label>
                <input class="form-control" type="password" id="newPassword" name="newPassword"><br>
                <label for="confirmedPassword">Confirmation du nouveau mot de passe :</label>
                <input class="form-control" type="password" id="confirmedPassword" name="confirmedPassword"><br>

                <input class="form-control" type="submit" value="MODIFIER">
            </form>
        </div>
    </div>
</div>
</body>
</html>
