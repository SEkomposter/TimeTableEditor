package by.alt.DAO;
//import by.alt.Object.PropReader;

import by.alt.Object.PropReader;
import com.mysql.jdbc.ResultSet;

import java.sql.*;
import java.util.Iterator;

public class DBReader{
    static String parameter1="db.connect.host",parameter2="db.connect.port",parameter3="db.connect.user",parameter4="db.connect.pass",parameter5="db.connect.driver";
    static Connection connection;
    public static Connection ConnectToDB()throws SQLException{
        PropReader propReader = new PropReader();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException exc){
            exc.printStackTrace();
        }
        connection= DriverManager.getConnection("jdbc:mysql://" +propReader.readCommonProps(parameter1)+":"+propReader.readCommonProps(parameter2)+"/tc-db-main?useUnicode=true&characterEncoding=utf-8",propReader.readCommonProps(parameter3),propReader.readCommonProps(parameter4));
        return connection;
    }
    public static void closeConnectionToDB() throws SQLException{
        connection.close();
    }
    public ResultSet QueryToDB(String query)throws SQLException{
        //PreparedStatement ps = connection.prepareStatement(query);
        //ps.setString(1, "бухгалтер"); // Parameter index is 1-based
       // ResultSet rs = (com.mysql.jdbc.ResultSet)ps.executeQuery();
        Statement stm = connection.createStatement();
        //return rs;
        return (com.mysql.jdbc.ResultSet)stm.executeQuery(query);
    }public ResultSet QueryToDB(String query, String position)throws Exception{
        PreparedStatement ps = connection.prepareStatement(query);
      //  String position2 = new String(position.getBytes("UTF-8"), "UTF-8");
        ps.setString(1, position); // Parameter index is 1-based
        ResultSet rs = (com.mysql.jdbc.ResultSet)ps.executeQuery();
        //Statement stm = connection.createStatement();
        return rs;
    }
}
