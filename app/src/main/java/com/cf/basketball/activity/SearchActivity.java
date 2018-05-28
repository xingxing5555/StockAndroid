package com.cf.basketball.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.cf.basketball.R;
import com.cf.basketball.adapter.search.SearchResultAdapter;
import com.cf.basketball.databinding.ActivitySearchBinding;
import com.cf.basketball.fragment.DefaultSearchFragment;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseActivity;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.home.CommonStateModel;
import com.example.admin.basic.model.search.SearchModel;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 搜索界面
 *
 * @author xinxin Shi
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher,
        SearchResultAdapter.OnSearchListener, OnRequestListener {

    private ActivitySearchBinding binding;
    private SearchResultAdapter adapter;
    private List<SearchModel.DataBean.CoinsBean> coins;

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
        adapter = new SearchResultAdapter(this);
        binding.rvSearchResult.setAdapter(adapter);

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

        String key = binding.etSearch.getText().toString().trim();
        if (key.isEmpty()) {
            binding.rvSearchResult.setVisibility(View.GONE);
            return;
        }
        NetManager.getInstance().getSearchKeyData(key, token, this);
        adapter.setOnSearchListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onItemClickListener(int position) {
        LogUtils.e("position="+position+";id="+coins.get(position).getId());
        startActivity(coins.get(position).getId(),CurrencyInfoActivity.class);
        finish();
    }

    @Override
    public void onAddOrSubChangeListener(int position, String event) {
        LogUtils.e("position=" + position + ";state=" + event);
        if (coins == null || coins.isEmpty()) {
            return;
        }
        String id = coins.get(position).getId();
        NetManager .getInstance().addOrDelCurrency(token, id, event, this);
        EventBus.getDefault().post(createData().get(position));
    }

    @Override
    public void onResponse(String tag, String json) {
        if (TextUtils.equals(tag, Constants.TAG_ADD_DEL_EVENT)) {
            LogUtils.e("添加事件：" + json);
            CommonStateModel model = new Gson().fromJson(json, CommonStateModel.class);
            if (model == null || model.getCode() == Constants.NET_REQUEST_SUCCESS_CODE) {
                ToastUtils.toastShot(this, getString(R.string.success));
                return;
            }
            ToastUtils.toastShot(this, getString(R.string.failure));
        }

        if (TextUtils.equals(tag, Constants.TAG_SEARCH)) {
            LogUtils.e("搜索：" + json);
            SearchModel searchModel = new Gson().fromJson(json, SearchModel.class);
            if (searchModel == null || searchModel.getCode() != Constants
                    .NET_REQUEST_SUCCESS_CODE) {
                return;
            }
            binding.rvSearchResult.setVisibility(View.VISIBLE);
            coins = searchModel.getData().getCoins();
//            list.clear();
//            list.addAll(coins);
            adapter.setList(coins);
            adapter.setOnSearchListener(this);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestFailure(String errorMsg) {

    }
}
