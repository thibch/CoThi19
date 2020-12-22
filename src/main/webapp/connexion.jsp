<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Inscription</title>

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
                    <a class="nav-link" href="inscription">Mon compte</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">

    <div class="col-lg-3">
        <h1 class="my-4">Connexion</h1>
    </div>

    <div class="col-lg-9">
        <div class = "form-group">
            <form action="connexionVerification" method="GET">
                <label for="mail">Mail :</label>
                <input class="form-control" type="email" id="mail" name="mail"><br>
                <label for="password">Mot de passe :</label>
                <input class="form-control" type="password" id="password" name="password"><br>

                <input class="form-control" type="submit" value="ENVOYER">
            </form>
        </div>
    </div>
</div>
</body>
</html>
