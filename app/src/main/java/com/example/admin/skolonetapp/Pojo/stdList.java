package com.example.admin.skolonetapp.Pojo;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

/**
 * Created by DELL on 1/16/2018.
 */

public class stdList extends KeyPairBoolData {
    int stdId;
    String stdName;

    public int getStdId() {
        return stdId;
    }

    public void setStdId(int stdId) {
        this.stdId = stdId;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }
}
