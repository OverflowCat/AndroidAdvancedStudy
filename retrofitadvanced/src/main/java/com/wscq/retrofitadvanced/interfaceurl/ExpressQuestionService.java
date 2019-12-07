package com.wscq.retrofitadvanced.interfaceurl;

import com.wscq.retrofitadvanced.bean.ExpressBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019-12-05
 * @describe
 */
public interface ExpressQuestionService {
    @GET("query?type=yuantong")
    Call<ExpressBean> queryExpress(@Query("postid") String expressCode);
}
