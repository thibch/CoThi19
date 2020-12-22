package servlet;

import connexionSQL.SQLConnector;
import exception.ExceptionCoThi19;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServletTestBDD extends HttpServlet {

    public static final String VUE = "";

    public ServletTestBDD(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>TESTO</title></head>");
        out.println("<body>");
        out.println("<p>Connexion à la base de données : </br>");

        System.out.println("Test connexion : ");

        SQLConnector sql = SQLConnector.getInstance(); // On récupère la connexion sql
        String i = null;
        try {
            ResultSet resultSet = sql.doRequest("Select name from User where id_user = 1");
            resultSet.next();
            i = resultSet.getString("name"); // On récupère le résultat

            System.out.println(i);
            out.println(i);

        } catch (SQLException | ExceptionCoThi19 throwables) {
            System.out.println(throwables.getMessage());
            out.println("</br>");
            out.println(throwables.getMessage());
        }

        out.println("</p>");

        out.println("</body>");
        out.println("</html>");
        out.close();
        //this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
