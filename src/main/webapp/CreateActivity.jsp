<%--
  Created by IntelliJ IDEA.
  User: Thibault
  Date: 22/12/2020
  Time: 14:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Création d'activité</title>
</head>
<body>
    <form action="CreateActivity" method="GET">
        <!-- Pour l'activité -->
        <label for="date">Date Acitivté :</label>
        <input type="date" id="date" name="date"><br>
        <label for="heureDebut">Heure de début :</label>
        <input type="number" id="heureDebut" name="heureDebut" value="0" min="0" max="23">
        <label for="minuteDebut"></label>
        <input type="number" id="minuteDebut" name="minuteDebut" value="0" min="0" max="59"><br>
        <label for="heureFin">Heure de fin :</label>
        <input type="number" id="heureFin" name="heureFin" value="0" min="0" max="23">
        <label for="minuteFin"></label>
        <input type="number" id="minuteFin" name="minuteFin" value="0" min="0" max="59"><br>
        <!-- Pour l'activité -->

        <label for="rechercheLieu">Entrez le nom du lieux</label>
        <input type="search" id="rechercheLieu" name="rechercheLieu" value=""><br>


        <input type="submit" value="Submit">
    </form>
</body>
</html>