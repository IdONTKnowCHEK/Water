package niu.edu.water;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import niu.edu.water.weather.Weather;
import niu.edu.water.weather.WeatherAPIInterface;
import niu.edu.water.weather.WeatherAPIManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements Serializable {

    WeatherAPIInterface weatherAPI;
    TextView textView, growthLength, goal;
    Button buttonDrink;
    ImageView imageDrop, plant;
    ImageButton log, weather, body;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());
    String str = formatter.format(curDate);
    private int length, weight;
    private String growthString, msg;
    private ArrayList<String> mDataset = new ArrayList<>();

    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
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

//        sp.edit().remove("dateD").commit();
        isNextDay(sharedPreferences, editor);

        growthString = "目前成長：\n" + sharedPreferences.getInt("growth", 0) + "cm";
        growthLength.setText(growthString);


        imageChange(params, sharedPreferences);

        final EditText input = new EditText(MainActivity.this);

        int environNums = sharedPreferences.getInt("ReasonNums", 0);
        Log.i("@@@@@@@@@@@@@@@@@@", String.valueOf(environNums));
        for (int i = 0; i < environNums; i++) {
            String environItem = sharedPreferences.getString("item_" + i, null);
            mDataset.add(environItem);
        }

        buttonDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNextDay(sharedPreferences, editor);


                formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                curDate = new Date(System.currentTimeMillis());
                str = formatter.format(curDate);
                mDataset.add(str);
//                Log.i("@@@@@@@@@@@@", String.valueOf(mDataset.size()));
                editor.putInt("ReasonNums", mDataset.size());
                for (int i = environNums; i < mDataset.size(); i++) {
                    editor.putString("item_" + i, mDataset.get(i));
//                    Log.i("@@@@@@@@@@@@", String.valueOf(mDataset.get(i)));
                }


                textView.setText(str);
                textView.setSelected(true);

                length = sharedPreferences.getInt("growth", 0);
                length++;
                editor.putInt("growth", length);
                editor.commit();
//                Log.i("@@@@@@@@@@", String.valueOf(length));

                growthString = "目前成長：\n" + sharedPreferences.getInt("growth", 0) + "cm";
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

                imageChange(params, sharedPreferences);
                imageDrop.startAnimation(translateAnimation);

            }
        });
        //FetchData
        call.clone().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                for (Weather.RecordsDTO.LocationDTO locationDTO : response.body().getRecords().getLocation()) {
                    if (locationDTO.getLocationName().equals("羅東")) {
                        for (Weather.RecordsDTO.LocationDTO.WeatherElementDTO weatherElementDTO : locationDTO.getWeatherElement()) {
                            msg += weatherElementDTO.getElementName() + ":" + weatherElementDTO.getElementValue() + ", ";
                        }
                        System.out.println("羅東/" + msg);
                    }
//                    String msg = "";
//                    for (Weather.RecordsDTO.LocationDTO.WeatherElementDTO weatherElementDTO : locationDTO.getWeatherElement()) {
//                        msg += weatherElementDTO.getElementName() + ":" + weatherElementDTO.getElementValue() + ", ";
//                    }
//                    Log.d(locationDTO.getLocationName(), msg);
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

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, drinkLog.class);
                intent.putExtra("LogList", mDataset);
                startActivity(intent);
            }
        });

    }

    private void isNextDay(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        String yesterday = sharedPreferences.getString("dateD", "");
        SimpleDateFormat Day = new SimpleDateFormat("dd");
        Date cur = new Date(System.currentTimeMillis());
        String today = Day.format(cur);
        if (!today.equals(yesterday)) {
            Log.i("today", today);
            Log.i("yesterday", yesterday);
            editor.putString("dateD", today);
            sharedPreferences.edit().remove("growth").commit();
            editor.putInt("growth", 0);
            editor.commit();
        }
    }

    private void imageChange(ViewGroup.LayoutParams params, SharedPreferences sharedPreferences) {
        handler.postDelayed(new Runnable() {
            public void run() {
                int length = sharedPreferences.getInt("growth", 0);
                imageDrop.setImageDrawable(null);
                imageDrop.setVisibility(View.GONE);
                if (length < 5) {
                    params.height = 100;
                    plant.setLayoutParams(params);
                    plant.setImageResource(R.drawable.seed);
                } else if (length > 5 && length < 10) {
                    params.height = 150;
                    plant.setLayoutParams(params);
                    plant.setImageResource(R.drawable.germination);
                } else if (length > 10 && length < 15) {
                    params.height = 300;
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
    }

}