package connexionSQL;

import beans.LieuBean;
import beans.UserBean;
import exception.ExceptionCoThi19;
import exception.ExceptionConnexionSQL;
import exception.ExceptionRequeteSQL;

import java.sql.*;
import java.util.ArrayList;
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
            usr = new UserBean(r.getString("email"));

        } catch (SQLException throwables) {
            throw new ExceptionRequeteSQL("Erreur lors de la récupération d'un utilisateur", "SELECT * FROM USER WHERE mail = " + mail + ", password = "+ password + ";");
        }
        return usr;
    }

    public List<LieuBean> getListeLieu(String name, String adress) throws ExceptionCoThi19{

        ArrayList<LieuBean> listeLieux = new ArrayList<>();

        String rqString = "SELECT * FROM PLACE WHERE name LIKE ?, adress LIKE ?;";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(rqString);
            preparedStmt.setString(1, "%" + name + "%");
            preparedStmt.setString(2, "%" + adress + "%");
            ResultSet r = preparedStmt.executeQuery();
            do{
                listeLieux.add(new LieuBean(r.getString("name"), r.getString("adress")));
            }while(r.next());

        } catch (SQLException throwables) {
            throw new ExceptionRequeteSQL("Erreur lors de la récupération d'un utilisateur", "SELECT * FROM PLACE WHERE name LIKE " + name + ", adress LIKE " + adress + ";");
        }
        return listeLieux;
    }
}
