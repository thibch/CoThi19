package beans;

public class LieuBean {
    private int id;
    private String name;
    private String adress;

    public LieuBean(int id, String name, String adress) {
        this.id = id;
        this.name = name;
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }
}
