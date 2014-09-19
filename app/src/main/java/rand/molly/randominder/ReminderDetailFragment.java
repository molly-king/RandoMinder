package rand.molly.randominder;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by mollyrand on 9/8/14.
 */
public class ReminderDetailFragment extends android.support.v4.app.Fragment {

    private Reminder reminderToEdit;
    private int reminderPosition;
    private TextView startTimeText;
    private TextView endTimeText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        View view = inflater.inflate(R.layout.fragment_reminder_detail, container, false);
        reminderPosition = args.getInt(RandominderActivity.ARG_POSITION);
        reminderToEdit = ReminderStorage.getReminders().get(reminderPosition);
        EditText reminderName = (EditText) view.findViewById(R.id.reminder_title_edit);
        reminderName.setText(reminderToEdit.getTitle());
        reminderName.setOnFocusChangeListener(titleListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            NumberPicker numPerDay = (NumberPicker) view.findViewById(R.id.num_reminders_per_day);
            numPerDay.setMinValue(1);
            numPerDay.setMaxValue(12);
            numPerDay.setValue(reminderToEdit.getRepeatPerDay());
            numPerDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    reminderToEdit.setRepeatPerDay(newVal);
                }
            });
            NumberPicker minGap = (NumberPicker)view.findViewById(R.id.min_gap_time);
            minGap.setVisibility(View.VISIBLE);
            minGap.setDisplayedValues(new String[]{"15", "30", "45", "60"});
            minGap.setValue(reminderToEdit.getMinimumGap());
        } else {
            Spinner numPerDay = (Spinner)view.findViewById(R.id.spinner_reminders_per_day);
            Integer[] numReminderOptions = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),R.layout.number_spinner_item, numReminderOptions);
            numPerDay.setAdapter(adapter);
        }
        startTimeText = (TextView) view.findViewById(R.id.start_time);
        startTimeText.setOnClickListener(startTimeListener);
        startTimeText.setTag(reminderToEdit);
        String startTime = getTimeString(reminderToEdit.getStartHour(), reminderToEdit.getStartMin());
        startTimeText.setText(startTime);

        endTimeText = (TextView) view.findViewById(R.id.end_time);
        endTimeText.setOnClickListener(startTimeListener);
        endTimeText.setTag(reminderToEdit);
        String endTime = getTimeString(reminderToEdit.getEndHour(), reminderToEdit.getEndMin());
        endTimeText.setText(endTime);

        CheckedTextView monBox = (CheckedTextView)view.findViewById(R.id.monday);
        monBox.setChecked(reminderToEdit.isRemindMonday());
        monBox.setOnClickListener(toggleDayListener);
        CheckedTextView tuesBox = (CheckedTextView)view.findViewById(R.id.tuesday);
        tuesBox.setChecked(reminderToEdit.isRemindTuesday());
        tuesBox.setOnClickListener(toggleDayListener);
        CheckedTextView wedBox = (CheckedTextView)view.findViewById(R.id.wednesday);
        wedBox.setChecked(reminderToEdit.isRemindWednesday());
        wedBox.setOnClickListener(toggleDayListener);
        CheckedTextView thuBox = (CheckedTextView)view.findViewById(R.id.thursday);
        thuBox.setChecked(reminderToEdit.isRemindThursday());
        thuBox.setOnClickListener(toggleDayListener);
        CheckedTextView friBox = (CheckedTextView)view.findViewById(R.id.friday);
        friBox.setChecked(reminderToEdit.isRemindFriday());
        friBox.setOnClickListener(toggleDayListener);
        CheckedTextView satBox = (CheckedTextView)view.findViewById(R.id.saturday);
        satBox.setChecked(reminderToEdit.isRemindSaturday());
        satBox.setOnClickListener(toggleDayListener);
        CheckedTextView sunBox = (CheckedTextView)view.findViewById(R.id.sunday);
        sunBox.setChecked(reminderToEdit.isRemindSunday());
        sunBox.setOnClickListener(toggleDayListener);

        return view;
    }


    //listeners

    View.OnFocusChangeListener titleListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                EditText theTitle = (EditText) v;
                String newTitle = theTitle.getText().toString();
                reminderToEdit.setTitle(newTitle);
            }
        }
    };

    View.OnClickListener startTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerDialogFragment timeDialog = new TimePickerDialogFragment();
            timeDialog.reminder = (Reminder)v.getTag();
            if (v.getId() == R.id.start_time){
                timeDialog.hour = timeDialog.reminder.getStartHour();
                timeDialog.aMinute = timeDialog.reminder.getStartMin();
                timeDialog.isStart = true;
            } else if (v.getId() == R.id.end_time){
                //this could just be an "else" block but for ease of understanding I'm going to be overly cautious
                //and check that I'm sure we're working with the end time here
                timeDialog.hour = timeDialog.reminder.getEndHour();
                timeDialog.aMinute = timeDialog.reminder.getEndMin();
                timeDialog.isStart = false;
            }
            timeDialog.show(getActivity().getSupportFragmentManager(), "startTimeDialog");
        }
    };

    View.OnClickListener toggleDayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckedTextView checkBox = (CheckedTextView)v;
            checkBox.toggle();
            switch(v.getId()){
                case R.id.monday:
                    reminderToEdit.toggleMonday();
                    break;
                case R.id.tuesday:
                    reminderToEdit.toggleTuesday();
                    break;
                case R.id.wednesday:
                    reminderToEdit.toggleWednesday();
                    break;
                case R.id.thursday:
                    reminderToEdit.toggleThursday();
                    break;
                case R.id.friday:
                    reminderToEdit.toggleFriday();
                    break;
                case R.id.saturday:
                    reminderToEdit.toggleSaturday();
                    break;
                case R.id.sunday:
                    reminderToEdit.toggleSunday();
                    break;
            }
        }
    };


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RandominderActivity.ARG_POSITION, reminderPosition);
    }

    public void setStartTime(int hour, int min){
        reminderToEdit.setStartHour(hour);
        reminderToEdit.setStartMin(min);
        startTimeText.setText(getTimeString(hour, min));
    }
    public void setEndTime(int hour, int min){
        reminderToEdit.setEndHour(hour);
        reminderToEdit.setEndMin(min);
        endTimeText.setText(getTimeString(hour,min));
    }

    public String getTimeString(int hour, int minute){
        String timeString;
        boolean isAm = true;

        if (hour == 0){
            //so 12 am doesn't show up as 0:00
            timeString = String.valueOf(hour+12) + ":";
        } else if (hour == 12) {
            //so noon doesn't show up as 12am
            timeString = String.valueOf(hour) + ":";
            isAm = false;
        } else if (hour<12) {
            timeString = String.valueOf(hour) + ":";
        } else {
            timeString = String.valueOf(hour-12) + ":";
            isAm = false;
        }
        if (minute < 10){
            timeString = timeString + "0" + String.valueOf(minute);
        } else {
            timeString = timeString + String.valueOf(minute);
        }

        if (isAm){
            timeString = timeString + " AM";
        } else {
            timeString = timeString + " PM";
        }

        return timeString;
    }




}
