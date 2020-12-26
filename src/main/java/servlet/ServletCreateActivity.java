package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

public class ServletCreateActivity extends HttpServlet {


    private static final String ATT_SESSION_USER = "userConnected";
    public static final String VUE = "/CreateActivity.jsp";
    public static final String VUE_ERROR = "/error404";

    public ServletCreateActivity(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        if(session.getAttribute(ATT_SESSION_USER) != null){

            String date = req.getParameter("date");
            if(date != null && date.matches("^[0-9]{4}-([0-9]|1[0-2])-(([3][0-1])|([1-2]?[0-9]))$")){
                LocalDate datesql2 = LocalDate.parse(date);
                System.out.println(datesql2);
            }else{
                date = "";
            }

            String heureDebString = req.getParameter("heureDebut");
            int heureDebut = -1;
            if(heureDebString != null && heureDebString.matches("^1?[0-9]$|^[2]?[0-3]$")){
                System.out.println("Yep, c'est correct");
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

            req.setAttribute("date", date);
            req.setAttribute("heureDebut", heureDebut);
            req.setAttribute("minuteDebut", minuteDebut);
            req.setAttribute("heureFin", heureFin);
            req.setAttribute("minuteFin", minuteFin);

            String rechercheLieuNom = req.getParameter("rechercheLieu");
            String rechercheLieuAdresse = req.getParameter("rechercheLieu");


            this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
        }else{
            this.getServletContext().getRequestDispatcher(VUE_ERROR).forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);

    }

}
