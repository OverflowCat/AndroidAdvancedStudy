package com.wscq.retrofitadvanced.express.presenter;

import com.wscq.retrofitadvanced.bean.ExpressBean;
import com.wscq.retrofitadvanced.express.contract.ExpressQueryContract;
import com.wscq.retrofitadvanced.express.model.ExpressQueryModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019-12-05
 * @describe
 */
public class ExpressQueryPresenter implements ExpressQueryContract.Presenter {
    private ExpressQueryContract.View view;
    private ExpressQueryContract.Model model;

    public ExpressQueryPresenter(ExpressQueryContract.View view) {
        this.view = view;
        model = new ExpressQueryModel();
    }

    @Override
    public void queryExpress(String expressCode) {
        model.queryExpress(expressCode, new Callback<ExpressBean>() {
            @Override
            public void onResponse(Call<ExpressBean> call, Response<ExpressBean> response) {
                if (response != null && response.body() != null) {
                    view.bindView(response.body());
                } else {
                    onFailure(null, null);
                }
            }

            @Override
            public void onFailure(Call<ExpressBean> call, Throwable t) {
                if (t != null && t.getMessage() != null) {
                    view.showToast(t.getMessage());
                } else {
                    view.showToast("槽糕,服务开小差了,稍后再试吧");
                }
            }
        });
    }
}
