package com.cf.basketball.fragment.btc;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;
import com.cf.basketball.adapter.btc.BtcMarketListAdapter;
import com.cf.basketball.databinding.FragmentBtcChartBinding;
import com.cf.basketball.net.NetManager;
import com.example.admin.basic.base.BaseFragment;
import com.example.admin.basic.constants.Constants;
import com.example.admin.basic.interfaces.OnRequestListener;
import com.example.admin.basic.model.market.BtcMarketModel;
import com.example.admin.basic.utils.LogUtils;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * BTC的图表界面
 *
 * @author xinxin Shi
 */
public class BtcMarketFragment extends BaseFragment implements OnRequestListener {
    private FragmentBtcChartBinding binding;
    private ArrayList<PieData> mPieDatas = new ArrayList<>();
    private BtcMarketListAdapter adapter;
    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
        LogUtils.e("btc 市场id=" + id);
        NetManager.getInstance().getBtcMarket(id, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_btc_chart, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.getRoot().findViewById(R.id.ll_sort_prompt).setVisibility(View.VISIBLE);
        binding.mrvList.setLayoutManager(createLayoutManager(true));
        binding.mrvList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new BtcMarketListAdapter(getContext());
        binding.mrvList.setAdapter(adapter);


    }

    private void initPieChart(List<PieEntry> strings, String title, String percent) {

        PieDataSet dataSet = new PieDataSet(strings, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(8f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.red));
        colors.add(getResources().getColor(R.color.blue));
        colors.add(getResources().getColor(R.color.green));
        dataSet.setColors(colors);
        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        binding.picChart.setData(pieData);
        binding.picChart.setTouchEnabled(false);

        binding.picChart.setCenterText(generateCenterSpannableText(title, percent));
        Description description = new Description();
        description.setText("");
        binding.picChart.setDescription(description);
        binding.picChart.setUsePercentValues(true);
        binding.picChart.setDrawEntryLabels(false);
        binding.picChart.setTransparentCircleColor(getResources().getColor(R.color.transparent));
        binding.picChart.setTransparentCircleAlpha(0);
        binding.picChart.setTransparentCircleRadius(0);
        binding.picChart.setRotationAngle(0);
        binding.picChart.setRotationEnabled(false);
        binding.picChart.getLegend().setEnabled(false);
        for (IDataSet<?> set : binding.picChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        IPieDataSet index = binding.picChart.getData().getDataSetByIndex(0);
        binding.picChart.highlightValue(index.getEntryForIndex(0).getX(), index.getEntryForIndex
                (0).getY(), 0);
        binding.picChart.invalidate();
    }

    @Override
    public void onResponse(String tag, String json) {
        LogUtils.e("市场：" + json);
        BtcMarketModel marketModel = new Gson().fromJson(json, BtcMarketModel.class);
        if (marketModel == null || marketModel.getCode() != Constants.NET_REQUEST_SUCCESS_CODE) {
            return;
        }
        BtcMarketModel.DataBean data = marketModel.getData();
        List<BtcMarketModel.DataBean.MarketsBean> markets = data.getMarkets();
        adapter.setDataList(markets);
        adapter.notifyDataSetChanged();
        binding.tvTurnVolume.setText(data.getAmount());
        List<String> rates = data.getRates();
        List<String> names = data.getNames();
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < rates.size(); i++) {
            String replace = rates.get(i).replace("%", "");
            float v = Float.parseFloat(replace);
            list.add(v);
        }
        float totalNum = 0;
        List<PieEntry> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            strings.add(new PieEntry(list.get(i), names.get(i)));
            totalNum += list.get(i);
        }
        if (totalNum == 100) {
            initPieChart(strings, names.get(0), rates.get(0));
        }
    }

    @Override
    public void onRequestFailure(String errorMsg) {

    }

    private SpannableString generateCenterSpannableText(String firstText, String percentText) {
        SpannableString s = new SpannableString(TextUtils.concat(firstText, "\n", percentText));
        s.setSpan(new RelativeSizeSpan(.8f), 0, firstText.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), firstText.length(), s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, firstText.length(), 0);
        s.setSpan(new RelativeSizeSpan(1.2f), firstText.length() + 1, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), firstText.length() + 1, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), firstText.length() + 1, s
                .length(), 0);
        return s;
    }
}
