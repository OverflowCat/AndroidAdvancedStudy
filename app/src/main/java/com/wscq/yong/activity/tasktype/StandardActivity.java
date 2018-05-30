package com.wscq.yong.activity.tasktype;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.wscq.yong.BaseActivity;
import com.wscq.yong.R;
import com.wscq.yong.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/16
 * @describe
 */
public class StandardActivity extends BaseActivity {
    @BindView(R.id.single_top)
    TextView singleTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);
        ButterKnife.bind(this);
        singleTop.setText("standar");
        LogUtil.d("standard", "OnCreate()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d("standard", "onNewIntent()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("standard", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("standard", "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("standard", "onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("standard", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("standard", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("standard", "onDestroy()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        LogUtil.d("standard", "onSaveInstanceState()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d("standard", "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.d("standard", "onRestoreInstanceState()");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        LogUtil.d("standard", "onRestoreInstanceState()");
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        LogUtil.d("standard", "onPostCreate()");
    }
}
