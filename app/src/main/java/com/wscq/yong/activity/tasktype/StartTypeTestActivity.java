package com.wscq.yong.activity.tasktype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
public class StartTypeTestActivity extends BaseActivity {
    @BindView(R.id.single_top)
    Button singleTop;
    @BindView(R.id.single_task)
    Button singleTask;
    @BindView(R.id.single_instance)
    Button typeTestActivity;
    @BindView(R.id.standard)
    Button standard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_type_test);
        ButterKnife.bind(this);
        LogUtil.d("typeTestActivity", "onCreate()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d("typeTestActivity", "onNewIntent()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("typeTestActivity", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("typeTestActivity", "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("typeTestActivity", "onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("typeTestActivity", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("typeTestActivity", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("typeTestActivity", "onDestroy()");
    }

    @OnClick({R.id.single_top, R.id.single_task, R.id.single_instance, R.id.standard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.single_top:
                startActivity(new Intent(this, SingleTopActivity.class));
                break;
            case R.id.single_task:
                startActivity(new Intent(this, SingleTaskActivity.class));
                break;
            case R.id.single_instance:
                Intent intent = new Intent(this, SingleInstanceActivity.class);
                startActivity(intent);
                break;
            case R.id.standard:
                startActivity(new Intent(this, StandardActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果请求码相符,且返回值为正确值,则取值
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            data.getStringExtra("key");
        }
    }
}
