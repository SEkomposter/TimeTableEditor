package by.alt.DAO;

import com.mysql.jdbc.ResultSet;

import java.sql.SQLException;

public class DaoClass {
    private static RootNode rootNode;
    private static DBReader dbReader = new DBReader();
    public RootNode getRootNode(){
        rootNode = new RootNode();
        try {
            ResultSet resultSet = dbReader.QueryToDB("SELECT `name`,`type` WHERE `id` = 0 FROM `tc-db-main.personal`;");
            rootNode.resultSet.getString("NAME");
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return rootNode;
    }
    public Node getNode(){
        return

    }
}
