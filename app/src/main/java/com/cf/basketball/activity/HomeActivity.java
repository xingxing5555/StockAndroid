package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;

import com.cf.basketball.R;
import com.cf.basketball.adapter.HomeNavigationAdapter;
import com.cf.basketball.adapter.HomeViewPagerAdapter;
import com.cf.basketball.databinding.ActivityHomeBinding;
import com.cf.basketball.fragment.HomeBTCFragment;
import com.cf.basketball.fragment.HomeHuobiFragment;
import com.cf.basketball.fragment.HomeIncreaseFragment;
import com.cf.basketball.fragment.HomeMarketFragment;
import com.cf.basketball.fragment.HomeOptionalFragment;
import com.cf.basketball.interfaces.OnItemClickListener;
import com.example.admin.basic.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 首页
 *
 * @author Xinxin Shi
 */
public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding binding;
    private HomeNavigationAdapter adapter;
    private String[] navigationTitleArray;
    private LinearLayoutManager layoutManager;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initData();
    }

    private void initData() {
//     顶部导航栏配置
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvTopTitle.setLayoutManager(layoutManager);
        navigationTitleArray = getResources().getStringArray(R.array.home_navigation_title);
        adapter = new HomeNavigationAdapter(this, Arrays.asList(navigationTitleArray));
        binding.rvTopTitle.setAdapter(adapter);
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


    private void getFragments() {
        fragmentList.add(new HomeOptionalFragment());
        fragmentList.add(new HomeMarketFragment());
        fragmentList.add(new HomeIncreaseFragment());
        fragmentList.add(new HomeBTCFragment());
        fragmentList.add(new HomeOptionalFragment());
        fragmentList.add(new HomeOptionalFragment());
        fragmentList.add(new HomeHuobiFragment());
        fragmentList.add(new HomeOptionalFragment());
        fragmentList.add(new HomeOptionalFragment());
    }


}
