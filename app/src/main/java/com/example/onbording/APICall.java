package com.example.onbording;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APICall {
    //https://run.mocky.io/v3/e172e4ed-71ea-4fe8-903c-a8bb45a2523f
    @GET("v3/e172e4ed-71ea-4fe8-903c-a8bb45a2523f")
     Call<DataModel> getData();
}
