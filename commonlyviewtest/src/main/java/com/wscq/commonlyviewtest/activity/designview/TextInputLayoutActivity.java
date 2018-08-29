package com.wscq.commonlyviewtest.activity.designview;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

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
public class TextInputLayoutActivity extends BaseActivity {
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        ButterKnife.bind(this);
        //显示错误以及取消错误提示,按照业务需要可以酌情添加
//        textInputLayout.setError("错误提示");
//        textInputLayout.setError(null);
    }
}
