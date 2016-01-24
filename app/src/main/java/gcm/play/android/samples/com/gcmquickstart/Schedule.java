
package gcm.play.android.samples.com.gcmquickstart;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Schedule extends AppCompatActivity {
    TextView State;
    String Appionment_Date = MyGcmListenerService.date;
    String Appionment_Date_Time;
    String Appionment_Time = MyGcmListenerService.time;
    long diff,diffSeconds,diffMinutes,diffHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        State = (TextView) findViewById(R.id.state);
        prin();
    }



    public void prin(){
        Appionment_Date_Time = Appionment_Date + " " + Appionment_Time;
        System.out.println("Appionment_Date_Time : " + Appionment_Date_Time);
        Calendar calendar = Calendar.getInstance();
        int Hour = calendar.get(Calendar.HOUR_OF_DAY);
        int Minute = calendar.get(Calendar.MINUTE);
        int Second = calendar.get(Calendar.SECOND);
        int Date = calendar.get(Calendar.DATE);
        int Month = calendar.get(Calendar.MONTH) + 1;
        int Year = calendar.get(Calendar.YEAR);
        String Current_Date = Date + "-" + Month + "-" + Year;
        String Current_Time = String.format("%02d:%02d:%02d", Hour, Minute, Second);
        String Current_Date_Time = Current_Date + " " + Current_Time;
        System.out.println(Current_Date_Time);
        String dateStart = Current_Date_Time;
        String dateStop = Appionment_Date_Time;
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

        } catch (ParseException e) {
            e.printStackTrace();

        }
         diff = d2.getTime() - d1.getTime();
         diffSeconds = diff / 1000 % 60;
         diffMinutes = diff / (60 * 1000) % 60;
         diffHours = diff / (60 * 60 * 1000);
        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
        System.out.println("Time in hours: " + diffHours + " hours.");
        if (diffHours <=0 || diffMinutes <0 || diffSeconds <0) {
            State.setText("UPCOMMING MEETINGS  " + diffMinutes + " Minutes Remaining");
            State.setTextColor(Color.parseColor("#FD0303"));

            Intent intent = new Intent(this, BroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP,Calendar.MINUTE+diffMinutes, pendingIntent);
            Toast.makeText(this, "Alarm set in " + diffMinutes + " Minutes",
                    Toast.LENGTH_LONG).show();

        } else {
            State.setText(diffHours + " Hours " + diffMinutes + "Minutes " + diffSeconds + "Seconds Remaining ");
            State.setTextColor(Color.parseColor("#024B0D"));
            int i = (int) (diffHours*(1000*60*60));
            Intent intent = new Intent(this, BroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (i%24), pendingIntent);
            Toast.makeText(this, "Alarm set in " + diffHours + "Minutes",
                    Toast.LENGTH_LONG).show();


        }
    }
}