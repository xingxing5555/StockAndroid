package com.cf.basketball.activity;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.adapter.currency.CurrencyLineAdapter;
import com.cf.basketball.databinding.ActivityTrendChartLandBinding;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.model.HSKlineModel;
import com.example.admin.basic.model.HSTodayModel;
import com.example.admin.basic.net.RequestManager;
import com.example.admin.basic.stock.KlineView;
import com.example.admin.basic.stock.MLineView;
import com.example.admin.basic.stock.TabIndicatorViewV2;
import com.example.admin.basic.utils.LogUtils;

import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 趋势图详情
 *
 * @author admin xinxin Shi
 */
public class TrendChartActivity extends BaseActivity implements
        TabIndicatorViewV2.OnTabSelectedListener, KlineView.GetMoreDataCallback, View
        .OnClickListener {

    private ActivityTrendChartLandBinding binding;

    public int yearDay = 2017;
    public int yearWeek = 2017;
    public int yearMonth = 2017;
    HSTodayModel hsModel;
    HSKlineModel hsDayKlineModel;
    HSKlineModel hsWeekKlineModel;
    HSKlineModel hsMonthKlineModel;

    String stockCode = "1399001";
    String curMarket = HS_MARKET;
    boolean canRefresh;
    int currentChart;

    @Override
    public void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trend_chart_land);
        binding.mLineView.setMarket(HS_MARKET);
        String[] title = getResources().getStringArray(R.array.currency_trend);
        binding.tabIndicatorView.setTitles(Arrays.asList(title));
        binding.tabIndicatorView.setOnTabSelectedListener(this);
        binding.ivClose.setOnClickListener(this);
        binding.mrvLine.setLayoutManager(createGridLayoutManager(3));
        binding.mrvLine.setAdapter(new CurrencyLineAdapter(this, R.layout.item_currency_kline_land,
                getData()));
    }

    @Override
    public void initData() {
        TrendChartActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Calendar now = Calendar.getInstance();
        yearDay = now.get(Calendar.YEAR);
        yearWeek = now.get(Calendar.YEAR);
        yearMonth = now.get(Calendar.YEAR);
        currentChart = MLINE;
        refresh();
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


    private void changeChartView() {
        binding.mLineView.setMarket(curMarket);
//        buySellContainer.setVisibility(View.GONE);
//        kLineTypeView.setVisibility(View.GONE);
        refresh();
        switch (currentChart) {
            case MLINE:
                binding.mLineView.clearData();
                binding.mLineView.setVisibility(View.VISIBLE);
//                binding.llLine.setVisibility(View.VISIBLE);

                if (hsModel != null) binding.mLineView.setData(hsModel.parseData());
                break;
            case DAY_KLINE:
                binding.kDayLineView.clearData();
                binding.kDayLineView.setVisibility(View.VISIBLE);
//                kLineTypeView.setVisibility(View.VISIBLE);
                if (hsDayKlineModel != null) {
                    binding.kDayLineView.setData(hsDayKlineModel.parseData(), DAY_KLINE, false,
                            KlineView.K_BOTTOM_TYPE_KDJ);
                }
                break;
            case WEEK_KLINE:
                binding.kWeekLineView.clearData();
                binding.kWeekLineView.setVisibility(View.VISIBLE);
//                kLineTypeView.setVisibility(View.VISIBLE);
                if (hsWeekKlineModel != null) {
                    binding.kWeekLineView.setData(hsWeekKlineModel.parseData(), WEEK_KLINE,
                            false, KlineView.K_BOTTOM_TYPE_CJL);
                }
                break;
            case MONTH_KLINE:
                binding.kMonthLineView.clearData();
                binding.kMonthLineView.setVisibility(View.VISIBLE);
//                kLineTypeView.setVisibility(View.VISIBLE);
                if (hsMonthKlineModel != null) {
                    binding.kMonthLineView.setData(hsMonthKlineModel.parseData(), MONTH_KLINE,
                            false, KlineView.K_BOTTOM_TYPE_KDJ);
                }
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
                    binding.mLineView.setMlineType(MLineView.ONE_DAY_MINUTES_LINE);
                    binding.mLineView.setData(hsModel.parseData());
                    binding.mLineView.postInvalidate();
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
                    binding.kDayLineView.setMoreData(hsDayKlineModel.parseData());
                } else {
                    if (hsDayKlineModel.getData().size() < 200) {
                        getDayData(market, stockCode, false);
                    }
                }
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {
                if (getMore) {
                    binding.kDayLineView.getMore = false;
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
                    binding.kDayLineView.setData(hsDayKlineModel.parseData(), DAY_KLINE, false,
                            KlineView.K_BOTTOM_TYPE_KDJ);
                    binding.kDayLineView.postInvalidate();
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
                    binding.kWeekLineView.setMoreData(hsWeekKlineModel.parseData());
                } else {
                    if (hsWeekKlineModel.getData().size() < 100) {
                        getWeekData(market, stockCode, false);
                    }
                }
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {
                if (getMore) {
                    binding.kWeekLineView.getMore = false;
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
                    binding.kWeekLineView.setData(hsWeekKlineModel.parseData(), WEEK_KLINE,
                            false, KlineView.K_BOTTOM_TYPE_CJL);
//                    hsWeekKlineModel.refresNewestData(response.body().getData().get(response.body
//                            ().getData().size() - 1));
                    binding.kWeekLineView.postInvalidate();
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
                    binding.kMonthLineView.setMoreData(hsMonthKlineModel.parseData());
                } else {
                    if (hsMonthKlineModel.getData().size() < 100) {
                        getMonthData(market, stockCode, false);
                    }
                }
            }

            @Override
            public void onFailure(Call<HSKlineModel> call, Throwable t) {
                if (getMore) {
                    binding.kMonthLineView.getMore = false;
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
                    binding.kMonthLineView.setData(hsMonthKlineModel.parseData(), MONTH_KLINE,
                            false, KlineView.K_BOTTOM_TYPE_KDJ);
                    binding.kMonthLineView.postInvalidate();
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
    public void onTabSelected(int position) {
        binding.kDayLineView.setVisibility(View.GONE);
        binding.kWeekLineView.setVisibility(View.GONE);
        binding.kMonthLineView.setVisibility(View.GONE);
        binding.mLineView.setVisibility(View.GONE);
//        binding.llLine.setVisibility(View.GONE);
        currentChart = position;
        changeChartView();
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.iv_close:
                    finish();
                    break;
                default:
                    break;
        }
    }
}
