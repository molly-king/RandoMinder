package rand.molly.randominder;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by mollyrand on 8/21/14.
 */
public class Reminder implements Serializable {

    //has to implement serializable to be able to save

    private String title;
    private int repeatPerDay;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;
    private boolean active;
    private boolean remindMonday;
    private boolean remindTuesday;
    private boolean remindWednesday;
    private boolean remindThursday;
    private boolean remindFriday;
    private boolean remindSaturday;
    private boolean remindSunday;
    private int minTimeBetween = 30;//default minimum time. Later version: allow user to select
    private int maxTimeBetween;


    public Reminder() {
        //default constructor
        repeatPerDay = 1;
        startHour = 9;
        endHour = 17;
        active = false;
    }


    //todo: reward system?
    //color change from red to green as you approach your target time/week
    //week over week graph of your success (probability of success widget?)

    public Reminder(String title) {
        //Todo:URL or app launch?
        //Todo: ringtone select
        //todo: notification icon color
        //todo: minimum time between reminders

        this.title = title;
        repeatPerDay = 1;
        startHour = 9;
        endHour = 17;
        active = false;
        calcMaxInterval();
    }

    public void calcMaxInterval(){
        maxTimeBetween = ((endHour * 60) + endMin - ((startHour*60) + startMin))/repeatPerDay;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
        calcMaxInterval();
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
        calcMaxInterval();
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
        calcMaxInterval();
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
        calcMaxInterval();
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRepeatPerDay() {
        return repeatPerDay;
    }

    public void setRepeatPerDay(int repeatPerDay) {
        this.repeatPerDay = repeatPerDay;
        calcMaxInterval();
    }

    public boolean getActive(){
        return active;
    }

    public void toggleActive(){
        this.active = !active;
    }

    public boolean isRemindMonday() {
        return remindMonday;
    }

    public boolean isRemindTuesday() {
        return remindTuesday;
    }

    public boolean isRemindWednesday() {
        return remindWednesday;
    }

    public boolean isRemindThursday() {
        return remindThursday;
    }

    public boolean isRemindFriday() {
        return remindFriday;
    }

    public boolean isRemindSaturday() {
        return remindSaturday;
    }

    public boolean isRemindSunday() {
        return remindSunday;
    }

    public void toggleMonday(){
        this.remindMonday = !remindMonday;
    }
    public void toggleTuesday(){
        this.remindTuesday = !remindTuesday;
    }
    public void toggleWednesday(){
        this.remindWednesday = !remindWednesday;
    }
    public void toggleThursday(){
        this.remindThursday = !remindThursday;
    }
    public void toggleFriday(){
        this.remindFriday = !remindFriday;
    }
    public void toggleSaturday(){
        this.remindSaturday = !remindSaturday;
    }
    public void toggleSunday(){
        this.remindSunday = !remindSunday;
    }

    public int repeatInterval(){
        int randomInterval =(int) Math.round(Math.random()*(maxTimeBetween - minTimeBetween));
        randomInterval = randomInterval + minTimeBetween;
        return randomInterval;
    }


}
