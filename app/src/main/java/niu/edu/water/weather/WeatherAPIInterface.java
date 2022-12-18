package niu.edu.water.weather;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherAPIInterface {
    @GET("api/v1/rest/datastore/O-A0001-001?Authorization=CWB-24B48CCB-D936-4E65-8CEF-654DE47CD55A")
    Call<Weather> getData();
}
