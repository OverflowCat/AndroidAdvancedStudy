package com.wscq.retrofitadvanced;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wscq.retrofitadvanced.express.view.ExpressQuestionActivity;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019-12-05
 * @describe
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        ExpressQuestionActivity.class));
            }
        });
    }
}
