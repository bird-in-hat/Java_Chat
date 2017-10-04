package db_classes;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {
    @DatabaseField(canBeNull = false, generatedId = true, columnName = "id")
    public int id;
    @DatabaseField(canBeNull = false, unique = true, columnName = "login")
    public String login;
    @DatabaseField(canBeNull = false, columnName = "password")
    public String password;

    //@ForeignCollectionField()
    //public ForeignCollection<UserConversation> userConversations;

    public User() {}
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
