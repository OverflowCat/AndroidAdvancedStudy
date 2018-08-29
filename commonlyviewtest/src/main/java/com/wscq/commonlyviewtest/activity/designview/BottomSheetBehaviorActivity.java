package com.wscq.commonlyviewtest.activity.designview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
public class BottomSheetBehaviorActivity extends BaseActivity {
    private static final String TAG = BottomSheetBehaviorActivity.class.getSimpleName();
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.designBottomSheet)
    LinearLayout designBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_behavior);
        ButterKnife.bind(this);
        initView();

        BottomSheetBehavior behavior = BottomSheetBehavior.from(designBottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d(TAG, "onStateChanged:" + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d(TAG, "onSlide:" + slideOffset);
            }
        });
    }

    private void initView() {
        toolbar.setTitle("👌好学习");
        toolbar.setNavigationIcon(R.drawable.coffee_topic_icon);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        iv.setImageResource(R.drawable.coffee_picture_checkbox_normal);
    }
}
