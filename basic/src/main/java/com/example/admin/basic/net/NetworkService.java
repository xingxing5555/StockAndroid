package com.example.admin.basic.net;

import com.example.admin.basic.model.HSFiveDayModel;
import com.example.admin.basic.model.HSKlineModel;
import com.example.admin.basic.model.HSTodayModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by luoyu on 2017/4/2.
 */
public interface NetworkService {
//    @GET("login")
//    Call<LoginModel> login(@Query("user") String account, @Query("passwd") String pwd);
//    @FormUrlEncoded
//    @POST("list")
//    Call<MatchListModel> getNBAMatchList(@Field("user") String accout, @Field("token") String
// token);
//    @FormUrlEncoded
//    @POST("set_push_rule")
//    Call<MatchModel> getMatch(@Field("user") String accout, @Field("token") String token,
// @Field("id") String id);

    //-----------A股-------------
//    http://img1.money.126.net/data/hs/time/today/0600519.json
    @GET("hs/time/today/{stockCode}.json")
    Call<HSTodayModel> getHSTodayModel(@Path("stockCode") String stockCode);


    @GET("hs/time/4days/{stockCode}.json")
    Call<HSFiveDayModel> getHS4daysModel(@Path("stockCode") String stockCode);

    @GET("hs/kline/day/history/{year}/{stockCode}.json")
    Call<HSKlineModel> getDayKlineModelWithYear(@Path("year") String year, @Path("stockCode")
            String stockCode);

    @GET("hs/kline/week/history/{year}/{stockCode}.json")
    Call<HSKlineModel> getWeekKlineModelWithYear(@Path("year") String year, @Path("stockCode")
            String stockCode);

    @GET("hs/kline/month/history/{year}/{stockCode}.json")
    Call<HSKlineModel> getMonthKlineModelWithYear(@Path("year") String year, @Path("stockCode")
            String stockCode);


    @GET("{market}/time/today/{stockCode}.json")
    Call<HSTodayModel> getTodayModelWithMarket(@Path("market") String market, @Path("stockCode")
            String stockCode);

    @GET("{market}/time/4days/{stockCode}.json")
    Call<HSFiveDayModel> get4daysModelWithMarket(@Path("market") String market, @Path
            ("stockCode") String stockCode);

    @GET("{market}/kline/day/history/{year}/{stockCode}.json")
    Call<HSKlineModel> getDayKlineModelWithMarket(@Path("market") String market, @Path("year")
            String year, @Path("stockCode") String stockCode);

    @GET("{market}/kline/week/history/{year}/{stockCode}.json")
    Call<HSKlineModel> getWeekKlineModelWithMarket(@Path("market") String market, @Path("year")
            String year, @Path("stockCode") String stockCode);

    @GET("{market}/kline/month/history/{year}/{stockCode}.json")
    Call<HSKlineModel> getMonthKlineModelWithMarket(@Path("market") String market, @Path("year")
            String year, @Path("stockCode") String stockCode);


    @GET("v1/tabs")
    Call<String> getHomeTab();

    @GET("v1/coins/mine")
    Call<String> getMineList(@Query("token") String token, @Query("pageNum") int pageNum, @Query
            ("order") int order);

    @GET("v1/coins/updown")
    Call<String> getUpDown(@Query("pageNum") int pageNum, @Query("order") int order);

    @GET("v1/coins/{id}")
    Call<String> getCoinData(@Query("pageNum") int pageNum, @Path("id") String id);
}