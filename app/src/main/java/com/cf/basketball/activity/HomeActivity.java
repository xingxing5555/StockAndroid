package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.cf.basketball.adapter.HomeNavigationAdapter;
import com.cf.basketball.R;
import com.cf.basketball.databinding.ActivityHomeBinding;
import com.cf.basketball.fragment.HomeOptionalFragment;
import com.cf.basketball.interfaces.OnItemClickListener;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.utils.LogUtils;

import java.util.Arrays;

/**
 * @author Xinxin Shi
 */
public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding binding;
    private HomeNavigationAdapter adapter;
    private String[] navigationTitleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initData();
    }

    private void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home_container, new
                HomeOptionalFragment()).commit();
        binding.rvTopTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .HORIZONTAL, false));
        navigationTitleArray = getResources().getStringArray(R.array.home_navigation_title);
        adapter = new HomeNavigationAdapter(R.layout.item_home_navigation, Arrays.asList
                (navigationTitleArray), navigationTitleArray[0]);
        binding.rvTopTitle.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(String item) {
                LogUtils.e("点击事件为：" + "名称为：" + item);

            }
        });
    }


}
