package db_classes;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
        import com.j256.ormlite.field.ForeignCollectionField;
        import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "convs")
public class Conversation {

    public static final String CONVERSATION_ID = "id";
    public static final String CONVERSATION_TITLE = "title";
    public static final String CONVERSATION_LINK = "link";

    @DatabaseField(canBeNull = false, generatedId = true, columnName = CONVERSATION_ID)
    public int id;
    @DatabaseField(canBeNull = false, columnName = CONVERSATION_TITLE)
    public String title;
    @DatabaseField(canBeNull = false, unique = true, columnName = CONVERSATION_LINK)
    public String link;

    @ForeignCollectionField()
    public ForeignCollection<Task> conversationTasks;

    @ForeignCollectionField()
    public ForeignCollection<Task> conversationMessages;

    public Conversation() {}
    public Conversation(String title, String link) {
        this.title = title;
        this.link = link;
    }
}
