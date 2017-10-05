package by.alt.DAO;
import static by.alt.DAO.ObjectType.EMP;

public class Personal implements SurvObject{
    private String name;
    private static final String TYPE = EMP.toString();
    private int id, parent_id;
    private Node node;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return TYPE;
    }

    public int getParent_id() {
        return parent_id;
    }
    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
    public Node getNode() {
        return node;
    }
    public void setNode(Node node) {
        this.node = node;
    }
    public int hashCode(){
        return this.getName().hashCode();
    }
    public boolean equals(Object obj){
        if (!(obj instanceof Node))
            return false;
        Node entry = (Node) obj;
        return name.equals(entry.getName());
    }
}
