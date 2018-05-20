package com.cf.basketball.fragment.btc;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
        binding.mrvList.addItemDecoration(createItemDecoration(R.color.grey_d));
        binding.mrvList.setAdapter(new HomeBtcAdapter(R.layout.item_home_btc, createData()));
//         int[] mColors = {getResources().getColor(R.color.red),getResources().getColor(R.color
// .blue),getResources().getColor(R.color.green)};
//        for (int i=0; i<3; i++){
//            PieData pieData = new PieData();
//            pieData.setName("区域"+i);
//            pieData.setValue((float)i+1);
//            pieData.setColor(mColors[i]);
//            mPieDatas.add(pieData);
//        }
//        binding.pieChart.setPieData(mPieDatas);
//        binding.pieChart.setAnimatedFlag(false);
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
//        binding.picChart.highlightValue(0, 2);
        for (IDataSet<?> set : binding.picChart.getData().getDataSets()) {
//            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        IPieDataSet index = binding.picChart.getData().getDataSetByIndex(0);
//        index.setHighlightEnabled(true);
        binding.picChart.highlightValue(index.getEntryForIndex(0).getX(),index.getEntryForIndex(0).getY(),0);
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
