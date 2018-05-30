package com.cf.basketball.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cf.basketball.R;
import com.cf.basketball.adapter.currency.CurrencyKLineAdpater;
import com.cf.basketball.adapter.currency.CurrencyLineAdapter;
import com.cf.basketball.adapter.home.HomeCurrencyInfoDataAdapter;
import com.cf.basketball.fragment.currency.CurrencyInfoBriefFragment;
import com.cf.basketball.fragment.currency.CurrencyInfoMarketFragment;
import com.cf.basketball.fragment.currency.CurrencyInfoNewsFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.interfaces.OnItemClickListener;
import com.example.admin.basic.interfaces.OnScrollChangedListener;
import com.example.admin.basic.stock.KlineView;
import com.example.admin.basic.stock.MLineView;
import com.example.admin.basic.stock.TabIndicatorViewV2;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.view.MeasureRecyclerView;
import com.example.admin.basic.view.ObservableScrollView;
import com.example.admin.basic.view.RxToolBar;
import com.example.admin.basic.view.SwitchLayout;

import java.util.Arrays;

public abstract class BaseCurrencyInfoActivity extends BaseActivity implements
        OnScrollChangedListener, OnItemClickListener, TabIndicatorViewV2.OnTabSelectedListener,
        KlineView.GetMoreDataCallback, View.OnClickListener {
    public RxToolBar rtlText;
    public ObservableScrollView svInfoContainer;
    public RecyclerView rvInfoData;
    public SwitchLayout slCurrencyNavigation;
    public RelativeLayout rlShare;
    public TextView tvAddOptional;
    public String currentTime;
    public TextView tvInfoForeignPrice, tvInfoPrice;
    public FragmentManager fragmentManager;
    public CurrencyInfoNewsFragment currencyInfoNewsFragment;
    public CurrencyInfoBriefFragment currencyInfoBriefFragment;
    public CurrencyInfoMarketFragment currencyInfoMarketFragment;
    LinearLayout llLine, llInfoContent;
    MeasureRecyclerView mrvLine;
    TextView tvLineTime;
    ImageView ivClose;
    TabIndicatorViewV2 tabIndicatorView;
    MLineView mLineView;
    KlineView kDayLineView;
    KlineView kWeekLineView;
    KlineView kMonthLineView;
    private boolean isLand;
    public TextView tvInfoRate;
    public TextView tvInfoTradeTotal;
    public HomeCurrencyInfoDataAdapter homeInfoDataAdapter;
    public String id;
    public CurrencyLineAdapter currencyLineAdapter;
    public CurrencyLineAdapter landCurrencyLineAdapter;
    public TextView tvTrendName;
    public MeasureRecyclerView mrvKlineType;
    private CurrencyKLineAdpater kLineAdpater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        id = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_currency_info);
        tvInfoPrice = (TextView) findViewById(R.id.tv_info_price);
        tvInfoForeignPrice = (TextView) findViewById(R.id.tv_info_foregin_price);
        tvInfoRate = (TextView) findViewById(R.id.tv_info_increase);
        tvInfoTradeTotal = (TextView) findViewById(R.id.tv_info_trade_total);
        llInfoContent = (LinearLayout) findViewById(R.id.ll_info_content);
        rvInfoData = (RecyclerView) findViewById(R.id.rv_info_data);
        rtlText = (RxToolBar) findViewById(R.id.rtl_text);
        rlShare = (RelativeLayout) findViewById(R.id.rl_share);
        tvAddOptional = (TextView) findViewById(R.id.tv_add_optional);
        slCurrencyNavigation = (SwitchLayout) findViewById(R.id.sl_currency_navigation);

        if (rtlText != null) {
            rtlText.setTvToolbarContent(currentTime);
            svInfoContainer = (ObservableScrollView) findViewById(R.id.sv_info_container);
            svInfoContainer.setOnScrollChangedListener(this);
            rvInfoData.setLayoutManager(createGridLayoutManager(2));
            homeInfoDataAdapter = new HomeCurrencyInfoDataAdapter(this);
            rvInfoData.setAdapter(homeInfoDataAdapter);
            slCurrencyNavigation.setOnItemClickListener(this);
            fragmentManager = getSupportFragmentManager();
            currencyInfoNewsFragment = new CurrencyInfoNewsFragment();
            currencyInfoBriefFragment = new CurrencyInfoBriefFragment();
            currencyInfoMarketFragment = new CurrencyInfoMarketFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            currencyInfoMarketFragment.setArguments(bundle);
            currencyInfoBriefFragment.setArguments(bundle);
            currencyInfoNewsFragment.setArguments(bundle);
            fragmentManager.beginTransaction().add(R.id.fl_currency_container,
                    currencyInfoNewsFragment).add(R.id.fl_currency_container,
                    currencyInfoBriefFragment).add(R.id.fl_currency_container,
                    currencyInfoMarketFragment).show(currencyInfoNewsFragment).hide
                    (currencyInfoBriefFragment).hide(currencyInfoMarketFragment).commit();
            rlShare.setOnClickListener(this);
            tvAddOptional.setOnClickListener(this);
        }
        tabIndicatorView = (TabIndicatorViewV2) this.findViewById(R.id.tabIndicatorView);
        mrvKlineType = (MeasureRecyclerView) findViewById(R.id.mrv_kline_type);
        mLineView = (MLineView) this.findViewById(R.id.mLineView);
        mLineView.setMarket(HS_MARKET);
        kDayLineView = (KlineView) this.findViewById(R.id.kDayLineView);
        kWeekLineView = (KlineView) this.findViewById(R.id.kWeekLineView);
        kMonthLineView = (KlineView) this.findViewById(R.id.kMonthLineView);
        llLine = (LinearLayout) this.findViewById(R.id.ll_line);
        mrvLine = (MeasureRecyclerView) this.findViewById(R.id.mrv_line);
        tvLineTime = (TextView) this.findViewById(R.id.tv_line_time);
        tvTrendName = (TextView) this.findViewById(R.id.tv_trend_name);
        ivClose = (ImageView) findViewById(R.id.iv_close);
        if (ivClose != null) {
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            });
            final String[] array = getResources().getStringArray(R.array.item_currency_info_kline);
            LogUtils.e("array=" + array.toString());
            LinearLayoutManager layout = new LinearLayoutManager(this);
            mrvKlineType.setLayoutManager(layout);
            layout.setStackFromEnd(true);
            kLineAdpater = new CurrencyKLineAdpater(R.layout.item_currency_kline_type, Arrays
                    .asList(array));
            mrvKlineType.setAdapter(kLineAdpater);
            kLineAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String type = array[position];
                    onClickKLineType(type);
                }
            });
        }
        String[] title = getResources().getStringArray(R.array.currency_trend);
        tabIndicatorView.setTitles(Arrays.asList(title));
        tabIndicatorView.setOnTabSelectedListener(this);
        mLineView.setOnClickListener(listener);
        kDayLineView.setOnClickListener(listener);
        kWeekLineView.setOnClickListener(listener);
        kMonthLineView.setOnClickListener(listener);

        mrvLine.setLayoutManager(createGridLayoutManager(3));
        LogUtils.e("isLand==" + isLand);
        if (isLand) {
            currencyLineAdapter = new CurrencyLineAdapter(this, R.layout
                    .item_currency_kline_land, getData());
        } else {
            currencyLineAdapter = new CurrencyLineAdapter(this, R.layout.item_currency_kline,
                    getData());
        }
        mrvLine.setAdapter(currencyLineAdapter);
    }

    @Override
    public void initData() {
        init();
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    };

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
            LogUtils.e("横屏");
            isLand = true;
        } else {
            LogUtils.e("竖屏");
            isLand = false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_optional:
                addOrDel();

                break;
            case R.id.rl_share:
                //TODO 用于分享的本页图片
                Bitmap bitmap = shotScrollView(svInfoContainer);
                break;
            default:
                break;
        }
    }

    @Override
    public void onScrollChanged(int top, int oldTop) {
        if (top >= llInfoContent.getBottom()) {
            onTitleContentChange(false);
        } else {
            onTitleContentChange(true);
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
        if (mrvKlineType != null) {
        mrvKlineType.setVisibility(View.GONE);
        }
        changeChartView(position);
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void getMoreData(int klineType) {

    }

    public abstract void changeChartView(int currentChart);

    public abstract void onTitleContentChange(boolean isTime);

    public abstract void init();

    public abstract void addOrDel();

    public abstract void onClickKLineType(String type);
}
