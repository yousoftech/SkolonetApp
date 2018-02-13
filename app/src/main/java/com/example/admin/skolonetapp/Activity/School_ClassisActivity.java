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

public class School_ClassisActivity extends AppCompatActivity {

    EditText edtSchoolOrganization, edtSchoolPartyName, edtSchoolDesignation, edtSchoolContactNumber,
            edtSankulAverageStudentEnglish,edtSankulAverageStudentHindi,edtSankulAverageStudentGujarati, edtSchoolAddress1, edtSchoolAddress2, edtSchoolCity, edtSchoolState, edtSchoolRemark;
    MultiSpinnerSearch spinnerStdEnglish, spinnerStdHindi,spinnerStdGujarati,  spinnerMedium;
    SingleSpinner spinnerSchoolBoardEnglish, spinnerSchoolBoardHindi,spinnerSchoolBoardGujarati;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    TextView txtFormName;
    Button btnSave, btnCancel;

    final List<KeyPairBoolData> listArrayStdEnglish = new ArrayList<>();
    final List<KeyPairBoolData> listArrayStdGujarati = new ArrayList<>();
    final List<KeyPairBoolData> listArrayStdHindi = new ArrayList<>();


    final List<KeyPairBoolData> listArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoardEnglish = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoardHindi = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoardGujarti = new ArrayList<>();

    final List<KeyPairBoolData> selectedlistArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayBoard = new ArrayList<>();



    String stdName="", mediumName="", boardName="";
    String selectedMediumName="", selectedBoardNameEnglish="",selectedStdNameEnglish="",selectedBoardNameHindi="",selectedStdNameHindi="",selectedBoardNameGujarati="",selectedStdNameGujarati="" ;

