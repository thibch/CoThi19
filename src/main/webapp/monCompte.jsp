<%@ page import="connexionSQL.SQLConnector" %>
<%@ page import="beans.UserBean" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="exception.ExceptionCoThi19" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="fr">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Mon compte</title>

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
                <%  UserBean user = (UserBean) request.getSession().getAttribute("userConnected");
                if (user.isAdmin()){ %>
                <li class="nav-item">
                    <a class="nav-link" href="keskecer">Administration</a>
                </li>
                <% } %>
                <li class="nav-item">
                    <a class="nav-link" href="createActivity">Créer une activités</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="deconnexion">Déconnexion</a>
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
    user = (UserBean) request.getSession().getAttribute("userConnected");
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
    <div class="row">
        <div class="col-lg-3">
            <img src="images/cothi19.png" alt="CoThi19" width="240" height="135">
        </div>
        <div class="col">
            <div class="col-lg-9">
                <div class="col-md-9">
                    <h1>Mon compte</h1>
                </div>
                <div class="card-body">
                    <h2>Informations personnelles :</h2>
                </div>
                    <div class="card-text">
                        <div class="col-lg-4">
                            <p>Mail :
                        <%
                            out.println(mail);
                        %>
                            </p>
                        </div>
                        <div class="col-lg-4">
                            <p>Nom :
                            <%
                                out.println(name);
                            %>
                            </p>
                        </div>
                        <div class="col-lg-4">
                            <p>Prenom :
                            <%
                                out.println(surname);
                            %>
                            </p>
                        </div>
                        <div class="col-lg-4">
                            <p>Birthdate :
                            <%
                                out.println(birthDate);
                            %>
                            </p>
                        </div>
                    </div>
                    <div>
                        <a href="modification" class="btn btn-info" role="button">Modifier mes informations personnelles</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>