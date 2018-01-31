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
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinner;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.example.admin.skolonetapp.Pojo.BoardList;
import com.example.admin.skolonetapp.Pojo.MediumList;
import com.example.admin.skolonetapp.Pojo.stdList;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;

public class SankulActivity extends AppCompatActivity {

    EditText edtSankulName, edtSankulOrganization, edtSankulPartyName, edtSankulDesignation, edtSankulContactNumber,
            edtSankulAverageStudentEnglish,edtSankulAverageStudentHindi,edtSankulAverageStudentGujarati, edtSankulAddress1, edtSankulAddress2, edtSankulCity, edtSankulState, edtSankulRemark;
    MultiSpinnerSearch spinnerStdEnglish,spinnerStdHindi ,spinnerStdGujarati, spinnerMedium;
    SingleSpinner spinnerSchoolBoardEnglish,spinnerSchoolBoardHindi,spinnerSchoolBoardGujarati;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    TextView txtFormName;
    Button btnSave, btnCancel;
    final List<KeyPairBoolData> listArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> listArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoard = new ArrayList<>();



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
        edtSankulAddress1 = (EditText) findViewById(R.id.edtSankulAddress1);
        edtSankulAddress2 = (EditText) findViewById(R.id.edtSankulAddress2);
        edtSankulCity = (EditText) findViewById(R.id.edtSankulCity);
        edtSankulState = (EditText) findViewById(R.id.edtSankulState);
        edtSankulRemark = (EditText) findViewById(R.id.edtSankulRemark);
        txtFormName = (TextView) findViewById(R.id.txtSankulForm);
        btnSave = (Button) findViewById(R.id.btnSankulYes);
        btnCancel = (Button) findViewById(R.id.btnSankulCancel);
        spinnerStdEnglish = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardEnglish);
        spinnerStdHindi = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardHindi);
        spinnerStdGujarati = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardGujarati);
        spinnerMedium = (MultiSpinnerSearch) findViewById(R.id.spinnerSankulMedium);
        spinnerSchoolBoardEnglish = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardEnglish);
        spinnerSchoolBoardGujarati = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardGujarati);
        spinnerSchoolBoardHindi = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardHindi);
        edtSankulAverageStudentEnglish = (EditText) findViewById(R.id.edtSchoolAverageStudentEnglish);
        edtSankulAverageStudentHindi = (EditText) findViewById(R.id.edtSchoolAverageStudentHindi);
        edtSankulAverageStudentGujarati = (EditText) findViewById(R.id.edtSchoolAverageStudentGujarati);


        txtFormName.setText("" + fromName);
        latitude=preferences.getString("latitude",null);
        longitude=preferences.getString("longitude",null);
        location=preferences.getString("location",null);
        std();


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
                } else if (edtSankulAverageStudentEnglish.getText().toString().equals("")) {
                    Toast.makeText(SankulActivity.this, "Please Enter AverageStudent", Toast.LENGTH_SHORT).show();
                } else if (boardName.equalsIgnoreCase("Select Board")) {
                    Toast.makeText(SankulActivity.this, "Please Select Board", Toast.LENGTH_SHORT).show();
                } else if (stdName.equalsIgnoreCase("Select Standard")) {
                    Toast.makeText(SankulActivity.this, "Please Select Standard", Toast.LENGTH_SHORT).show();
                } else if (mediumName.equalsIgnoreCase("Select Medium")) {
                    Toast.makeText(SankulActivity.this, "Please Select Medium", Toast.LENGTH_SHORT).show();
                }else {
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

                                        Log.d("nickname", "" + stdId + " " + stdName);
                                    }
                                    spinnerStdEnglish.setItems(listArrayStd, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    Log.i("adcs", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                    JSONArray objArray2 = obj.getJSONArray("mediumList");
                                    for (int i = 0; i < objArray2.length(); i++) {
                                        JSONObject jresponse = objArray2.getJSONObject(i);
                                        int mediumId = jresponse.getInt("iMedium");
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
                                        Log.d("nickname", "" + mediumId + " " + mediumName);
                                    }
                                    spinnerMedium.setItems(listArrayMedium, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                    Log.d( "checkedevalboard",listArrayBoard + "" );

                                    spinnerSchoolBoardEnglish.setItems(listArrayBoard, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                   /* ArrayAdapter medium = new ArrayAdapter(SankulActivity.this, android.R.layout.simple_spinner_item, mediumArr);
                                    medium.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerMedium.setAdapter(medium);

                                    ArrayAdapter std = new ArrayAdapter(SankulActivity.this, android.R.layout.simple_spinner_item, spotArr);
                                    std.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerStd.setAdapter(std);


                                    ArrayAdapter board = new ArrayAdapter(SankulActivity.this, android.R.layout.simple_spinner_item, boardArr);
                                    board.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinnerSchoolBoard.setAdapter(board);*/

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
                Calendar c1 = Calendar.getInstance();
                SimpleDateFormat df1 = new SimpleDateFormat("dd/M/yyyy");
                String formattedDate1 = df1.format(c1.getTime());

                object.put("OrganisationName", edtSankulOrganization.getText().toString());
                object.put("PartyName", edtSankulPartyName.getText().toString());
                object.put("Designation", edtSankulDesignation.getText().toString());
                object.put("SankulName", edtSankulName.getText().toString());
                object.put( "reminderDate",formattedDate1 );

//                object.put("ShopName", "");
//                object.put("DistubitorName", "Kush");
//                object.put("DistubitorType", "Sankul");
                object.put("CreatedBy",Userid);
                object.put("UpdatedBy",Userid);
                object.put("ContactNo", edtSankulContactNumber.getText().toString());
                object.put("Board", boardId);
                object.put("Medium", mediumId);
                object.put("Std", stdId);
                object.put("AvgStudent", edtSankulAverageStudentEnglish.getText().toString());
                object.put("iPartyTypeId", fromId);
                object.put("AddressLine1", edtSankulAddress1.getText().toString());
                object.put("AddressLine2", edtSankulAddress2.getText().toString());
                object.put("CityName", edtSankulCity.getText().toString());
                object.put("StateName", edtSankulState.getText().toString());
                object.put("Remark", edtSankulRemark.getText().toString());
                object.put("Latitude", latitude);
                object.put("Longitude", longitude);
                object.put("Location", location);
                Log.d("passingdara",object+"");
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SankulActivity.this, SalesMan.class));
        finish();
    }
}
