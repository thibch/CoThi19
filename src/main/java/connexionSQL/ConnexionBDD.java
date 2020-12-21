package connexionSQL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Roberge-Mentec Corentin
 */

public final class ConnexionBDD {

    private static volatile ConnexionBDD instance;
    private Connection cnx;

    private ConnexionBDD() {
        try {

            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/FormationDB");
            cnx = ds.getConnection();


        } catch (NamingException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static synchronized ConnexionBDD getInstance() {
        if(instance==null) {
            instance = new ConnexionBDD();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

    public void closeCnx() throws SQLException{
        if(cnx!=null){
            cnx.close();
            instance=null;
        }
    }
}
