package com.wscq.commonlyviewtest.activity.designview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.wscq.commonlyviewtest.BaseActivity;
import com.wscq.commonlyviewtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/8/29
 * @describe designView总结
 */
public class DesignViewActivity extends BaseActivity {
    @BindView(R.id.snackBar)
    TextView snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_view);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.snackBar, R.id.textInputLayout, R.id.tabLayout, R.id.navigationView, R.id.floatingActionButton,
            R.id.coordinatorLayout, R.id.collapsingToolbarLayout, R.id.bottomSheetBehavior})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.snackBar:
                showSnackBar();
                break;
            case R.id.textInputLayout:
                startActivity(new Intent(this, TextInputLayoutActivity.class));
                break;
            case R.id.tabLayout:
                startActivity(new Intent(this, TabLayoutActivity.class));
                break;
            case R.id.navigationView:
                startActivity(new Intent(this, NavigationViewActivity.class));
                break;
            case R.id.floatingActionButton:
                startActivity(new Intent(this, FloatingActionButtonActivity.class));
                break;
            case R.id.coordinatorLayout:
                startActivity(new Intent(this, CoordinatorLayoutActivity.class));
                break;
            case R.id.collapsingToolbarLayout:
                startActivity(new Intent(this, CollapsingToolbarLayoutActivity.class));
                break;
            case R.id.bottomSheetBehavior:
                startActivity(new Intent(this, BottomSheetBehaviorActivity.class));
                break;
        }
    }

    private void showSnackBar() {
        Snackbar.make(snackBar, "here's a SnackBar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }
}
