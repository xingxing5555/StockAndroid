package com.cf.basketball.fragment.currency;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.activity.NewsInfoActivity;
import com.cf.basketball.adapter.currency.CurrencyInfoNewsAdapter;
import com.example.admin.basic.model.CurrencyInfoNewsModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.basic.application.BaseApplication;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.view.FullyLinearLayoutManager;
import com.example.admin.basic.view.MeasureRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 货币详情-新闻
 *
 * @author xinxin Shi
 */
public class CurrencyInfoNewsFragment extends BaseFragment implements BaseQuickAdapter
        .OnItemClickListener {

    private List<CurrencyInfoNewsModel> list = new ArrayList<>();
    private MeasureRecyclerView mRecyclerView;
    private CurrencyInfoNewsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout
                .common_fragment_mesure_list, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        mRecyclerView = (MeasureRecyclerView) view.findViewById(R.id.mrv_list);
        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(BaseApplication.getInstance()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mRecyclerView.setNestedScrollingEnabled(false);
        adapter = new CurrencyInfoNewsAdapter(getContext(), R.layout.item_currency_info_news, list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }


    public void getData() {
        list.add(new CurrencyInfoNewsModel("“BTC不再是比特币，BCH才是比特币”——Bitcoin.co...", "2小时前", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=885950579,4133410334&fm=200&gp=0.jpg"));
        list.add(new CurrencyInfoNewsModel("“BTC不再是比特币，BCH才是比特币”——Bitcoin.co...", "2小时前", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=885950579,4133410334&fm=200&gp=0.jpg"));
        list.add(new CurrencyInfoNewsModel("“BTC不再是比特币，BCH才是比特币”——Bitcoin.co...", "2小时前", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=885950579,4133410334&fm=200&gp=0.jpg"));
        list.add(new CurrencyInfoNewsModel("“BTC不再是比特币，BCH才是比特币”——Bitcoin.co...", "2小时前", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=885950579,4133410334&fm=200&gp=0.jpg"));
        list.add(new CurrencyInfoNewsModel("“BTC不再是比特币，BCH才是比特币”——Bitcoin.co...", "2小时前", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=885950579,4133410334&fm=200&gp=0.jpg"));
        list.add(new CurrencyInfoNewsModel("“BTC不再是比特币，BCH才是比特币”——Bitcoin.co...", "2小时前", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=885950579,4133410334&fm=200&gp=0.jpg"));
        list.add(new CurrencyInfoNewsModel("“BTC不再是比特币，BCH才是比特币”——Bitcoin.co...", "2小时前", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=885950579,4133410334&fm=200&gp=0.jpg"));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(NewsInfoActivity.class);
    }
}
