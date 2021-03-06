package by.alt.DAO;
//import by.alt.Object.PropReader;

import by.alt.Object.Logger;
import by.alt.Object.PropReader;
import com.mysql.jdbc.ResultSet;

import java.sql.*;
import java.util.Iterator;

public class DBReader{
    static String parameter1="db.connect.host",parameter2="db.connect.port",parameter3="db.connect.user",parameter4="db.connect.pass",parameter5="db.connect.driver";
    static Connection connection;
    public Connection ConnectToDB()throws SQLException{
        PropReader propReader = new PropReader();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException exc){
            new Logger().pushToScreenNlog(exc,exc.getClass());
        }
        connection= DriverManager.getConnection("jdbc:mysql://" +propReader.readCommonProps(parameter1)+":"+propReader.readCommonProps(parameter2)+"/tc-db-main?useUnicode=true&characterEncoding=utf-8",propReader.readCommonProps(parameter3),propReader.readCommonProps(parameter4));
        return connection;
    }
    public static void closeConnectionToDB() throws SQLException{
        connection.close();
    }
    public ResultSet QueryToDB(String query)throws SQLException{
        Statement stm = connection.createStatement();
        return (com.mysql.jdbc.ResultSet)stm.executeQuery(query);
    }
}
