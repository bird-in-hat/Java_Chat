package db_classes;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
        import com.j256.ormlite.field.ForeignCollectionField;
        import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "convs")
public class Conversation {
    @DatabaseField(canBeNull = false, generatedId = true)
    public int id;
    @DatabaseField(canBeNull = false)
    public String title;
    @DatabaseField(canBeNull = false, unique = true)
    public String link;

    @ForeignCollectionField(eager = true)
    public ForeignCollection<Task> conversationTasks;

    public Conversation() {}
    public Conversation(String title, String link) {
        this.title = title;
        this.link = link;
    }
}
