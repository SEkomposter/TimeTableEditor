package by.alt.DAO;

import com.mysql.jdbc.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

public class DaoClass {
    static RootNode rootNode;
    private Node node;
    private ArrayList<Node> childNodeList;
    private static DBReader dbReader = new DBReader();
    public RootNode getRootNode(){
        try {
            ResultSet resultSet = dbReader.QueryToDB("SELECT `name`, `type` FROM `personal` WHERE `id` = 0;");
            rootNode = new RootNode(resultSet.getString("NAME"));
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return rootNode;
    }
    public static void getObjectId (){
        try {
            ResultSet resultSet = dbReader.QueryToDB("SELECT `name`, `id` FROM `personal`");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("ID") + "  " + resultSet.getString("NAME"));
                //rootNode = new RootNode(resultSet.getString("NAME"));
            }
        }catch (SQLException exc){
            exc.printStackTrace();
        }
    }
    public ArrayList<Node> getChildNodeList(int parent_id){
        try {
           ResultSet resultSet = dbReader.QueryToDB("SELECT `id`,`name`,`type`, `parent_id` WHERE `parent_id` = " + String.valueOf(parent_id) + " FROM `personal`");
           rootNode = new RootNode(resultSet.getString("NAME"));
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return childNodeList;
    }
}
