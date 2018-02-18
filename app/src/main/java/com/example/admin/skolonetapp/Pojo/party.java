package com.example.admin.skolonetapp.Pojo;

/**
 * Created by DELL on 1/21/2018.
 */

public class party {
    int salesId;
    String salesName;

    public party( int _id, String _name )
    {
        salesId = _id;
        salesName = _name;
    }
    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }
}
