package com.wscq.retrofitadvanced.express.model;

import com.wscq.retrofitadvanced.bean.ExpressBean;
import com.wscq.retrofitadvanced.express.contract.ExpressQueryContract;
import com.wscq.retrofitadvanced.interfaceurl.ExpressQuestionService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.gson.GsonConverterFactory;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019-12-05
 * @describe
 */
public class ExpressQueryModel implements ExpressQueryContract.Model {
    @Override
    public void queryExpress(String expressCode, Callback<ExpressBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //根据接口来创建实现类
        ExpressQuestionService service = retrofit.create(ExpressQuestionService.class);
        Call<ExpressBean> repos = service.queryExpress(expressCode);
        repos.enqueue(callback);
    }
}
