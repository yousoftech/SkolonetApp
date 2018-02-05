package com.example.admin.skolonetapp.Activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.skolonetapp.Adapter.adapterAdminSales;
import com.example.admin.skolonetapp.Adapter.adapterSales;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;


public class AdminSalesDetails extends AppCompatActivity {

    String firstName, lastName, Userid;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    RecyclerView recyclerView;
    TextView txtRecords;Toolbar toolbar;
    Button btnLogout,btnFilter;
    TextView txtTitle;
    boolean doubleBackToExitPressedOnce = false;
    Sales sales;
    adapterAdminSales aSales;
    ArrayList<Sales> event;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_sales_details );
        detector = new ConnectionDetector( this );

        preferences = getSharedPreferences( PREFS_NAME, MODE_PRIVATE );

        firstName = preferences.getString( "firstName", null );
        lastName = preferences.getString( "lastName", null );
        Userid = preferences.getString( "LoggedUser", null );
        recyclerView = (RecyclerView) findViewById( R.id.recyclerSales );
        txtRecords = (TextView) findViewById( R.id.txtNoRecords );
        btnLogout=(Button)findViewById(R.id.btnLogout);
        txtTitle=(TextView)findViewById(R.id.txtTitle);
        event = new ArrayList<Sales>();
        btnFilter = (Button)findViewById( R.id.btnFilter );

        btnFilter.setVisibility( View.GONE );


        setupToolbar( "" + firstName + " " + lastName );

        detailFrom( -1 );

    }
    public void detailFrom(final int typeId) {

        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog( this );
            progressDialog.setCancelable( false );
            progressDialog.setMessage( "Loading..." );
            progressDialog.show();

            JsonObjectRequest objectRequest = new JsonObjectRequest( Request.Method.POST,
                    Constant.PATH + "Sales/GetSalesData?id=" + Userid, null, new Response.Listener<JSONObject>() {
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

                                    String location = obj.getString( "location" );
                                    String partyDate1 = obj.getString( "datetimeCreated" );
                                    String latitude = obj.getString( "latitude" );
                                    String longitude = obj.getString( "longitude" );
                                    double priority = obj.getDouble( "priority" );
                                    int ipartTypeId = obj.getInt( "iPartyTypeId" );
                                    String partyDate = convert( partyDate1 );


                                    sales.setPartyName( partyName );
                                        sales.setPartyInfoId( partyInfoId );
                                        sales.setStrPartyType( iPartyTypeName );
                                        sales.setLocation( "Address : " + strAddress );
                                        sales.setDatetimeCreated( partyDate );
                                        // sales.setStrLatitude( "Medium : " + strMedium );
                                        //sales.setStrLongitude( "Longitude : " + longitude );
                                        sales.setPriority( priority );
                                        String reminderDate1 = obj.getString( "reminderDate" );
                                        sales.setReminderDate( reminderDate1 );



                                        //  Date date1=format.parse(reminderDate1);


                                        event.add( sales );



                                    int totalElements = event.size();
                                    if (totalElements > 0) {
                                        txtRecords.setVisibility( View.GONE );
                                        recyclerView.setVisibility( View.VISIBLE );
                                        aSales = new adapterAdminSales( AdminSalesDetails.this, event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( AdminSalesDetails.this, LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();
                                        btnFilter.setVisibility( View.VISIBLE );

                                    }
                                    else{
                                        txtRecords.setVisibility( View.VISIBLE );
                                        recyclerView.setVisibility( View.GONE );
                                        aSales = new adapterAdminSales( AdminSalesDetails.this, event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( AdminSalesDetails.this, LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();
                                        btnFilter.setVisibility( View.GONE );
                                    }
                                    progressDialog.dismiss();

                                }

                            }
                            else
                            {
                                txtRecords.setVisibility( View.VISIBLE );
                                recyclerView.setVisibility( View.GONE );
                                aSales = new adapterAdminSales( AdminSalesDetails.this, event );
                                recyclerView.setAdapter( aSales );
                                recyclerView.setLayoutManager( new LinearLayoutManager( AdminSalesDetails.this, LinearLayoutManager.VERTICAL, false ) );
                                aSales.notifyDataSetChanged();
                                btnFilter.setVisibility( View.GONE );
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
            RequestQueue requestQueue = Volley.newRequestQueue( AdminSalesDetails.this );
            requestQueue.add( objectRequest );
        } else {
            Toast.makeText( AdminSalesDetails.this, "Please check your internet connection before verification..!", Toast.LENGTH_LONG ).show();
        }
    }

    private void setupToolbar(String title) {
        setSupportActionBar( toolbar );
        txtTitle.setText( title +"");

        btnLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( AdminSalesDetails.this );
                LayoutInflater inflater = AdminSalesDetails.this.getLayoutInflater();
                dialogBuilder.setMessage( "Are you sure you want to logout ?" );

                dialogBuilder.setPositiveButton( "Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove( "logged" );
                        editor.clear();
                        editor.commit();
                        finish();
                        startActivity( new Intent( AdminSalesDetails.this, LoginActivity.class ) );
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
        //  finish();

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
