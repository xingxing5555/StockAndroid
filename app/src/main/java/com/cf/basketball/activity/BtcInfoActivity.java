package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;

import com.cf.basketball.R;
import com.cf.basketball.databinding.ActivityBtcInfoBinding;
import com.cf.basketball.fragment.btc.BtcBriefFragment;
import com.cf.basketball.fragment.btc.BtcMarketFragment;
import com.cf.basketball.fragment.currency.CurrencyInfoNewsFragment;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.interfaces.OnItemClickListener;

/**
 * BTC详情页
 *
 * @author xinxin Shi
 */
public class BtcInfoActivity extends BaseActivity implements OnItemClickListener {

    private ActivityBtcInfoBinding binding;
    private BtcMarketFragment btcChartFragment;
    private BtcBriefFragment btcBriefFragment;
    private CurrencyInfoNewsFragment infoNewsFragment;


    @Override
    public void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_btc_info);
        binding.slBtcNavigation.setText(getString(R.string.currency_market), getString(R.string
                .currency_brief), getString(R.string.currency_news));
        binding.slBtcNavigation.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        btcChartFragment = new BtcMarketFragment();
        btcBriefFragment = new BtcBriefFragment();
        infoNewsFragment = new CurrencyInfoNewsFragment();
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
}
