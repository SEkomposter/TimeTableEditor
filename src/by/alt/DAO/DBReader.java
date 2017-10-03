package by.alt.DAO;
//import by.alt.Object.PropReader;

import by.alt.Object.PropReader;
import com.mysql.jdbc.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class DBReader{
    String parameter1="db.connect.host",parameter2="db.connect.port",parameter3="db.connect.user",parameter4="db.connect.pass",parameter5="db.connect.driver";
    static Connection connection;
    public Connection ConnectToDB()throws SQLException{
        PropReader propReader = new PropReader();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException exc){
            exc.printStackTrace();
        }
        connection= DriverManager.getConnection("jdbc:mysql://" +propReader.readCommonProps(parameter1)+":"+propReader.readCommonProps(parameter2)+"/tc-db-main",propReader.readCommonProps(parameter3),propReader.readCommonProps(parameter4));
        return connection;
    }
    public void closeConnectionToDB() throws SQLException{
        connection.close();
    }
    public ResultSet QueryToDB(String query)throws SQLException{
        Statement stm = this.ConnectToDB().createStatement();
        return (com.mysql.jdbc.ResultSet)stm.executeQuery(query);
    }
}
