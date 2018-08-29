package com.wscq.commonlyviewtest.activity.designview;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
public class CoordinatorLayoutActivity extends BaseActivity {
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.mainContent)
    CoordinatorLayout mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mainContent, "topic", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
