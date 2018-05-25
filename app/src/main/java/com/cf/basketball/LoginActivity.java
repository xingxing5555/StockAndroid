package com.cf.basketball;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.admin.basic.model.HSFiveDayModel;
import com.example.admin.basic.model.HSKlineModel;
import com.example.admin.basic.model.HSTodayModel;
import com.example.admin.basic.net.RequestManager;
import com.example.admin.basic.stock.KlineView;
import com.example.admin.basic.stock.MLineView;
import com.example.admin.basic.stock.TabIndicatorViewV2;

import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements KlineView.GetMoreDataCallback {
    public static final int MLINE = 0;
    public static final int FIVE_MLINE = 1;
    public static final int DAY_KLINE = 2;
    public static final int WEEK_KLINE = 3;
    public static final int MONTH_KLINE = 4;

    public int yearDay = 2017;
    public int yearWeek = 2017;
    public int yearMonth = 2017;

    public static boolean isLandScape;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{"foo@example.com:hello",
            "bar@example.com:world"};

    HSTodayModel hsModel;
    HSFiveDayModel hsFiveModel;
    HSKlineModel hsDayKlineModel;
    HSKlineModel hsWeekKlineModel;
    HSKlineModel hsMonthKlineModel;
    TabIndicatorViewV2 tabIndicatorView;
    MLineView mLineView;
    KlineView kDayLineView;
    KlineView kWeekLineView;
    KlineView kMonthLineView;

    Spinner spinner;
    EditText editText;


    public static final String HS_MARKET = "hs";
    public static final String HK_MARKET = "hk";
    public static final String US_MARKET = "us";
    String stockCode;
    String curMarket;
    boolean canRefresh;
    int currentChart;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Calendar now = Calendar.getInstance();
        yearDay = now.get(Calendar.YEAR);
        yearWeek = now.get(Calendar.YEAR);
        yearMonth = now.get(Calendar.YEAR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabIndicatorView = (TabIndicatorViewV2) this.findViewById(R.id.tabIndicatorView);
        mLineView = (MLineView) this.findViewById(R.id.mLineView);
        mLineView.setMarket(HS_MARKET);
        kDayLineView = (KlineView) this.findViewById(R.id.kDayLineView);
        kWeekLineView = (KlineView) this.findViewById(R.id.kWeekLineView);
        kMonthLineView = (KlineView) this.findViewById(R.id.kMonthLineView);
        String[] title = getResources().getStringArray(R.array.currency_trend);
        tabIndicatorView.setTitles(Arrays.asList(title));
        tabIndicatorView.setOnTabSelectedListener(new TabIndicatorViewV2.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                kDayLineView.setVisibility(View.GONE);
                kWeekLineView.setVisibility(View.GONE);
                kMonthLineView.setVisibility(View.GONE);
                mLineView.setVisibility(View.GONE);
                currentChart = position;
                changeChartView();
            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.setRequestedOrientation(ActivityInfo
                        .SCREEN_ORIENTATION_LANDSCAPE);
            }
        };
        mLineView.setOnClickListener(listener);
        kDayLineView.setOnClickListener(listener);
        kWeekLineView.setOnClickListener(listener);
        kMonthLineView.setOnClickListener(listener);
//        initView();
    }

    public void reset() {
        Calendar now = Calendar.getInstance();
        yearDay = now.get(Calendar.YEAR);
        yearWeek = now.get(Calendar.YEAR);
        yearMonth = now.get(Calendar.YEAR);
        hsModel = null;
        hsFiveModel = null;
        hsDayKlineModel = null;
        hsWeekKlineModel = null;
        hsMonthKlineModel = null;
    }

    public void onClick(View view) {
        if (!TextUtils.isEmpty(editText.getText())) {
            reset();
            tabIndicatorView.selectPosition(0, true);
            String code = editText.getText().toString();
            code = curMarket.equals(HS_MARKET) ? "1" + code : code;
            stockCode = code;
            getTodayData(curMarket, code, false);
            getFiveDayData(curMarket, code);
            getDayData(curMarket, code, false);
            getWeekData(curMarket, code, false);
            getMonthData(curMarket, code, false);
            mHandler.postDelayed(refreshData, 6000);
        }
    }

    Runnable refreshData = new Runnable() {
        @Override
        public void run() {
            if (canRefresh) {
                refresh();
                mHandler.postDelayed(refreshData, 6000);
            } else {
                mHandler.postDelayed(refreshData, 2000);
            }
        }
    };
