package com.cf.basketball.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.HomeCurrencyAdapter;
import com.cf.basketball.databinding.FragmentHomeOptionalBinding;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.model.HomeCurrencyModel;
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
    //    private HomeCurrencyAdapter adapter;
    private HomeCurrencyAdapter adapter;
    private List<HomeCurrencyModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_optional, container,
                false);
        initData();
        return binding.getRoot();
    }

    private void initData() {
        createData();
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
                // 不同的ViewType不能拖拽换位置。
                if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) return false;

                // 真实的Position：通过ViewHolder拿到的position都需要减掉HeadView的数量。
                int fromPosition = srcHolder.getAdapterPosition();
                int toPosition = targetHolder.getAdapterPosition();

                Collections.swap(list, fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;// 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置。
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
