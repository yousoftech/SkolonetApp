package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinner;
import com.androidbuts.multispinnerfilter.SpinnerListener;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FullDetail extends AppCompatActivity {

    ProgressDialog progressDialog;
    ConnectionDetector detector;
    String fromId, type, itypeId;
    EditText edtSankulName, edtSankulOrganization, edtSankulPartyName, edtSankulDesignation, edtSankulContactNumber,
            edtSankulAddress1,edtSankulAverageStudentEnglish,edtSankulAverageStudentHindi,edtSankulAverageStudentGujarati, edtSankulAddress2, edtSankulCity, edtSankulState, edtSankulRemark, edtPartyShopName, edtPartyType, edtPartyDistributorName;
    MultiSpinnerSearch spinnerStdEnglish, spinnerStdHindi,spinnerStdGujarati,  spinnerMedium;
    SingleSpinner spinnerSchoolBoardEnglish, spinnerSchoolBoardHindi,spinnerSchoolBoardGujarati;
    Sales sales;
    String stdName="", mediumName="", boardName="";
    String selectedMediumName="",strMedium, selectedBoardNameEnglish="",selectedStdNameEnglish="",selectedBoardNameHindi="",selectedStdNameHindi="",selectedBoardNameGujarati="",selectedStdNameGujarati="" ;

    final List<KeyPairBoolData> listArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> listArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoard = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayBoard = new ArrayList<>();

    List<String> getselectedMedium = null;
    Integer[] array ={};
    List<String> getselectedStdHindi;
    List<String> getselectedStdEnglish;
    List<String> getselectedStdGujarati;
    List<String> getselectedBoardHindi;
    List<String> getselectedBoardGujarati;
    List<String> getselectedBoardEnglish;


    String strBoardHindi,strBoardGujarati,strBoardEnglish,strStandardHindi,strStandardEnglish,strStandardGujarati;
    int avgStudentEnglish,avgStudentHindi,avgStudentGujarati;

    int stdId, mediumId, boardId;
    int selectedStdId,selectedBoard,selectedMedium;
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

        spinnerMedium = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolMedium);
        spinnerStdEnglish = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardEnglish);
        spinnerSchoolBoardEnglish = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardEnglish);
        edtSankulAverageStudentEnglish = (EditText) findViewById(R.id.edtSchoolAverageStudentEnglish);

        spinnerStdHindi = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardHindi );
        spinnerSchoolBoardHindi  = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardHindi );
        edtSankulAverageStudentHindi  = (EditText) findViewById(R.id.edtSchoolAverageStudentHindi );

        spinnerStdGujarati = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardGujarati );
        spinnerSchoolBoardGujarati  = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardGujarati );
        edtSankulAverageStudentGujarati  = (EditText) findViewById(R.id.edtSchoolAverageStudentGujarati);


        edtPartyShopName = (EditText) findViewById(R.id.edtPartyShopName);
        edtPartyDistributorName = (EditText) findViewById(R.id.edtPartyDistributorName);
        edtPartyType = (EditText) findViewById(R.id.edtPartyType);
        btnSave = (Button) findViewById(R.id.btnYesUpdate);
        btnCancel = (Button) findViewById(R.id.btnCancelUpdate);
        detector = new ConnectionDetector(this);
        Intent intent = getIntent();
        fromId = intent.getStringExtra("fromId");
        Log.d("Typee", type + " ");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(FullDetail.this, SalesMan.class));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Sankul")) {
                    if (edtSankulName.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Sankul Name", Toast.LENGTH_SHORT).show();
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
                    } else if (edtSankulAverageStudentEnglish.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter AverageStudent", Toast.LENGTH_SHORT).show();
                    } else {
                        submitForm();
                        startActivity(new Intent(FullDetail.this, SalesMan.class));
                    }
                } else if (type.equals("Party")) {
                    if (edtPartyShopName.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Shop Name", Toast.LENGTH_SHORT).show();
                    } else if (edtPartyDistributorName.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Distributor Name", Toast.LENGTH_SHORT).show();
                    } else if (edtPartyType.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter Party Type", Toast.LENGTH_SHORT).show();
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
                    } else {
                        submitForm();
                        startActivity(new Intent(FullDetail.this, SalesMan.class));
                    }
                } else if (type.equals("Classes") || type.equals(("School"))) {
                    if (edtSankulOrganization.getText().toString().equals("")) {
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
                    } else if (edtSankulAverageStudentEnglish.getText().toString().equals("")) {
                        Toast.makeText(FullDetail.this, "Please Enter AverageStudent", Toast.LENGTH_SHORT).show();
                    } else {
                        submitForm();
                        startActivity(new Intent(FullDetail.this, SalesMan.class));
                    }
                } else if (type.equals("Others")) {
                    if (edtSankulOrganization.getText().toString().equals("")) {
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
                    } else {
                        submitForm();
                        startActivity(new Intent(FullDetail.this, SalesMan.class));
                    }
                }


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(FullDetail.this, SalesMan.class));
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
                if (type.equals("Sankul")) {
                    edtPartyShopName.setVisibility(View.GONE);
                    edtPartyType.setVisibility(View.GONE);
                    edtPartyDistributorName.setVisibility(View.GONE);

                    object.put("OrganisationName", edtSankulOrganization.getText().toString());

                    object.put("PartyName", edtSankulPartyName.getText().toString());
                    object.put("SankulName", edtSankulName.getText().toString());
//                object.put("ShopName", "");
//                object.put("DistubitorName", "Kush");
//                object.put("DistubitorType", "Sankul");
                    Log.d("boarddid", boardId + "");
                    Log.d("mediummid", mediumId + "");
                    Log.d("stddid", stdId + "");

                    object.put("Board", boardId);
                    object.put("Medium", mediumId);
                    object.put("Std", stdId);
                    object.put("AvgStudent", edtSankulAverageStudentEnglish.getText().toString());

                }
                if (type.equals("Party")) {
                    edtSankulName.setVisibility(View.GONE);
                    edtSankulOrganization.setVisibility(View.GONE);
                    edtSankulPartyName.setVisibility(View.GONE);
                    spinnerStdEnglish.setVisibility(View.GONE);
                    spinnerSchoolBoardEnglish.setVisibility(View.GONE);
                    spinnerMedium.setVisibility(View.GONE);
                    object.put("ShopName", edtPartyShopName.getText().toString());
                    object.put("DistubitorName", edtPartyDistributorName.getText().toString());
                    object.put("DistubitorType", edtPartyType.getText().toString());
                }
                if (type.equals("Classes") || type.equals(("School"))) {
                    edtSankulName.setVisibility(View.GONE);
                    edtPartyShopName.setVisibility(View.GONE);
                    edtPartyDistributorName.setVisibility(View.GONE);
                    edtPartyType.setVisibility(View.GONE);
                    object.put("OrganisationName", edtSankulOrganization.getText().toString());
                    object.put("PartyName", edtSankulPartyName.getText().toString());
                    object.put("Board", boardId);
                    object.put("Medium", mediumId);
                    object.put("Std", stdId);
                    object.put("AvgStudent", edtSankulAverageStudentEnglish.getText().toString());


                }
                if (type.equals("Others")) {
                    object.put("OrganisationName", edtSankulOrganization.getText().toString());
                    object.put("PartyName", edtSankulPartyName.getText().toString());
                    edtSankulAverageStudentEnglish.setVisibility((View.GONE));
                    edtSankulName.setVisibility(View.GONE);
                    edtPartyShopName.setVisibility(View.GONE);
                    edtPartyDistributorName.setVisibility(View.GONE);
                    edtPartyType.setVisibility(View.GONE);
                    spinnerMedium.setVisibility(View.GONE);
                    spinnerSchoolBoardEnglish.setVisibility(View.GONE);
                    spinnerStdEnglish.setVisibility(View.GONE);

                }
                object.put("Designation", edtSankulDesignation.getText().toString());

                object.put("ContactNo", edtSankulContactNumber.getText().toString());
                object.put("AddressLine1", edtSankulAddress1.getText().toString());
                object.put("AddressLine2", edtSankulAddress2.getText().toString());
                object.put("CityName", edtSankulCity.getText().toString());
                object.put("StateName", edtSankulState.getText().toString());
                object.put("Remark", edtSankulRemark.getText().toString());
                Log.d("updatevalue", object.toString() + " ");
            } catch (JSONException e) {
                Toast.makeText(FullDetail.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/Update?id=" + fromId, object,
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
                                    Toast.makeText(FullDetail.this, "" + msg, Toast.LENGTH_SHORT).show();
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
                        Log.d("asdas", code + "");
                        if (code == true) {
                            progressDialog.dismiss();
                            JSONObject object = response.getJSONObject("data");
//                            Log.d("yatra", object.toString());
                            String partyInfoId = object.getString("partyInfoId");
                            String partyName = object.getString("partyName");
                            String organisationName = object.getString("organisationName");
                            String designation = object.getString("designation");
                            String sankulName = object.getString("sankulName");
                            String shopName = object.getString("shopName");
                            String distubitorName = object.getString("distubitorName");
                            String distubitorType = object.getString("distubitorType");
                            String iUserTypeId = object.getString("iUserTypeId");
                            String contactNo = object.getString("contactNo");

                            strMedium = object.getString( "strMedium" );
                            strBoardHindi = object.getString("strBoardHindi");
                            strBoardGujarati = object.getString("strBoardGujarati");
                            strBoardEnglish = object.getString("strBoardEnglish");
                            strStandardEnglish = object.getString("strStandardEnglish");
                            strStandardHindi = object.getString("strStandardHindi");
                            strStandardGujarati = object.getString("strStandardGujarati");
                            avgStudentEnglish = object.getInt("avgStudentEnglish");
                            avgStudentHindi = object.getInt("avgStudentHindi");
                            avgStudentGujarati = object.getInt("avgStudentGujarati");
                            getselectedMedium = Arrays.asList(strMedium.split(","));

                            getselectedBoardEnglish = Arrays.asList(strBoardEnglish.split(","));
                            getselectedBoardGujarati = Arrays.asList(strBoardGujarati.split(","));
                            getselectedBoardHindi = Arrays.asList(strBoardHindi.split(","));
                            getselectedStdEnglish = Arrays.asList(strStandardEnglish.split(","));
                            getselectedStdGujarati = Arrays.asList(strStandardGujarati.split(","));
                            getselectedStdHindi = Arrays.asList(strStandardHindi.split(","));

                            String addressLine1 = object.getString("addressLine1");
                            String addressLine2 = object.getString("addressLine2");
                            String cityName = object.getString("cityName");
                            String stateName = object.getString("stateName");
                            String remark = object.getString("remark");
                            String location = object.getString("location");
                            String datetimeCreated = object.getString("datetimeCreated");
                            String iPartyTypeId = object.getString("iPartyTypeId");
                            String strPartyType = object.getString("strPartyType");
                            Log.d("PartyTypee", strPartyType + "");
                            type = strPartyType;
                            itypeId = iPartyTypeId;
                            BoardList boardList = new BoardList();
                            MediumList mediumList = new MediumList();
                            stdList StdList = new stdList();

                            if (strPartyType.equals("Others")) {
                                edtSankulAverageStudentEnglish.setVisibility((View.GONE));
                                edtSankulName.setVisibility(View.GONE);
                                edtPartyShopName.setVisibility(View.GONE);
                                edtPartyDistributorName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                spinnerMedium.setVisibility(View.GONE);
                                spinnerSchoolBoardEnglish.setVisibility(View.GONE);
                                spinnerStdEnglish.setVisibility(View.GONE);
                                setColor();
                                edtSankulOrganization.setText(organisationName);
                                edtSankulPartyName.setText(partyName);

                                //   disabled();


                            }
                            if (strPartyType.equals("Party")) {
                                edtSankulName.setVisibility(View.GONE);
                                edtSankulOrganization.setVisibility(View.GONE);
                                edtSankulPartyName.setVisibility(View.GONE);
                                edtSankulAverageStudentEnglish.setVisibility(View.GONE);
                                spinnerSchoolBoardEnglish.setVisibility(View.GONE);
                                spinnerStdEnglish.setVisibility(View.GONE);
                                spinnerMedium.setVisibility(View.GONE);
                                edtPartyShopName.setText(shopName);
                                edtPartyDistributorName.setText(distubitorName);
                                edtPartyType.setText(distubitorType);

                            }
                            if (strPartyType.equals("Classes") || strPartyType.equals(("School"))) {

                                edtSankulName.setVisibility(View.GONE);
                                edtPartyShopName.setVisibility(View.GONE);
                                edtPartyDistributorName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                edtSankulAverageStudentEnglish.setVisibility( View.GONE );
                                edtSankulAverageStudentGujarati.setVisibility( View.GONE );


                                setColor();
                                edtSankulOrganization.setText(organisationName);
                                edtSankulPartyName.setText(partyName);
                                edtSankulAverageStudentEnglish.setText(avgStudentEnglish+"");
                                edtSankulAverageStudentHindi.setText(avgStudentHindi+"");
                                edtSankulAverageStudentGujarati.setText(avgStudentGujarati+"");


                                //   disabled();


                            }
                            if (strPartyType.equals("Sankul")) {
                                edtSankulName.setText(sankulName);
                                edtPartyShopName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                edtPartyDistributorName.setVisibility(View.GONE);
                                edtSankulAverageStudentEnglish.setVisibility( View.GONE );
                                edtSankulOrganization.setText(organisationName);
                                edtSankulPartyName.setText(partyName);
                                edtSankulAverageStudentEnglish.setText(avgStudentEnglish+"");
                                edtSankulAverageStudentHindi.setText(avgStudentHindi+"");
                                edtSankulAverageStudentGujarati.setText(avgStudentGujarati+"");
                           //     Log.d("indexofboard",getIndex(spinnerSchoolBoard,boardId + " ") + " "+ boardId);







                            }

                            edtSankulDesignation.setText(designation);
                            edtSankulContactNumber.setText(contactNo);
                            edtSankulAddress1.setText(addressLine1);
                            edtSankulAddress2.setText(addressLine2);
                            edtSankulCity.setText(cityName);
                            edtSankulState.setText(stateName);
                            edtSankulRemark.setText(remark);

                            std();

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

    //private method of your class

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


                                    progressDialog.dismiss();
                                    JSONObject obj = response.getJSONObject("data");
                                    JSONArray objArray1 = obj.getJSONArray("stdList");
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject(i);
                                        int stdId = jresponse.getInt("iStandardId");
                                        String stdName = jresponse.getString("strStandardName");
                                        KeyPairBoolData stddata = new KeyPairBoolData();

                                        /*if (i == 0) {
                                            stddata.setId(0);
                                            stddata.setName("Select Standard");
                                            stddata.setSelected(false );
                                            listArrayStd.add(stddata);

                                        }*/
                                        stddata.setId(stdId);
                                        stddata.setName(stdName);
                                        stddata.setSelected(false );
                                        Log.d("ArrayStd",stddata + "");

                                        listArrayStd.add(stddata);

                                        Log.d("nickname", "" + stdId + " " + stddata.getName());
                                    }
                                    Log.d("nicknameaas", "" + listArrayStd);

                                    spinnerStdEnglish.setItems(listArrayStd, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedStdNameEnglish += items.get( i ).getId() + ",";
                                                    selectedlistArrayStd.add( stdkey );
                                                    Log.i("adcs", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                    spinnerStdHindi.setItems(listArrayStd, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedStdNameHindi += items.get( i ).getId() + ",";
                                                    selectedlistArrayStd.add( stdkey );
                                                    Log.i("adcs", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                    spinnerStdGujarati.setItems(listArrayStd, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedStdNameGujarati += items.get( i ).getId() + ",";
                                                    selectedlistArrayStd.add( stdkey );
                                                    Log.i("adcs", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                    JSONArray objArray2 = obj.getJSONArray("mediumList");
                                    for (int i = 0; i < objArray2.length(); i++) {
                                        JSONObject jresponse = objArray2.getJSONObject(i);
                                        int mediumId = jresponse.getInt( "iMedium" );
                                        String mediumName = jresponse.getString("strMediumName");
                                        KeyPairBoolData mediumdata = new KeyPairBoolData();
                                       /* if (i == 0) {
                                            mediumdata.setId(0);
                                            mediumdata.setName("Select Medium");
                                            mediumdata.setSelected(false);
                                            listArrayMedium.add( mediumdata );

                                        }*/
                                        mediumdata.setId(mediumId);
                                        mediumdata.setName(mediumName);
                                        mediumdata.setSelected(false);

                                        listArrayMedium.add( mediumdata );

                                        Log.d("ArrayMedium",mediumdata+"");

                                        Log.d("nickname", "" + mediumId + " " + mediumName);
                                    }


                                    JSONArray objArray3 = obj.getJSONArray("boardList");
                                    for (int i = 0; i < objArray3.length(); i++) {
                                        JSONObject jresponse = objArray3.getJSONObject(i);
                                        int boardId = jresponse.getInt("iBoardId");
                                        String boardName = jresponse.getString("strBoardName");
                                        KeyPairBoolData boarddata = new KeyPairBoolData();

                                       /* if (i == 0) {
                                            boarddata.setId(0);
                                            boarddata.setName("Select Board");
                                            boarddata.setSelected( false );

                                            listArrayBoard.add( boarddata );

                                        }*/
                                        boarddata.setId(boardId);
                                        boarddata.setName(boardName);
                                        boarddata.setSelected( false );
                                        listArrayBoard.add(boarddata);



                                    }


                                    for(int k =0;k<=listArrayMedium.size()-1;k++)
                                    {

                                        Object key = new Object();
                                        key= listArrayMedium.get( k ).getId();
                                       String name=listArrayMedium.get( k ).getName();
                                        String a = key.toString();
                                        if(k<=getselectedMedium.size()) {
                                            for(int f =0;f<=getselectedMedium.size()-1;f++)
                                            {
                                                String id=getselectedMedium.get( f );

                                                Object as=new Object();
                                                as=id;
                                                if (a.equals( as ) ) {
                                                    listArrayMedium.get( k ).setSelected( true );
                                                    if(name.equalsIgnoreCase( "English" ))
                                                    {
                                                        spinnerStdEnglish.setVisibility(View.VISIBLE);

                                                        spinnerSchoolBoardEnglish.setVisibility(View.VISIBLE);
                                                        edtSankulAverageStudentEnglish.setVisibility( View.VISIBLE );
                                                        break;

                                                    }
                                                     if(name.equalsIgnoreCase( "Hindi" ))
                                                    {
                                                        spinnerStdHindi.setVisibility(View.VISIBLE);

                                                        spinnerSchoolBoardHindi.setVisibility(View.VISIBLE);
                                                        edtSankulAverageStudentHindi.setVisibility( View.VISIBLE );
                                                        break;
                                                    }
                                                     if(name.equalsIgnoreCase( "Gujarati" ))
                                                    {
                                                        spinnerStdGujarati.setVisibility(View.VISIBLE);

                                                        spinnerSchoolBoardGujarati.setVisibility(View.VISIBLE);
                                                        edtSankulAverageStudentGujarati.setVisibility( View.VISIBLE );
                                                        break;

                                                    }
                                                    break;
                                                }
                                                else
                                                {
                                                    listArrayMedium.get( k ).setSelected( false );

                                                }
                                            }

                                        }
                                    }

                                    Log.d( "checkedevalmedium",listArrayMedium + "" );

                                    spinnerMedium.setItems(listArrayMedium, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();

                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedMediumName += items.get( i ).getId() + "";
                                                    if(items.get(i).getName().equals("Hindi"))
                                                    {
                                                        spinnerStdHindi.setVisibility(View.VISIBLE);

                                                        spinnerSchoolBoardHindi.setVisibility(View.VISIBLE);
                                                        edtSankulAverageStudentHindi.setVisibility( View.VISIBLE );

                                                    }
                                                    if(items.get(i).getName().equals("Gujarati"))
                                                    {
                                                        spinnerStdGujarati.setVisibility(View.VISIBLE);

                                                        spinnerSchoolBoardGujarati.setVisibility(View.VISIBLE);
                                                        edtSankulAverageStudentGujarati.setVisibility( View.VISIBLE );
                                                    }
                                                    if(items.get(i).getName().equals("English"))
                                                    {
                                                        spinnerStdEnglish.setVisibility(View.VISIBLE);

                                                        spinnerSchoolBoardEnglish.setVisibility(View.VISIBLE);
                                                        edtSankulAverageStudentEnglish.setVisibility( View.VISIBLE );
                                                    }
                                                    selectedlistArrayMedium.add( stdkey );
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }

                                        }

                                    });
                                 //   for(int k=0;k<getselectedMedium.size();k++)
                                   // {
                                     //   String id=getselectedMedium.get( k );
                                    //    KeyPairBoolData key = new KeyPairBoolData();
                                     //   key.setId(Integer.parseInt( id) );

                                       // int pos= listArrayMedium.indexOf(key);
                                      //  int a=pos;
                                   // }

                                    Log.d( "checkedevalboard",listArrayBoard + "" );

                                    spinnerSchoolBoardEnglish.setItems(listArrayBoard, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedBoardNameEnglish = items.get( i ).getId()+"";
                                                    selectedlistArrayBoard.add( stdkey );
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });

                                    spinnerSchoolBoardHindi.setItems(listArrayBoard, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedBoardNameHindi = items.get( i ).getId() + "";
                                                    selectedlistArrayBoard.add( stdkey );
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                    spinnerSchoolBoardGujarati.setItems(listArrayBoard, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedBoardNameGujarati = items.get( i ).getId() + "";
                                                    selectedlistArrayBoard.add( stdkey );
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });

                                    //   ArrayAdapter medium = new ArrayAdapter(School_ClassisActivity.this, android.R.layout.simple_spinner_item, mediumArr);
                                    //    medium.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    //Setting the ArrayAdapter data on the Spinner
                                    //     spinnerMedium.setAdapter(medium);

                                    //  ArrayAdapter std = new ArrayAdapter(School_ClassisActivity.this, android.R.layout.simple_spinner_item, spotArr);
                                    // std.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    //Setting the ArrayAdapter data on the Spinner
                                    //  spinnerStd.setAdapter(std);


                                    //      ArrayAdapter board = new ArrayAdapter(School_ClassisActivity.this, android.R.layout.simple_spinner_item, boardArr);
                                    //      board.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    //Setting the ArrayAdapter data on the Spinner
                                    //      spinnerSchoolBoard.setAdapter(board);

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

   public void d (AdapterView<?> parent,
                         View view,
                         int position,
                         long id)
    {

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

    public void setColor() {
        edtSankulName.setTextColor(Color.BLACK);
        edtSankulOrganization.setTextColor(Color.BLACK);
        edtSankulPartyName.setTextColor(Color.BLACK);
        edtSankulDesignation.setTextColor(Color.BLACK);
        edtSankulContactNumber.setTextColor(Color.BLACK);
        edtSankulAddress1.setTextColor(Color.BLACK);
        edtSankulAddress2.setTextColor(Color.BLACK);
        edtSankulCity.setTextColor(Color.BLACK);
        edtSankulState.setTextColor(Color.BLACK);
        edtSankulRemark.setTextColor(Color.BLACK);
        edtPartyShopName.setTextColor(Color.BLACK);
        edtPartyType.setTextColor(Color.BLACK);
        edtPartyDistributorName.setTextColor(Color.BLACK);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FullDetail.this, SalesMan.class));
        finish();
    }

}
