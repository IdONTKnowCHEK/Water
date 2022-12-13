package niu.edu.water;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;




public class MainActivity extends AppCompatActivity {

    TextView textView;
    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());
    String str = formatter.format(curDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.marquee);
        textView.setText(str);
        textView.setSelected(true);

    }
}