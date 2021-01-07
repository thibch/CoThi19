package beans;

/**
 * @author Roberge-Mentec Corentin
 */


public class UserBean {

    private String mail;
    private boolean admin;
    private boolean isInfected;

    public UserBean(String mail, int isInfected){
        this.mail = mail;
        admin = false;
        System.out.println("IS INFECTED :" + isInfected);
        this.isInfected = isInfected == 1;
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

    public boolean isInfected() {
        return isInfected;
    }

    public void setInfected(boolean infected) {
        isInfected = infected;
    }
}
