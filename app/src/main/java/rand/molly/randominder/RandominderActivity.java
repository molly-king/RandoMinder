package rand.molly.randominder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class RandominderActivity extends android.support.v7.app.ActionBarActivity implements ReminderAdapter.EditSelectedReminderListener, TimePickerDialogFragment.ChooseTime{

    private ArrayList<Reminder> reminders;
    public static final String ARG_POSITION = "Position";
    ReminderDetailFragment detailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReminderStorage.loadReminders(this);
        setContentView(R.layout.activity_randominder);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new ReminderListFragment())
                .commit();
    }

    @Override
    public void editSelectedReminder(int position) {
        detailFragment = new ReminderDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        detailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ReminderStorage.saveReminders(this);
    }

    @Override
    public void returnTime(int hour, int min, boolean isStart) {
        if (detailFragment.isVisible()){
            if (isStart){
                detailFragment.setStartTime(hour, min);
            } else {
                detailFragment.setEndTime(hour, min);
            }
        }
    }
}
