package beans;

import java.sql.Date;
import java.sql.Time;

public class ActiviteBean {

    private Date date;
    private Time heureDebut;
    private Time heureFin;

    public ActiviteBean(Date date, Time heureDebut, Time heureFin) {
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public Date getDate() {
        return date;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }
}
