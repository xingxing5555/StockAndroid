package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.adapter.home.HomeTabAdapter;
import com.cf.basketball.adapter.home.HomeViewPagerAdapter;
import com.cf.basketball.databinding.ActivityHomeBinding;
import com.cf.basketball.fragment.home.HomeType3Fragment;
import com.cf.basketball.fragment.home.HomeType4Fragment;
import com.cf.basketball.fragment.home.HomeMarketFragment;
import com.cf.basketball.fragment.home.HomeOptionalFragment;
import com.cf.basketball.fragment.home.HomeUpDownFragment;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnItemClickListener;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.home.HomeTabModel;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 * @author Xinxin Shi
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, OnRequestListener {

    private ActivityHomeBinding binding;
    private HomeTabAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private long exitTime;

    @Override
    public void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        //     顶部导航栏配置
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvTopTitle.setLayoutManager(layoutManager);
        adapter = new HomeTabAdapter(this);
        binding.rvTopTitle.setAdapter(adapter);
        binding.ivSearch.setOnClickListener(this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int item) {
                binding.vpHomeContainer.setCurrentItem(item);
            }
        });

        binding.vpHomeContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                moveToPosition(layoutManager, position);
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        NetManager.getInstance().getHomeTab(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                startActivity(SearchActivity.class);
                break;
            default:
                break;
        }
    }

    //重写 onKeyDown方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.toastShot(this, "再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResponse(String tag, String json) {
        LogUtils.e("标签json=" + json);
        HomeTabModel model = new Gson().fromJson(json, HomeTabModel.class);
        if (model == null || model.getCode() != Constants.NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        List<HomeTabModel.DataBean.TabsBean> tabs = model.getData().getTabs();
        adapter.setDataList(tabs);
        adapter.notifyDataSetChanged();
        getFragments(tabs);
    }

    @Override
    public void onRequestFailure(String errorMsg) {
        LogUtils.e(errorMsg);
    }

    private void getFragments(List<HomeTabModel.DataBean.TabsBean> list) {
        fragmentList.clear();
        for (HomeTabModel.DataBean.TabsBean bean : list) {
            switch (bean.getType()) {
                case 0:
                    fragmentList.add(new HomeOptionalFragment());
                    break;
                case 1:
                    fragmentList.add(new HomeMarketFragment());
                    break;
                case 2:
                    fragmentList.add(new HomeUpDownFragment());
                    break;
                case 3:
                    LogUtils.e("首页btc=" + bean.getId());
                    HomeType3Fragment fragment = new HomeType3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(bean.getId()));
                    fragment.setArguments(bundle);
                    fragmentList.add(fragment);
                    break;
                case 4:
                    LogUtils.e("首页火币=" + bean.getId());
                    HomeType4Fragment huobiFragment = new HomeType4Fragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("id", String.valueOf(bean.getId()));
                    huobiFragment.setArguments(bundle1);
                    fragmentList.add(huobiFragment);
                    break;
                default:
                    break;
            }
        }
        binding.vpHomeContainer.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(),
                fragmentList));
    }
}