package com.cf.basketball.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class HomeNavigationAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private String selectedItem;
    private com.cf.basketball.interfaces.OnItemClickListener onItemClickListener;
    private TextView selectedView;

    public HomeNavigationAdapter(int layoutResId, @Nullable List<String> data, String
            selectedItem) {
        super(layoutResId, data);
        this.selectedItem = selectedItem;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        helper.setText(R.id.tv_navigation_title, item);
//        helper.setText(R.id.tv_navigation_line, item);
        if (TextUtils.equals(selectedItem, item)) {
            selectedView =(TextView) helper.itemView.findViewById(R.id.tv_navigation_line);
            selectedView.setVisibility(View.VISIBLE);
        } else {
            helper.itemView.findViewById(R.id.tv_navigation_line).setVisibility(View.INVISIBLE);
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    selectedItem = item;
                    selectedView.setVisibility(View.INVISIBLE);
                    selectedView=(TextView) helper.itemView.findViewById(R.id.tv_navigation_line);
                    selectedView.setVisibility(View
                            .VISIBLE);
                    onItemClickListener.onItemClickListener(item);
                }
            }
        });
    }

    public void setOnItemClickListener(com.cf.basketball.interfaces.OnItemClickListener
                                               onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
