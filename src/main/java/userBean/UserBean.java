package userBean;

/**
 * @author Roberge-Mentec Corentin
 */


public class UserBean {

    private String mail;

    public UserBean(String mail){
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
