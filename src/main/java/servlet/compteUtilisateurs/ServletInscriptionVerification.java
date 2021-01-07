package servlet.compteUtilisateurs;

import connexionSQL.SQLConnector;
import exception.ExceptionCoThi19;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roberge-Mentec Corentin
 */

public class ServletInscriptionVerification extends HttpServlet {

    public ServletInscriptionVerification(){
        super();
    }

    public static final String VUE = "/inscription.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean verification = true;
        List<String> errorMessage = new ArrayList<>();
        String idUser, mail, name, surname, password, birthDateString;
        mail = request.getParameter("mail");
        name = request.getParameter("name");
        surname = request.getParameter("surname");
        password = request.getParameter("password");
        birthDateString = request.getParameter("birthDate");
        Date birthDate = null;
        if (!birthDateString.equals("")) {
            birthDate = Date.valueOf(birthDateString);
        }

        if (mail.equals("")){
            verification = false;
            errorMessage.add("Le champs de mail est vide");
        }
        if (name.equals("")){
            verification = false;
            errorMessage.add("Le champs de nom est vide");
        }
        if (surname.equals("")){
            verification = false;
            errorMessage.add("Le champs de prénom est vide");
        }
        if (password.equals("")){
            verification = false;
            errorMessage.add("Le champs de mot de passe est vide");
        }

        java.util.Date date = new java.util.Date();

        if (birthDateString.equals("")){
            verification = false;
            errorMessage.add("Le champs de date de naissance est vide");
        }
        if (birthDate != null) {
            if (birthDate.after(date)) {
                verification = false;
                errorMessage.add("Le champs de la date de naissance est dans le futur");
            }
        }

        if (verification){
            SQLConnector sql = SQLConnector.getInstance(); // On récupère la connexion sql
            int i;
            try {
                i = 0;
                ResultSet resultIdSet = sql.doRequest("Select * from User");
                while (resultIdSet.next()){
                    i = resultIdSet.getInt("id_user"); // On récupère le résultat
                }
                i++;
                int resultInsertSet = sql.doInsert("INSERT INTO User (id_user, email, password, name, surname, birth_date, isAdmin, path_picture, isInfected) VALUES ("+i+", \""+mail+"\", \""+password+"\", \""+name+"\", \""+surname+"\", '"+birthDate+"', "+0+", NULL, " + 0 + ");");
                if (resultInsertSet == 1){
                    System.out.println("Insertion réussie");
                }else{
                    System.out.println("Error");
                }
            } catch (SQLException | ExceptionCoThi19 throwables) {
                throwables.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/accueil");
        }else{
            request.setAttribute("errorMessage", errorMessage);
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        }
    }
}