package rand.molly.randominder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by mollyrand on 9/19/14.
 */
public class AlarmService extends IntentService {
    private static final String TAG = "AlarmService";

    public AlarmService(){
        super(TAG);
    }

    @Override
    public void onHandleIntent(Intent intent) {

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Reminder")
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle("reminder name")
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);

    }

    public static void setServiceAlarm(Context context, Reminder reminder, boolean isOn){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        long firstAlarmMillis = ((reminder.getStartHour()*60)+reminder.getStartMin())*60*1000;
        long intervalMillis = reminder.repeatInterval()*60*1000;
        Intent i = new Intent(context, AlarmService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        if (isOn){
            alarmManager.setRepeating(AlarmManager.RTC, firstAlarmMillis, intervalMillis, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }
}
