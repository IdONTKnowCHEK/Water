package niu.edu.water;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button buttonDrink;
    ImageView imageDrop;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());
    String str = formatter.format(curDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.marquee);
        buttonDrink = findViewById(R.id.button);
        imageDrop = findViewById(R.id.waterDrop);
        imageDrop.setImageDrawable(null);
        imageDrop.setVisibility(View.GONE);
        textView.setText(str);
        textView.setSelected(true);

        buttonDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageDrop.setImageResource(R.drawable.drop);
                imageDrop.setVisibility(View.VISIBLE);
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        0f,
                        0f,
                        0f,
                        350f
                );
                translateAnimation.setDuration(1500);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imageDrop.setImageDrawable(null);
                        imageDrop.setVisibility(View.GONE);
                    }
                }, 1500);
                imageDrop.startAnimation(translateAnimation);

            }
        });


    }
}