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


    /**
     * 首页导航栏标签
     *
     * @return Call对象
     */
    @GET("v1/tabs")
    Call<String> getHomeTab();

    @GET("v1/exchanges/mine/order")
    Call<String> changeOrder(@Query("token") String token, @Query("ids") String ids);

    @GET("v1/exchanges/mine")
    Call<String> getMineList(@Query("token") String token, @Query("pageNum") int pageNum, @Query
            ("order") int order);

    /**
     * 首页市值
     *
     * @param pageNum 页码
     * @param order   排序
     * @return Call对象
     */
    @GET("v1/coins/value")
    Call<String> getMarketList(@Query("pageNum") int pageNum, @Query("order") int order);

    /**
     * 添加/删除自选货币
     *
     * @param token 用户token
     * @param id    标识
     * @param event 事件
     * @return Call对象
     */
    @GET("v1/exchanges/mine/add_del")
    Call<String> addOrDelCurrency(@Query("token") String token, @Query("id") String id, @Query
            ("event") String event);

//   首页


    @GET("v1/exchanges/updown")
    Call<String> getUpDown(@Query("pageNum") int pageNum, @Query("order") int order);

    @GET("v1/market/{id}")
    Call<String> getHomeHuobiList(@Path("id") String id, @Query("pageNum") int pageNum);

    @GET("v1/coins/{id}")
    Call<String> getCoinData(@Path("id") String id, @Query("pageNum") int pageNum);

    @GET("v1/searchpage")
    Call<String> getDefaultSearchData(@Query("token") String token);

    @GET("v1/search")
    Call<String> getSearchKeyData(@Query("key") String key, @Query("token") String token);

    //    货币详情

    @GET("v1/exchanges/base")
    Call<String> getCurrencyInfo(@Query("id") String id);

    @GET("v1/exchanges/mline")
    Call<String> getMline(@Query("id") String id);

    @GET("v1/exchanges/kline")
    Call<String> getKline(@Query("id") String id, @Query("klineType") int klineType);

    @GET("v1/market/{id}")
    Call<String> getCurrencyMarketList(@Query("pageNum") int pageNum, @Path("id") String id);

    @GET("v1/exchanges/detail")
    Call<String> getCurrencyMarketData(@Query("id") String id);

    @GET("v1/exchanges/info")
    Call<String> getCurrencyBriefData(@Query("id") String id);

    //    市值详情

    @GET("v1/market/coin")
    Call<String> getMarketInfo(@Query("id") String id);

    @GET("v1/market/desc")
    Call<String> getMarketDesc(@Query("id") String id);


    @GET("v1/market/market")
    Call<String> getBtcMarket(@Query("id") String id);
}
