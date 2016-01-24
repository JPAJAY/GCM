package gcm.play.android.samples.com.gcmquickstart;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.jar.Attributes;

public class Check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);on();}

    @Override protected void onStart() {super.onStart();on();}

    @Override protected void onRestart(){super.onRestart();on();}

    @Override protected void onResume() {super.onResume();on();}
    public void on(){
        DatabaseHelper  myb = new DatabaseHelper(this);;
        Cursor res = myb.viewUsers();
        if (res.getCount() == 0)
            startActivity(new Intent(this, welcome.class));
        else
            startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
