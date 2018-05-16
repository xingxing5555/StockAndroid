package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.adapter.HomeCurrencyInfoDataAdapter;
import com.cf.basketball.databinding.ActivityCurrencyInfoBinding;
import com.cf.basketball.fragment.CurrencyInfoBriefFragment;
import com.cf.basketball.fragment.CurrencyInfoMarketFragment;
import com.cf.basketball.fragment.CurrencyInfoNewsFragment;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.interfaces.OnItemClickListener;
import com.example.admin.basic.interfaces.OnScrollChangedListener;
import com.example.admin.basic.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 详情界面
 *
 * @author xinxin Shi
 */
public class CurrencyInfoActivity extends BaseActivity implements View.OnClickListener,
        OnScrollChangedListener, OnItemClickListener {

    private ActivityCurrencyInfoBinding binding;
    private String currentTime;
    private FragmentManager fragmentManager;
    private CurrencyInfoNewsFragment currencyInfoNewsFragment;
    private CurrencyInfoBriefFragment currencyInfoBriefFragment;
    private CurrencyInfoMarketFragment currencyInfoMarketFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency_info);
        initView();
        initData();
    }


    private void initView() {
        currentTime = DateUtils.getCurrentTime();
        binding.tvInfoTime.setText(currentTime);
        binding.ivBack.setOnClickListener(this);
        binding.svInfoContainer.setOnScrollChangedListener(this);
        binding.rvInfoData.setLayoutManager(createGridLayoutManager(2));
        binding.rvInfoData.setAdapter(new HomeCurrencyInfoDataAdapter(this, createData()));
        binding.slCurrencyNavigation.setOnItemClickListener(this);
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        currencyInfoNewsFragment = new CurrencyInfoNewsFragment();
        currencyInfoBriefFragment = new CurrencyInfoBriefFragment();
        currencyInfoMarketFragment = new CurrencyInfoMarketFragment();
        fragmentManager.beginTransaction().add(R.id.fl_currency_container,
                currencyInfoNewsFragment).add(R.id.fl_currency_container,
                currencyInfoBriefFragment).add(R.id.fl_currency_container,
                currencyInfoMarketFragment).show(currencyInfoNewsFragment).hide
                (currencyInfoBriefFragment).hide(currencyInfoMarketFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
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
            binding.tvInfoTime.setText("交易量什么的啊");
            binding.ivDown.setVisibility(View.VISIBLE);
        } else {
            binding.tvInfoTime.setText(currentTime);
            binding.ivDown.setVisibility(View.GONE);
        }
    }

    public List<String> createData() {
        List<String> list = new ArrayList<>();
        list.add("8908.31");
        list.add("8908.31");
        list.add("8908.31");
        list.add("8908.31");
        list.add("8908.3");
        list.add("8908.31");
        return list;
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
}
