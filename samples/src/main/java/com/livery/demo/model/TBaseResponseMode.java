package com.livery.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/*implements Parcelable*/
public class TBaseResponseMode<T> implements Parcelable {

    private String code;
    private String msg;
    private T data;

    protected TBaseResponseMode(Parcel in) {
        code = in.readString();
        msg = in.readString();
    }

    public boolean isSuccess() {
        return "success".equals(getCode());
    }

    public boolean isLogin() {
        return "http_login_out".equals(getCode());
    }

    public static final Creator<TBaseResponseMode> CREATOR = new Creator<TBaseResponseMode>() {
        @Override
        public TBaseResponseMode createFromParcel(Parcel in) {
            return new TBaseResponseMode(in);
        }

        @Override
        public TBaseResponseMode[] newArray(int size) {
            return new TBaseResponseMode[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(msg);
    }
}