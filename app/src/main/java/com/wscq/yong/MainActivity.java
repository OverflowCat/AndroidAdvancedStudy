package com.wscq.yong;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.wscq.yong.activity.tasktype.StartTypeTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.activity_start_type_test)
    Button activityStartTypeTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_start_type_test)
    public void onViewClicked() {
        startActivity(new Intent(this, StartTypeTestActivity.class));
    }
}
