package connexionSQL;

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
            // On fait appel au driver
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            con = DriverManager.getConnection(DB_URL, USER, PASS); // On récupère la connexion avec la bdd
            System.out.println("Conexion réussie !");


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
        System.out.println("End of Connection...");
    }

    public static SQLConnector getInstance() {
        if(instance == null) {
            synchronized (SQLConnector.class){
                instance = new SQLConnector();
            }
        }
        return instance;
    }
/*
    public UserBean getUser(String email, String password){

        UserBean user = null;

        String rqString = "Select * from user where email ='" + email + "' and password='" + password + "'";
        ResultSet res = doRequest(rqString);

        try{
            user = new UserBean();
            while (res.next()){
                user.setId(res.getInt("id"));
                user.setEmail(res.getString("email"));
                user.setPassword(res.getString("password"));
                user.setRang(res.getString("rang"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    public UserBean setNewUser(String email, String password){
        String rqString = "Insert into User(email, password, rang) VALUES(?, ?, ?);";

        try{
            PreparedStatement preparedStmt = con.prepareStatement(rqString);
            preparedStmt.setString (1, email);
            preparedStmt.setString(2, password);
            preparedStmt.setString (3, "user");
            preparedStmt.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return getUser(email, password);
    }
*/

    public ResultSet doRequest(String sql_string){
        ResultSet results = null;
        try {
            Statement stmt = con.createStatement();
            results = stmt.executeQuery(sql_string);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return results;
    }

}
