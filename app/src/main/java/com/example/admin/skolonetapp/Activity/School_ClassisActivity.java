package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
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

public class School_ClassisActivity extends AppCompatActivity {

    EditText edtSchoolOrganization, edtSchoolPartyName, edtSchoolDesignation, edtSchoolContactNumber,
            edtSchoolAddress1, edtSchoolAddress2, edtSchoolCity, edtSchoolState, edtSchoolRemark;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    TextView txtFormName;
    Button btnSave,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school__classis);
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
        txtFormName=(TextView)findViewById(R.id.txtForm);
        btnSave=(Button)findViewById(R.id.btnYes);
        btnCancel=(Button)findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSchoolOrganization.getText().toString().equals("")){
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                }else if (edtSchoolPartyName.getText().toString().equals("")){
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Party Name", Toast.LENGTH_SHORT).show();
                }else if (edtSchoolDesignation.getText().toString().equals("")){
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Designation", Toast.LENGTH_SHORT).show();
                }else if (edtSchoolContactNumber.getText().toString().equals("")){
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                }else if (edtSchoolAddress1.getText().toString().equals("")){
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                }else if (edtSchoolAddress2.getText().toString().equals("")){
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                }else if (edtSchoolCity.getText().toString().equals("")){
                    Toast.makeText(School_ClassisActivity.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                }else if (edtSchoolState.getText().toString().equals("")){
                    Toast.makeText(School_ClassisActivity.this, "Please Enter State", Toast.LENGTH_SHORT).show();
                }else if (edtSchoolRemark.getText().toString().equals("")){
                    Toast.makeText(School_ClassisActivity.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
                }else {
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
                object.put("OrganisationName", edtSchoolOrganization.getText().toString());
                object.put("PartyName", edtSchoolPartyName.getText().toString());
                object.put("Designation", edtSchoolDesignation.getText().toString());
//                object.put("SankulName", "");
//                object.put("ShopName", "");
//                object.put("DistubitorName", "");
//                object.put("DistubitorType", "");
                object.put("ContactNo", edtSchoolContactNumber.getText().toString());
                object.put("Board", "CBSC");
                object.put("Medium", "English");
                object.put("Std", "12");
                object.put("AvgStudent", "100");
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
