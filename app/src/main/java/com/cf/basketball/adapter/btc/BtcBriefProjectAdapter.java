package com.cf.basketball.adapter.btc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author Xinxin Shi
 */

public class BtcBriefProjectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private String[] btfBriefProjectTitle;
    private Context context;

    public BtcBriefProjectAdapter(Context context, int layoutResId) {
        super(layoutResId);
        this.context = context;
        btfBriefProjectTitle = context.getResources().getStringArray(R.array
                .btc_info_project_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, final String item) {
        helper.setText(R.id.tv_project_title, btfBriefProjectTitle[helper.getAdapterPosition()]);
        helper.setText(R.id.tv_project_content, item);
        if (helper.getAdapterPosition() == 6) {
            helper.setText(R.id.tv_project_content,"官网，区块链浏览器，白皮书");
            helper.setTextColor(R.id.tv_project_content, context.getResources().getColor(R.color
                    .blue_link));
            helper.itemView.findViewById(R.id.tv_project_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(item);
                    intent.setData(content_url);
                    context.startActivity(intent);
                }
            });
        }
    }
}
