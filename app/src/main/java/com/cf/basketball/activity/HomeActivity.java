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
import com.cf.basketball.fragment.HomeOptionalFragment;
import com.cf.basketball.interfaces.OnItemClickListener;
import com.example.admin.basic.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
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
                MoveToPosition(layoutManager, position);
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void getFragments() {
        for (int i = 0; i < navigationTitleArray.length; i++) {
            fragmentList.add(new HomeOptionalFragment());
        }
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager 设置RecyclerView对应的manager
     * @param n       要跳转的位置
     */
    public static void MoveToPosition(LinearLayoutManager manager, int n) {
        int lastCompletelyVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
        int firstCompletelyVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition();
        if (n >= lastCompletelyVisibleItemPosition) {
            manager.scrollToPositionWithOffset(n, 0);
            manager.setStackFromEnd(true);
        }
        if (n < firstCompletelyVisibleItemPosition) {
            manager.scrollToPositionWithOffset(0, n);
            manager.setStackFromEnd(false);
        }
    }
}
