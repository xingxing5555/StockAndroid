package com.cf.basketball.adapter.currency;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cf.basketball.R;
import com.example.admin.basic.model.CurrencyInfoNewsModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class CurrencyInfoNewsAdapter extends BaseQuickAdapter<CurrencyInfoNewsModel,
        BaseViewHolder> {

    private Context context;
    private ImageView ivNewsPicutre;

    public CurrencyInfoNewsAdapter(Context context, int layoutResId, @Nullable
            List<CurrencyInfoNewsModel> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CurrencyInfoNewsModel item) {
        helper.setText(R.id.tv_news_content, item.getNews());
        helper.setText(R.id.tv_news_time, item.getTime());
        ivNewsPicutre = (ImageView) helper.itemView.findViewById(R.id.iv_news_picture);
        Glide.with(context).load(item.getPicture()).into(ivNewsPicutre);

    }
}
