package com.wscq.networkadvanced;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient client;
    private TextView textView;
    private TextView runONAsynchronous;
    private TextView contextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.run_on_synchronous);
        runONAsynchronous = findViewById(R.id.run_on_asynchronous);
        contextView = findViewById(R.id.context_view);


        client = new OkHttpClient();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String text = MainActivity.this.run("https://suggest.taobao.com/sug?code=utf-8&q=%E5%8D%AB%E8%A1%A3&callback=cb");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contextView.setText(text);
                            }
                        });
                    }
                }).start();
            }
        });
        runONAsynchronous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runInOtherThread();
            }
        });
    }

    //同步请求
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    String run(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //异步请求
    void runInOtherThread() {
        Request request = new Request.Builder()
                .url("https://suggest.taobao.com/sug?code=utf-8&q=%E5%8D%AB%E8%A1%A3&callback=cb")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            contextView.setText(response.body().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
