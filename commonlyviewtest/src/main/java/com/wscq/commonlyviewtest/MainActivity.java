package com.wscq.commonlyviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wscq.commonlyviewtest.activity.TestTextViewActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.text_view_test, R.id.constraint_layout_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_view_test:
                startActivity(new Intent(this, TestTextViewActivity.class));
                break;
            case R.id.constraint_layout_test:
                break;
        }
    }
}
