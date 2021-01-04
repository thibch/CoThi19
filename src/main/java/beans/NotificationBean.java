package beans;

import java.util.Objects;

public class NotificationBean {

    private int idNotif;
    private int idReceiv;
    private Integer idAsk;
    private String content;
    private boolean seen;

    public NotificationBean(int idNotif, int idReceiv, Integer idAsk, String content, boolean seen) {
        this.idNotif = idNotif;
        this.idReceiv = idReceiv;
        this.idAsk = idAsk;
        this.content = content;
        this.seen = seen;
    }

    public int getIdNotif() {
        return idNotif;
    }

    public int getIdReceiv() {
        return idReceiv;
    }

    public Integer getIdAsk() {
        return idAsk;
    }

    public String getContent() {
        return content;
    }

    public boolean isSeen() {
        return seen;
    }

    @Override
    public String toString() {
        return "NotificationBean{" +
                "idNotif=" + idNotif +
                ", idReceiv=" + idReceiv +
                ", idAsk=" + idAsk +
                ", content='" + content + '\'' +
                ", seen=" + seen +
                '}';
    }
}
