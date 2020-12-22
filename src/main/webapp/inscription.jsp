<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscription</title>
</head>
<body>
    <form action="inscription" method="GET">
        <label for="mail">Mail :</label>
        <input type="email" id="mail" name="mail"><br>
        <label for="surname">Prenom :</label>
        <input type="text" id="surname" name="surname"><br>
        <label for="name">Nom :</label>
        <input type="text" id="name" name="name"><br>
        <label for="password">Mot de passe :</label>
        <input type="password" id="password" name="password"><br>
        <label for="birthday">Date de naissance :</label>
        <input type="date" id="birthday" name="birthday"><br>

        <input type="submit" value="SEND">
    </form>
</body>
</html>
