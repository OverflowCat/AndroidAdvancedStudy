package com.wscq.commonlyviewtest.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wscq.commonlyviewtest.BaseActivity;
import com.wscq.commonlyviewtest.R;
import com.wscq.commonlyviewtest.widget.ClickColorMovementMethod;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/6/27
 * @describe 测试TextView的一些功能
 */
public class TestTextViewActivity extends BaseActivity {
    @BindView(R.id.text_view)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_text_view);
        ButterKnife.bind(this);
        setTextViewClickEffect();
    }

    //设置TextView的部分文字点击效果
    private void setTextViewClickEffect() {
        final String name = "@wenyong";
        String text = name + ",你好啊,多大了?结婚没?";
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.user_name_text_color));
        SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        style.append(text);

        ClickableSpan nameClickListener = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(TestTextViewActivity.this, "click -- " + name, Toast.LENGTH_SHORT).show();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void updateDrawState(TextPaint ds) {
            }
        };
        //设置姓名部分变色
        style.setSpan(foregroundColorSpan, 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置姓名部分可点击
        style.setSpan(nameClickListener, 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(style);
        //使点击事件生效
        textView.setMovementMethod(ClickColorMovementMethod.getInstance());
    }
}
