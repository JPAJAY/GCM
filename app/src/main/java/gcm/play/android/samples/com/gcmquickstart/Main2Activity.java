package gcm.play.android.samples.com.gcmquickstart;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.gcm.GcmListenerService;

public class Main2Activity extends Activity implements  View.OnClickListener {
    Button Accept, Reject;
    TextView text;
    public static String MessageButton;
    int id = 0;

    DatabaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DatabaseHelper(this);
        setContentView(R.layout.activity_main2);
        text = (TextView)findViewById(R.id.textView);
        Accept = (Button) findViewById(R.id.accept);
        Reject = (Button) findViewById(R.id.reject);
        text.setText(MyGcmListenerService.message);
        try {
            Accept.setOnClickListener(this);
            Reject.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View A) {
        id++;
        switch (A.getId()) {
            case R.id.accept:
                MessageButton = "Accepted";
                System.out.println(MessageButton);
                break;
            case R.id.reject:
                MessageButton = "Rejected";
                System.out.println(MessageButton);
                break;
            default:
                throw new RuntimeException("unKnown");

        }
//        mydb.insert(Integer.toString(id),MyGcmListenerService.date.toString(),MyGcmListenerService.time.toString(),MessageButton);
        String[] array = new String[5];
        String from = MyGcmListenerService.fromAddr;
        String toAddr = MyGcmListenerService.toAddr;
        String date = MyGcmListenerService.date;
        String time = MyGcmListenerService.time;
        String meet = MyGcmListenerService.meet;
      System.out.println("in main2Activity"+from+toAddr+date+time);
        array[0]=MessageButton;
        array[1]=from;
        array[2]=date;
        array[3]=time;
        array[4]=meet;
        hello  hi = new hello();
        hi.execute(array);
        mydb.UpdateMeetings(
                meet,
                MessageButton
        );
        finish();
    }
    class  hello extends AsyncTask<String, String[],Integer>{
        @Override
        protected Integer doInBackground(String[] array) {
            GcmSender.main(array);
            return null;
        }
    }
}