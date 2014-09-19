package rand.molly.randominder;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;


/**
 * Created by mollyrand on 9/11/14.
 */
public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public int hour;
    public int aMinute;
    public Reminder reminder;
    public boolean isStart;
    private ChooseTime activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour , aMinute,
                false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (ChooseTime)activity;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        activity.returnTime(hourOfDay,minute, isStart);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public interface ChooseTime{
        public void returnTime(int hour, int min, boolean isStart);
    }
}
