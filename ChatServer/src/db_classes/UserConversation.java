package db_classes;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_convs")
public class UserConversation
{

    @DatabaseField(canBeNull = false, generatedId = true, columnName = "id")
    public int id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "user")
    public User user;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "conversation")
    public Conversation conversation;

    public UserConversation() {}
    public UserConversation(User user, Conversation conversation)
    {
        this.user = user;
        this.conversation = conversation;
    }
}
