package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;

public class SankulActivity extends AppCompatActivity {

    EditText edtSankulName, edtSankulOrganization, edtSankulPartyName, edtSankulDesignation, edtSankulContactNumber,
            edtSankulAverageStudent, edtSankulAddress1, edtSankulAddress2, edtSankulCity, edtSankulState, edtSankulRemark;
    Spinner spinnerStd, spinnerSchoolBoard, spinnerMedium;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    TextView txtFormName;
    Button btnSave, btnCancel;
    ArrayList<stdList> arrayStd;
    ArrayList<String> spotArr;
    ArrayList<MediumList> arrayMedium;
    ArrayList<String> mediumArr;
    ArrayList<BoardList> arrayBoard;
    ArrayList<String> boardArr;
    stdList stdlist;
    MediumList mediumList;
    BoardList boardList;
    String stdName, mediumName, boardName;
    int stdId, mediumId, boardId;
    int fromId;
    String fromName,Userid,latitude,longitude,location;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sankul);
        detector = new ConnectionDetector(this);
        Intent intent = getIntent();
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Userid = preferences.getString("LoggedUser", null);
        fromId = intent.getIntExtra("fromId", 0);
        fromName = intent.getStringExtra("fromName");
        edtSankulName = (EditText) findViewById(R.id.edtSankulName);
        edtSankulOrganization = (EditText) findViewById(R.id.edtSankulOrganization);
        edtSankulPartyName = (EditText) findViewById(R.id.edtSankulPartyName);
        edtSankulDesignation = (EditText) findViewById(R.id.edtSankulDesignation);
        edtSankulContactNumber = (EditText) findViewById(R.id.edtSankulContactNumber);
        edtSankulAverageStudent = (EditText) findViewById(R.id.edtSankulAverageStudent);
        edtSankulAddress1 = (EditText) findViewById(R.id.edtSankulAddress1);
        edtSankulAddress2 = (EditText) findViewById(R.id.edtSankulAddress2);
        edtSankulCity = (EditText) findViewById(R.id.edtSankulCity);
        edtSankulState = (EditText) findViewById(R.id.edtSankulState);
        edtSankulRemark = (EditText) findViewById(R.id.edtSankulRemark);
        txtFormName = (TextView) findViewById(R.id.txtSankulForm);
        btnSave = (Button) findViewById(R.id.btnSankulYes);
        btnCancel = (Button) findViewById(R.id.btnSankulCancel);
        spinnerStd = (Spinner) findViewById(R.id.spinnerSankulStandard);
        spinnerMedium = (Spinner) findViewById(R.id.spinnerSankulMedium);
        spinnerSchoolBoard = (Spinner) findViewById(R.id.spinnerSankulBoard);
        txtFormName.setText("" + fromName);
        latitude=preferences.getString("latitude",null);
        longitude=preferences.getString("longitude",null);
        location=preferences.getString("location",null);

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
                if (edtSankulName.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                } else if (edtSankulOrganization.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                } else if (edtSankulPartyName.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter Party Name", Toast.LENGTH_SHORT).show();
                } else if (edtSankulDesignation.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter Designation", Toast.LENGTH_SHORT).show();
                } else if (edtSankulContactNumber.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                } else if (edtSankulAddress1.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (edtSankulAddress2.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (edtSankulCity.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                } else if (edtSankulState.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter State", Toast.LENGTH_SHORT).show();
                } else if (edtSankulRemark.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
                } else if (edtSankulAverageStudent.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter AverageStudent", Toast.LENGTH_SHORT).show();
                } else {
                    submitForm();
                    startActivity(new Intent(SankulActivity.this, SalesMan.class));
                }

            }
        });
        btnCancel .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SankulActivity.this, SalesMan.class));
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

                                    ArrayAdapter medium = new ArrayAdapter(SankulActivity.this, android.R.layout.simple_spinner_item, mediumArr);
                                    medium.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerMedium.setAdapter(medium);

                                    ArrayAdapter std = new ArrayAdapter(SankulActivity.this, android.R.layout.simple_spinner_item, spotArr);
                                    std.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerStd.setAdapter(std);


                                    ArrayAdapter board = new ArrayAdapter(SankulActivity.this, android.R.layout.simple_spinner_item, boardArr);
                                    board.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerSchoolBoard.setAdapter(board);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(SankulActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(SankulActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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
                object.put("OrganisationName", edtSankulOrganization.getText().toString());
                object.put("PartyName", edtSankulPartyName.getText().toString());
                object.put("Designation", edtSankulDesignation.getText().toString());
                object.put("SankulName", edtSankulName.getText().toString());
//                object.put("ShopName", "");
//                object.put("DistubitorName", "Kush");
//                object.put("DistubitorType", "Sankul");
                object.put("CreatedBy",Userid);
                object.put("UpdatedBy",Userid);
                object.put("ContactNo", edtSankulContactNumber.getText().toString());
                object.put("Board", boardId);
                object.put("Medium", mediumId);
                object.put("Std", stdId);
                object.put("AvgStudent", edtSankulAverageStudent.getText().toString());
                object.put("iPartyTypeId", fromId);
                object.put("AddressLine1", edtSankulAddress1.getText().toString());
                object.put("AddressLine2", edtSankulAddress2.getText().toString());
                object.put("CityName", edtSankulCity.getText().toString());
                object.put("StateName", edtSankulState.getText().toString());
                object.put("Remark", edtSankulRemark.getText().toString());
                object.put("Latitude", latitude);
                object.put("Longitude", longitude);
                object.put("Location", location);
            } catch (JSONException e) {
                Toast.makeText(SankulActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(SankulActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SankulActivity.this, SalesMan.class));

                                    // JSONObject obj = response.getJSONObject("data");
                                    progressDialog.dismiss();
                                    finish();
                                } else if (code == false) {
                                    String msg1 = response.getString("message");
                                    progressDialog.dismiss();
                                    Toast.makeText(SankulActivity.this, "" + msg1, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(SankulActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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
