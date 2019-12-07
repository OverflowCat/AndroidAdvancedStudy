package com.wscq.retrofitadvanced.express.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wscq.retrofitadvanced.R;
import com.wscq.retrofitadvanced.bean.ExpressBean;
import com.wscq.retrofitadvanced.express.contract.ExpressQueryContract;
import com.wscq.retrofitadvanced.express.presenter.ExpressQueryPresenter;

public class ExpressQuestionActivity extends AppCompatActivity implements ExpressQueryContract.View {
    private Button button;
    private TextView textView;
    private EditText editText;
    private ExpressQueryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_question);
        findView();
        presenter = new ExpressQueryPresenter(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getExpressCode())) {
                    showToast("单号不能为空");
                } else {
                    presenter.queryExpress(getExpressCode());
                }
            }
        });
    }

    private String getExpressCode() {
        String code = editText.getText().toString().trim();
        return code;
    }

    private void findView() {
        button = findViewById(R.id.button_get);
        textView = findViewById(R.id.net_result);
        editText = findViewById(R.id.express_code);
    }

    @Override
    public void bindView(ExpressBean bean) {
        textView.setText(bean.toString());
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }
}
