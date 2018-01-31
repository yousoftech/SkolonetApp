package com.example.admin.skolonetapp.Pojo;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

/**
 * Created by DELL on 1/18/2018.
 */

public class MediumList extends KeyPairBoolData {

    int mediumId;
    String mediumName;

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
    }
}
