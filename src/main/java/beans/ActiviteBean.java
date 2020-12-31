package beans;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class ActiviteBean {

    private Date date;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    public ActiviteBean(Date date, LocalTime heureDebut, LocalTime heureFin) {
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public Date getDate() {
        return date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }
}
