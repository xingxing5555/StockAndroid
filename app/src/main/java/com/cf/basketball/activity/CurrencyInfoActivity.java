package com.cf.basketball.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cf.basketball.R;
import com.cf.basketball.adapter.currency.CurrencyLineAdapter;
import com.cf.basketball.adapter.home.HomeCurrencyInfoDataAdapter;
import com.cf.basketball.databinding.ActivityCurrencyInfoBinding;
import com.cf.basketball.fragment.currency.CurrencyInfoBriefFragment;
import com.cf.basketball.fragment.currency.CurrencyInfoMarketFragment;
import com.cf.basketball.fragment.currency.CurrencyInfoNewsFragment;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.interfaces.OnItemClickListener;
import com.example.admin.basic.interfaces.OnScrollChangedListener;
import com.example.admin.basic.model.HSKlineModel;
import com.example.admin.basic.model.HSTodayModel;
import com.example.admin.basic.net.RequestManager;
import com.example.admin.basic.stock.KlineView;
import com.example.admin.basic.stock.MLineView;
import com.example.admin.basic.stock.TabIndicatorViewV2;
import com.example.admin.basic.utils.DateUtils;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.view.MeasureRecyclerView;

import org.greenrobot.eventbus.EventBus;

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
    HSKlineModel hsDayKlineModel;
    HSKlineModel hsWeekKlineModel;
    HSKlineModel hsMonthKlineModel;
    TabIndicatorViewV2 tabIndicatorView;
    MLineView mLineView;
    KlineView kDayLineView;
    KlineView kWeekLineView;
    KlineView kMonthLineView;
    LinearLayout llLine;
    MeasureRecyclerView mrvLine;
    TextView tvLineTime;

    String stockCode = "1399001";
    String curMarket = HS_MARKET;
    boolean canRefresh;
    int currentChart;

    @Override
    public void initView() {
        RequestManager.init();
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
        llLine = (LinearLayout) this.findViewById(R.id.ll_line);
        mrvLine = (MeasureRecyclerView) this.findViewById(R.id.mrv_line);
        tvLineTime = (TextView) this.findViewById(R.id.tv_line_time);

        String[] title = getResources().getStringArray(R.array.currency_trend);
        tabIndicatorView.setTitles(Arrays.asList(title));
        tabIndicatorView.setOnTabSelectedListener(this);
        mLineView.setOnClickListener(listener);
        kDayLineView.setOnClickListener(listener);
        kWeekLineView.setOnClickListener(listener);
        kMonthLineView.setOnClickListener(listener);
        binding.rlShare.setOnClickListener(this);
        binding.tvAddOptional.setOnClickListener(this);
        mrvLine.setLayoutManager(createGridLayoutManager(3));
        mrvLine.setAdapter(new CurrencyLineAdapter(this, R.layout.item_currency_kline, getData()));
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity(TrendChartActivity.class);
        }
    };

    @Override
    public void initData() {
        Calendar now = Calendar.getInstance();
        yearDay = now.get(Calendar.YEAR);
        yearWeek = now.get(Calendar.YEAR);
        yearMonth = now.get(Calendar.YEAR);
        currentChart = MLINE;
        refresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        CurrencyInfoActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        CurrencyInfoActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);

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
        llLine.setVisibility(View.GONE);
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
        refresh();
        switch (currentChart) {
            case MLINE:
                mLineView.clearData();
                mLineView.setVisibility(View.VISIBLE);
                llLine.setVisibility(View.VISIBLE);

                if (hsModel != null) mLineView.setData(hsModel.parseData());
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
            case DAY_KLINE:
                refreshDayData(curMarket, stockCode);
                break;
            case WEEK_KLINE:
                refreshWeekData(curMarket, stockCode);
                break;
            case MONTH_KLINE:
                refreshMonthData(curMarket, stockCode);
                break;
            default:
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
                    mLineView.setMlineType(MLineView.ONE_DAY_MINUTES_LINE);
                    mLineView.setData(hsModel.parseData());
                    mLineView.postInvalidate();
                } else {
                    changeChartView();

                }
                canRefresh = true;
            }

            @Override
            public void onFailure(Call<HSTodayModel> call, Throwable t) {
                LogUtils.e("错误日志：" + t.getMessage());
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
                hsDayKlineModel = response.body();
//                if (hsDayKlineModel != null && hsDayKlineModel.getName().equals(response.body()
//                        .getName())) {
                if (hsDayKlineModel != null) {
//                    hsDayKlineModel.refresNewestData(response.body().getData().get(response.body
//                            ().getData().size() - 1));
                    kDayLineView.setData(hsDayKlineModel.parseData(), DAY_KLINE, false, KlineView
                            .K_BOTTOM_TYPE_KDJ);
                    kDayLineView.postInvalidate();
                }
                canRefresh = true;
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {
                LogUtils.e("错误日志：" + t.getMessage());
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
                hsWeekKlineModel = response.body();
//                if (hsWeekKlineModel != null && hsWeekKlineModel.getName().equals(response.body()
//                        .getName())) {
                if (hsWeekKlineModel != null) {
                    kWeekLineView.setData(hsWeekKlineModel.parseData(), WEEK_KLINE, false,
                            KlineView.K_BOTTOM_TYPE_CJL);
//                    hsWeekKlineModel.refresNewestData(response.body().getData().get(response.body
//                            ().getData().size() - 1));
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
                hsMonthKlineModel = response.body();
//                if (hsMonthKlineModel != null && hsMonthKlineModel.getName().equals(response.body
//                        ().getName())) {
                if (hsMonthKlineModel != null) {
//                    hsMonthKlineModel.refresNewestData(response.body().getData().get(response
//                            .body().getData().size() - 1));
                    kMonthLineView.setData(hsMonthKlineModel.parseData(), MONTH_KLINE, false,
                            KlineView.K_BOTTOM_TYPE_KDJ);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("详情页的onResult");
    }
}
