package android.burul.androidisaev.bridge;

import android.burul.androidisaev.bridge.enums.Delegates;
import android.burul.androidisaev.bridge.httpHandler.HTTPHandler;
import android.burul.androidisaev.bridge.serializersAndDeserializers.*;
import android.burul.androidisaev.entities.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;



public class Bridge {

    private HTTPHandler httpHandler = new HTTPHandler();

    private Type listMarksType = new TypeToken<List<Mark>>(){}.getType();
    private Type listGroupsType = new TypeToken<List<Group>>(){}.getType();
    private Type listUsersType = new TypeToken<List<User>>(){}.getType();

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Group.class, new GroupSerDes())
            .registerTypeAdapter(Mark.class, new MarkSerDes())
            .registerTypeAdapter(Subject.class, new SubjectSerDes())
            .registerTypeAdapter(User.class, new UserSerDes())
            .registerTypeAdapter(listMarksType, new MarksSerDes())
            .registerTypeAdapter(listGroupsType, new GroupsSerDes())
            .registerTypeAdapter(listUsersType, new UsersSerDes())
            .create();


    public List <Group> getGroups(){
        String data = httpHandler.connect("/group");
        List<Group> groups = gson.fromJson(data,listGroupsType);
        return groups;
    }

    public List <Mark> getTopMarks(String groupName){
        String data = httpHandler.connect("/marks/groupname/" + groupName);
        List<Mark> top = gson.fromJson(data, listMarksType);
        return top;
    }

    public List <Mark> getMarksOfUser(String userName){
        String data = httpHandler.connect("/marks/username/" + userName);
        List<Mark> marks = gson.fromJson(data,listMarksType);
        return  marks;
    }

    public List <User> getUsersOfGroup(String groupName){
        String data = httpHandler.connect("/user/groupname/" + groupName);
        List <User> users = gson.fromJson(data,listUsersType);
        return users;
    }
}
