package connexionSQL;

import exception.ExceptionCoThi19;
import exception.ExceptionConnexionSQL;
import exception.ExceptionRequeteSQL;

import java.sql.*;

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

}
