package com.niupiao.niupiao.fragments.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.fragments.ViewPagerFragment;

/**
 * Created by kevinchen on 3/9/15.
 */
public class TransferTicketsFragment extends ViewPagerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.linear_layout_with_list_view, container, false);
        return root;
    }

    @Override
    public String getTitle() {
        return "Transfer Tickets";
    }

    public static TransferTicketsFragment newInstance() {
        return new TransferTicketsFragment();
    }
}