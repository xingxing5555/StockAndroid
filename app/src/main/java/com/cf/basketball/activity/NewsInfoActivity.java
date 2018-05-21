package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cf.basketball.R;
import com.cf.basketball.databinding.ActivityNewInfoBinding;

/**
 * 新闻详情页
 *
 * @author xinxin Shi
 */
public class NewsInfoActivity extends AppCompatActivity {

    private ActivityNewInfoBinding binding;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_info);
//        Glide.with(this).load(url).into(binding.ivNewsPicture);
    }
}
