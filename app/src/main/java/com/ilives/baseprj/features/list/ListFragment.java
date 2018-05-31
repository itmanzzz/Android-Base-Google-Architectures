package com.ilives.baseprj.features.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilives.baseprj.R;
import com.ilives.baseprj.common.base.BaseFragment;
import com.ilives.baseprj.common.base.BaseFragmentContract;
import com.ilives.baseprj.databinding.FragmentListBinding;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.features.list
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/31/18
 * ❖ Time: 15:42
 * -------------^_^-------------
 **/
public class ListFragment extends BaseFragment {

    FragmentListBinding rootView;

    @Override
    public BaseFragmentContract.Presenter getPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        return this.rootView.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initViews();
    }


    /**
     * initViews
     */
    private void initViews() {

    }
}
