package com.wscq.commonlyviewtest.activity.designview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

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
public class CollapsingToolbarLayoutActivity extends BaseActivity {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar);
        ButterKnife.bind(this);
        toolbar.setTitle("👌好学习");
        toolbar.setNavigationIcon(R.drawable.coffee_topic_icon);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        iv.setImageResource(R.drawable.coffee_picture_checkbox_normal);
    }
}
