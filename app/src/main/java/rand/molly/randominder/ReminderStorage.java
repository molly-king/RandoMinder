package rand.molly.randominder;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mollyrand on 9/8/14.
 */
public class ReminderStorage {

    private static ArrayList<Reminder> reminderCache = new ArrayList<Reminder>();
    private static ReminderStorage storageRef;
    private static String DATA_FILE_ARRAY = "randoMinderData";

    private ReminderStorage(){

    }

    public static ReminderStorage getInstance()
    {
        if (storageRef == null){
            storageRef = new ReminderStorage();
        }
        return storageRef;
    }

    private static HashMap<String,Reminder> getMyData(Context context){
        try{
            File f = context.getFileStreamPath(DATA_FILE_ARRAY);
            if(!f.exists()){
                HashMap<String,Reminder> myData = new HashMap<String, Reminder>();
                saveMyData(context, myData);
            }
            FileInputStream fis = context.openFileInput(DATA_FILE_ARRAY);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object objectReadFromStream = ois.readObject();
            ois.close();

            if (objectReadFromStream != null && objectReadFromStream instanceof HashMap){
                return (HashMap<String,Reminder>) objectReadFromStream;
            }
        } catch (IOException e){
            e.printStackTrace();
            Log.e(DATA_FILE_ARRAY, "error getting");
            Log.e(DATA_FILE_ARRAY, e.getMessage());
            Log.e(DATA_FILE_ARRAY, e.toString());
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            Log.e(DATA_FILE_ARRAY, "error getting");
            Log.e(DATA_FILE_ARRAY, e.getMessage());
            Log.e(DATA_FILE_ARRAY, e.toString());
        }
        return null;
    }

    private static boolean saveMyData(Context context, HashMap<String,Reminder> myData){
        try{
            FileOutputStream fos = context.openFileOutput(DATA_FILE_ARRAY, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(myData);
            oos.close();
        } catch (IOException e ) {
            e.printStackTrace();
            Log.e(DATA_FILE_ARRAY,"error saving");
            Log.e(DATA_FILE_ARRAY, e.getMessage());
            Log.e(DATA_FILE_ARRAY, e.toString());
            return false;
        }
        return true;
    }

    public static ArrayList<Reminder> getReminders(){

        return reminderCache;
    }

    public static void setReminders(ArrayList<Reminder> reminders){
        reminderCache = reminders;
    }

    public static void loadReminders(Context context){
        HashMap<String,Reminder> dataFromDisk = getMyData(context);
        if (dataFromDisk != null && dataFromDisk.size()>0){
            reminderCache = new ArrayList(dataFromDisk.values());
        }
    }

    public static void saveReminders(Context context){

        HashMap<String, Reminder> dataToDisk = new HashMap<String, Reminder>();
        for (Reminder aReminder : reminderCache){
            dataToDisk.put(aReminder.getTitle(), aReminder);
        }
        saveMyData(context, dataToDisk);
    }

}
