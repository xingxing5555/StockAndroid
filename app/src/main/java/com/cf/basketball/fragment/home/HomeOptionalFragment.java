package com.cf.basketball.fragment.home;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.activity.SearchActivity;
import com.cf.basketball.adapter.home.HomeOptionalAdapter;
import com.cf.basketball.databinding.FragmentHomeOptionalBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.view.SortLayout;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

/**
 * 自选
 *
 * @author Xinxin Shi
 */
public class HomeOptionalFragment extends BaseFragment implements View.OnClickListener,
        SortLayout.OnSortChangeListener {

    private FragmentHomeOptionalBinding binding;
    private HomeOptionalAdapter adapter;
    private List<HomeCurrencyModel> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        list = createData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_optional, container,
                false);
        initView();
        return binding.getRoot();
    }


    private void initView() {
        binding.getRoot().findViewById(R.id.ll_sort_prompt).setVisibility(View.VISIBLE);
        binding.sryContainer.setLayoutManager(createLayoutManager(true));
        binding.sryContainer.addItemDecoration(createItemDecoration(R.color.grey_d));
        adapter = new HomeOptionalAdapter(R.layout.item_home_optional, list);
        binding.sryContainer.setAdapter(adapter);
        binding.sryContainer.setLongPressDragEnabled(true);
        binding.sryContainer.setOnItemMoveListener(getItemMoveListener());
        binding.btnAddCurrency.setOnClickListener(this);
        binding.slSort.setOnSortChangeListener(this);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("position==" + position);
                startActivity(CurrencyInfoActivity.class);
            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(HomeCurrencyModel messageEvent) {
        LogUtils.e("Event is execute ");
        list.add(messageEvent);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_currency:
                startActivity(SearchActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSortChangeListener(int type, int changeState) {
        LogUtils.e("type=" + type + ";changeState=" + changeState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
