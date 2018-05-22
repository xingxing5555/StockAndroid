package com.cf.basketball.fragment.btc;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.cf.basketball.adapter.home.HomeBtcAdapter;
import com.cf.basketball.databinding.FragmentBtcChartBinding;
import com.example.admin.basic.base.BaseFragment;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * BTC的图表界面
 *
 * @author xinxin Shi
 */
public class BtcMarketFragment extends BaseFragment {
    private FragmentBtcChartBinding binding;
    private ArrayList<PieData> mPieDatas = new ArrayList<>();
    private HomeBtcAdapter adapter;


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
        adapter = new HomeBtcAdapter(getContext());
        adapter.setDataList(createData());
        binding.mrvList.setAdapter(adapter);
        initPieChart();
    }

    private void initPieChart() {
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(20f, ""));
        strings.add(new PieEntry(30f, ""));
        strings.add(new PieEntry(50f, ""));
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

        binding.picChart.setCenterText(generateCenterSpannableText("Binance", "19.45%"));
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
