package com.cf.basketball.fragment.home;


import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cf.basketball.R;
import com.cf.basketball.activity.CurrencyInfoActivity;
import com.cf.basketball.activity.SearchActivity;
import com.cf.basketball.adapter.home.HomeOptionalAdapter2;
import com.cf.basketball.databinding.FragmentHomeOptionalBinding;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.HomeCurrencyModel;
import com.example.admin.basic.utils.LogUtils;
import com.example.admin.basic.view.SortLayout;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 自选
 *
 * @author Xinxin Shi
 */
public class HomeOptionalFragment extends BaseFragment implements SortLayout
        .OnSortChangeListener, OnRequestListener, OnRefreshListener {

    private FragmentHomeOptionalBinding binding;
    private HomeOptionalAdapter2 adapter;
    private List<HomeCurrencyModel> list;
    private int pageNum = 1;
    private int order = 0;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String token = "0";
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        NetManager.getInstance().getMineList(token, pageNum, order, this);
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
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(binding.sryContainer);
        adapter = new HomeOptionalAdapter2(getContext(),token);
        adapter.setDataList(list);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        adapter.setmLRecyclerViewAdapter(mLRecyclerViewAdapter);
        binding.sryContainer.setAdapter(mLRecyclerViewAdapter);
        binding.sryContainer.setOnRefreshListener(this);
        binding.sryContainer.setLoadMoreEnabled(false);
        binding.slSort.setOnSortChangeListener(this);

        view = LayoutInflater.from(getActivity()).inflate(R.layout.foot_add_currency, null, false);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.alignWithParent = true;
        view.setLayoutParams(rlp);
        view.findViewById(R.id.btn_add_currency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchActivity.class);
            }
        });
        mLRecyclerViewAdapter.addFooterView(view);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                startActivity(CurrencyInfoActivity.class);
            }
        });
        binding.sryContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    binding.sryContainer.setPullRefreshEnabled(true);
                }
                return false;
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addEvent(HomeCurrencyModel messageEvent) {
        LogUtils.e("Event is execute ");
        list.add(messageEvent);
        adapter.setDataList(list);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onSortChangeListener(int order) {
        LogUtils.e("type=" + order);
        this.order = order;
        NetManager.getInstance().getMineList(token, pageNum, order, this);
    }


    @Override
    public void onResponse(String tag,String json) {
        binding.sryContainer.refreshComplete(pageNum);
        LogUtils.e("自选 json=" + json);
    }

    @Override
    public void onRequestFailure(String errorMsg) {
        binding.sryContainer.refreshComplete(pageNum);
        LogUtils.e(errorMsg);
    }



    private ItemTouchHelper.Callback mCallback = new ItemTouchHelper.Callback() {

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0, swipeFlags = 0;
            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
            } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                //设置侧滑方向为从左到右和从右到左都可以
                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source,
                              RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) {
                return false;
            } else {
                adapter.onItemDragMoving(source, target);
                return true;//返回true表示执行拖动
            }

        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder
                viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            binding.sryContainer.setPullRefreshEnabled(false);
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //滑动时改变Item的透明度
                final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            }
        }
    };


    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        pageNum++;
        NetManager.getInstance().getMineList(token, pageNum, order, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
