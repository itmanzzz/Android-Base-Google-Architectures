package com.ilives.baseprj.features;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ilives.baseprj.R;
import com.ilives.baseprj.common.base.BaseActivity;
import com.ilives.baseprj.common.base.BaseActivityContract;
import com.ilives.baseprj.databinding.ActivityHomeBinding;
import com.ilives.baseprj.features.list.ListFragment;

import java.util.Objects;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.features
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/31/18
 * ❖ Time: 15:22
 * -------------^_^-------------
 **/
public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    ActivityHomeBinding rootView;

    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_online_chat,
            R.drawable.ic_online_support,
            R.drawable.ic_user_profile,
            R.drawable.ic_home,
    };

    private HomePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.rootView = DataBindingUtil.setContentView(this, R.layout.activity_home);
        this.initViews();
    }

    @Override
    public BaseActivityContract.Presenter getPresenter() {
        return null;
    }

    /**
     * Initializing views and listener
     */
    private void initViews() {
        // Init env
        this.setupViewPager();
        this.setupTabIcons();

        // Init listener
        this.rootView.pager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupViewPager() {
        this.mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        this.mPagerAdapter.addFrag(new ListFragment(), "LIST1");
        this.mPagerAdapter.addFrag(new ListFragment(), "LIST2");
        this.mPagerAdapter.addFrag(new ListFragment(), "LIST3");
        this.mPagerAdapter.addFrag(new ListFragment(), "LIST4");
        this.mPagerAdapter.addFrag(new ListFragment(), "LIST5");

        // load to pager
        this.rootView.pager.setAdapter(this.mPagerAdapter);
        this.rootView.tabs.setupWithViewPager(this.rootView.pager);
    }

    /**
     * setupTabIcons
     */
    private void setupTabIcons() {
        Objects.requireNonNull(this.rootView.tabs.getTabAt(0))
                .setCustomView(this.getTabView(this.rootView.tabs, 0));
        Objects.requireNonNull(this.rootView.tabs.getTabAt(1))
                .setCustomView(this.getTabView(this.rootView.tabs, 1));
        Objects.requireNonNull(this.rootView.tabs.getTabAt(2))
                .setCustomView(this.getTabView(this.rootView.tabs, 2));
        Objects.requireNonNull(this.rootView.tabs.getTabAt(3))
                .setCustomView(this.getTabView(this.rootView.tabs, 3));
        Objects.requireNonNull(this.rootView.tabs.getTabAt(4))
                .setCustomView(this.getTabView(this.rootView.tabs, 4));
    }

    public View getTabView(TabLayout tabLayout, int position) {
        View view = LayoutInflater.from(this)
                .inflate(R.layout.common_tab, tabLayout, false);
        ImageView icon = view.findViewById(R.id.ivIcon);
        icon.setImageResource(this.tabIcons[position]);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // TODO VIEW_ID
        }
    }
}
