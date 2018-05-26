package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.adapter.home.HomeNavigationAdapter;
import com.cf.basketball.adapter.home.HomeViewPagerAdapter;
import com.cf.basketball.databinding.ActivityHomeBinding;
import com.cf.basketball.fragment.home.HomeBtcFragment;
import com.cf.basketball.fragment.home.HomeHuobiFragment;
import com.cf.basketball.fragment.home.HomeMarketFragment;
import com.cf.basketball.fragment.home.HomeOptionalFragment;
import com.cf.basketball.fragment.home.HomeUpDownFragment;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.interfaces.OnItemClickListener;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 首页
 *
 * @author Xinxin Shi
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, OnRequestListener {

    private ActivityHomeBinding binding;
    private HomeNavigationAdapter adapter;
    private String[] navigationTitleArray;
    private LinearLayoutManager layoutManager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private long exitTime;

    @Override
    public void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        //     顶部导航栏配置
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvTopTitle.setLayoutManager(layoutManager);
        navigationTitleArray = getResources().getStringArray(R.array.home_navigation_title);
        adapter = new HomeNavigationAdapter(this, Arrays.asList(navigationTitleArray));
        binding.rvTopTitle.setAdapter(adapter);
        binding.ivSearch.setOnClickListener(this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int item) {
                binding.vpHomeContainer.setCurrentItem(item);
            }
        });
//        底部滑动ViewPager配置
        getFragments();
        binding.vpHomeContainer.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(),
                fragmentList));
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


    private void getFragments() {
        fragmentList.add(new HomeOptionalFragment());
        fragmentList.add(new HomeMarketFragment());
        fragmentList.add(new HomeUpDownFragment());
        fragmentList.add(new HomeBtcFragment());
        fragmentList.add(new HomeBtcFragment());
        fragmentList.add(new HomeBtcFragment());
        fragmentList.add(new HomeHuobiFragment());
        fragmentList.add(new HomeHuobiFragment());
        fragmentList.add(new HomeHuobiFragment());
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
    public void onResponse(String json) {
        LogUtils.e("标签json=" + json);
    }

    @Override
    public void onRequestFailure(String errorMsg) {
        LogUtils.e(errorMsg);
    }
}
