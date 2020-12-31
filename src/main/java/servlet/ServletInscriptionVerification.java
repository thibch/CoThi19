package servlet;

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

/**
 * @author Roberge-Mentec Corentin
 */

public class ServletInscriptionVerification extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean verification = true;
        String mail, name, surname, password, birthDateString;
        mail = request.getParameter("mail");
        name = request.getParameter("name");
        surname = request.getParameter("surname");
        password = request.getParameter("password");
        birthDateString = request.getParameter("birthDate");
        Date birthDate = Date.valueOf(birthDateString);

        if (mail.equals("")){
            verification = false;
        }
        if (name.equals("")){
            verification = false;
        }
        if (surname.equals("")){
            verification = false;
        }
        if (password.equals("")){
            verification = false;
        }
        if (birthDateString.equals("")){
            verification = false;
        }
        if (verification){
            SQLConnector sql = SQLConnector.getInstance(); // On récupère la connexion sql
            int i;
            String bd = null;
            try {
                ResultSet resultIdSet = sql.doRequest("Select * from User");
                if (resultIdSet.next()){
                    i = resultIdSet.getInt("id_user"); // On récupère le résultat
                }else{
                    i = 0;
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
            response.sendRedirect(request.getContextPath() + "/inscription.jsp");
        }
    }
}