//
//    private void initView() {
//        spinner = (Spinner) findViewById(R.id.spinner);
//        editText = (EditText) findViewById(R.id.editText);
//        //数据
//        ArrayList<String> data_list = new ArrayList<String>();
//        data_list.add("沪深");
//        data_list.add("港股");
//        data_list.add("美股");
//
//        //适配器
//        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this, android.R.layout
//                .simple_spinner_item, data_list);
//        //设置样式
//        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //加载适配器
//        spinner.setAdapter(arr_adapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        curMarket = HS_MARKET;
//                        break;
//                    case 1:
//                        curMarket = HK_MARKET;
//                        break;
//                    case 2:
//                        curMarket = US_MARKET;
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        int mCurrentOrientation = getResources().getConfiguration().orientation;
        if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandScape = true;
            return;
        }
        isLandScape = false;

    }


    private void changeChartView() {
        mLineView.setMarket(curMarket);
//        buySellContainer.setVisibility(View.GONE);
//        kLineTypeView.setVisibility(View.GONE);
        switch (currentChart) {
            case MLINE:
                mLineView.clearData();
                mLineView.setVisibility(View.VISIBLE);
                mLineView.setMlineType(MLineView.ONE_DAY_MINUTES_LINE);
                if (hsModel != null) mLineView.setData(hsModel.parseData());
                break;
            case FIVE_MLINE:
                mLineView.clearData();
                mLineView.setVisibility(View.VISIBLE);
                mLineView.setMlineType(MLineView.FIVE_DAY_MINUTES_LINE);
                if (hsFiveModel != null)
                    mLineView.setData(hsFiveModel.parseData(hsModel, curMarket));

                break;
            case DAY_KLINE:
                kDayLineView.clearData();
                kDayLineView.setVisibility(View.VISIBLE);
//                kLineTypeView.setVisibility(View.VISIBLE);
                if (hsDayKlineModel != null) {
                    kDayLineView.setData(hsDayKlineModel.parseData(), DAY_KLINE, false, KlineView
                            .K_BOTTOM_TYPE_KDJ);
                }
                break;
            case WEEK_KLINE:
                kWeekLineView.clearData();
                kWeekLineView.setVisibility(View.VISIBLE);
//                kLineTypeView.setVisibility(View.VISIBLE);
                if (hsWeekKlineModel != null) {
                    kWeekLineView.setData(hsWeekKlineModel.parseData(), WEEK_KLINE, false,
                            KlineView.K_BOTTOM_TYPE_CJL);
                }
                break;
            case MONTH_KLINE:
                kMonthLineView.clearData();
                kMonthLineView.setVisibility(View.VISIBLE);
//                kLineTypeView.setVisibility(View.VISIBLE);
                if (hsMonthKlineModel != null) {
                    kMonthLineView.setData(hsMonthKlineModel.parseData(), MONTH_KLINE, false,
                            KlineView.K_BOTTOM_TYPE_KDJ);
                }
                break;
        }
    }

    private void refresh() {
        canRefresh = false;
        switch (currentChart) {
            case MLINE:
                getTodayData(curMarket, stockCode, true);
                break;
            case FIVE_MLINE:
                getTodayData(curMarket, stockCode, true);
                break;
            case DAY_KLINE:
                refreshDayData(curMarket, stockCode);
                break;
            case WEEK_KLINE:
                refreshWeekData(curMarket, stockCode);
                break;
            case MONTH_KLINE:
                refreshMonthData(curMarket, stockCode);
                break;

        }
    }

    private void getTodayData(String market, String stockCode, final boolean isRefresh) {
        Call<HSTodayModel> call = RequestManager.getService().getTodayModelWithMarket(market,
                stockCode);
        call.enqueue(new Callback<HSTodayModel>() {
            @Override
            public void onResponse(Call<HSTodayModel> call, Response<HSTodayModel> response) {
                hsModel = response.body();
                if (isRefresh) {
                    if (currentChart == FIVE_MLINE) {
                        mLineView.setData(hsFiveModel.parseData(hsModel, curMarket));
                    } else if (currentChart == MLINE) {
                        mLineView.setData(hsModel.parseData());
                    }
                    mLineView.postInvalidate();
                } else {
                    changeChartView();

                }
                canRefresh = true;
            }

            @Override
            public void onFailure(Call<HSTodayModel> call, Throwable t) {

            }
        });
    }

    private void getFiveDayData(String market, String stockCode) {
        Call<HSFiveDayModel> call = RequestManager.getService().get4daysModelWithMarket(market,
                stockCode);
        call.enqueue(new Callback<HSFiveDayModel>() {
            @Override
            public void onResponse(Call<HSFiveDayModel> call, Response<HSFiveDayModel> response) {
                hsFiveModel = response.body();
            }

            @Override
            public void onFailure(Call<HSFiveDayModel> call, Throwable t) {

            }
        });
    }

    private void getDayData(final String market, final String stockCode, final boolean getMore) {
        Call<HSKlineModel> call = RequestManager.getService().getDayKlineModelWithMarket(market,
                String.valueOf(yearDay), stockCode);
        call.enqueue(new Callback<HSKlineModel>() {
            @Override
            public void onResponse(Call<HSKlineModel> call, Response<HSKlineModel> response) {
                if (response.body() == null) {
                    return;
                }
                if (hsDayKlineModel != null && hsDayKlineModel.getName().equals(response.body()
                        .getName())) {
                    hsDayKlineModel.add(response.body());
                } else {
                    hsDayKlineModel = response.body();
                }
                yearDay--;
                if (getMore) {
                    kDayLineView.setMoreData(hsDayKlineModel.parseData());
                } else {
                    if (hsDayKlineModel.getData().size() < 200) {
                        getDayData(market, stockCode, false);
                    }
                }
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {
                if (getMore) {
                    kDayLineView.getMore = false;
                }
            }
        });
    }

    private void refreshDayData(final String market, final String stockCode) {
        Calendar now = Calendar.getInstance();
        Call<HSKlineModel> call = RequestManager.getService().getDayKlineModelWithMarket(market,
                String.valueOf(now.get(Calendar.YEAR)), stockCode);
        call.enqueue(new Callback<HSKlineModel>() {
            @Override
            public void onResponse(Call<HSKlineModel> call, Response<HSKlineModel> response) {
                if (response.body() == null) {
                    return;
                }
                if (hsDayKlineModel != null && hsDayKlineModel.getName().equals(response.body()
                        .getName())) {
                    hsDayKlineModel.refresNewestData(response.body().getData().get(response.body
                            ().getData().size() - 1));
                    kDayLineView.postInvalidate();
                }
                canRefresh = true;
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {

            }
        });
    }

    private void getWeekData(final String market, final String stockCode, final boolean getMore) {
        Call<HSKlineModel> call = RequestManager.getService().getWeekKlineModelWithMarket(market,
                String.valueOf(yearWeek), stockCode);
        call.enqueue(new Callback<HSKlineModel>() {
            @Override
            public void onResponse(Call<HSKlineModel> call, Response<HSKlineModel> response) {
                if (response.body() == null) {
                    return;
                }
                if (hsWeekKlineModel != null && hsWeekKlineModel.getName().equals(response.body()
                        .getName())) {
                    hsWeekKlineModel.add(response.body());
                } else {
                    hsWeekKlineModel = response.body();
                }
                yearWeek--;
                if (getMore) {
                    kWeekLineView.setMoreData(hsWeekKlineModel.parseData());
                } else {
                    if (hsWeekKlineModel.getData().size() < 100) {
                        getWeekData(market, stockCode, false);
                    }
                }
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {
                if (getMore) {
                    kWeekLineView.getMore = false;
                }
            }
        });
    }

    private void refreshWeekData(final String market, final String stockCode) {
        Calendar now = Calendar.getInstance();
        Call<HSKlineModel> call = RequestManager.getService().getWeekKlineModelWithMarket(market,
                String.valueOf(now.get(Calendar.YEAR)), stockCode);
        call.enqueue(new Callback<HSKlineModel>() {
            @Override
            public void onResponse(Call<HSKlineModel> call, Response<HSKlineModel> response) {
                if (response.body() == null) {
                    return;
                }
                if (hsWeekKlineModel != null && hsWeekKlineModel.getName().equals(response.body()
                        .getName())) {
                    hsWeekKlineModel.refresNewestData(response.body().getData().get(response.body
                            ().getData().size() - 1));
                    kWeekLineView.postInvalidate();
                }
                canRefresh = true;
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {

            }
        });
    }


    private void getMonthData(final String market, final String stockCode, final boolean getMore) {
        Call<HSKlineModel> call = RequestManager.getService().getMonthKlineModelWithMarket
                (market, String.valueOf(yearMonth), stockCode);
        call.enqueue(new Callback<HSKlineModel>() {
            @Override
            public void onResponse(Call<HSKlineModel> call, Response<HSKlineModel> response) {
                if (response.body() == null) {
                    return;
                }
                if (hsMonthKlineModel != null && hsMonthKlineModel.getName().equals(response.body
                        ().getName())) {
                    hsMonthKlineModel.add(response.body());
                } else {
                    hsMonthKlineModel = response.body();
                }
                yearMonth--;
                if (getMore) {
                    kMonthLineView.setMoreData(hsMonthKlineModel.parseData());
                } else {
                    if (hsMonthKlineModel.getData().size() < 100) {
                        getMonthData(market, stockCode, false);
                    }
                }
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {
                if (getMore) {
                    kMonthLineView.getMore = false;
                }
            }
        });
    }

    private void refreshMonthData(final String market, final String stockCode) {
        Calendar now = Calendar.getInstance();
        Call<HSKlineModel> call = RequestManager.getService().getMonthKlineModelWithMarket
                (market, String.valueOf(now.get(Calendar.YEAR)), stockCode);
        call.enqueue(new Callback<HSKlineModel>() {
            @Override
            public void onResponse(Call<HSKlineModel> call, Response<HSKlineModel> response) {
                if (response.body() == null) {
                    return;
                }
                if (hsMonthKlineModel != null && hsMonthKlineModel.getName().equals(response.body
                        ().getName())) {
                    hsMonthKlineModel.refresNewestData(response.body().getData().get(response
                            .body().getData().size() - 1));
                    kMonthLineView.postInvalidate();
                }
                canRefresh = true;
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void getMoreData(int klineType) {
        switch (klineType) {
            case DAY_KLINE:
                getDayData(stockCode, curMarket, true);
                break;
            case WEEK_KLINE:
                getWeekData(stockCode, curMarket, true);
                break;
            case MONTH_KLINE:
                getMonthData(stockCode, curMarket, true);
                break;
        }
    }
}

