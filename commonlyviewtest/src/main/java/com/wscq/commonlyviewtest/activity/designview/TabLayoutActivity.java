package com.wscq.commonlyviewtest.activity.designview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wscq.commonlyviewtest.BaseActivity;
import com.wscq.commonlyviewtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/8/29
 * @describe
 */
public class TabLayoutActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);
        //关联viewPage
        init();
    }

    private void init() {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return false;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
}