package niu.edu.water;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    TextView textView;
    Button button;
    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());
    String str = formatter.format(curDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //API Code
        weatherAPI = WeatherAPIManager.getInstance().getAPI();
        Call<Weather> call = weatherAPI.getData();
        //

        textView = findViewById(R.id.marquee);
        textView.setText(str);
        textView.setSelected(true);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
    }
}