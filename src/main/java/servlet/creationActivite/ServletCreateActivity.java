package servlet.creationActivite;

import beans.LieuBean;
import connexionSQL.SQLConnector;
import exception.ExceptionCoThi19;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServletCreateActivity extends HttpServlet {


    private static final String ATT_SESSION_USER = "userConnected";
    public static final String VUE = "/createActivity.jsp";
    public static final String VUE_NEXT = "/createLieux.jsp";
    public static final String VUE_ERROR = "/error404";

    public ServletCreateActivity(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        if(session.getAttribute(ATT_SESSION_USER) != null){
            String date = null;
            int heureDebut = 0;
            int minuteDebut = 0;
            int heureFin = 0;
            int minuteFin = 0;
            boolean noError = true;
            boolean firstTime = !(req.getParameter("estActif") != null && req.getParameter("estActif").equals("1"));

            // TODO : check des heures

            if(!firstTime){
                date = req.getParameter("date");
                if(date != null && date.matches("^[0-9]{4}-(0[1-9]|1[0-2])-(([3][0-1])|([0-2]?[0-9]))$")){
                    LocalDate datesql2 = LocalDate.parse(date);
                    //System.out.println(datesql2);
                }else{
                    date = "";
                    noError = false;
                }

                String heureDebString = req.getParameter("heureDebut");
                heureDebut = -1;
                if(heureDebString != null && heureDebString.matches("^1?[0-9]$|^[2]?[0-3]$")){
                    heureDebut = Integer.parseInt(heureDebString);
                }else{
                    noError = false;
                }
                String minuteDebString = req.getParameter("minuteDebut");
                minuteDebut = -1;
                if(minuteDebString != null && minuteDebString.matches("^[1-5]?[0-9]$")){
                    minuteDebut = Integer.parseInt(minuteDebString);
                }else{
                    noError = false;
                }
                String heureFinString = req.getParameter("heureFin");
                heureFin = -1;
                if(heureFinString != null && heureFinString.matches("^1?[0-9]$|^[2]?[0-3]$")){
                    heureFin = Integer.parseInt(heureFinString);
                }else{
                    noError = false;
                }

                String minuteFinString = req.getParameter("minuteFin");
                minuteFin = -1;
                if(minuteFinString != null && minuteFinString.matches("^[1-5]?[0-9]$")){
                    minuteFin = Integer.parseInt(minuteFinString);
                }else{
                    noError = false;
                }
            }
            req.setAttribute("date", date);
            req.setAttribute("heureDebut", heureDebut);
            req.setAttribute("minuteDebut", minuteDebut);
            req.setAttribute("heureFin", heureFin);
            req.setAttribute("minuteFin", minuteFin);

            if(noError && !firstTime){
                this.getServletContext().getRequestDispatcher(VUE_NEXT).forward(req, resp);
            }else{
                this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
            }

        }else{
            this.getServletContext().getRequestDispatcher(VUE_ERROR).forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);

    }

}
