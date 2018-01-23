package com.example.admin.skolonetapp.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.admin.skolonetapp.Adapter.adapterSales;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.Pojo.SalesList;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;
import com.example.admin.skolonetapp.Util.LocationAddress;
import com.example.admin.skolonetapp.Util.LocationResult;
import com.example.admin.skolonetapp.Util.MyLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;

public class SalesMan extends AppCompatActivity implements LocationResult {

    FloatingActionButton fabButton;
    Spinner spinner;
    TextView txtTitle;
    Toolbar toolbar;
    Button btnLogout,btnFilter;
    String from, addtLocation, latlong;
    String firstName, lastName, Userid;
    int salesId;
    SalesList salesList;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    ArrayList<SalesList> arraySales;
    ArrayList<String> salesArr;
    Sales sales;
    RecyclerView recyclerView;
    ArrayList<Sales> event;
    adapterSales aSales;
    private MyLocation myLocation = null;
    boolean doubleBackToExitPressedOnce = false;
    TextView txtRecords;



    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final int INITIAL_REQUEST = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_man);
        detector = new ConnectionDetector(this);
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnFilter= (Button)findViewById(R.id.btnFilter);
        firstName = preferences.getString("firstName", null);
        lastName = preferences.getString("lastName", null);
        Userid = preferences.getString("LoggedUser", null);
        setupToolbar("" + firstName + " " + lastName);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerSales);
        txtRecords = (TextView) findViewById(R.id.txtNoRecords);
        btnFilter = (Button)findViewById(R.id.btnFilter);
        event = new ArrayList<Sales>();
        myLocation = new MyLocation();

        fabButton = (FloatingActionButton) findViewById(R.id.fabButton);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canAccessLocation() || !canAccessCoreLocation()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
                    }

                } else {
                    boolean networkPresent = myLocation.getLocation(SalesMan.this, SalesMan.this);
                    if (!networkPresent) {
                        showSettingsAlert();
                    }
                }

                std();
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SalesMan.this);
                final LayoutInflater inflater = (LayoutInflater) SalesMan.this.getSystemService(SalesMan.this.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.app_choose, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog a = dialogBuilder.create();
                spinner = (Spinner) dialogView.findViewById(R.id.spinnerForm);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        from = spinner.getSelectedItem().toString();
                        salesList = arraySales.get(i);
                        salesId = salesList.getSalesId();

                        if (from.equalsIgnoreCase("School")) {
                            Intent intent = new Intent(SalesMan.this, School_ClassisActivity.class);
                            intent.putExtra("fromName", from);
                            intent.putExtra("fromId", salesId);
                            startActivity(intent);
                            finish();
                        } else if (from.equalsIgnoreCase("Classes")) {
                            Intent intent = new Intent(SalesMan.this, School_ClassisActivity.class);
                            intent.putExtra("fromName", from);
                            intent.putExtra("fromId", salesId);
                            startActivity(intent);
                            finish();
                        } else if (from.equalsIgnoreCase("Sankul")) {
                            Intent intent = new Intent(SalesMan.this, SankulActivity.class);
                            intent.putExtra("fromName", from);
                            intent.putExtra("fromId", salesId);
                            startActivity(intent);
                            finish();
                        } else if (from.equalsIgnoreCase("Party")) {
                            Intent intent = new Intent(SalesMan.this, PartyActivity.class);
                            intent.putExtra("fromName", from);
                            intent.putExtra("fromId", salesId);
                            startActivity(intent);
                            finish();
                        } else if (from.equalsIgnoreCase("Others")) {
                            Intent intent = new Intent(SalesMan.this, OtherActivity.class);
                            intent.putExtra("fromName", from);
                            intent.putExtra("fromId", salesId);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                a.show();
            }
        });
        detailFrom();
    }

    private void setupToolbar(String title) {
        setSupportActionBar(toolbar);
        txtTitle.setText(title);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SalesMan.this);
                LayoutInflater inflater = SalesMan.this.getLayoutInflater();
                dialogBuilder.setMessage("Are you sure you want to logout ?");

                dialogBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("logged");
                        editor.clear();
                        editor.commit();
                        finish();
                        startActivity(new Intent(SalesMan.this, LoginActivity.class));
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog a = dialogBuilder.create();
                a.show();
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SalesMan.this, "Filter", Toast.LENGTH_SHORT).show();
            }
        });
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
    }

    public void std() {

        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);


            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "User/GetPartyType", null,
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
                                    arraySales = new ArrayList<>();
                                    salesArr = new ArrayList<>();

                                    progressDialog.dismiss();
                                    JSONArray objArray1 = response.getJSONArray("data");
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject(i);
                                        int stdId = jresponse.getInt("iPartyTypeId");
                                        String stdName = jresponse.getString("strPartyType");
                                        salesList = new SalesList();
                                        if (i == 0) {
                                            salesList.setSalesId(0);
                                            salesList.setSalesName("Select Type");
                                            salesArr.add(salesList.getSalesName());
                                            arraySales.add(salesList);
                                        }
                                        salesList.setSalesId(stdId);
                                        salesList.setSalesName(stdName);
                                        salesArr.add(salesList.getSalesName());
                                        arraySales.add(salesList);


                                        progressDialog.dismiss();
                                        Log.d("nickname", "" + stdId + " " + stdName);
                                    }

                                    ArrayAdapter board = new ArrayAdapter(SalesMan.this, android.R.layout.simple_spinner_item, salesArr);
                                    board.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    spinner.setAdapter(board);

