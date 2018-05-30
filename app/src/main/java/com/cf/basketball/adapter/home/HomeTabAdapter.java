package com.cf.basketball.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cf.basketball.R;
import com.example.admin.basic.application.BaseApplication;
import com.example.admin.basic.interfaces.OnItemClickListener;
import com.example.admin.basic.model.home.HomeTabModel;

import java.util.List;

/**
 * 顶部导航栏数据
 *
 * @author Xinxin Shi
 */

public class HomeTabAdapter extends RecyclerView.Adapter<HomeTabAdapter.ViewHolder> {
    private Context context;
    private List<HomeTabModel.DataBean.TabsBean> dataList;
    private int selectedPosition = -1;
    private OnItemClickListener onItemClickListener;
    private View selectedView;
    private TextView selectedTextView;

    public HomeTabAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<HomeTabModel.DataBean.TabsBean> dataList) {
        this.dataList = dataList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout
                .item_home_navigation, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        HomeTabModel.DataBean.TabsBean tabsBean = dataList.get(position);
        holder.setData(tabsBean.getName());
//        设置选中状态
        if ((selectedPosition == -1 && position == 0) || selectedPosition == position) {
            selectedPosition = position;
            holder.tvNavigationLine.setVisibility(View.VISIBLE);
            selectedTextView = holder.tvNavigationTitle;
            selectedView = holder.tvNavigationLine;
            selectedTextView.setSelected(true);
        } else {
            holder.tvNavigationTitle.setSelected(false);
            holder.tvNavigationLine.setVisibility(View.INVISIBLE);
        }
        holder.rlNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    selectedView.setVisibility(View.INVISIBLE);
                    selectedTextView.setSelected(false);
                    selectedView = holder.tvNavigationLine;
                    selectedTextView = holder.tvNavigationTitle;
                    selectedView.setVisibility(View.VISIBLE);
                    selectedTextView.setSelected(true);
                    onItemClickListener.onItemClickListener(position);
                }
            }
        });
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNavigationTitle;
        private View tvNavigationLine;
        private RelativeLayout rlNavigation;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNavigationTitle = (TextView) itemView.findViewById(R.id.tv_navigation_title);
            tvNavigationLine = itemView.findViewById(R.id.tv_navigation_line);
            rlNavigation = (RelativeLayout) itemView.findViewById(R.id.rl_navigation);
        }

        public void setData(String tabName) {
            tvNavigationTitle.setText(tabName);
        }
    }
}
