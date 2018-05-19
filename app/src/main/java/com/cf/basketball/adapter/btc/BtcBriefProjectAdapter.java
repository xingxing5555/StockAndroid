package com.cf.basketball.adapter.btc;

import android.content.Context;
import android.support.annotation.Nullable;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class BtcBriefProjectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private String[] btfBriefProjectTitle;
    private Context context;

    public BtcBriefProjectAdapter(Context context, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.context = context;
        btfBriefProjectTitle = context.getResources().getStringArray(R.array
                .btc_info_project_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_project_title, btfBriefProjectTitle[helper.getAdapterPosition()]);
        helper.setText(R.id.tv_project_content, item);
        if (helper.getAdapterPosition() == 6) {
            helper.setTextColor(R.id.tv_project_content, context.getResources().getColor(R.color
                    .blue_link));
        }
    }
}