//                                    ArrayAdapter board = new ArrayAdapter(SalesMan.this, android.R.layout.simple_spinner_item, salesArr);
//                                    board.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    //Setting the ArrayAdapter data on the Spinner
//                                    spinner.setAdapter(board);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(SalesMan.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(SalesMan.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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

    public void detailFrom() {
        if (detector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/GetSalesData?id=" + Userid, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("yatra", response.toString());
                    try {
                        boolean code = response.getBoolean("status");
                        if (code == true) {
                            progressDialog.dismiss();
                            JSONArray array = response.getJSONArray("data");
                            Log.d("yatra", array.toString());
                            for (int n = 0; n < array.length(); n++) {
                                JSONObject obj = array.getJSONObject(n);
                                sales = new Sales();

                                String partyInfoId = obj.getString("partyInfoId");
                                String iPartyTypeName = obj.getString("strPartyType");
                                String shopName = obj.getString("shopName");
                                String partyName = obj.getString("partyName");


                                String location = obj.getString("location");
                                String partyDate = obj.getString("datetimeCreated");
                                String latitude = obj.getString("latitude");
                                String longitude = obj.getString("longitude");

                                sales.setPartyInfoId(partyInfoId);
                                sales.setStrPartyType(iPartyTypeName);
                                sales.setLocation("Address : " + location);
                                sales.setDatetimeCreated(partyDate);
                                sales.setStrLatitude("Latitude : " + latitude);
                                sales.setStrLongitude("Longitude : " + longitude);
                                if(partyName!="null")
                                {
                                    sales.setPartyName(partyName);


                                }
                                else
                                {
                                    sales.setShopName(shopName);

                                }
                                event.add(sales);


                                int totalElements = event.size();
                                if (totalElements > 0) {
                                    txtRecords.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    aSales = new adapterSales(SalesMan.this, event);
                                    recyclerView.setAdapter(aSales);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(SalesMan.this, LinearLayoutManager.VERTICAL, false));
                                    aSales.notifyDataSetChanged();
                                }
                                progressDialog.dismiss();
                            }
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
            RequestQueue requestQueue = Volley.newRequestQueue(SalesMan.this);
            requestQueue.add(objectRequest);
        } else {
            Toast.makeText(SalesMan.this, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean canAccessCoreLocation() {
        return (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    private boolean hasPermission(String perm) {

        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(SalesMan.this, perm));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case INITIAL_REQUEST:
                if (canAccessLocation() && canAccessCoreLocation()) {
                    boolean networkPresent = myLocation.getLocation(SalesMan.this, this);
                    if (!networkPresent) {
                        showSettingsAlert();
                    }
                }
                break;
        }

    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                SalesMan.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        SalesMan.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void gotLocation(Location location) {
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();
        latlong = "Latitude: " + location.getLatitude() +
                " Longitude: " + location.getLongitude();
        Log.d("latlongggg", latlong);

        preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        String lat = location.getLatitude() + "";
        String lon = location.getLongitude() + "";
        editor.putString("latitude", lat);
        editor.putString("longitude", lon);
        editor.commit();
        Log.d("latlong", latlong + "");
        SalesMan.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,
                        getApplicationContext(), new GeocoderHandler());
            }
        });

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            addtLocation = locationAddress;
            Log.d("location", addtLocation);

            preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("location", locationAddress);
            editor.commit();

            // tvAddress.setText(locationAddress);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
