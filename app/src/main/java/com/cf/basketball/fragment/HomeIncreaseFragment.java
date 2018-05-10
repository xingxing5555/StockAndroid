package com.cf.basketball.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;

/**
 * 涨幅
 *
 * @author xinxin Shi
 */
public class HomeIncreaseFragment extends Fragment {


    public HomeIncreaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_increase, container, false);
    }

}
