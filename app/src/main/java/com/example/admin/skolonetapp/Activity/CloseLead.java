package com.example.admin.skolonetapp.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.NotificationManager;
import android.app.Notification;
import android.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.example.admin.skolonetapp.Adapter.adapterSales;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.Pojo.SalesList;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;
import com.example.admin.skolonetapp.Util.LocationAddress;
import com.example.admin.skolonetapp.Util.LocationResult;
import com.example.admin.skolonetapp.Util.MyLocation;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import  android.net.Uri;
import android.media.RingtoneManager;

import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;

public class CloseLead extends AppCompatActivity{

    FloatingActionButton fabButton;
    Spinner spinner;
    TextView txtTitle;
    Toolbar toolbar;
    Button btnLogout;
    String from, addtLocation, latlong;
    String firstName, lastName, Userid;
    int salesId;
    SalesList salesList;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    ArrayList<SalesList> arraySales;
    ArrayList<String> salesArr;
    ArrayList<SalesList> arrayFilter;
    ArrayList<String> filterArr;
    Sales sales;
    RecyclerView recyclerView;
    ArrayList<Sales> event;
    adapterSales aSales;
    boolean doubleBackToExitPressedOnce = false;
    TextView txtRecords;
    RelativeLayout relativeFilter;
    Spinner spinnerFliter;
    final List<KeyPairBoolData> listArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> listArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoard = new ArrayList<>();
    String strMedium1 =null;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    private static final int INITIAL_REQUEST = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sales_man );

        detector = new ConnectionDetector( this );


        preferences = getSharedPreferences( PREFS_NAME, MODE_PRIVATE );
        txtTitle = (TextView) findViewById( R.id.txtTitle );
        btnLogout = (Button) findViewById( R.id.btnLogout );
        relativeFilter = (RelativeLayout) findViewById( R.id.RelativeFilter );
        spinnerFliter = (Spinner) findViewById( R.id.spinnerFilter );
        firstName = preferences.getString( "firstName", null );
        lastName = preferences.getString( "lastName", null );
        Userid = preferences.getString( "LoggedUser", null );
        setupToolbar( "" + firstName + " " + lastName );
        recyclerView = (RecyclerView) findViewById( R.id.recyclerSales );
        txtRecords = (TextView) findViewById( R.id.txtNoRecords );
        event = new ArrayList<Sales>();
        NotificationManager manager;
        Notification myNotication;
        //  recyclerView.destroyDrawingCache();
        Bundle bundle = getIntent().getExtras();

        Log.d( "asdv" ,bundle+"" );


       /*if (!bundle.getString("total").equalsIgnoreCase( "" ))
        {
           txtRecords.setVisibility( View.VISIBLE );

       }
       else
       {
           txtRecords.setVisibility( View.GONE );
       }*/
        manager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );



        spinnerFliter.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                event.clear();
                aSales.notifyDataSetChanged();
                from = spinnerFliter.getSelectedItem().toString();
                salesList = arraySales.get( i );
                salesId = salesList.getSalesId();
                Log.d( "asdassadasdasd", salesId + " " + from );


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );


        detailFrom();
    }

    private void setupToolbar(String title) {
        setSupportActionBar( toolbar );
        txtTitle.setText( title );
        btnLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( CloseLead.this );
                LayoutInflater inflater = CloseLead.this.getLayoutInflater();
                dialogBuilder.setMessage( "Are you sure you want to logout ?" );

                dialogBuilder.setPositiveButton( "Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove( "logged" );
                        editor.clear();
                        editor.commit();
                        finish();
                        startActivity( new Intent( CloseLead.this, LoginActivity.class ) );
                        Toast.makeText( getApplicationContext(), "Logout", Toast.LENGTH_SHORT ).show();
                    }
                } );
                dialogBuilder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                } );

                AlertDialog a = dialogBuilder.create();
                a.show();
            }
        } );

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle( "" );
            actionBar.setDisplayHomeAsUpEnabled( false );
            actionBar.setHomeButtonEnabled( false );
        }
    }



    public void detailFrom() {
        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog( this );
            progressDialog.setCancelable( false );
            progressDialog.setMessage( "Loading..." );
            progressDialog.show();

            JsonObjectRequest objectRequest = new JsonObjectRequest( Request.Method.POST,
                    Constant.PATH + "Sales/getsalesclosedata?id=" + Userid, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d( "yatra", response.toString() );
                    try {
                        boolean code = response.getBoolean( "status" );
                        if (code == true) {
                            progressDialog.dismiss();
                            JSONArray array = response.getJSONArray( "data" );
                            Log.d( "yatra", array.toString() );
                            if(array.length() > 0) {

                                for (int n = 0; n < array.length(); n++) {
                                    JSONObject obj = array.getJSONObject( n );
                                    sales = new Sales();

                                    String partyInfoId = obj.getString( "partyInfoId" );
                                    String iPartyTypeName = obj.getString( "strPartyType" );
                                    String shopName = obj.getString( "shopName" );
                                    String partyName = obj.getString( "partyName" );
                                    String strAddress1 = obj.getString( "addressLine1" );
                                    String strAddress2 = obj.getString( "addressLine2" );
                                    String strCityName = obj.getString( "cityName" );
                                    String strStateName = obj.getString( "stateName" );


                                    String strAddress = strAddress1 + " " + strAddress2 + " " + strCityName + " " + strStateName;
                                    String reminderDate = obj.getString( "reminderDate" );

                                    String partyDate1 = obj.getString( "datetimeCreated" );

                                    double priority = obj.getDouble( "priority" );
                                    String partyDate = convert( partyDate1 );
                                    String ContactNo = obj.getString( "contactNo" );
                                    String strDistributorName = obj.getString( "distubitorName" );
                                    String strOrganisationName = obj.getString( "organisationName" );
                                    int ipartTypeId = obj.getInt( "iPartyTypeId" );



                                    sales.setPartyInfoId( partyInfoId );
                                    sales.setStrPartyType( iPartyTypeName );
                                    sales.setLocation( "Address : " + strAddress );
                                    sales.setDatetimeCreated( partyDate.toString() );
                                    sales.setContactNo( ContactNo );

                                    //  sales.setStrLatitude( "Medium : " + strMedium );
                                    //  sales.setStrLongitude( "Longitude : " + longitude );
                                    sales.setReminderDate( reminderDate );
                                    sales.setPriority( priority );
                                    if (partyName != "null") {
                                        sales.setPartyName( partyName );
                                    } else {
                                        sales.setDistubitorName( strDistributorName );

                                    }
                                    if (strOrganisationName != "null") {
                                        sales.setOrganisationName( strOrganisationName );
                                    } else {
                                        sales.setShopName( shopName );

                                    }
                                        event.add( sales );



                                    int totalElements = event.size();
                                    if (totalElements > 0) {
                                        txtRecords.setVisibility( View.GONE );
                                        recyclerView.setVisibility( View.VISIBLE );
                                        aSales = new adapterSales( CloseLead.this, event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( CloseLead.this, LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();

                                    }
                                    else{
                                        txtRecords.setVisibility( View.VISIBLE );
                                        recyclerView.setVisibility( View.GONE );
                                        aSales = new adapterSales( CloseLead.this, event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( CloseLead.this, LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();
                                    }
                                    progressDialog.dismiss();

                                }

                            }
                            else
                            {
                                txtRecords.setVisibility( View.VISIBLE );
                                recyclerView.setVisibility( View.GONE );
                                aSales = new adapterSales( CloseLead.this, event );
                                recyclerView.setAdapter( aSales );
                                recyclerView.setLayoutManager( new LinearLayoutManager( CloseLead.this, LinearLayoutManager.VERTICAL, false ) );
                                aSales.notifyDataSetChanged();
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
            } );
            objectRequest.setRetryPolicy( new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
            RequestQueue requestQueue = Volley.newRequestQueue( CloseLead.this );
            requestQueue.add( objectRequest );
        } else {
            Toast.makeText( CloseLead.this, "Please check your internet connection before verification..!", Toast.LENGTH_LONG ).show();
        }
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                CloseLead.this );
        alertDialog.setTitle( "SETTINGS" );
        alertDialog.setMessage( "Enable Location Provider! Go to settings menu?" );
        alertDialog.setPositiveButton( "Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        CloseLead.this.startActivity( intent );
                    }
                } );
        alertDialog.setNegativeButton( "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );
        alertDialog.show();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText( this, "Please click BACK again to exit", Toast.LENGTH_SHORT ).show();

        new Handler().postDelayed( new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000 );

        //finish();

    }




    public String convert(String s){
        SimpleDateFormat newformat = new SimpleDateFormat("dd-MM-yyyy");
        try{
            if(s.contains("T")){
                String datestring = s.split("T")[0];
                SimpleDateFormat oldformat = new SimpleDateFormat("yyyy-MM-dd");
                String reformattedStr = newformat.format(oldformat.parse(datestring));
                return reformattedStr;
            }
            else{
                if(Integer.parseInt(s.split("-")[0])>13){
                    SimpleDateFormat oldformat = new SimpleDateFormat("yyyy-MM-dd");
                    String reformattedStr = newformat.format(oldformat.parse(s));
                    return reformattedStr;
                }
                else{
                    SimpleDateFormat oldformat = new SimpleDateFormat("MM-dd-yyyy");
                    String reformattedStr = newformat.format(oldformat.parse(s));
                    return reformattedStr;
                }

            }
        }
        catch (Exception e){
            return null;
        }
    }

}
