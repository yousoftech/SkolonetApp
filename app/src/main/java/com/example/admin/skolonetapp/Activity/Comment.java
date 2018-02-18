package com.example.admin.skolonetapp.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.example.admin.skolonetapp.Adapter.adapterSales;
import com.example.admin.skolonetapp.Adapter.adaptercomment;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.Pojo.SalesList;
import com.example.admin.skolonetapp.Pojo.comment;
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

public class Comment extends AppCompatActivity {

    FloatingActionButton fabButton;
    Spinner spinner;
    TextView txtTitle;
    Toolbar toolbar;
    Button btnLogout, btnFilter;
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
    comment comment;
    RecyclerView recyclerView;
    ArrayList<comment> event;
    adaptercomment aComment;
    boolean doubleBackToExitPressedOnce = false;
    TextView txtRecords;
    RelativeLayout relativeFilter;
    Spinner spinnerFliter;
    final List<KeyPairBoolData> listArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> listArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoard = new ArrayList<>();
    String strMedium1 = null;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    private static final int INITIAL_REQUEST = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.commentrecyclerview );

        detector = new ConnectionDetector( this );


        preferences = getSharedPreferences( PREFS_NAME, MODE_PRIVATE );
        txtTitle = (TextView) findViewById( R.id.txtTitle );
        btnLogout = (Button) findViewById( R.id.btnLogout );
        firstName = preferences.getString( "firstName", null );
        lastName = preferences.getString( "lastName", null );
        Userid = preferences.getString( "LoggedUser", null );
        setupToolbar( "" + firstName + " " + lastName );
        recyclerView = (RecyclerView) findViewById( R.id.recyclercomment );
        txtRecords = (TextView) findViewById( R.id.txtNoRecords );
        event = new ArrayList<comment>();
        NotificationManager manager;
        Notification myNotication;

        detailFrom();
    }
    private void setupToolbar(String title) {
        setSupportActionBar( toolbar );
        txtTitle.setText( title );
        btnLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( Comment.this );
                LayoutInflater inflater = Comment.this.getLayoutInflater();
                dialogBuilder.setMessage( "Are you sure you want to logout ?" );

                dialogBuilder.setPositiveButton( "Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove( "logged" );
                        editor.clear();
                        editor.commit();
                        finish();
                        startActivity( new Intent( Comment.this, LoginActivity.class ) );
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
                    Constant.PATH + "Comment/getall", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d( "yatra", response.toString() );
                    try {
                        boolean code = response.getBoolean( "status" );
                        if (code == true) {
                            progressDialog.dismiss();
                            JSONArray array = response.getJSONArray( "data" );
                            Log.d( "yatra", array.toString() );
                            if (array.length() > 0) {

                                for (int n = 0; n < array.length(); n++) {
                                    JSONObject obj = array.getJSONObject( n );
                                    comment = new comment();

                                    String commentText = obj.getString( "commentText" );


                                    comment.setRemark( commentText );


                                    event.add( comment );


                                    int totalElements = event.size();
                                    if (totalElements > 0) {
                                        txtRecords.setVisibility( View.GONE );
                                        recyclerView.setVisibility( View.VISIBLE );
                                        aComment = new adaptercomment( Comment.this, event );
                                        recyclerView.setAdapter( aComment );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( Comment.this, LinearLayoutManager.VERTICAL, false ) );
                                        aComment.notifyDataSetChanged();

                                    } else {
                                        txtRecords.setVisibility( View.VISIBLE );
                                        recyclerView.setVisibility( View.GONE );
                                        aComment = new adaptercomment( Comment.this, event );
                                        recyclerView.setAdapter( aComment );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( Comment.this, LinearLayoutManager.VERTICAL, false ) );
                                        aComment.notifyDataSetChanged();
                                    }
                                    progressDialog.dismiss();

                                }

                            } else {
                                txtRecords.setVisibility( View.VISIBLE );
                                recyclerView.setVisibility( View.GONE );
                                aComment = new adaptercomment( Comment.this, event );
                                recyclerView.setAdapter( aComment );
                                recyclerView.setLayoutManager( new LinearLayoutManager( Comment.this, LinearLayoutManager.VERTICAL, false ) );
                                aComment.notifyDataSetChanged();
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
            RequestQueue requestQueue = Volley.newRequestQueue( Comment.this );
            requestQueue.add( objectRequest );
        } else {
            Toast.makeText( Comment.this, "Please check your internet connection before verification..!", Toast.LENGTH_LONG ).show();
        }
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Comment.this );
        alertDialog.setTitle( "SETTINGS" );
        alertDialog.setMessage( "Enable Location Provider! Go to settings menu?" );
        alertDialog.setPositiveButton( "Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        Comment.this.startActivity( intent );
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


}

