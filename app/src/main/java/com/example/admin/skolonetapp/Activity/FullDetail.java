package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.admin.skolonetapp.Pojo.BoardList;
import com.example.admin.skolonetapp.Pojo.MediumList;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.Pojo.stdList;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FullDetail extends AppCompatActivity {

    ProgressDialog progressDialog;
    ConnectionDetector detector;
    String fromId,type;
    EditText edtSankulName, edtSankulOrganization, edtSankulPartyName, edtSankulDesignation, edtSankulContactNumber,
            edtSankulAddress1,edtSankulAverageStudent, edtSankulAddress2, edtSankulCity, edtSankulState, edtSankulRemark,edtPartyShopName,edtPartyType,edtPartyDistributorName;
    Spinner spinnerStd, spinnerSchoolBoard, spinnerMedium;
    Sales sales;
    stdList stdlist;
    MediumList mediumList;
    BoardList boardList;
    ArrayList<stdList> arrayStd;
    ArrayList<String> spotArr;
    ArrayList<MediumList> arrayMedium;
    ArrayList<String> mediumArr;
    ArrayList<BoardList> arrayBoard;
    ArrayList<String> boardArr;
    String stdName, mediumName, boardName;
    int stdId, mediumId, boardId;
    Button btnSave, btnCancel;
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
        edtSankulAverageStudent = (EditText)findViewById(R.id.edtSankulAverageStudent);
        btnSave = (Button) findViewById(R.id.btnYesUpdate);
        btnCancel = (Button) findViewById(R.id.btnCancelUpdate);
        detector = new ConnectionDetector(this);
        Intent intent = getIntent();
        fromId = intent.getStringExtra("fromId");
        type = intent.getStringExtra("Type");
        Log.d("Typee",type + " ");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Sankul")) {
                    if (edtSankulName.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulOrganization.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulPartyName.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Party Name", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulDesignation.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Designation", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulContactNumber.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulAddress1.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulAddress2.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulCity.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulState.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter State", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulRemark.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
                    } else if (edtSankulAverageStudent.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter AverageStudent", Toast.LENGTH_SHORT).show();
                    } else {
                        submitForm();
                        startActivity(new Intent(FullDetail.this, SalesMan.class));
                    }
                }
            }
        });

        spinnerStd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stdName = spinnerStd.getSelectedItem().toString();
                stdlist = arrayStd.get(i);
                stdId = stdlist.getStdId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMedium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mediumName = spinnerMedium.getSelectedItem().toString();
                mediumList = arrayMedium.get(i);
                mediumId = mediumList.getMediumId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerSchoolBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                boardName = spinnerSchoolBoard.getSelectedItem().toString();
                boardList = arrayBoard.get(i);
                boardId = boardList.getBoardId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fullDetail();

     /*   btnCancel .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(FullDetail.this, SalesMan.class));
            }
        });*/


    }
    public void submitForm() {
        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            final JSONObject object = new JSONObject();
            try {
               // Log.d("create object",type);
                if(type.equals("Sankul")) {

                    object.put("OrganisationName", edtSankulOrganization.getText().toString());

                    object.put("PartyName", edtSankulPartyName.getText().toString());
                    object.put("Designation", edtSankulDesignation.getText().toString());
                    object.put("SankulName", edtSankulName.getText().toString());
//                object.put("ShopName", "");
//                object.put("DistubitorName", "Kush");
//                object.put("DistubitorType", "Sankul");

                    object.put("Board", boardId);
                    object.put("Medium", mediumId);
                    object.put("Std", stdId);
                    object.put("AvgStudent", edtSankulAverageStudent.getText().toString());

                }
                object.put("ContactNo", edtSankulContactNumber.getText().toString());
                object.put("AddressLine1", edtSankulAddress1.getText().toString());
                object.put("AddressLine2", edtSankulAddress2.getText().toString());
                object.put("CityName", edtSankulCity.getText().toString());
                object.put("StateName", edtSankulState.getText().toString());
                object.put("Remark", edtSankulRemark.getText().toString());
                Log.d("updatevalue" , object.toString() + " ");
            } catch (JSONException e) {
                Toast.makeText(FullDetail.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/Update?id=" + fromId , object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("RESPONSE", response.toString());

                            try {
                                boolean code = response.getBoolean("status");
                                Log.d("Login", "" + code);
                                String msg = response.getString("message");
                                // Toast.makeText(this, ""+code, Toast.LENGTH_SHORT).show();
                                if (code == true) {
                                    Toast.makeText(FullDetail.this, ""+msg, Toast.LENGTH_SHORT).show();
                                    // JSONObject obj = response.getJSONObject("data");
                                    progressDialog.dismiss();
                                    finish();
                                } else if (code == false) {
                                    String msg1 = response.getString("message");
                                    progressDialog.dismiss();
                                    Toast.makeText(FullDetail.this, "" + msg1, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(FullDetail.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Log.d("RESPONSE", "That didn't work!");
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);
        } else {
            Toast.makeText(this, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
        }
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
                            Log.d("PartyTypee",strPartyType + "");
                            std();

                            if(strPartyType.equals("Others"))
                            {
                                edtSankulPartyName.setTextColor(Color.BLACK);
                                edtSankulAverageStudent.setVisibility((View.GONE));
                                edtSankulName.setVisibility(View.GONE);
                                edtPartyShopName.setVisibility(View.GONE);
                                edtPartyDistributorName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                spinnerMedium.setVisibility(View.GONE);
                                spinnerSchoolBoard.setVisibility(View.GONE);
                                spinnerStd.setVisibility(View.GONE);
                                setColor();
                                edtSankulOrganization.setText(organisationName);
                                edtSankulPartyName.setText(partyName);

                             //   disabled();




                            }
                            if(strPartyType.equals("Classes") || strPartyType.equals(("School")))
                            {

                                edtSankulName.setVisibility(View.GONE);
                               edtPartyShopName.setVisibility(View.GONE);
                                edtPartyDistributorName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                spinnerMedium.setSelection(medium);
                                spinnerStd.setSelection(std);
                                spinnerSchoolBoard.setSelection(board);
                                setColor();
                                edtSankulOrganization.setText( organisationName);
                                edtSankulPartyName.setText( partyName);
                                edtSankulAverageStudent.setText(avgStudent);



                                //   disabled();




                            }
                            if(strPartyType.equals("Sankul"))
                            {
                                edtSankulName.setText(sankulName);
                                edtPartyShopName.setVisibility(View.GONE);edtPartyType.setVisibility(View.GONE);edtPartyDistributorName.setVisibility(View.GONE);
                                edtSankulOrganization.setText( organisationName);
                                edtSankulPartyName.setText( partyName);
                                edtSankulAverageStudent.setText(avgStudent);
                                spinnerMedium.setSelection(medium);
                                spinnerStd.setSelection(std);
                                spinnerSchoolBoard.setSelection(board);
                            }

                            edtSankulDesignation.setText( designation); edtSankulContactNumber.setText(contactNo);
                            edtSankulAddress1.setText(addressLine1); edtSankulAddress2.setText( addressLine2);
                            edtSankulCity.setText( cityName); edtSankulState.setText( stateName); edtSankulRemark.setText(remark);

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
    
    public void std() {

        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
           progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);


            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    Constant.PATH + "master/getcollection", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("RESPONSE", response.toString());

                            try {
                                boolean code = response.getBoolean("status");
                                Log.d("Login", "" + code);
                                String msg = response.getString("message");
                                // Toast.makeText(this, ""+code, Toast.LENGTH_SHORT).show();
                                if (code == true) {
                                    arrayStd = new ArrayList<>();
                                    spotArr = new ArrayList<>();
                                    arrayMedium = new ArrayList<>();
                                    mediumArr = new ArrayList<>();
                                    arrayBoard = new ArrayList<>();
                                    boardArr = new ArrayList<>();

                                    progressDialog.dismiss();
                                    JSONObject obj = response.getJSONObject("data");
                                    JSONArray objArray1 = obj.getJSONArray("stdList");
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject(i);
                                        int stdId = jresponse.getInt("iStandardId");
                                        String stdName = jresponse.getString("strStandardName");
                                        stdlist = new stdList();
                                    /*    if (i == 0) {
                                            stdlist.setStdId(0);
                                            stdlist.setStdName("Select Standard");
                                            spotArr.add(stdlist.getStdName());
                                            arrayStd.add(stdlist);
                                        }*/
                                        stdlist.setStdId(stdId);
                                        stdlist.setStdName(stdName);
                                        spotArr.add(stdlist.getStdName());
                                        arrayStd.add(stdlist);
                                        Log.d("nickname", "" + stdId + " " + stdName);
                                    }
                                    JSONArray objArray2 = obj.getJSONArray("mediumList");
                                    for (int i = 0; i < objArray2.length(); i++) {
                                        JSONObject jresponse = objArray2.getJSONObject(i);
                                        int mediumId = jresponse.getInt("iMedium");
                                        String mediumName = jresponse.getString("strMediumName");
                                        mediumList = new MediumList();
                                       /* if (i == 0) {
                                            mediumList.setMediumId(0);
                                            mediumList.setMediumName("Select Medium");
                                            mediumArr.add(mediumList.getMediumName());
                                            arrayMedium.add(mediumList);
                                        }*/
                                        mediumList.setMediumId(mediumId);
                                        mediumList.setMediumName(mediumName);
                                        mediumArr.add(mediumList.getMediumName());
                                        arrayMedium.add(mediumList);
                                        Log.d("nickname", "" + mediumId + " " + mediumName);
                                    }
                                    JSONArray objArray3 = obj.getJSONArray("boardList");
                                    for (int i = 0; i < objArray3.length(); i++) {
                                        JSONObject jresponse = objArray3.getJSONObject(i);
                                        int mediumId = jresponse.getInt("iBoardId");
                                        String mediumName = jresponse.getString("strBoardName");
                                        boardList = new BoardList();
                                       /* if (i == 0) {
                                            boardList.setBoardId(0);
                                            boardList.setBoardName("Select Board");
                                            boardArr.add(boardList.getBoardName());
                                            arrayBoard.add(boardList);
                                        }*/
                                        boardList.setBoardId(mediumId);
                                        boardList.setBoardName(mediumName);
                                        boardArr.add(boardList.getBoardName());
                                        arrayBoard.add(boardList);
                                        Log.d("nickname", "" + mediumId + " " + mediumName);
                                    }

                                    ArrayAdapter medium = new ArrayAdapter(FullDetail.this, android.R.layout.simple_spinner_item, mediumArr);
                                    medium.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerMedium.setAdapter(medium);

                                    ArrayAdapter std = new ArrayAdapter(FullDetail.this, android.R.layout.simple_spinner_item, spotArr);
                                    std.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerStd.setAdapter(std);


                                    ArrayAdapter board = new ArrayAdapter(FullDetail.this, android.R.layout.simple_spinner_item, boardArr);
                                    board.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerSchoolBoard.setAdapter(board);
                                  //  progressDialog.dismiss();

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(FullDetail.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(FullDetail.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Log.d("RESPONSE", "That didn't work!");
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);
        } else {
            Toast.makeText(this, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
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
