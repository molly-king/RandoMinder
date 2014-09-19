package rand.molly.randominder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mollyrand on 9/8/14.
 */
public class ReminderAdapter extends ArrayAdapter<Reminder> {

    private Context mContext;
    public static final String REMINDER_KEY = "Reminder";
    private int mResourceId;
    private ArrayList<Reminder> reminders;
    private EditSelectedReminderListener editListener;


    public ReminderAdapter(Context context, int resource, ArrayList<Reminder> objects) {
        super(context, resource, objects);
        editListener = (EditSelectedReminderListener)context;
        reminders = objects;
        this.mContext = context;
        mResourceId = resource;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        Holder holder = null;
        if (rowView == null) {
            holder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rowView = inflater.inflate(mResourceId, parent, false);
            holder.reminderName= (TextView) rowView.findViewById(R.id.task_name);
            holder.onOffButton = (ImageView) rowView.findViewById(R.id.on_off_toggle);
            rowView.setTag(holder);
        } else {
            holder = (Holder) rowView.getTag();
        }
        Reminder reminderItem = reminders.get(position);
        holder.onOffButton.setTag(reminderItem);
        holder.onOffButton.setOnClickListener(toggleOnOff);
        updateOnOffButton(holder.onOffButton);
        holder.reminderName.setTag(position);
        holder.reminderName.setText(reminderItem.getTitle());
        holder.reminderName.setOnClickListener(nameClickListener);

        return rowView;
    }

    private void updateOnOffButton(ImageView button){
        Reminder mReminder = (Reminder)button.getTag();
        if (mReminder.getActive()){
            button.setImageResource(R.drawable.ic_off);
        } else {
            button.setImageResource(R.drawable.ic_on);
        }
    }

    ///Listeners
    View.OnClickListener nameClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer pos = (Integer)v.getTag();
            editListener.editSelectedReminder(pos);
        }
    };

    View.OnClickListener toggleOnOff = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Reminder mReminder = (Reminder)v.getTag();
            mReminder.toggleActive();
            ImageView onOff = (ImageView)v;
            updateOnOffButton(onOff);
            if (mReminder.getActive()){
                Toast.makeText(getContext(), mReminder.getTitle() + " activated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), mReminder.getTitle() + " deactivated", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //holder pattern
    private static class Holder{
        TextView reminderName;
        ImageView onOffButton;
    }

    //interface for click callback
    public interface EditSelectedReminderListener{
        public void editSelectedReminder(int position);
    }
}
