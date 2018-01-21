package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

public class FullDetail extends AppCompatActivity {

    ProgressDialog progressDialog;
    ConnectionDetector detector;
    String fromId;
    Sales sales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);
        detector = new ConnectionDetector(this);
        Intent intent = getIntent();
        fromId = intent.getStringExtra("fromId");
        fullDetail();
    }

    public void fullDetail() {
        if (detector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/GetSalesDataById?id=" + fromId, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("fullDetail", response.toString());
                    try {
                        boolean code = response.getBoolean("status");
                        if (code == true) {
                            progressDialog.dismiss();
                            JSONObject object = response.getJSONObject("data");
//                            Log.d("yatra", object.toString());
                            String partyInfoId=object.getString("partyInfoId");
                            String partyName=object.getString("partyName");
                            String organisationName=object.getString("organisationName");
                            String designation=object.getString("designation");
                            String sankulName=object.getString("sankulName");
                            String shopName=object.getString("shopName");
                            String distubitorName=object.getString("distubitorName");
                            String distubitorType=object.getString("distubitorType");
                            String iUserTypeId=object.getString("iUserTypeId");
                            String contactNo=object.getString("contactNo");
                            String board=object.getString("board");
                            String medium=object.getString("medium");
                            String std=object.getString("std");
                            String avgStudent=object.getString("avgStudent");
                            String addressLine1=object.getString("addressLine1");
                            String addressLine2=object.getString("addressLine2");
                            String cityName=object.getString("cityName");
                            String stateName=object.getString("stateName");
                            String remark=object.getString("remark");
                            String location=object.getString("location");
                            String datetimeCreated=object.getString("datetimeCreated");
                            String iPartyTypeId=object.getString("iPartyTypeId");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (progressDialog != null)
                        progressDialog.dismiss();
                }
            });
            objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(FullDetail.this);
            requestQueue.add(objectRequest);
        } else {
            Toast.makeText(FullDetail.this, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
        }
    }

}
