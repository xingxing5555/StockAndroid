package com.cf.basketball.fragment.btc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cf.basketball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BTCBriefFragment extends Fragment {


    public BTCBriefFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_btc_brief, container, false);
    }

}
