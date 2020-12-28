package servlet;

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
            String date = null;
            int heureDebut = 0;
            int minuteDebut = 0;
            int heureFin = 0;
            int minuteFin = 0;

            String rechercheLieuNom = null;
            String rechercheLieuAdresse = null;

            List<LieuBean> lieux = null;

            // TODO : check des heures

            if(req.getParameter("estActif") != null && req.getParameter("estActif").equals("1")){
                date = req.getParameter("date");
                if(date != null && date.matches("^[0-9]{4}-(0[1-9]|1[0-2])-(([3][0-1])|([0-2]?[0-9]))$")){
                    LocalDate datesql2 = LocalDate.parse(date);
                    //System.out.println(datesql2);
                }else{
                    date = "";
                }

                String heureDebString = req.getParameter("heureDebut");
                heureDebut = -1;
                if(heureDebString != null && heureDebString.matches("^1?[0-9]$|^[2]?[0-3]$")){
                    heureDebut = Integer.parseInt(heureDebString);
                }
                String minuteDebString = req.getParameter("minuteDebut");
                minuteDebut = -1;
                if(minuteDebString != null && minuteDebString.matches("^[1-5]?[0-9]$")){
                    minuteDebut = Integer.parseInt(minuteDebString);
                }
                String heureFinString = req.getParameter("heureFin");
                heureFin = -1;
                if(heureFinString != null && heureFinString.matches("^1?[0-9]$|^[2]?[0-3]$")){
                    heureFin = Integer.parseInt(heureFinString);
                }

                String minuteFinString = req.getParameter("minuteFin");
                minuteFin = -1;
                if(minuteFinString != null && minuteFinString.matches("^[1-5]?[0-9]$")){
                    minuteFin = Integer.parseInt(minuteFinString);
                }


                rechercheLieuNom = req.getParameter("rechercheLieuNom");
                rechercheLieuAdresse = req.getParameter("rechercheLieuAdresse");

                if(rechercheLieuNom != null && rechercheLieuAdresse != null) { // Si on fait une recherche d'abord
                    if (!rechercheLieuNom.equals("") || !rechercheLieuAdresse.equals("")) {
                        try {
                            lieux = SQLConnector.getInstance().getListeLieu(rechercheLieuNom, rechercheLieuAdresse, 10);
                            System.out.println(lieux);
                        } catch (ExceptionCoThi19 exceptionCoThi19) {
                            System.out.println("Impossible de récupérer les lieux");
                            System.out.println(exceptionCoThi19.getMessage());
                            rechercheLieuNom = "ERROR";
                            rechercheLieuAdresse = "ERROR";
                        }
                    }
                }else{

                    if(req.getParameter("creerLieu") == null){
                        //Création de lieux
                    }
                }
            }
            req.setAttribute("date", date);
            req.setAttribute("heureDebut", heureDebut);
            req.setAttribute("minuteDebut", minuteDebut);
            req.setAttribute("heureFin", heureFin);
            req.setAttribute("minuteFin", minuteFin);

            req.setAttribute("rechercheLieuNom", rechercheLieuNom);
            req.setAttribute("rechercheLieuAdresse", rechercheLieuAdresse);

            req.setAttribute("rechercheLieux", lieux);

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
