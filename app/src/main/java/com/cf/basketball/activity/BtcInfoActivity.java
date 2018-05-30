package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.cf.basketball.R;
import com.cf.basketball.databinding.ActivityBtcInfoBinding;
import com.cf.basketball.fragment.btc.BtcBriefFragment;
import com.cf.basketball.fragment.btc.BtcMarketFragment;
import com.cf.basketball.fragment.currency.CurrencyInfoNewsFragment;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnItemClickListener;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.market.MarketInfoModel;
import com.example.admin.basic.utils.LogUtils;
import com.google.gson.Gson;

/**
 * BTC详情页
 *
 * @author xinxin Shi
 */
public class BtcInfoActivity extends BaseActivity implements OnItemClickListener,
        OnRequestListener {

    private ActivityBtcInfoBinding binding;
    private BtcMarketFragment btcChartFragment;
    private BtcBriefFragment btcBriefFragment;
    private CurrencyInfoNewsFragment infoNewsFragment;
    private String id;


    @Override
    public void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_btc_info);
        binding.slBtcNavigation.setText(getString(R.string.currency_market), getString(R.string
                .currency_brief), getString(R.string.currency_news));
        binding.slBtcNavigation.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        id = getIntent().getStringExtra("id");
        LogUtils.e("传入的id=" + id);
        NetManager.getInstance().getMarketInfo(id, this);
        btcChartFragment = new BtcMarketFragment();
        btcBriefFragment = new BtcBriefFragment();
        infoNewsFragment = new CurrencyInfoNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        btcChartFragment.setArguments(bundle);
        btcBriefFragment.setArguments(bundle);
        infoNewsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, btcChartFragment)
                .add(R.id.fl_container, btcBriefFragment).add(R.id.fl_container,
                infoNewsFragment).hide(btcBriefFragment).hide(infoNewsFragment).commit();
    }


    @Override
    public void onItemClickListener(int item) {
        switch (item) {
            case 1:
                getSupportFragmentManager().beginTransaction().hide(btcChartFragment).show
                        (btcBriefFragment).hide(infoNewsFragment).commitAllowingStateLoss();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().hide(btcChartFragment).hide
                        (btcBriefFragment).show(infoNewsFragment).commitAllowingStateLoss();
                break;
            default:
                getSupportFragmentManager().beginTransaction().show(btcChartFragment).hide
                        (btcBriefFragment).hide(infoNewsFragment).commitAllowingStateLoss();
                break;

        }
    }

    @Override
    public void onResponse(String tag, String json) {
        LogUtils.e("市值详情：" + json);
        MarketInfoModel marketInfoModel = new Gson().fromJson(json, MarketInfoModel.class);
        if (marketInfoModel == null || marketInfoModel.getCode() != Constants
                .NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        MarketInfoModel.DataBean data = marketInfoModel.getData();
        binding.rtlBar.setToolbarTitle(data.getBarName());
        binding.rtlBar.setTvToolbarContent(data.getTime());
        binding.tvBtcName.setText(TextUtils.concat(data.getCnName(), "\t", data.getEnName()));
        binding.tvBtcPrice.setText(data.getPrice1());
        binding.tvBtcForeignPrice.setText(data.getPrice2());
        binding.tvVolume.setText(TextUtils.concat("24H量\t", data.getVolumn(), "\n", "24H额\t" +
                data.getAmount()));
    }

    @Override
    public void onRequestFailure(String errorMsg) {

    }
}
