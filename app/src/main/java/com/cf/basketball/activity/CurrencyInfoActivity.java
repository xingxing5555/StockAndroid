package com.cf.basketball.activity;

import android.text.TextUtils;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.HSKlineModel;
import com.example.admin.basic.model.HSTodayModel;
import com.example.admin.basic.model.currency.CurrencyInfoModel;
import com.example.admin.basic.model.home.CommonStateModel;
import com.example.admin.basic.net.RequestManager;
import com.example.admin.basic.stock.KlineView;
import com.example.admin.basic.stock.MLineView;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 详情界面
 *
 * @author xinxin Shi
 */
public class CurrencyInfoActivity extends BaseCurrencyInfoActivity implements OnRequestListener {


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
    String id = "36";
    private CurrencyInfoModel.DataBean data;

    @Override
    public void init() {
        Calendar now = Calendar.getInstance();
        yearDay = now.get(Calendar.YEAR);
        yearWeek = now.get(Calendar.YEAR);
        yearMonth = now.get(Calendar.YEAR);
        currentChart = MLINE;
        refresh();
        NetManager.getInstance().getCurrencyInfo(id, this);
    }

    /**
     * 添加或删除到自选
     */
    @Override
    public void addOrDel() {
        NetManager.getInstance().addOrDelCurrency(token, id, Constants.EVENT_ADD, this);

    }

    @Override
    public void changeChartView(int currentChart) {
        mLineView.setMarket(curMarket);
//        buySellContainer.setVisibility(View.GONE);
//        kLineTypeView.setVisibility(View.GONE);
        this.currentChart = currentChart;
        refresh();
        switch (currentChart) {
            case MLINE:
                mLineView.clearData();
                mLineView.setVisibility(View.VISIBLE);
                llLine.setVisibility(View.VISIBLE);

                if (hsModel != null) {
                    mLineView.setData(hsModel.parseData());
                }
                break;
            case DAY_KLINE:
                kWeekLineView.clearData();
                kWeekLineView.setVisibility(View.VISIBLE);
//                kLineTypeView.setVisibility(View.VISIBLE);
                if (hsWeekKlineModel != null) {
                    kWeekLineView.setData(hsWeekKlineModel.parseData(), WEEK_KLINE, false,
                            KlineView.K_BOTTOM_TYPE_CJL);
                }
//                kDayLineView.clearData();
//                kDayLineView.setVisibility(View.VISIBLE);
////                kLineTypeView.setVisibility(View.VISIBLE);
//                if (hsDayKlineModel != null) {
//                    kDayLineView.setData(hsDayKlineModel.parseData(), DAY_KLINE, false, KlineView
//                            .K_BOTTOM_TYPE_KDJ);
//                }
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

    @Override
    public void onTitleContentChange(boolean isTime) {
        if (data == null) {
            return;
        }
        if (isTime) {
            rtlText.setTvToolbarContent(data.getTime());
            rtlText.setDownVisible(false);
        } else {
            rtlText.setTvToolbarContent(TextUtils.concat(data.getPrice1(), " ", data.getUpdown()
                    + " " + data.getRate()).toString());
            rtlText.setDownVisible(true);
        }
    }


    private void refresh() {
        RequestManager.init();
        canRefresh = false;
        switch (currentChart) {
            case MLINE:
                NetManager.getInstance().getMLine(id, this);
                getTodayData(curMarket, stockCode, true);
                break;
            case DAY_KLINE:
                NetManager.getInstance().getKLine(id, currentChart - 1, this);
//                refreshDayData(curMarket, stockCode);
                refreshWeekData(curMarket, stockCode);
                break;
            case WEEK_KLINE:
                NetManager.getInstance().getKLine(id, currentChart - 1, this);
                refreshWeekData(curMarket, stockCode);
                break;
            case MONTH_KLINE:
                NetManager.getInstance().getKLine(id, currentChart - 1, this);
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
                    changeChartView(currentChart);

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
        RequestManager.init();
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


    /**
     * @param tag
     * @param json
     */
    @Override
    public void onResponse(String tag, String json) {
        if (TextUtils.equals(tag, Constants.TAG_CURRENCY_INFO)) {
            LogUtils.e("详情页：" + json);
            CurrencyInfoModel model = new Gson().fromJson(json, CurrencyInfoModel.class);
            if (model == null || model.getCode() != Constants.NET_REQUEST_SUCCESS_CODE) {
                return;
            }
            data = model.getData();
            rtlText.setToolbarTitle(data.getBarName());
            rtlText.setTvToolbarContent(data.getTime());
            tvInfoForeignPrice.setText(data.getPrice1());
            tvInfoPrice.setText(TextUtils.concat("(¥", data.getPrice2(), ")"));
            tvInfoRate.setText(TextUtils.concat(data.getUpdown() + "\t\t" + data.getRate()));
            tvInfoTradeTotal.setText(TextUtils.concat("成交量 " + data.getVolumn()));
            homeInfoDataAdapter.setDataList(getInfoData());
            homeInfoDataAdapter.notifyDataSetChanged();
        }

        if (TextUtils.equals(tag, Constants.TAG_ADD_DEL_EVENT)) {
            LogUtils.e("添加详情：" + json);
            CommonStateModel stateModel = new Gson().fromJson(json, CommonStateModel.class);
            if (stateModel == null || stateModel.getCode() != Constants.NET_REQUEST_SUCCESS_CODE) {
                ToastUtils.toastShot(CurrencyInfoActivity.this, getString(R.string.failure));
                return;
            }
            ToastUtils.toastShot(CurrencyInfoActivity.this, getString(R.string.success));
            EventBus.getDefault().post(Constants.EVENT_REFRESH);
        }

        if (TextUtils.equals(tag, Constants.TAG_CURRENCY_MLINE)) {
//TODO 实时图
            LogUtils.e("实时图：" + json);
        }

        if (TextUtils.equals(tag, TextUtils.concat(Constants.TAG_CURRENCY_KLINE, String.valueOf
                (DAY_KLINE - 1)))) {
//       TODO     日K图
            LogUtils.e("K图0：" + json);
        }
        if (TextUtils.equals(tag, TextUtils.concat(Constants.TAG_CURRENCY_KLINE, String.valueOf
                (WEEK_KLINE - 1)))) {
//       TODO     周K图
            LogUtils.e("K图1：" + json);
        }
        if (TextUtils.equals(tag, TextUtils.concat(Constants.TAG_CURRENCY_KLINE, String.valueOf
                (MONTH_KLINE - 1)))) {
//       TODO     月K图
            LogUtils.e("K图2：" + json);
        }
    }

    @Override
    public void onRequestFailure(String errorMsg) {

    }

    public List<String> getInfoData() {
        List<String> list = new ArrayList<>();
        list.add(data.getHigh());
        list.add(data.getLow());
        list.add(data.getOpen());
        list.add(data.getClose());
        list.add(data.getBuy1());
        list.add(data.getSell1());
        return list;
    }

}
