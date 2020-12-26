package beans;

public class LieuBean {
    private String name;
    private String adress;

    public LieuBean(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }


    public String getAdress() {
        return adress;
    }
}
