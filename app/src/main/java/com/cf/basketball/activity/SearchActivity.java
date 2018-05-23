package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.adapter.search.SearchResultAdapter;
import com.cf.basketball.databinding.ActivitySearchBinding;
import com.cf.basketball.fragment.DefaultSearchFragment;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 搜索界面
 *
 * @author xinxin Shi
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher,
        SearchResultAdapter.OnSearchListener {

    private ActivitySearchBinding binding;
    private SearchResultAdapter adapter;

    @Override
    public void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.tvCancel.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_search_container, new
                DefaultSearchFragment()).commit();
        binding.rvSearchResult.setLayoutManager(createLinearLayoutManager());
        binding.rvSearchResult.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        binding.etSearch.addTextChangedListener(this);
    }

    @Override
    public void initData() {

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
        adapter = new SearchResultAdapter(this, createData());
        binding.rvSearchResult.setAdapter(adapter);
        adapter.setOnSearchListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onItemClickListener(int position) {
        startActivity(CurrencyInfoActivity.class);
        finish();
    }

    @Override
    public void onAddOrSubChangeListener(int position, int state) {
        LogUtils.e("position=" + position + ";state=" + state);
        EventBus.getDefault().post(createData().get(position));
    }
}
