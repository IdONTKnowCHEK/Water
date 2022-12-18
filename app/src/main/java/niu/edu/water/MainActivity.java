package niu.edu.water;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

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
    ImageButton log, body;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());
    String str = formatter.format(curDate);
    String temp, humd;
    private int length, weight;
    private String growthString, msg;
    private ArrayList<String> mDataset = new ArrayList<>();
    private long currentSystemTime;
    private long setTime;
    private Calendar calendar;

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
        textView.setText(str + "  溫度：" + temp + "  濕度：" + humd);
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

        for (int i = 0; i < environNums; i++) {
            Log.i("@@@@@@@@@@@@@@@@@@", String.valueOf(environNums));
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
                    Log.i("@@@@@@@@@@@@", String.valueOf(mDataset.get(i)));
                }


                textView.setText(str + "  溫度：" + temp + "  濕度：" + humd);
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

                currentTime();
                setTime(calendar);
                setAlarm();
//                showtime();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("bottle/interval");

                myRef.setValue(60);
                // 傳值進firebase

                handler.postDelayed(new Runnable() {
                    public void run() {
                        String id = "123";
                        int importance = NotificationManager.IMPORTANCE_LOW;
                        NotificationChannel channel = new NotificationChannel(id, "drink", importance);
//                channel.enableLights(true);
//                channel.enableVibration(true);
                        NotificationManager notificationManager
                                = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                        notificationManager.createNotificationChannel(channel);
                        //        建立通知物件內容
                        Notification notification = new NotificationCompat.Builder(MainActivity.this, "123")
                                .setCategory(Notification.CATEGORY_MESSAGE)
                                .setWhen(setTime)
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle("提醒")
                                .setContentText("該喝水囉!")
//                        .setAutoCancel(true)
                                .build();

                        //        發送通知
                        notificationManager.notify(1, notification);
                    }
                }, 5000); // 喝水之後通知提醒間隔


            }
        });
        //FetchData
        call.clone().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                for (Weather.RecordsDTO.LocationDTO locationDTO : response.body().getRecords().getLocation()) {
                    if (locationDTO.getLocationName().equals("羅東")) {
                        temp = locationDTO.getWeatherElement().get(3).getElementValue();
                        humd = locationDTO.getWeatherElement().get(4).getElementValue();
                        textView.setText(str + "  溫度：" + temp + "  濕度：" + humd);

                        for (Weather.RecordsDTO.LocationDTO.WeatherElementDTO weatherElementDTO : locationDTO.getWeatherElement()) {
                            msg += weatherElementDTO.getElementName() + ":" + weatherElementDTO.getElementValue() + ", ";

                        }
//                        System.out.println("羅東/" + msg);

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

    private void currentTime() {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        currentSystemTime = System.currentTimeMillis();
        Log.i("現在時間", String.valueOf(currentSystemTime));
    }

    private void setTime(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        setTime = calendar.getTimeInMillis();
        Log.i("發送時間", String.valueOf(setTime));

//        if (currentSystemTime > setTime) {
//            calendar.add(Calendar.MONTH, 1);
//            setTime = calendar.getTimeInMillis();
//        }
    }


    private void setAlarm() {
        Intent intent = new Intent(this, alarmReceiver.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, setTime, pendingIntent);
    }

    private void showtime() {

        String text = (calendar.get(Calendar.MONTH) + 1) + "月"
                + calendar.get(Calendar.DAY_OF_MONTH) + "日\n"
                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE);

        Toast.makeText(this, "下次喝水提醒半小時後", Toast.LENGTH_LONG)
                .show();
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
                } else if (length > 15) {
                    params.height = 500;
                    plant.setLayoutParams(params);
                    plant.setImageResource(R.drawable.growth);
                }
                buttonDrink.setEnabled(true);
            }
        }, 500);
    }

}