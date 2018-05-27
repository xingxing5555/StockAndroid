package com.cf.basketball.adapter.search;

import com.cf.basketball.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.basic.model.search.DefaultSearchModel;

/**
 * @author Xinxin Shi
 */

public class DefaultSearchAdapter extends BaseQuickAdapter<DefaultSearchModel.DataBean.HotCointsBean, BaseViewHolder> {

    public DefaultSearchAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DefaultSearchModel.DataBean.HotCointsBean item) {
        helper.setText(R.id.btn_search_name, item.getName());
    }
}
