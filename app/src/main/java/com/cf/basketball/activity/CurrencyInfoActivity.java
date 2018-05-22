package com.cf.basketball.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.cf.basketball.R;
import com.cf.basketball.adapter.home.HomeCurrencyInfoDataAdapter;
import com.cf.basketball.databinding.ActivityCurrencyInfoBinding;
import com.cf.basketball.fragment.currency.CurrencyInfoBriefFragment;
import com.cf.basketball.fragment.currency.CurrencyInfoMarketFragment;
import com.cf.basketball.fragment.currency.CurrencyInfoNewsFragment;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.interfaces.OnItemClickListener;
import com.example.admin.basic.interfaces.OnScrollChangedListener;
import com.example.admin.basic.model.HSFiveDayModel;
import com.example.admin.basic.model.HSKlineModel;
import com.example.admin.basic.model.HSTodayModel;
import com.example.admin.basic.net.RequestManager;
import com.example.admin.basic.stock.KlineView;
import com.example.admin.basic.stock.MLineView;
import com.example.admin.basic.stock.TabIndicatorViewV2;
import com.example.admin.basic.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 详情界面
 *
 * @author xinxin Shi
 */
public class CurrencyInfoActivity extends BaseActivity implements OnScrollChangedListener,
        OnItemClickListener, TabIndicatorViewV2.OnTabSelectedListener, KlineView
                .GetMoreDataCallback, View.OnClickListener {

    private ActivityCurrencyInfoBinding binding;
    private String currentTime;
    private FragmentManager fragmentManager;
    private CurrencyInfoNewsFragment currencyInfoNewsFragment;
    private CurrencyInfoBriefFragment currencyInfoBriefFragment;
    private CurrencyInfoMarketFragment currencyInfoMarketFragment;
    public int yearDay = 2017;
    public int yearWeek = 2017;
    public int yearMonth = 2017;
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
    String stockCode;
    String curMarket;
    boolean canRefresh;
    int currentChart;
    Handler mHandler = new Handler();

    @Override
    public void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency_info);
        currentTime = DateUtils.getCurrentTime();
        binding.rtlText.setTvToolbarContent(currentTime);
        binding.svInfoContainer.setOnScrollChangedListener(this);
        binding.rvInfoData.setLayoutManager(createGridLayoutManager(2));
        binding.rvInfoData.setAdapter(new HomeCurrencyInfoDataAdapter(this, getData()));
        binding.slCurrencyNavigation.setOnItemClickListener(this);
        fragmentManager = getSupportFragmentManager();
        currencyInfoNewsFragment = new CurrencyInfoNewsFragment();
        currencyInfoBriefFragment = new CurrencyInfoBriefFragment();
        currencyInfoMarketFragment = new CurrencyInfoMarketFragment();
        fragmentManager.beginTransaction().add(R.id.fl_currency_container,
                currencyInfoNewsFragment).add(R.id.fl_currency_container,
                currencyInfoBriefFragment).add(R.id.fl_currency_container,
                currencyInfoMarketFragment).show(currencyInfoNewsFragment).hide
                (currencyInfoBriefFragment).hide(currencyInfoMarketFragment).commit();
        tabIndicatorView = (TabIndicatorViewV2) this.findViewById(R.id.tabIndicatorView);
        mLineView = (MLineView) this.findViewById(R.id.mLineView);
        mLineView.setMarket(HS_MARKET);
        kDayLineView = (KlineView) this.findViewById(R.id.kDayLineView);
        kWeekLineView = (KlineView) this.findViewById(R.id.kWeekLineView);
        kMonthLineView = (KlineView) this.findViewById(R.id.kMonthLineView);
        String[] title = getResources().getStringArray(R.array.currency_trend);
        tabIndicatorView.setTitles(Arrays.asList(title));
        tabIndicatorView.setOnTabSelectedListener(this);
        mLineView.setOnClickListener(listener);
        kDayLineView.setOnClickListener(listener);
        kWeekLineView.setOnClickListener(listener);
        kMonthLineView.setOnClickListener(listener);
        binding.rlShare.setOnClickListener(this);
        binding.tvAddOptional.setOnClickListener(this);
//        initSpinner();
    }

    private void initSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        editText = (EditText) findViewById(R.id.editText);
        //数据
        ArrayList<String> data_list = new ArrayList<String>();
        data_list.add("沪深");
        data_list.add("港股");
        data_list.add("美股");

        //适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        curMarket = HS_MARKET;
                        break;
                    case 1:
                        curMarket = HK_MARKET;
                        break;
                    case 2:
                        curMarket = US_MARKET;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CurrencyInfoActivity.this.setRequestedOrientation(ActivityInfo
                    .SCREEN_ORIENTATION_LANDSCAPE);
        }
    };

    @Override
    public void initData() {
        Calendar now = Calendar.getInstance();
        yearDay = now.get(Calendar.YEAR);
        yearWeek = now.get(Calendar.YEAR);
        yearMonth = now.get(Calendar.YEAR);
    }


    /**
     * ScrollView的滑动监听
     *
     * @param top    顶部位置
     * @param oldTop 旧顶部位置
     */
    @Override
    public void onScrollChanged(int top, int oldTop) {
        if (top >= binding.llInfoContent.getBottom()) {
            binding.rtlText.setTvToolbarContent("交易量什么的啊");
            binding.rtlText.setDownVisible(true);
        } else {
            binding.rtlText.setTvToolbarContent(currentTime);
            binding.rtlText.setDownVisible(false);
        }
    }

    /**
     * 横竖屏切换监听
     *
     * @param newConfig 新配置
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int mCurrentOrientation = getResources().getConfiguration().orientation;
        if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            startActivity(TrendChartActivity.class);
            return;
        }

    }


    @Override
    public void onItemClickListener(int item) {
        switch (item) {
            case 1:
                fragmentManager.beginTransaction().hide(currencyInfoNewsFragment).show
                        (currencyInfoBriefFragment).hide(currencyInfoMarketFragment)
                        .commitAllowingStateLoss();
                break;
            case 2:
                fragmentManager.beginTransaction().hide(currencyInfoNewsFragment).hide
                        (currencyInfoBriefFragment).show(currencyInfoMarketFragment)
                        .commitAllowingStateLoss();
                break;
            default:
                fragmentManager.beginTransaction().show(currencyInfoNewsFragment).hide
                        (currencyInfoBriefFragment).hide(currencyInfoMarketFragment)
                        .commitAllowingStateLoss();
                break;
        }
    }

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
            default:
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
            default:
                break;
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_optional:
                EventBus.getDefault().post(createData().get(0));
                break;
            case R.id.rl_share:
                //TODO 用于分享的本页图片
                Bitmap bitmap = shotScrollView(binding.svInfoContainer);
                break;
            default:
                break;
        }
    }

    public Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            binding.svInfoContainer.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }
}
