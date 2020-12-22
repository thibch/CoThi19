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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ServletConnexionVerification")
public class ServletConnexionVerification extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean verification = true;
        boolean recuperation = true;
        String mail, password;
        mail = request.getParameter("mail");
        password = request.getParameter("password");

        if (mail.equals("")){
            verification = false;
        }
        if (password.equals("")){
            verification = false;
        }
        if (verification){
            SQLConnector sql = SQLConnector.getInstance(); // On récupère la connexion sql
            try {
                ResultSet resultIdSet = sql.doRequest("Select * from User WHERE email = \""+mail+"\" AND password = \""+password+"\"");
                if (resultIdSet.next()){
                    System.out.println("Récupération réussie");
                    recuperation = true;
                }else{
                    System.out.println("Error");
                    recuperation = false;
                }
            } catch (SQLException | ExceptionCoThi19 throwables) {
                throwables.printStackTrace();
            }
            if (recuperation) {
                response.sendRedirect(request.getContextPath() + "/accueil");
            }else{
                response.sendRedirect(request.getContextPath() + "/connexion");
            }
        }else{
            response.sendRedirect(request.getContextPath() + "/connexion");
        }
    }
}
