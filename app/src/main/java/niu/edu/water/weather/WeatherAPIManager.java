package niu.edu.water.weather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherAPIManager {
    private static WeatherAPIManager mInstance = new WeatherAPIManager();
    private WeatherAPIInterface weatherApiInterFace;

    private WeatherAPIManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opendata.cwb.gov.tw/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApiInterFace = retrofit.create(WeatherAPIInterface.class);
    }

    public static  WeatherAPIManager getInstance() {
        return mInstance;
    }

    public WeatherAPIInterface getAPI() {
        return weatherApiInterFace;
    }
}
