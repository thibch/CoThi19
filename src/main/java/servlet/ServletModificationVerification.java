package servlet;

import connexionSQL.SQLConnector;
import exception.ExceptionCoThi19;
import beans.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ServletModificationVerification")
public class ServletModificationVerification extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean verification = true;
        String name, surname, password, newPassword, confirmedPassword, birthDateString;
        String bdName, bdSurname, bdPassword, bdBirthdate;
        name = request.getParameter("name");
        surname = request.getParameter("surname");
        password = request.getParameter("password");
        newPassword = request.getParameter("newPassword");
        confirmedPassword = request.getParameter("confirmedPassword");
        birthDateString = request.getParameter("birthDate");
        Date birthDate = Date.valueOf(birthDateString);

        UserBean user = (UserBean) request.getSession().getAttribute("userConnected");

        SQLConnector sql = SQLConnector.getInstance();

        try {
            ResultSet resultSet = sql.doRequest("Select * from User WHERE email = \""+user.getMail()+"\"");
            if (resultSet.next()){
                bdName = resultSet.getString("name");
                bdSurname = resultSet.getString("surname");
                bdPassword = resultSet.getString("password");
                bdBirthdate = resultSet.getString("birth_date");
            }else{
                bdName = "";
                bdSurname = "";
                bdPassword = "";
                bdBirthdate = "";
            }
            if (!bdName.equals(name) && !name.equals("")) {
                int resultInsertSet = sql.doInsert("UPDATE User SET name = \""+name+"\" where email = \""+user.getMail()+"\";");
            }
            if (!bdSurname.equals(surname) && !surname.equals("")) {
                int resultInsertSet = sql.doInsert("UPDATE User SET surname = \""+surname+"\" where email = \""+user.getMail()+"\";");
            }
            if (!bdBirthdate.equals(birthDateString) && !birthDateString.equals("")) {
                int resultInsertSet = sql.doInsert("UPDATE User SET birth_date = '"+birthDate+"' where email = \""+user.getMail()+"\";");
            }
            System.out.println(bdPassword);
            System.out.println(password);
            if (bdPassword.equals(password) && !password.equals("")) {
                if (!newPassword.equals(password) && !newPassword.equals("")){
                    if (confirmedPassword.equals(newPassword)){
                        int resultInsertSet = sql.doInsert("UPDATE User SET password = \""+confirmedPassword+"\" where email = \""+user.getMail()+"\";");
                    }
                }
            }
        } catch (SQLException | ExceptionCoThi19 throwables) {
            throwables.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/monCompte");
    }
}
