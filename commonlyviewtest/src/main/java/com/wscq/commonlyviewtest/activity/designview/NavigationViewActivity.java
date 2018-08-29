package com.wscq.commonlyviewtest.activity.designview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
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
 * @describe
 */
public class NavigationViewActivity extends BaseActivity {
    @BindView(R.id.showMenu)
    TextView showMenu;
    @BindView(R.id.navView)
    NavigationView navView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naivgation_view);
        ButterKnife.bind(this);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Snackbar.make(showMenu, item.getTitle() + "pressed", Snackbar.LENGTH_LONG).show();
                item.setCheckable(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @OnClick(R.id.showMenu)
    public void onViewClicked() {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
