package servlet.creationActivite;

import beans.ActiviteBean;
import beans.LieuBean;
import beans.UserBean;
import connexionSQL.SQLConnector;
import exception.ExceptionCoThi19;
import exception.ExceptionRequeteSQL;
import servlet.notif.ServletNotif;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

public class ServletRechercheLieux extends HttpServlet {

    private static final String ATT_SESSION_USER = "userConnected";
    public static final String VUE = "/createLieux.jsp";
    public static final String VUE_SUCCESS = "/summaryActivity.jsp";
    public static final String VUE_ERROR = "/error404";

    public ServletRechercheLieux(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        if(session.getAttribute(ATT_SESSION_USER) != null){
            req.setAttribute("notifs", ServletNotif.getNotif((UserBean)session.getAttribute(ATT_SESSION_USER), 100));
            String date = ServletCreateActivity.getDate(req);
            int heureDebut = ServletCreateActivity.getTimeRegex(req, "heureDebut", ServletCreateActivity.getRegexHeure());
            int minuteDebut = ServletCreateActivity.getTimeRegex(req, "minuteDebut", ServletCreateActivity.getRegexMinute());
            int heureFin = ServletCreateActivity.getTimeRegex(req, "heureFin", ServletCreateActivity.getRegexHeure());
            int minuteFin = ServletCreateActivity.getTimeRegex(req, "minuteFin", ServletCreateActivity.getRegexMinute());
            if(heureFin < heureDebut || heureFin == heureDebut && minuteFin <= minuteDebut){
                minuteFin = -1;
                heureFin = -1;
            }

            String rechercheLieuNom = null;
            String rechercheLieuAdresse = null;

            List<LieuBean> lieux = null;
            LieuBean lieu = null;
            ActiviteBean activite = null;

            boolean creationReussie = false;
            boolean erreur = date.equals("") || heureDebut == -1 || heureFin == -1 || minuteDebut == -1 || minuteFin == -1;

            if(req.getParameter("estActif") != null && req.getParameter("estActif").equals("1")){

                rechercheLieuNom = req.getParameter("rechercheLieuNom");
                rechercheLieuAdresse = req.getParameter("rechercheLieuAdresse");

                if(rechercheLieuNom != null && rechercheLieuAdresse != null) { // Si on fait une recherche d'abord
                    if (!rechercheLieuNom.equals("") || !rechercheLieuAdresse.equals("")) {
                        try {
                            lieux = SQLConnector.getInstance().getListeLieu(rechercheLieuNom, rechercheLieuAdresse, 10);
                        } catch (ExceptionCoThi19 exceptionCoThi19) {
                            System.err.println("Impossible de récupérer les lieux");
                            System.err.println(exceptionCoThi19.getMessage());
                            rechercheLieuNom = "ERROR";
                            rechercheLieuAdresse = "ERROR";
                        }
                    }
                }else{ // Choix ou création lieux
                    String choixLieux = req.getParameter("choixLieux");
                    if(choixLieux != null){ // Choix du lieux
                        String name = req.getParameter(choixLieux + "name");
                        String adress = req.getParameter(choixLieux + "adress");
                        try {
                            lieu = SQLConnector.getInstance().getLieu(name, adress);
                            creationReussie = lieu != null;
                        } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
                            erreur = true;
                            System.err.println(exceptionRequeteSQL.getMessage());
                        }
                    }else{
                        //Création de lieux
                        if(req.getParameter("createLieux") != null){
                            if(req.getParameter("createLieux").equals("1")){
                                req.setAttribute("createLieux", "1");
                            }else{
                                if(req.getParameter("createLieux").equals("2")){
                                    String name = req.getParameter("createPlaceName");
                                    String adresse = req.getParameter("createPlaceAdress");
                                    String cityName = req.getParameter("createPlaceCityName");
                                    if(name.equals("") && adresse.equals("") && cityName.equals("")){
                                        erreur = true;
                                    }else{
                                        try {
                                            lieu = SQLConnector.getInstance().createLieux(name, adresse + ", " + cityName);
                                            if(lieu != null){
                                                creationReussie = true;
                                            }else{
                                                erreur = true;
                                            }
                                        } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
                                            erreur = true;
                                            System.err.println(exceptionRequeteSQL.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(creationReussie){
                        UserBean usr =  (UserBean)session.getAttribute(ATT_SESSION_USER);
                        try {
                            /*System.out.println(usr.getMail());
                            System.out.println(lieu.getId());
                            System.out.println(Date.valueOf(date));
                            System.out.println(LocalTime.of(heureDebut, minuteDebut, 0, 0));
                            System.out.println(LocalTime.of(heureFin, minuteFin, 0, 0));
                            */activite = SQLConnector.getInstance().createActivite(usr.getMail(), lieu.getId(), Date.valueOf(date), LocalTime.of(heureDebut, minuteDebut, 0, 0), LocalTime.of(heureFin, minuteFin, 0, 0));
                        } catch (ExceptionRequeteSQL exceptionRequeteSQL) {
                            creationReussie = false;
                            erreur = true;
                            exceptionRequeteSQL.printStackTrace();
                        }
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

            if(creationReussie){
                req.setAttribute("activite", activite);
                req.setAttribute("lieu", lieu);
                this.getServletContext().getRequestDispatcher(VUE_SUCCESS).forward(req, resp);
            }else{
                if(erreur){
                    req.setAttribute("error", "Une erreur s'est produite !");
                    this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
                }else{
                    this.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
                }
            }
        }else{
            this.getServletContext().getRequestDispatcher(VUE_ERROR).forward(req, resp);
        }
    }
}
