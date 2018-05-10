package com.cf.basketball.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.HomeCurrencyAdapter;
import com.cf.basketball.databinding.FragmentHomeOptionalBinding;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.example.admin.basic.utils.LogUtils;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自选
 *
 * @author Xinxin Shi
 */
public class HomeOptionalFragment extends BaseFragment {

    private FragmentHomeOptionalBinding binding;
    private HomeCurrencyAdapter adapter;
    private List<HomeCurrencyModel> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("====onCreate=====");
        createData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        LogUtils.e("======onCreateView====");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_optional, container,
                false);
        initData();
        return binding.getRoot();
    }

    private void initData() {
        binding.sryContainer.setLayoutManager(createLayoutManager(true));
        binding.sryContainer.addItemDecoration(createItemDecoration(R.color.grey_d));
        adapter = new HomeCurrencyAdapter(R.layout.item_home_currency, list);
        binding.sryContainer.setAdapter(adapter);
        binding.sryContainer.setLongPressDragEnabled(true);
        binding.sryContainer.setOnItemMoveListener(getItemMoveListener());
    }

    /**
     * 拖拽事件监听
     *
     * @return 事件将
     */
    private OnItemMoveListener getItemMoveListener() {
        return new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder
                    targetHolder) {
                if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) {
                    return false;
                }
                int fromPosition = srcHolder.getAdapterPosition();
                int toPosition = targetHolder.getAdapterPosition();
                Collections.swap(list, fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder viewHolder) {
            }
        };
    }


    public void createData() {
        list.add(new HomeCurrencyModel("火币", "BTC/USD", "¥56352.54", "8856.825",
                "交易量27163.070BT", "-4.81%", "0"));
        list.add(new HomeCurrencyModel("火币", "BTC/USD", "¥56352.54", "8856.825",
                "交易量27163.070BT", "-4.81%", "1"));
        list.add(new HomeCurrencyModel("火币", "BTC/USD", "¥56352.54", "8856.825",
                "交易量27163.070BT", "-4.81%", "1"));
    }
}
