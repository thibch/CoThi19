package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

public class ServletCreateActivity extends HttpServlet {


    public static final String VUE = "/CreateActivity.jsp";

    public ServletCreateActivity(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String date = req.getParameter("date");
        System.out.println(date);
        if(date.matches("^[0-9]{4}-(0[0-9]|1[0-2])-([1-2][0-9]|3[01])}$")){
            //
        }

        String heureDebString = req.getParameter("heureDebut");
        int heureDebut = -1;
        if(heureDebString != null && heureDebString.matches("^1?[0-9]$|^[2]?[0-3]$")){
            heureDebut = Integer.parseInt(heureDebString);
        }
        String minuteDebString = req.getParameter("minuteDebut");
        int minuteDebut = -1;
        if(minuteDebString != null && minuteDebString.matches("^[1-5]?[0-9]$")){
            minuteDebut = Integer.parseInt(minuteDebString);
        }
        String heureFinString = req.getParameter("heureFin");
        int heureFin = -1;
        if(heureFinString != null && heureFinString.matches("^1?[0-9]$|^[2]?[0-3]$")){
            heureFin = Integer.parseInt(heureFinString);
        }

        String minuteFinString = req.getParameter("minuteFin");
        int minuteFin = -1;
        if(minuteFinString != null && minuteFinString.matches("^[1-5]?[0-9]$")){
            minuteFin = Integer.parseInt(minuteFinString);
        }

        String rechercheLieu = req.getParameter("rechercheLieu");

        req.setAttribute("date", date);
        req.setAttribute("heureDebut", heureDebut);
        req.setAttribute("minuteDebut", minuteDebut);
        req.setAttribute("heureFin", heureFin);
        req.setAttribute("minuteFin", minuteFin);

        this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
