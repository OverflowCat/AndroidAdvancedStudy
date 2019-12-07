package com.wscq.retrofitadvanced.bean;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019-12-05
 * @describe
 */
public class ExpressBean {

    /**
     * message : ok
     * nu : 11111111111
     * ischeck : 1
     * com : yuantong
     * status : 200
     * condition : F00
     * state : 3
     * data : [{"time":"2019-11-23 16:05:40","context":"查无结果","ftime":"2019-11-23 16:05:40"}]
     */

    public String message;
    public String nu;
    public String ischeck;
    public String com;
    public String status;
    public String condition;
    public String state;
    public List<DataBean> data;

    public static class DataBean {
        public String time;
        public String context;
        public String ftime;

        @Override
        public String toString() {
            return "DataBean{" +
                    "time='" + time + '\'' +
                    "\n, context='" + context + '\'' +
                    "\n, ftime='" + ftime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ExpressBean{" +
                "\nmessage='" + message + '\'' +
                "\n, nu='" + nu + '\'' +
                "\n, ischeck='" + ischeck + '\'' +
                "\n, com='" + com + '\'' +
                "\n, status='" + status + '\'' +
                "\n, condition='" + condition + '\'' +
                "\n, state='" + state + '\'' +
                "\n, data=" + data +
                '}';
    }
}
