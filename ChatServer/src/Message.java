import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "messages")
public class Message
{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField()
    public String text;
    @DatabaseField(canBeNull = false, foreign = true)
    public User user;
    @DatabaseField(canBeNull = false, foreign = true)
    public Conversation conversation;
    @DatabaseField(dataType = DataType.DATE_STRING)
    public Date time;

    public Message() {}
    public Message(User user, Conversation conversation, String text) {
        this.text = text;
        this.user = user;
        this.conversation = conversation;
        this.time = null;
    }

}
