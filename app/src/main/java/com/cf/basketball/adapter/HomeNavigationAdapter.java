package com.cf.basketball.adapter;

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

import java.util.List;

/**
 * 顶部导航栏数据
 *
 * @author Xinxin Shi
 */

public class HomeNavigationAdapter extends RecyclerView.Adapter<HomeNavigationAdapter.ViewHolder> {
    private Context context;
    private List<String> dataList;
    private int selectedPosition = -1;
    private OnItemClickListener onItemClickListener;
    private View selectedView;

    public HomeNavigationAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    public void setOnItemClickListener(OnItemClickListener
                                               onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout
                .item_home_navigation, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(dataList.get(position));
//        设置选中状态
        if ((selectedPosition == -1 && position == 0) || selectedPosition == position) {
            selectedPosition = position;
            holder.tvNavigationLine.setVisibility(View.VISIBLE);
            selectedView = holder.tvNavigationLine;
        } else {
            holder.tvNavigationLine.setVisibility(View.INVISIBLE);
        }
        holder.rlNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    selectedView.setVisibility(View.INVISIBLE);
                    selectedView = holder.tvNavigationLine;
                    selectedView.setVisibility(View.VISIBLE);
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

        public void setData(String title) {
            tvNavigationTitle.setText(title);
        }
    }
}
