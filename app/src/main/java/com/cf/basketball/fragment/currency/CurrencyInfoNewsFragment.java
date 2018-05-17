package com.cf.basketball.fragment.currency;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.currency.CurrencyInfoNewsAdapter;
import com.cf.basketball.model.CurrencyInfoNewsModel;
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
public class CurrencyInfoNewsFragment extends BaseFragment {

    private List<CurrencyInfoNewsModel> list = new ArrayList<>();
    private MeasureRecyclerView mRecyclerView;

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
        mRecyclerView.addItemDecoration(createItemDecoration(R.color.grey_d));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(new CurrencyInfoNewsAdapter(getContext(), R.layout
                .item_currency_info_news, list));
    }


    public void getData() {
        list.add(new CurrencyInfoNewsModel("的简繁垃圾房拉斯嘎嘎叫房间爱是否拉风", "2小时前", "https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1526042675790&di" +
                "=5bc14bb61eb2e48cdb2a360672255e9f&imgtype=0&src=http%3A%2F%2Fimg5.duitang" + ""
                + ".com%2Fuploads%2Fitem%2F201509%2F25%2F20150925110719_HtTCj.jpeg"));
        list.add(new CurrencyInfoNewsModel("的简繁垃圾房拉斯嘎嘎叫房间爱是否拉风", "2小时前", "https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1526042675790&di" +
                "=5bc14bb61eb2e48cdb2a360672255e9f&imgtype=0&src=http%3A%2F%2Fimg5.duitang" + ""
                + ".com%2Fuploads%2Fitem%2F201509%2F25%2F20150925110719_HtTCj.jpeg"));
        list.add(new CurrencyInfoNewsModel("的简繁垃圾房拉斯嘎嘎叫房间爱是否拉风", "2小时前", "https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1526042675790&di" +
                "=5bc14bb61eb2e48cdb2a360672255e9f&imgtype=0&src=http%3A%2F%2Fimg5.duitang" + ""
                + ".com%2Fuploads%2Fitem%2F201509%2F25%2F20150925110719_HtTCj.jpeg"));
        list.add(new CurrencyInfoNewsModel("的简繁垃圾房拉斯嘎嘎叫房间爱是否拉风", "2小时前", "https://timgsa.baidu" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1526042675790&di" +
                "=5bc14bb61eb2e48cdb2a360672255e9f&imgtype=0&src=http%3A%2F%2Fimg5.duitang" + ""
                + ".com%2Fuploads%2Fitem%2F201509%2F25%2F20150925110719_HtTCj.jpeg"));
    }

}
