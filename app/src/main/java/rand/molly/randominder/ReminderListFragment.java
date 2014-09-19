package rand.molly.randominder;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by mollyrand on 9/9/14.
 */
public class ReminderListFragment extends android.support.v4.app.Fragment {

    private ArrayList<Reminder> allReminders;
    private ReminderAdapter adapter;

    public ReminderListFragment(){

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        allReminders = ReminderStorage.getReminders();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allReminders = ReminderStorage.getReminders();
        if (allReminders == null){
            //put in demo starter reminder for users who don't have any currently. Will be deactivated by default to avoid
            //unexpected notifications
            allReminders.add(new Reminder("My First Reminder"));
        }
        adapter = new ReminderAdapter(getActivity(), R.layout.item_list_reminders, allReminders);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_list, container, false);
        ListView reminderList = (ListView)view.findViewById(R.id.reminder_list);
        reminderList.setAdapter(adapter);
        ImageView addNewButton = (ImageView)view.findViewById(R.id.add_new_reminder_button);
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create new reminder
                Reminder reminder = new Reminder();
                //add to all reminders
                allReminders.add(reminder);
                ReminderStorage.setReminders(allReminders);
                //notify data set changed
                adapter.notifyDataSetChanged();

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        allReminders = ReminderStorage.getReminders();
    }

    @Override
    public void onPause() {
        super.onPause();
        ReminderStorage.setReminders(allReminders);
        ReminderStorage.saveReminders(getActivity());
    }
}
