import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tasks")
public class Task
{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(canBeNull = false, foreign = true)
    public Conversation conversation;
    @DatabaseField(canBeNull = false)
    public String title;
    @DatabaseField(canBeNull = false)
    public String description;

    public Task() {}
}
