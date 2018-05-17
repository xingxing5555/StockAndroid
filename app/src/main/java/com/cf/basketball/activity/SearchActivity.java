package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.adapter.search.SearchResultAdapter;
import com.cf.basketball.databinding.ActivitySearchBinding;
import com.cf.basketball.fragment.DefaultSearchFragment;
import com.example.admin.basic.base.BaseActivity;

/**
 * 搜索界面
 *
 * @author xinxin Shi
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.tvCancel.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_search_container, new
                DefaultSearchFragment()).commit();
        binding.rvSearchResult.setLayoutManager(createLinearLayoutManager());
        binding.rvSearchResult.addItemDecoration(createItemDecoration(R.color.grey_d));
        binding.etSearch.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (binding.etSearch.getText().toString().trim().isEmpty()) {
            binding.rvSearchResult.setVisibility(View.GONE);
            return;
        }
        binding.rvSearchResult.setVisibility(View.VISIBLE);
        binding.rvSearchResult.setAdapter(new SearchResultAdapter(R.layout.item_search_result,
                createData()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
