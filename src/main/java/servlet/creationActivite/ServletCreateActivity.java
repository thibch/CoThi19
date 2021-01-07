package servlet.creationActivite;

import beans.UserBean;
import servlet.notif.ServletNotif;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

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
        doPost(req, resp);
    }

    public static int getTimeRegex(HttpServletRequest req, String parametersName, String regex) {
        String timeString = req.getParameter(parametersName);
        int time = -1;
        if (timeString != null && timeString.matches(regex)) {
            time = Integer.parseInt(timeString);
        }
        return time;
    }

    public static String getDate(HttpServletRequest req) {
        String date;
        date = req.getParameter("date");
        if(date != null && date.matches("^[0-9]{4}-(0[1-9]|1[0-2])-(([3][0-1])|([0-2]?[0-9]))$")){
            LocalDate datesql2 = LocalDate.parse(date);
            //System.out.println(datesql2);
        }else{
            date = "";
        }
        return date;
    }

    public static String getRegexHeure(){
        return "^1?[0-9]$|^[2]?[0-3]$";
    }
    public static String getRegexMinute(){
        return "^[1-5]?[0-9]$";
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        if(session.getAttribute(ATT_SESSION_USER) != null){
            req.setAttribute("notifs", ServletNotif.getNotif((UserBean)req.getSession().getAttribute(ATT_SESSION_USER), 100));
            String date = null;
            int heureDebut = 0;
            int minuteDebut = 0;
            int heureFin = 0;
            int minuteFin = 0;
            boolean noError = true;
            boolean firstTime = !(req.getParameter("estActif") != null && req.getParameter("estActif").equals("1"));

            if(!firstTime){
                date = getDate(req);

                heureDebut = getTimeRegex(req, "heureDebut", getRegexHeure());
                minuteDebut = getTimeRegex(req, "minuteDebut", getRegexMinute());
                heureFin = getTimeRegex(req, "heureFin", getRegexHeure());
                minuteFin = getTimeRegex(req, "minuteFin", getRegexMinute());

                if(heureFin < heureDebut || heureFin == heureDebut && minuteFin <= minuteDebut){
                    minuteFin = -1;
                    heureFin = -1;
                }
                noError = !date.equals("") && heureDebut != 1 && heureFin != -1 && minuteDebut != -1 && minuteFin != -1;
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

}
