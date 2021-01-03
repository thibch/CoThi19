package beans;

/**
 * @author Roberge-Mentec Corentin
 */


public class UserBean {

    private String mail;
    private boolean admin;

    public UserBean(String mail){
        this.mail = mail;
        admin = false;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin == 1;
    }
}
