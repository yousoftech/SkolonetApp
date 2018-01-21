package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    EditText edtSankulName, edtSankulOrganization, edtSankulPartyName, edtSankulDesignation, edtSankulContactNumber,
            edtSankulAddress1, edtSankulAddress2, edtSankulCity, edtSankulState, edtSankulRemark,edtPartyShopName,edtPartyType,edtPartyDistributorName;
    Spinner spinnerStd, spinnerSchoolBoard, spinnerMedium;
    Sales sales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);
        edtSankulName = (EditText) findViewById(R.id.edtSankulName);
        edtSankulOrganization = (EditText) findViewById(R.id.edtSankulOrganization);
        edtSankulPartyName = (EditText) findViewById(R.id.edtSankulPartyName);
        edtSankulDesignation = (EditText) findViewById(R.id.edtSankulDesignation);
        edtSankulContactNumber = (EditText) findViewById(R.id.edtSankulContactNumber);
        edtSankulAddress1 = (EditText) findViewById(R.id.edtSankulAddress1);
        edtSankulAddress2 = (EditText) findViewById(R.id.edtSankulAddress2);
        edtSankulCity = (EditText) findViewById(R.id.edtSankulCity);
        edtSankulState = (EditText) findViewById(R.id.edtSankulState);
        edtSankulRemark = (EditText) findViewById(R.id.edtSankulRemark);
        spinnerStd = (Spinner) findViewById(R.id.spinnerSankulStandard);
        spinnerMedium = (Spinner) findViewById(R.id.spinnerSankulMedium);
        spinnerSchoolBoard = (Spinner) findViewById(R.id.spinnerSankulBoard);
        edtPartyShopName = (EditText) findViewById(R.id.edtPartyShopName);
        edtPartyDistributorName = (EditText) findViewById(R.id.edtPartyDistributorName);
        edtPartyType = (EditText) findViewById(R.id.edtPartyType);
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
                            int board=object.getInt("board");
                            int medium=object.getInt("medium");
                            int std=object.getInt("std");
                            String avgStudent=object.getString("avgStudent");
                            String addressLine1=object.getString("addressLine1");
                            String addressLine2=object.getString("addressLine2");
                            String cityName=object.getString("cityName");
                            String stateName=object.getString("stateName");
                            String remark=object.getString("remark");
                            String location=object.getString("location");
                            String datetimeCreated=object.getString("datetimeCreated");
                            String iPartyTypeId=object.getString("iPartyTypeId");
                            String strPartyType=object.getString("strPartyType");
                            Log.d("PartyTypee",strPartyType);
                            if(strPartyType.equals("Others"))
                            {
                                edtSankulPartyName.setTextColor(Color.BLACK);

                                edtSankulName.setVisibility(View.GONE);
                                edtPartyShopName.setVisibility(View.GONE);
                                edtPartyDistributorName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                spinnerMedium.setVisibility(View.GONE);
                                spinnerSchoolBoard.setVisibility(View.GONE);
                                spinnerStd.setVisibility(View.GONE);
                                setColor();
                                edtSankulOrganization.setText("Organisation Name : " + organisationName);
                                edtSankulPartyName.setText("Party Name : " + partyName);
                                        edtSankulDesignation.setText("Designation : " + designation); edtSankulContactNumber.setText("Contact No : "+ contactNo);
                                        edtSankulAddress1.setText("Address Line 1 : "+addressLine1); edtSankulAddress2.setText("Address Line 2: " + addressLine2);
                                        edtSankulCity.setText("City Name : "+ cityName); edtSankulState.setText("State : " + stateName); edtSankulRemark.setText("Remark : "+ remark);
                             //   disabled();




                            }
                             else if(strPartyType.equals("Classes") || strPartyType.equals(("School")))
                            {



                                setColor();
                                edtSankulOrganization.setText("Organisation Name : " + organisationName);
                                edtSankulPartyName.setText("Party Name : " + partyName);
                                edtSankulDesignation.setText("Designation : " + designation); edtSankulContactNumber.setText("Contact No : "+ contactNo);
                                edtSankulAddress1.setText("Address Line 1 : "+addressLine1); edtSankulAddress2.setText("Address Line 2: " + addressLine2);
                                edtSankulCity.setText("City Name : "+ cityName); edtSankulState.setText("State : " + stateName); edtSankulRemark.setText("Remark : "+ remark);
                                spinnerMedium.setId(medium);
                                spinnerStd.setId(std);
                                spinnerSchoolBoard.setId(board);

                                edtSankulName.setVisibility(View.GONE);
                                 edtPartyShopName.setVisibility(View.GONE);
                                 edtPartyDistributorName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                //   disabled();




                            }

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
   // public void disabled()
    //{
     //   edtSankulName.setEnabled(false); edtSankulOrganization.setEnabled(false); edtSankulPartyName.setEnabled(false); edtSankulDesignation.setEnabled(false); edtSankulContactNumber.setEnabled(false);
      //          edtSankulAddress1.setEnabled(false); edtSankulAddress2.setEnabled(false); edtSankulCity.setEnabled(false); edtSankulState.setEnabled(false); edtSankulRemark.setEnabled(false);edtPartyShopName.setEnabled(false);edtPartyType.setEnabled(false);edtPartyDistributorName.setEnabled(false);
   //spinnerMedium.setEnabled(false);
  // spinnerSchoolBoard.setEnabled(false);
  // spinnerStd.setEnabled(false);
   
 //   }

   // public void enabled()
   // {

    //}
    public void setColor()
    {
        edtSankulName.setTextColor(Color.BLACK); edtSankulOrganization.setTextColor(Color.BLACK); edtSankulPartyName.setTextColor(Color.BLACK); edtSankulDesignation.setTextColor(Color.BLACK); edtSankulContactNumber.setTextColor(Color.BLACK);
        edtSankulAddress1.setTextColor(Color.BLACK); edtSankulAddress2.setTextColor(Color.BLACK); edtSankulCity.setTextColor(Color.BLACK); edtSankulState.setTextColor(Color.BLACK); edtSankulRemark.setTextColor(Color.BLACK);edtPartyShopName.setTextColor(Color.BLACK);edtPartyType.setTextColor(Color.BLACK);edtPartyDistributorName.setTextColor(Color.BLACK);

    }


}
