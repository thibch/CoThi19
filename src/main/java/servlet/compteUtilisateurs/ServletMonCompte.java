package servlet.compteUtilisateurs;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletMonCompte extends HttpServlet {

    private static final String VUE = "/monCompte.jsp";

    public ServletMonCompte(){
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}
