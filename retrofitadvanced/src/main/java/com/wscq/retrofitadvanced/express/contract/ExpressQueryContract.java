package com.wscq.retrofitadvanced.express.contract;

import com.wscq.retrofitadvanced.bean.ExpressBean;

import retrofit2.Callback;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019-12-05
 * @describe
 */
public interface ExpressQueryContract {
    interface View {
        /**
         * 绑定返回数据到页面
         */
        void bindView(ExpressBean bean);

        /**
         * 显示提示信息
         */
        void showToast(String message);
    }

    interface Presenter {
        /**
         * 处理查询逻辑
         */
        void queryExpress(String expressCode);
    }

    interface Model {
        /**
         * 调用接口查询信息
         */
        void queryExpress(String expressCode, Callback<ExpressBean> callback);
    }
}
