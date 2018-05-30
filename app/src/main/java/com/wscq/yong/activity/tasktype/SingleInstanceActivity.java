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
public class SingleInstanceActivity extends BaseActivity {
    @BindView(R.id.singleInstance)
    TextView singleInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
        ButterKnife.bind(this);
        LogUtil.d("singleInstance", "OnCreate()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d("singleInstance", "onNewIntent()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("singleInstance", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("singleInstance", "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("singleInstance", "onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("singleInstance", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("singleInstance", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("singleInstance", "onDestroy()");
    }

    @OnClick(R.id.singleInstance)
    public void onViewClicked() {
        startActivity(new Intent(this, StartTypeTestActivity.class));
    }
}
