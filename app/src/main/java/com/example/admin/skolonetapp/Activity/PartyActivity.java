package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;

public class PartyActivity extends AppCompatActivity {

    EditText edtPartyShopName, edtPartyDistributorName, edtPartyType, edtPartyDesignation, edtPartyContactNumber,
            edtPartyAddress1, edtPartyAddress2, edtPartyCity, edtPartyState, edtPartyRemark;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    TextView txtFormName;
    Button btnSave, btnCancel;
    int fromId;
    String fromName,Userid,latitude,longitude,location;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        detector = new ConnectionDetector(this);
        Intent intent = getIntent();
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Userid = preferences.getString("LoggedUser", null);
        fromId = intent.getIntExtra("fromId", 0);
        fromName = intent.getStringExtra("fromName");
        edtPartyShopName = (EditText) findViewById(R.id.edtPartyShopName);
        edtPartyDistributorName = (EditText) findViewById(R.id.edtPartyDistributorName);
        edtPartyType = (EditText) findViewById(R.id.edtPartyType);
        edtPartyDesignation = (EditText) findViewById(R.id.edtPartyDesignation);
        edtPartyContactNumber = (EditText) findViewById(R.id.edtPartyContactNumber);
        edtPartyAddress1 = (EditText) findViewById(R.id.edtPartyAddress1);
        edtPartyAddress2 = (EditText) findViewById(R.id.edtPartyAddress2);
        edtPartyCity = (EditText) findViewById(R.id.edtPartyCity);
        edtPartyState = (EditText) findViewById(R.id.edtPartyState);
        edtPartyRemark = (EditText) findViewById(R.id.edtPartyRemark);
        txtFormName = (TextView) findViewById(R.id.txtPartyForm);
        btnSave = (Button) findViewById(R.id.btnPartyYes);
        btnCancel = (Button) findViewById(R.id.btnPartyCancel);
        latitude=preferences.getString("latitude",null);
        longitude=preferences.getString("longitude",null);
        location=preferences.getString("location",null);
        txtFormName.setText("" + fromName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtPartyShopName.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter Shop Name", Toast.LENGTH_SHORT).show();
                } else if (edtPartyDistributorName.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter Distributor Name", Toast.LENGTH_SHORT).show();
                } else if (edtPartyType.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter Party Type", Toast.LENGTH_SHORT).show();
                } else if (edtPartyDesignation.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter Party Designation", Toast.LENGTH_SHORT).show();
                } else if (edtPartyContactNumber.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter Party Contact Number", Toast.LENGTH_SHORT).show();
                } else if (edtPartyAddress1.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (edtPartyAddress2.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (edtPartyCity.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                } else if (edtPartyState.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter State", Toast.LENGTH_SHORT).show();
                } else if (edtPartyRemark.getText().toString().equals("")) {
                    Toast.makeText(PartyActivity.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
                } else {
                    submitForm();
                    startActivity(new Intent(PartyActivity.this, SalesMan.class));
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(PartyActivity.this, SalesMan.class));
            }
        });
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
//                object.put("OrganisationName", edtSchoolOrganization.getText().toString());
//                object.put("PartyName", edtSchoolPartyName.getText().toString());
                object.put("Designation", edtPartyDesignation.getText().toString());
//                object.put("SankulName", "");
                object.put("ShopName", edtPartyShopName.getText().toString());
                object.put("DistubitorName", edtPartyDistributorName);
                object.put("DistubitorType", edtPartyType);
                object.put("ContactNo", edtPartyContactNumber.getText().toString());
//                object.put("Board", "");
//                object.put("Medium", "");
//                object.put("Std", "");
//                object.put("AvgStudent", "");
                object.put("CreatedBy",Userid);
                object.put("UpdatedBy",Userid);
                object.put("iPartyTypeId",fromId);
                object.put("AddressLine1", edtPartyAddress1.getText().toString());
                object.put("AddressLine2", edtPartyAddress2.getText().toString());
                object.put("CityName", edtPartyCity.getText().toString());
                object.put("StateName", edtPartyState.getText().toString());
                object.put("Remark", edtPartyRemark.getText().toString());
                object.put("Latitude", latitude);
                object.put("Longitude", longitude);
                object.put("Location", location);            } catch (JSONException e) {
                Toast.makeText(PartyActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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
                                   // JSONObject obj = response.getJSONObject("data");
                                    Toast.makeText(PartyActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish();
                                } else if (code == false) {
                                    String msg1 = response.getString("message");
                                    progressDialog.dismiss();
                                    Toast.makeText(PartyActivity.this, "" + msg1, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(PartyActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(PartyActivity.this, SalesMan.class));
        finish();
    }
}
