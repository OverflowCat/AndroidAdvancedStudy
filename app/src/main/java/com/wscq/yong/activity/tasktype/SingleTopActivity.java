package com.wscq.yong.activity.tasktype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.wscq.yong.BaseActivity;
import com.wscq.yong.R;
import com.wscq.yong.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/16
 * @describe
 */
public class SingleTopActivity extends BaseActivity {
    @BindView(R.id.single_top)
    TextView singleTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);
        ButterKnife.bind(this);
        LogUtil.d("singleTop", "OnCreate()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d("singleTop", "onNewIntent()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("singleTop", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("singleTop", "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("singleTop", "onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("singleTop", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("singleTop", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("singleTop", "onDestroy()");
    }

    @OnClick(R.id.single_top)
    public void onViewClicked() {
        startActivity(new Intent(this, SingleTopActivity.class));
    }
}