    int stdId, mediumId, boardId;
    int fromId;
    String fromName, Userid, latitude, longitude, location;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school__classis);
        Intent intent = getIntent();
        fromId = intent.getIntExtra("fromId", 0);
        fromName = intent.getStringExtra("fromName");
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        detector = new ConnectionDetector(this);
        edtSchoolOrganization = (EditText) findViewById(R.id.edtSchoolOrganization);
        edtSchoolPartyName = (EditText) findViewById(R.id.edtSchoolPartyName);
        edtSchoolDesignation = (EditText) findViewById(R.id.edtSchoolDesignation);
        edtSchoolContactNumber = (EditText) findViewById(R.id.edtSchoolContactNumber);
        edtSchoolAddress1 = (EditText) findViewById(R.id.edtSchoolAddress1);
        edtSchoolAddress2 = (EditText) findViewById(R.id.edtSchoolAddress2);
        edtSchoolCity = (EditText) findViewById(R.id.edtSchoolCity);
        edtSchoolState = (EditText) findViewById(R.id.edtSchoolState);
        edtSchoolRemark = (EditText) findViewById(R.id.edtSchoolRemark);

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



        txtFormName = (TextView) findViewById(R.id.txtForm);
        btnSave = (Button) findViewById(R.id.btnYes);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        Userid = preferences.getString("LoggedUser", null);
        latitude = preferences.getString("latitude", null);
        longitude = preferences.getString("longitude", null);
        location = preferences.getString("location", null);
        std();


        Log.d("lat", latitude + "");
        Log.d("lon", longitude + "");
        Log.d("loc", location + "");

        txtFormName.setText("" + fromName);






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
                } else if (edtSankulAverageStudentEnglish.getText().toString().equals("")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Enter AverageStudent", Toast.LENGTH_SHORT).show();
                }else if (boardName.equalsIgnoreCase("Select Board")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Select Board", Toast.LENGTH_SHORT).show();
                } else if (stdName.equalsIgnoreCase("Select Standard")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Select Standard", Toast.LENGTH_SHORT).show();
                } else if (mediumName.equalsIgnoreCase("Select Medium")) {
                    Toast.makeText(School_ClassisActivity.this, "Please Select Medium", Toast.LENGTH_SHORT).show();
                } else {
                    submitForm();
                    startActivity(new Intent(School_ClassisActivity.this, HomeScreen.class));
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(School_ClassisActivity.this, HomeScreen.class));
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
                                        stddata.setId(stdId);
                                        stddata.setName(stdName);
                                        stddata.setSelected(false );
                                        Log.d("ArrayStd",stddata + "");

                                        listArrayStdEnglish.add(stddata);

                                        Log.d("nickname", "" + stdId + " " + stddata.getName());
                                    }
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject(i);
                                        int stdId = jresponse.getInt("iStandardId");
                                        String stdName = jresponse.getString("strStandardName");
                                        KeyPairBoolData stddata = new KeyPairBoolData();
                                        stddata.setId(stdId);
                                        stddata.setName(stdName);
                                        stddata.setSelected(false );
                                        Log.d("ArrayStd",stddata + "");

                                        listArrayStdGujarati.add(stddata);
                                    }
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject(i);
                                        int stdId = jresponse.getInt("iStandardId");
                                        String stdName = jresponse.getString("strStandardName");
                                        KeyPairBoolData stddata = new KeyPairBoolData();
                                        stddata.setId(stdId);
                                        stddata.setName(stdName);
                                        stddata.setSelected(false );
                                        Log.d("ArrayStd",stddata + "");

                                        listArrayStdHindi.add(stddata);
                                    }

                                    Log.d("nicknameaas", "" + listArrayStdEnglish);

                                    spinnerStdEnglish.setItems(listArrayStdEnglish, -1, new SpinnerListener() {

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
                                    spinnerStdHindi.setItems(listArrayStdHindi, -1, new SpinnerListener() {

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
                                    }); spinnerStdGujarati.setItems(listArrayStdGujarati, -1, new SpinnerListener() {

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
                                        listArrayBoardEnglish.add(boarddata);



                                    }
                                    for (int i = 0; i < objArray3.length(); i++) {
                                        JSONObject jresponse = objArray3.getJSONObject(i);
                                        int boardId = jresponse.getInt("iBoardId");
                                        String boardName = jresponse.getString("strBoardName");
                                        KeyPairBoolData boarddata = new KeyPairBoolData();
                                        boarddata.setId(boardId);
                                        boarddata.setName(boardName);
                                        boarddata.setSelected( false );
                                        listArrayBoardGujarti.add(boarddata);
                                    }
                                    for (int i = 0; i < objArray3.length(); i++) {
                                        JSONObject jresponse = objArray3.getJSONObject(i);
                                        int boardId = jresponse.getInt("iBoardId");
                                        String boardName = jresponse.getString("strBoardName");
                                        KeyPairBoolData boarddata = new KeyPairBoolData();
                                        boarddata.setId(boardId);
                                        boarddata.setName(boardName);
                                        boarddata.setSelected( false );
                                        listArrayBoardHindi.add(boarddata);
                                    }



                                    Log.d( "checkedevalmedium",listArrayMedium + "" );

                                    spinnerMedium.setItems(listArrayMedium, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()==true) {
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
                                                if (items.get(i).isSelected() == false) {
                                                    if(items.get(i).getName().equals("English"))
                                                    {
                                                        spinnerStdEnglish.setVisibility(View.GONE);

                                                        spinnerSchoolBoardEnglish.setVisibility(View.GONE);
                                                        edtSankulAverageStudentEnglish.setVisibility( View.GONE );
                                                    }
                                                    if(items.get(i).getName().equals("Hindi"))
                                                    {
                                                        spinnerStdHindi.setVisibility(View.GONE);

                                                        spinnerSchoolBoardHindi.setVisibility(View.GONE);
                                                        edtSankulAverageStudentHindi.setVisibility( View.GONE );

                                                    }
                                                    if(items.get(i).getName().equals("Gujarati"))
                                                    {
                                                        spinnerStdGujarati.setVisibility(View.GONE);

                                                        spinnerSchoolBoardGujarati.setVisibility(View.GONE);
                                                        edtSankulAverageStudentGujarati.setVisibility( View.GONE );
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    Log.d( "checkedevalboard",listArrayBoardEnglish + "" );

                                    spinnerSchoolBoardEnglish.setItems(listArrayBoardEnglish, -1, new SpinnerListener() {

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

                                    spinnerSchoolBoardHindi.setItems(listArrayBoardHindi, -1, new SpinnerListener() {

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
                                    spinnerSchoolBoardGujarati.setItems(listArrayBoardGujarti, -1, new SpinnerListener() {

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
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat df1 = new SimpleDateFormat("dd/M/yyyy");
            String formattedDate1 = df1.format(c1.getTime());
            final JSONObject object = new JSONObject();
            try {
                object.put("OrganisationName", edtSchoolOrganization.getText().toString());
                object.put("PartyName", edtSchoolPartyName.getText().toString());
                object.put("Designation", edtSchoolDesignation.getText().toString());
                object.put( "reminderDate",formattedDate1 );

//
                object.put("CreatedBy", Userid);
                object.put("UpdatedBy", Userid);

Log.d( "boardName",selectedBoardNameEnglish );
                Log.d( "mediumName",selectedMediumName );
                Log.d( "stdName",selectedStdNameEnglish );
                object.put("strMedium", selectedMediumName);
                object.put("strBoardEnglish", selectedBoardNameEnglish);
                object.put("strStandardEnglish", selectedStdNameEnglish);
                object.put("strBoardHindi", selectedBoardNameHindi);
                object.put("strStandardHindi", selectedStdNameHindi);
                object.put("strBoardGujarati", selectedBoardNameGujarati);
                object.put("strStandardGujarati", selectedStdNameGujarati);
                object.put("AvgStudentEnglish", edtSankulAverageStudentEnglish.getText().toString());
                object.put("AvgStudentHindi", edtSankulAverageStudentHindi.getText().toString());
                object.put("AvgStudentGujarati", edtSankulAverageStudentGujarati.getText().toString());
                object.put("iPartyTypeId", fromId);
                object.put("AddressLine1", edtSchoolAddress1.getText().toString());
                object.put("AddressLine2", edtSchoolAddress2.getText().toString());
                object.put("CityName", edtSchoolCity.getText().toString());
                object.put("StateName", edtSchoolState.getText().toString());
                object.put("Remark", edtSchoolRemark.getText().toString());
                object.put("Latitude", latitude);
                object.put("Longitude", longitude);
                object.put("Location", location);
                Log.d( "finalobj",object +"");
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
                                    Toast.makeText(School_ClassisActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish();

                                } else if (code == false) {
                                    String msg1 = response.getString("message");
                                    progressDialog.dismiss();
                                    finish();

                                    Toast.makeText(School_ClassisActivity.this, "" + msg1, Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                finish();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(School_ClassisActivity.this, HomeScreen.class));
        finish();
    }

}
