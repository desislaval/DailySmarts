package com.dessylazarowa.android.dailysmarts.Model.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuoteApi {
    @GET("?method=getQuote&format=json&lang=en")
    Call<QuoteModel> getQuote();
}
