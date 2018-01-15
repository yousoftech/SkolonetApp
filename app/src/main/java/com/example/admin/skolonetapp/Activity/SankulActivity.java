package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SankulActivity extends AppCompatActivity {

    EditText edtSankulName,edtSankulOrganization, edtSankulPartyName, edtSankulDesignation, edtSankulContactNumber,
            edtSankulAddress1, edtSankulAddress2, edtSankulCity, edtSankulState, edtSankulRemark;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    TextView txtFormName;
    Button btnSave,btnCancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sankul);
        detector = new ConnectionDetector(this);
        edtSankulName=(EditText)findViewById(R.id.edtSankulName);
        edtSankulOrganization = (EditText) findViewById(R.id.edtSankulOrganization);
        edtSankulPartyName = (EditText) findViewById(R.id.edtSankulPartyName);
        edtSankulDesignation = (EditText) findViewById(R.id.edtSankulDesignation);
        edtSankulContactNumber = (EditText) findViewById(R.id.edtSankulContactNumber);
        edtSankulAddress1 = (EditText) findViewById(R.id.edtSankulAddress1);
        edtSankulAddress2 = (EditText) findViewById(R.id.edtSankulAddress2);
        edtSankulCity = (EditText) findViewById(R.id.edtSankulCity);
        edtSankulState = (EditText) findViewById(R.id.edtSankulState);
        edtSankulRemark = (EditText) findViewById(R.id.edtSankulRemark);
        txtFormName=(TextView)findViewById(R.id.txtSankulForm);
        btnSave=(Button)findViewById(R.id.btnSankulYes);
        btnCancel=(Button)findViewById(R.id.btnSankulCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSankulName.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                }else if (edtSankulOrganization.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                }else if (edtSankulPartyName.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter Party Name", Toast.LENGTH_SHORT).show();
                }else if (edtSankulDesignation.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter Designation", Toast.LENGTH_SHORT).show();
                }else if (edtSankulContactNumber.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                }else if (edtSankulAddress1.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                }else if (edtSankulAddress2.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                }else if (edtSankulCity.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                }else if (edtSankulState.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter State", Toast.LENGTH_SHORT).show();
                }else if (edtSankulRemark.getText().toString().equals("")){
                    Toast.makeText(SankulActivity.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
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
                object.put("OrganisationName", edtSankulOrganization.getText().toString());
                object.put("PartyName", edtSankulPartyName.getText().toString());
                object.put("Designation", edtSankulDesignation.getText().toString());
                object.put("SankulName", edtSankulName.getText().toString());
//                object.put("ShopName", "");
//                object.put("DistubitorName", "Kush");
//                object.put("DistubitorType", "Sankul");
                object.put("ContactNo", edtSankulContactNumber.getText().toString());
                object.put("Board", "GHSC");
                object.put("Medium", "GUJRATI");
                object.put("Std", "12");
                object.put("AvgStudent", "100");
                object.put("AddressLine1", edtSankulAddress1.getText().toString());
                object.put("AddressLine2", edtSankulAddress2.getText().toString());
                object.put("CityName", edtSankulCity.getText().toString());
                object.put("StateName", edtSankulState.getText().toString());
                object.put("Remark", edtSankulRemark.getText().toString());
                object.put("Location", "Nae j male koe day");
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
