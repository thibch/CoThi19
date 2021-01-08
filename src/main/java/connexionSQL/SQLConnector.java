package connexionSQL;

import beans.ActiviteBean;
import beans.LieuBean;
import beans.NotificationBean;
import beans.UserBean;
import exception.ExceptionCoThi19;
import exception.ExceptionConnexionSQL;
import exception.ExceptionRequeteSQL;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLConnector {
    private static volatile SQLConnector instance;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cothi19 ?useUnicode=true" +
            "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
            "serverTimezone=UTC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    private Connection con;

    private SQLConnector(){
        Statement stmt = null;

        try{
            boolean connexion = tryConnection();

            if(connexion){
                //STEP 4: Execute a query
                System.out.println("Creating statement...");
                stmt = con.createStatement();
                String sql;
                sql = "SELECT * FROM User";
                ResultSet rs = stmt.executeQuery(sql);

                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    String mail  = rs.getString("email");
                    String password = rs.getString("password");
                    boolean rang = rs.getBoolean("isAdmin");

                    //Display values
                    System.out.print("email : " + mail);
                    System.out.print(", password : " + password);
                    System.out.print(", rang : " + rang + "\n");
                }

                //STEP 6: Clean-up environment
                rs.close();
                stmt.close();
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException ignored){
            }// nothing we can do
        }//end try
    }

    /**
     * Tente de se connecter à la base de données.
     * Si échec la con est mis à null
     * @return vrai si la connexion est réussie, faux sinon
     */
    private boolean tryConnection(){
        boolean success;
        try {
            if(con == null || con.isClosed()){
                // On fait appel au driver
                Class.forName(JDBC_DRIVER);

                System.out.println("Connecting to database...");
                con = DriverManager.getConnection(DB_URL, USER, PASS); // On récupère la connexion avec la bdd
                System.out.println("Conexion réussie !");
            }
            success = true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("-------------------------------------------------\n" +
                    "ERROR : \n");
            System.out.println(e.getMessage());
            System.out.println("-------------------------------------------------");
            con = null;
            success = false;
        }
        return success;
    }

    public static SQLConnector getInstance() {
        if(instance == null) {
            synchronized (SQLConnector.class){
                if(instance == null) {
                    instance = new SQLConnector();
                }
            }
        }
        return instance;
    }

    /**
     * Execute la requete si possible, sinon retourne une exception
     * @param requeteSQL requete SQL à executer sur la base de données
     * @return le résultat de la requete exécutée
     * @throws ExceptionCoThi19 Exception de connexion ou d'exécution de la requête SQL
     */
    public ResultSet doRequest(String requeteSQL) throws ExceptionCoThi19 {
        ResultSet resultSet;
        if(tryConnection()){
            try {
                Statement stmt = con.createStatement();
                resultSet = stmt.executeQuery(requeteSQL);
            } catch (SQLException throwables) {
                throw new ExceptionRequeteSQL("Impossible d'exécuter la requete SQL", requeteSQL);
            }
        }else{
            throw new ExceptionConnexionSQL("Impossible de se connecter à la base de donnée");
        }
        return resultSet;
    }

    public int doInsert(String requeteSQL) throws ExceptionCoThi19 {
        int results = 0;
        if(tryConnection()){
            try {
                Statement stmt = con.createStatement();
                results = stmt.executeUpdate(requeteSQL);
            } catch (SQLException throwables) {
                throw new ExceptionRequeteSQL("Impossible d'exécuter la requete SQL", requeteSQL);
            }
        }else{
            throw new ExceptionConnexionSQL("Impossible de se connecter à la base de donnée");
        }
        return results;
    }

    public UserBean getUser(String mail, String password) throws ExceptionCoThi19{
        UserBean usr = null;
        //String rqString = "UPDATE User SET login = ?, password = ?, last_name = ?, first_name = ?, birthday = ?, is_admin = ?, is_infected = ? WHERE login = ?;";
        String rqString = "SELECT * FROM USER WHERE mail = ?, password = ?;";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(rqString);
            preparedStmt.setString(1, mail);
            preparedStmt.setString(2, password);
            ResultSet r = preparedStmt.executeQuery();
            usr = new UserBean(r.getString("email"), r.getInt("isInfected"));

        } catch (SQLException throwables) {
            throw new ExceptionRequeteSQL("Erreur lors de la récupération d'un utilisateur", "SELECT * FROM USER WHERE mail = " + mail + ", password = "+ password + ";");
        }
        return usr;
    }

    public List<LieuBean> getListeLieu(String name, String adress, int max) throws ExceptionCoThi19{

        ArrayList<LieuBean> listeLieux = new ArrayList<>();

        String rqString = "SELECT * " +
                "FROM PLACE " +
                "WHERE name LIKE ? AND adress LIKE ? " +
                "LIMIT " + max + ";";
        try {
            if(tryConnection()){
                PreparedStatement preparedStmt = con.prepareStatement(rqString);
                preparedStmt.setString(1, "%" + name + "%");
                preparedStmt.setString(2, "%" + adress + "%");
                ResultSet r = preparedStmt.executeQuery();
                while(r.next()){
                    listeLieux.add(new LieuBean(r.getInt("id_place"), r.getString("name"), r.getString("adress")));
                }
            }

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new ExceptionRequeteSQL("Erreur lors de la récupération des lieux", "SELECT * FROM PLACE WHERE name LIKE %" + name + "%, adress LIKE %" + adress + "% LIMIT " + max + ";");
        }
        return listeLieux;
    }

    public LieuBean createLieux(String name, String adress) throws ExceptionRequeteSQL {
        String rqString = "INSERT INTO Place(name, adress, gps_coordinates) VALUES(?, ?, ?);";
        String rqString2 = "Select * " +
                "FROM Place " +
                "WHERE name = ? AND adress = ?;";

        LieuBean lieu = null;

        try {
            PreparedStatement preparedStmt = con.prepareStatement(rqString);
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, adress);
            preparedStmt.setString(3, "0");
            preparedStmt.executeUpdate();

            PreparedStatement preparedStmt2 = con.prepareStatement(rqString2);
            preparedStmt2.setString(1, name);
            preparedStmt2.setString(2, adress);
            ResultSet r = preparedStmt2.executeQuery();
            r.next();
            lieu = new LieuBean(r.getInt("id_place"), r.getString("name"), r.getString("adress"));
        }catch (SQLException throwables) {
            throw new ExceptionRequeteSQL("Erreur lors de la creation d'un lieux",
                    "INSERT INTO Place(name, adress, gps_coordinates) VALUES(" + name + ", " + adress +", 0);");
        }

        return lieu;
    }

    public ActiviteBean createActivite(String email, int idPlace, Date date, LocalTime hourStart, LocalTime hourEnd) throws ExceptionRequeteSQL {
        String rqString = "INSERT INTO Activity(id_user, id_place, date, hourStart, hourEnd) VALUES(?, ?, ?, ?, ?);";
        String rqToGetActivity = "Select * FROM Activity WHERE id_user = ? AND id_place = ?;";

        String rqToGetUser = "Select * FROM User WHERE email = ?;";
        ActiviteBean act;
        int idUtilisateur = -1;
        try {
            PreparedStatement preparedStmt = con.prepareStatement(rqToGetUser);
            preparedStmt.setString(1, email);
            ResultSet r = preparedStmt.executeQuery();
            r.next();

            idUtilisateur = r.getInt("id_user");

            preparedStmt = con.prepareStatement(rqString);
            preparedStmt.setInt(1, idUtilisateur);
            preparedStmt.setInt(2, idPlace);
            preparedStmt.setDate(3, date);
            preparedStmt.setTime(4, Time.valueOf(hourStart));
            preparedStmt.setTime(5, Time.valueOf(hourEnd));
            preparedStmt.executeUpdate();

            preparedStmt = con.prepareStatement(rqToGetActivity);
            preparedStmt.setInt(1, idUtilisateur);
            preparedStmt.setInt(2, idPlace);
            r = preparedStmt.executeQuery();
            r.next();
            act = new ActiviteBean(r.getDate("date"), hourStart, hourEnd);
        }catch (SQLException throwables) {
            throw new ExceptionRequeteSQL("Erreur lors de la creation d'une activité",
                    "INSERT INTO Activity(id_user, id_place, date, hourStart, hourEnd) VALUES("+idUtilisateur+","+idPlace+","+date+", "+hourStart+ ","+hourEnd+");");
        }
        return act;
    }

    public LieuBean getLieu(String name, String adress) throws ExceptionRequeteSQL {
        String rqString = "SELECT * " +
                "FROM PLACE " +
                "WHERE name = ? AND adress = ? ";
        LieuBean lieu = null;
        try {
            if(tryConnection()){
                PreparedStatement preparedStmt = con.prepareStatement(rqString);
                preparedStmt.setString(1, name);
                preparedStmt.setString(2, adress);
                ResultSet r = preparedStmt.executeQuery();
                r.next();
                int id = r.getInt("id_place");
                String name1 = r.getString("name");
                String adress1 = r.getString("adress");
                lieu = new LieuBean(id, name1, adress1);
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new ExceptionRequeteSQL("Erreur lors de la récupération d'un lieu", "SELECT * FROM PLACE WHERE name = " + name + ", adress = " + adress + ";");
        }
        return lieu;
    }

    public Collection<NotificationBean> getListeNotif(String email, int max) throws ExceptionRequeteSQL {

        Collection<NotificationBean> collection = new ArrayList<>();

        String rqString = "SELECT * " +
                "FROM Notification JOIN User " +
                "WHERE email = ? AND id_user = id_receive " +
                "ORDER BY id_notif DESC " +
                "LIMIT " + max + " ";

        try {
            if(tryConnection()){
                PreparedStatement preparedStmt = con.prepareStatement(rqString);
                preparedStmt.setString(1, email);
                ResultSet r = preparedStmt.executeQuery();
                while(r.next()){
                    collection.add(new NotificationBean(r.getInt("id_notif"),
                            r.getInt("id_receive"),
                            r.getInt("id_ask"),
                            r.getString("content"),
                            r.getBoolean("seen")));
                }
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new ExceptionRequeteSQL("Erreur lors de la récupération de notification",
                    "SELECT *  FROM Notification JOIN User WHERE email = " + email + " AND id_user = id_receive LIMIT" + max);
        }
        return collection;
    }

    public void setNotifAsSeen(Collection<NotificationBean> notifications) throws ExceptionRequeteSQL {
        String rqString = "UPDATE Notification " +
                "SET seen = 1 " +
                "WHERE id_notif = ? ";

        if(tryConnection() && notifications != null){
            PreparedStatement preparedStmt = null;
            try {
                preparedStmt = con.prepareStatement(rqString);
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
                throw new ExceptionRequeteSQL("Erreur lors de la création du Statement notifications", rqString);
            }
            for(NotificationBean notif : notifications){
                try {
                    preparedStmt.setInt(1, notif.getIdNotif());
                    preparedStmt.executeUpdate();
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                    throw new ExceptionRequeteSQL("Erreur lors de l'update d'une notif",
                            "UPDATE Notification " +
                                    "SET seen = 1 " +
                                    "WHERE id_notif = " + notif.getIdNotif());
                }
            }
        }
    }

    public void delNotif(int notif, UserBean usr) throws ExceptionRequeteSQL {
        String rqString = "Select * FROM User\n" +
                "WHERE email = ?";
        String rqString2 = "DELETE FROM Notification\n" +
                "WHERE id_notif = ? AND id_receive = ?";


        try {
            if(tryConnection()){
                PreparedStatement preparedStmt = con.prepareStatement(rqString);
                preparedStmt.setString(1, usr.getMail());
                ResultSet r = preparedStmt.executeQuery();
                int idReceiv = -1;
                if(r.next()){
                    idReceiv = r.getInt("id_user");
                }

                preparedStmt = con.prepareStatement(rqString2);
                preparedStmt.setInt(1, notif);
                preparedStmt.setInt(2, idReceiv);
                preparedStmt.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new ExceptionRequeteSQL("Erreur lors de la récupération d'un lieu",
                    "SELECT *  FROM Notification JOIN User WHERE email = " + " AND id_user = id_receive LIMIT" );
        }
    }

    public boolean setUserPositive(UserBean usr) throws ExceptionRequeteSQL {
        String rqString = "UPDATE User SET isInfected = 1 \n" +
                            "WHERE email = ?";
        usr.setInfected(true);
        String places = "SELECT id_place, date, hourStart, hourEnd \n" +
                "FROM `user` JOIN activity USING (id_user) JOIN place USING (id_place) \n" +
                "WHERE email = ? AND date >= DATE_SUB(Date(NOW()), INTERVAL 10 DAY)";

        String users = "SELECT DISTINCT id_user, place.name, adress \n"+
                "FROM `user` JOIN activity USING (id_user) JOIN place USING (id_place) \n" +
                "WHERE id_place = ? AND (date = ?) AND " +
                "((hourStart >= ? AND hourStart <= ?) OR (hourEnd >= ? AND hourEnd <= ?) OR (hourStart <= ? AND hourEnd >= ?))" +
                "AND NOT (email = ?) ";

        /*

        SELECT DISTINCT id_user
FROM `user` JOIN activity USING (id_user) JOIN place USING (id_place)
WHERE id_place = 9 AND (date = DATE(2021-01-01)) AND
((hourStart >= TIME(00:00:00) AND hourStart <= TIME(23:00:00)) OR (hourEnd >= TIME(00:00:00) AND hourEnd <= TIME(23:00:00)) OR (hourStart <= TIME(00:00:00) AND hourEnd >= TIME(23:00:00)))
         */

        String casContact = "INSERT INTO NOTIFICATION(id_receive, content) \n" +
                "VALUES (?, ?)";

        try {
            if(tryConnection()){
                PreparedStatement preparedStmt = con.prepareStatement(rqString);
                preparedStmt.setString(1, usr.getMail());
                preparedStmt.executeUpdate();

                PreparedStatement findPlaces = con.prepareStatement(places);
                findPlaces.setString(1, usr.getMail());
                ResultSet r = findPlaces.executeQuery();

                PreparedStatement findUsersContact;
                PreparedStatement notifUsers;
                Time timeStart;
                Time timeEnd;
                ResultSet resUsers;
                while(r.next()){
                    //Récupération lieux et date :


                    findUsersContact = con.prepareStatement(users);
                    int id = r.getInt("id_place");
                    findUsersContact.setInt(1, id); // id
                    Date date = r.getDate("date");
                    findUsersContact.setDate(2, date); // date
                    timeStart = r.getTime("hourStart");
                    timeEnd = r.getTime("hourEnd");
                    findUsersContact.setTime(3, timeStart); // bounds 1 hourStart
                    findUsersContact.setTime(4, timeEnd); // bounds 2 hourStart
                    findUsersContact.setTime(5, timeStart); // bounds 1 hourEnd
                    findUsersContact.setTime(6, timeEnd); // bounds 2 hourEnd
                    findUsersContact.setTime(7, timeStart); // bounds 1 hourEnd
                    findUsersContact.setTime(8, timeEnd); // bounds 2 hourEnd
                    findUsersContact.setString(9, usr.getMail()); // email de l'utilisateur qui a émet la requête

                    resUsers = findUsersContact.executeQuery();

                    notifUsers = con.prepareStatement(casContact);
                    while(resUsers.next()) {
                        int id_u = resUsers.getInt("id_user");
                        String name = resUsers.getString("place.name");
                        String adress = resUsers.getString("adress");
                        notifUsers.setInt(1, id_u);
                        notifUsers.setString(2, "Vous avez été cas contact ! <br>Dans cet établissement : " + name + " adresse : " + adress);
                        notifUsers.executeUpdate();
                    }
                }
                return true;
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new ExceptionRequeteSQL("Erreur lors de la tentative de notifier les users",
                    "Pleins de requetes SQL" );
        }
        return false;
    }

    public Collection<ActiviteBean> getActivity(UserBean usr, int maxNumber, int fromMaxDay) throws ExceptionRequeteSQL {
        String rqString = "SELECT id_place, date, hourStart, hourEnd, place.name, adress FROM Activity JOIN USER USING(id_user) JOIN PLACE USING(id_place) \n " +
                "WHERE email = ? AND date >= DATE_SUB(Date(NOW()), INTERVAL " + fromMaxDay + " DAY) \n " +
                "ORDER BY date DESC \n " +
                "LIMIT " + maxNumber;

        ArrayList<ActiviteBean> activities = new ArrayList<>();

        try {
            if (tryConnection()) {
                PreparedStatement findPlaces = con.prepareStatement(rqString);
                findPlaces.setString(1, usr.getMail());
                ResultSet r = findPlaces.executeQuery();
                ActiviteBean act;
                while(r.next()){
                    act = new ActiviteBean(r.getDate("date"), r.getTime("hourStart").toLocalTime(), r.getTime("hourEnd").toLocalTime());
                    act.setLieu(new LieuBean(r.getInt("id_place"), r.getString("name"), r.getString("adress")));
                    activities.add(act);
                }
            }
        } catch (SQLException throwables) {
            System.err.println(throwables.getMessage());
            activities.clear();
        }
        return activities;
    }

}
