package niu.edu.water;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import niu.edu.water.weather.Weather;
import niu.edu.water.weather.WeatherAPIInterface;
import niu.edu.water.weather.WeatherAPIManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    WeatherAPIInterface weatherAPI;
    TextView textView, growthLength, goal;
    Button buttonDrink;
    ImageView imageDrop, plant;
    ImageButton log, weather, body;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());
    String str = formatter.format(curDate);
    private int length = 0, weight;
    private String growthString, msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //API Code
        weatherAPI = WeatherAPIManager.getInstance().getAPI();
        Call<Weather> call = weatherAPI.getData();
        //
        weather = findViewById(R.id.weatherButton);
        log = findViewById(R.id.drinkButton);
        body = findViewById(R.id.bodyButton);

        goal = findViewById(R.id.goal);
        textView = findViewById(R.id.marquee);
        growthLength = findViewById(R.id.plantLength);
        buttonDrink = findViewById(R.id.button);
        imageDrop = findViewById(R.id.waterDrop);
        plant = findViewById(R.id.plant);
        ViewGroup.LayoutParams params = plant.getLayoutParams();
        imageDrop.setImageDrawable(null);
        imageDrop.setVisibility(View.GONE);
        textView.setText(str);
        textView.setSelected(true);

        growthString = "目前成長：\n" + length + "cm";

        growthLength.setText(growthString);

        final EditText input = new EditText(MainActivity.this);

        buttonDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                length++;
                growthString = "目前成長：\n" + length + "cm";
                growthLength.setText(growthString);
                buttonDrink.setEnabled(false);

                imageDrop.setImageResource(R.drawable.drop);
                imageDrop.setVisibility(View.VISIBLE);
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        0f,
                        0f,
                        0f,
                        350f
                );

                translateAnimation.setDuration(500);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imageDrop.setImageDrawable(null);
                        imageDrop.setVisibility(View.GONE);
                        if (length > 5 && length < 10) {
                            params.height = 200;
                            plant.setLayoutParams(params);
                            plant.setImageResource(R.drawable.germination);
                        } else if (length > 10 && length < 15) {
                            params.height = 315;
                            plant.setLayoutParams(params);
                            plant.setImageResource(R.drawable.plant);
                        } else if (length > 15 && length < 20) {
                            params.height = 500;
                            plant.setLayoutParams(params);
                            plant.setImageResource(R.drawable.growth);
                        }
                        buttonDrink.setEnabled(true);
                    }
                }, 500);
                imageDrop.startAnimation(translateAnimation);

            }
        });
        //FetchData
        call.clone().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                for(Weather.RecordsDTO.LocationDTO locationDTO : response.body().getRecords().getLocation()){
                    String msg = "";
                    for(Weather.RecordsDTO.LocationDTO.WeatherElementDTO weatherElementDTO : locationDTO.getWeatherElement()){
                        msg += weatherElementDTO.getElementName() + ":" +weatherElementDTO.getElementValue() + ", ";
                    }
                    Log.d(locationDTO.getLocationName(), msg);
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d("Data", "Failed");
            }
        });
        //
        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                builder.setTitle("請輸入體重：");
                builder.setView(input);
                builder.setNegativeButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        weight = Integer.parseInt(input.getText().toString());
                        msg = "建議喝水量：" + (weight * 30) + "ml";
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

                        goal.setText(msg);
                        dialogInterface.dismiss();


                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }
}