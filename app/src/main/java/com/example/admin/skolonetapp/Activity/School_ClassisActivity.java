package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.admin.skolonetapp.Pojo.stdList;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class School_ClassisActivity extends AppCompatActivity {

    EditText edtSchoolOrganization, edtSchoolPartyName, edtSchoolDesignation, edtSchoolContactNumber,
            edtSchoolAddress1, edtSchoolAddress2, edtSchoolCity, edtSchoolState, edtSchoolRemark;
    Spinner spinnerStd, spinnerSchoolBoard, spinnerMedium;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    TextView txtFormName;
    Button btnSave, btnCancel;
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
    int fromId;
    String fromName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school__classis);
        Intent intent = getIntent();
        fromId = intent.getIntExtra("fromId", 0);
        fromName = intent.getStringExtra("fromName");

        detector = new ConnectionDetector(this);
        edtSchoolOrganization = (EditText) findViewById(R.id.edtSchoolOrganization);
        edtSchoolPartyName = (EditText) findViewById(R.id.edtSchoolPartyName);
        edtSchoolDesignation = (EditText) findViewById(R.id.edtOtherDesignation);
        edtSchoolContactNumber = (EditText) findViewById(R.id.edtSchoolContactNumber);
        edtSchoolAddress1 = (EditText) findViewById(R.id.edtOtherAddress1);
        edtSchoolAddress2 = (EditText) findViewById(R.id.edtOtherAddress2);
        edtSchoolCity = (EditText) findViewById(R.id.edtSchoolCity);
        edtSchoolState = (EditText) findViewById(R.id.edtSchoolState);
        edtSchoolRemark = (EditText) findViewById(R.id.edtSchoolRemark);
        spinnerStd = (Spinner) findViewById(R.id.spinnerSchoolStandard);
        spinnerMedium = (Spinner) findViewById(R.id.spinnerSchoolMedium);
        spinnerSchoolBoard = (Spinner) findViewById(R.id.spinnerSchoolBoard);
        txtFormName = (TextView) findViewById(R.id.txtForm);
        btnSave = (Button) findViewById(R.id.btnYes);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        txtFormName.setText("" + fromName);

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


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSchoolOrganization.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                } else if (edtSchoolPartyName.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Party Name", Toast.LENGTH_SHORT).show();
                } else if (edtSchoolDesignation.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Designation", Toast.LENGTH_SHORT).show();
                } else if (edtSchoolContactNumber.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                } else if (edtSchoolAddress1.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (edtSchoolAddress2.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (edtSchoolCity.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                } else if (edtSchoolState.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter State", Toast.LENGTH_SHORT).show();
                } else if (edtSchoolRemark.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
                } else {

                    submitForm();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        std();
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
                                        boardList.setBoardId(mediumId);
                                        boardList.setBoardName(mediumName);
                                        boardArr.add(boardList.getBoardName());
                                        arrayBoard.add(boardList);
                                        Log.d("nickname", "" + mediumId + " " + mediumName);
                                    }

                                    ArrayAdapter medium = new ArrayAdapter(School_ClassisActivity.this, android.R.layout.simple_spinner_item, mediumArr);
                                    medium.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerMedium.setAdapter(medium);

                                    ArrayAdapter std = new ArrayAdapter(School_ClassisActivity.this, android.R.layout.simple_spinner_item, spotArr);
                                    std.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerStd.setAdapter(std);


                                    ArrayAdapter board = new ArrayAdapter(School_ClassisActivity.this, android.R.layout.simple_spinner_item, boardArr);
                                    board.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerSchoolBoard.setAdapter(board);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(School_ClassisActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(School_ClassisActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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


    public void submitForm() {
        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            final JSONObject object = new JSONObject();
            try {
                object.put("OrganisationName", edtSchoolOrganization.getText().toString());
                object.put("PartyName", edtSchoolPartyName.getText().toString());
                object.put("Designation", edtSchoolDesignation.getText().toString());
//                object.put("SankulName", "");
//                object.put("ShopName", "");
//                object.put("DistubitorName", "");
//                object.put("DistubitorType", "");
                object.put("ContactNo", edtSchoolContactNumber.getText().toString());
                object.put("Board", boardName);
                object.put("Medium", mediumName);
                object.put("Std", stdId);
                object.put("AvgStudent", "100");
                object.put("iPartyTypeId", fromId);
                object.put("AddressLine1", edtSchoolAddress1.getText().toString());
                object.put("AddressLine2", edtSchoolAddress2.getText().toString());
                object.put("CityName", edtSchoolCity.getText().toString());
                object.put("StateName", edtSchoolState.getText().toString());
                object.put("Remark", edtSchoolRemark.getText().toString());
                object.put("Location", "Haji nathi malyu");
            } catch (JSONException e) {
                Toast.makeText(School_ClassisActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/Add", object,
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
                                    JSONObject obj = response.getJSONObject("data");
                                    progressDialog.dismiss();

                                } else if (code == false) {
                                    String msg1 = response.getString("message");
                                    progressDialog.dismiss();
                                    Toast.makeText(School_ClassisActivity.this, "" + msg1, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(School_ClassisActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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

}
