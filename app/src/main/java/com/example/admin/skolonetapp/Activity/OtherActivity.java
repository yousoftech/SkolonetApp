package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class OtherActivity extends AppCompatActivity {

    EditText edtOtherOrganization, edtOtherPartyName, edtOtherDesignation, edtOtherContactNumber,
            edtOtherAddress1, edtOtherAddress2, edtOtherCity, edtOtherState, edtOtherRemark;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    TextView txtFormName;
    Button btnSave, btnCancel;
    int fromId;
    String fromName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Intent intent = getIntent();
        fromId = intent.getIntExtra("fromId", 0);
        fromName = intent.getStringExtra("fromName");
        detector = new ConnectionDetector(this);
        edtOtherOrganization = (EditText) findViewById(R.id.edtOtherOrganization);
        edtOtherPartyName = (EditText) findViewById(R.id.edtOtherPartyName);
        edtOtherDesignation = (EditText) findViewById(R.id.edtOtherDesignation);
        edtOtherContactNumber = (EditText) findViewById(R.id.edtOtherContactNumber);
        edtOtherAddress1 = (EditText) findViewById(R.id.edtOtherAddress1);
        edtOtherAddress2 = (EditText) findViewById(R.id.edtOtherAddress2);
        edtOtherCity = (EditText) findViewById(R.id.edtOtherCity);
        edtOtherState = (EditText) findViewById(R.id.edtOtherState);
        edtOtherRemark = (EditText) findViewById(R.id.edtOtherRemark);
        txtFormName = (TextView) findViewById(R.id.txtOtherForm);
        btnSave = (Button) findViewById(R.id.btnOtherYes);
        btnCancel = (Button) findViewById(R.id.btnOtherCancel);
        txtFormName.setText("" + fromName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtOtherOrganization.getText().toString().equals("")) {
                    Toast.makeText(OtherActivity.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                } else if (edtOtherPartyName.getText().toString().equals("")) {
                    Toast.makeText(OtherActivity.this, "Please Enter Party Name", Toast.LENGTH_SHORT).show();
                } else if (edtOtherDesignation.getText().toString().equals("")) {
                    Toast.makeText(OtherActivity.this, "Please Enter Designation", Toast.LENGTH_SHORT).show();
                } else if (edtOtherContactNumber.getText().toString().equals("")) {
                    Toast.makeText(OtherActivity.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                } else if (edtOtherAddress1.getText().toString().equals("")) {
                    Toast.makeText(OtherActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (edtOtherAddress2.getText().toString().equals("")) {
                    Toast.makeText(OtherActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (edtOtherCity.getText().toString().equals("")) {
                    Toast.makeText(OtherActivity.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                } else if (edtOtherState.getText().toString().equals("")) {
                    Toast.makeText(OtherActivity.this, "Please Enter State", Toast.LENGTH_SHORT).show();
                } else if (edtOtherRemark.getText().toString().equals("")) {
                    Toast.makeText(OtherActivity.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
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
                object.put("OrganisationName", edtOtherOrganization.getText().toString());
                object.put("PartyName", edtOtherPartyName.getText().toString());
                object.put("Designation", edtOtherDesignation.getText().toString());
//                object.put("SankulName", "");
//                object.put("ShopName", "");
//                object.put("DistubitorName", "");
//                object.put("DistubitorType", "Other");
                object.put("ContactNo", edtOtherContactNumber.getText().toString());
//                object.put("Board", "");
//                object.put("Medium", "");
//                object.put("Std", "");
//                object.put("AvgStudent", "");
                object.put("iPartyTypeId",fromId);
                object.put("AddressLine1", edtOtherAddress1.getText().toString());
                object.put("AddressLine2", edtOtherAddress2.getText().toString());
                object.put("CityName", edtOtherCity.getText().toString());
                object.put("StateName", edtOtherState.getText().toString());
                object.put("Remark", edtOtherRemark.getText().toString());
                object.put("Location", "sorry nathi malata");
            } catch (JSONException e) {
                Toast.makeText(OtherActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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
//                                    JSONObject obj = response.getJSONObject("data");
                                    progressDialog.dismiss();
                                    finish();
                                } else if (code == false) {
                                    String msg1 = response.getString("message");
                                    progressDialog.dismiss();
                                    Toast.makeText(OtherActivity.this, "" + msg1, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(OtherActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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
