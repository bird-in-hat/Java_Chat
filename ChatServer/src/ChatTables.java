import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db_classes.*;
import com.j256.ormlite.stmt.QueryBuilder;
import java.util.List;

import java.sql.SQLException;
import nodes.*;

public class ChatTables {

    private Dao<User, Integer> userDao;
    private Dao<UserConversation, Integer> userConvDao;
    private Dao<Task, Integer> taskDao;
    private Dao<Conversation, Integer> convDao;
    private Dao<Message, Integer> msgDao;

    public ChatTables(ConnectionSource cs) {
        try {
            userDao = DaoManager.createDao(cs, User.class);
            userConvDao = DaoManager.createDao(cs, UserConversation.class);
            taskDao = DaoManager.createDao(cs, Task.class);
            convDao = DaoManager.createDao(cs, Conversation.class);
            msgDao = DaoManager.createDao(cs, Message.class);

            TableUtils.dropTable(cs, User.class, true);
            TableUtils.dropTable(cs, UserConversation.class, true);
            TableUtils.dropTable(cs, Task.class, true);
            TableUtils.dropTable(cs, Conversation.class, true);
            TableUtils.dropTable(cs, Message.class, true);

            TableUtils.createTableIfNotExists(cs, User.class);
            TableUtils.createTableIfNotExists(cs, UserConversation.class);
            TableUtils.createTableIfNotExists(cs, Task.class);
            TableUtils.createTableIfNotExists(cs, Conversation.class);
            TableUtils.createTableIfNotExists(cs, Message.class);

        } catch (SQLException e) { System.out.println("problems with ChatTables"+e.getMessage());}
    }

    private User getUser(String login_) {
        List<User> userList = null;
        try {
            QueryBuilder<User, Integer> queryBuilder =
                    userDao.queryBuilder();
            userList = userDao.query(queryBuilder.where().eq("login", login_).prepare());
        } catch (SQLException e){System.out.println("getUser "+e.getMessage());}
        if (userList == null || userList.isEmpty())
            return null;
        else
            return userList.get(0);
    }

    private Conversation getConversation(String link_){
        List<Conversation> convList = null;
        try {
            QueryBuilder<Conversation, Integer> queryBuilder =
                    convDao.queryBuilder();
            convList = convDao.query(queryBuilder.where().eq("link", link_).prepare());
        } catch (SQLException e){System.out.println("getConversation "+e.getMessage());}
        if (convList == null || convList.isEmpty())
            return null;
        else
            return convList.get(0);
    }

    public void addUser (String login, String password){
        try {
            User u = new User(login, password);
            userDao.create(u);
        } catch (SQLException e){System.out.println("addUser "+e.getMessage());}
    }

    public void addConversation (String title, String link){
        try {
            Conversation c1 = new Conversation(title, link);
            convDao.create(c1);
        } catch (SQLException e){System.out.println("addConversation "+e.getMessage());}
    }

    public void addMessageToConversation (String login, String link, String text){
        try {
            Message m = new Message(getUser(login), getConversation(link), text);
            msgDao.create(m);
        }catch (SQLException e){System.out.println("addMessageToConversation "+e.getMessage());}
    }

    public boolean isUserExist(String login){
        if (getUser(login) != null)
            return true;
        else
            return false;
    }

    public boolean isConversationExist(String link){
        if (getConversation(link) != null)
            return true;
        else
            return false;
    }

    public boolean isUserInConversation(String login, String link) {
        List<UserConversation> user_conversationList = null;
        try {
            User u = getUser(login);
            Conversation c = getConversation(link);
            QueryBuilder<UserConversation, Integer> queryBuilder =
                    userConvDao.queryBuilder();
            user_conversationList = userConvDao.query(queryBuilder.where().
                    eq("user", u).and().eq("conversation", c).prepare());
        } catch (SQLException e){System.out.println("getConversations "+e.getMessage());}
        if (user_conversationList == null || user_conversationList.isEmpty())
            return false;
        else
            return true;
    }

    public boolean isPasswordCorrect(String login, String password){
        User u = getUser(login);
        if (u.password.equals(password))
            return true;
        else
            return false;
    }

    public MessageNode[] getMessages(String link) {
        List<Message> messageList = null;
        try {
            Conversation c = getConversation(link);
            QueryBuilder<Message, Integer> queryBuilder =
                    msgDao.queryBuilder();
            messageList = msgDao.query(queryBuilder.where().eq("conversation", c).prepare());
        } catch (SQLException e){System.out.println("getMessages "+e.getMessage());}
        if (messageList == null || messageList.isEmpty())
            return null;
        // else
        MessageNode[] mn = new MessageNode[messageList.size()];
        int i = 0;
        for(Message m: messageList){
            mn[i].text1 = m.user.login;
            mn[i].text2 = m.text;
            i++;
        }
        return mn;
    }

    public MessageNode[] getConversations(String login) {
        List<UserConversation> user_conversationList = null;
        try {
            User u = getUser(login);
            QueryBuilder<UserConversation, Integer> queryBuilder =
                    userConvDao.queryBuilder();
            user_conversationList = userConvDao.query(queryBuilder.where().eq("user", u).prepare());
        } catch (SQLException e){System.out.println("getConversations "+e.getMessage());}
        if (user_conversationList == null || user_conversationList.isEmpty())
            return null;
        // else
        MessageNode[] mn = new MessageNode[user_conversationList.size()];
        int i = 0;
        for(UserConversation uc: user_conversationList){
            mn[i].text1 = uc.conversation.title;
            mn[i].text2 = uc.conversation.link;
            i++;
        }
        return mn;
    }

    public MessageNode[] getMembers(String link) {
        List<UserConversation> conversation_usersList = null;
        try {
            Conversation c = getConversation(link);
            QueryBuilder<UserConversation, Integer> queryBuilder =
                    userConvDao.queryBuilder();
            conversation_usersList = userConvDao.query(queryBuilder.where().eq("conversation", c).prepare());
        } catch (SQLException e){System.out.println("getMembers "+e.getMessage());}
        if (conversation_usersList == null || conversation_usersList.isEmpty())
            return null;
        // else
        MessageNode[] mn = new MessageNode[conversation_usersList.size()];
        int i = 0;
        for(UserConversation uc: conversation_usersList){
            mn[i].text1 = uc.user.login;
            i++;
        }
        return mn;
    }

    public void joinConversation(String login, String link) {
        User u = getUser(login);
        Conversation c = getConversation(link);
        UserConversation uc = new UserConversation(u, c);
        try {
            userConvDao.create(uc);
        } catch (SQLException e) {System.out.println("joinConversation "+e.getMessage());}
    }

    public void leaveConversation(String login, String link) {
        List<UserConversation> user_conversationList = null;
        try {
            User u = getUser(login);
            Conversation c = getConversation(link);
            QueryBuilder<UserConversation, Integer> queryBuilder =
                    userConvDao.queryBuilder();
            user_conversationList = userConvDao.query(queryBuilder.where().
                    eq("user", u).and().eq("conversation", c).prepare());
            if (!(user_conversationList == null || user_conversationList.isEmpty()))
                userConvDao.delete(user_conversationList.get(0));
        } catch (SQLException e){System.out.println("getConversations "+e.getMessage());}
    }
}
