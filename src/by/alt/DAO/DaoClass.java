package by.alt.DAO;

import com.mysql.jdbc.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class DaoClass {
    static RootNode rootNode;
    private Node node;
    private ArrayList<Node> childNodeList;
    private LinkedHashSet<String> positionList;
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
        childNodeList = new ArrayList<Node>();
        try {
           ResultSet resultSet = dbReader.QueryToDB("SELECT `id`,`name`,`type`, `pos`, `parent_id`  FROM `personal` Where parent_id = "+String.valueOf(parent_id)+" AND type=" + ObjectType.DEP.ordinal()+1);
           while (resultSet.next()){
               childNodeList.add(new Node(resultSet.getInt("ID"),resultSet.getString("NAME"),resultSet.getInt("PARENT_ID")));
               System.out.println(resultSet.getString("POS"));
           }
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return childNodeList;
    }
    public LinkedHashSet<String> getPositionList(){
        positionList = new LinkedHashSet<>();
        try {
            ResultSet resultSet = dbReader.QueryToDB("SELECT `id`, `name`,`type`, `pos`  FROM `personal` Where `pos` IS NOT NULL");
            while (resultSet.next()){
                positionList.add(resultSet.getString("POS"));
                System.out.println(resultSet.getString("POS"));
            }
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return positionList;
    }
}
