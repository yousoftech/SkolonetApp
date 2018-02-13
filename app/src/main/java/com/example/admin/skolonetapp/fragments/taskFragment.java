package com.example.admin.skolonetapp.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.admin.skolonetapp.Activity.AdminSalesDetails;
import com.example.admin.skolonetapp.Activity.Comment;
import com.example.admin.skolonetapp.Activity.LoginActivity;
import com.example.admin.skolonetapp.Adapter.adapterAdminSales;
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

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;


public class taskFragment extends Fragment {

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

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    private static final int INITIAL_REQUEST = 13;


    public taskFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_admin_sales_details, container, false);

        // Inflate the layout for this fragment
        detector = new ConnectionDetector( getContext() );
        preferences = getContext().getSharedPreferences( PREFS_NAME, MODE_PRIVATE );

        firstName = preferences.getString( "firstName", null );
        lastName = preferences.getString( "lastName", null );
        Userid = preferences.getString( "LoggedUser", null );

        txtTitle = (TextView)view.findViewById( R.id.txtTitle );
        btnLogout = (Button)view.findViewById( R.id.btnLogout );
        btnFilter = (Button)view.findViewById( R.id.btnFilter );

        recyclerView = (RecyclerView)view.findViewById( R.id.recyclerSales );
        txtRecords = (TextView)view.findViewById( R.id.txtNoRecords );
        btnFilter = (Button)view.findViewById( R.id.btnFilter );
        event = new ArrayList<Sales>();


        detailFrom(-1);
        return  view;

    }


    public void detailFrom(final int typeId) {

        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog( getContext() );
            progressDialog.setCancelable( false );
            progressDialog.setMessage( "Loading..." );
            progressDialog.show();

            JsonObjectRequest objectRequest = new JsonObjectRequest( Request.Method.GET,
                    Constant.PATH + "task/GetAll?userGuid=" + Userid.toString(), null, new Response.Listener<JSONObject>() {
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

                                    String taskName = obj.getString( "taskName" );
                                    String description = obj.getString( "description" );






                                    sales.setStrTaskName("Task Name : " +  taskName );
                                    sales.setStrTaskDescription("Task Description : " + description );




                                    //  Date date1=format.parse(reminderDate1);


                                    event.add( sales );



                                    int totalElements = event.size();
                                    if (totalElements > 0) {
                                        txtRecords.setVisibility( View.GONE );
                                        recyclerView.setVisibility( View.VISIBLE );
                                        aSales = new adapterAdminSales( getContext(), event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();
                                       // btnFilter.setVisibility( View.VISIBLE );

                                    }
                                    else{
                                        txtRecords.setVisibility( View.VISIBLE );
                                        recyclerView.setVisibility( View.GONE );
                                        aSales = new adapterAdminSales( getContext(), event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();
                                     //   btnFilter.setVisibility( View.GONE );
                                    }
                                    progressDialog.dismiss();

                                }

                            }
                            else
                            {
                                txtRecords.setVisibility( View.VISIBLE );
                                recyclerView.setVisibility( View.GONE );
                                aSales = new adapterAdminSales( getContext(), event );
                                recyclerView.setAdapter( aSales );
                                recyclerView.setLayoutManager( new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false ) );
                                aSales.notifyDataSetChanged();
                             //   btnFilter.setVisibility( View.GONE );
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
            RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
            requestQueue.add( objectRequest );
        } else {
            Toast.makeText( getContext(), "Please check your internet connection before verification..!", Toast.LENGTH_LONG ).show();
        }
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getContext() );
        alertDialog.setTitle( "SETTINGS" );
        alertDialog.setMessage( "Enable Location Provider! Go to settings menu?" );
        alertDialog.setPositiveButton( "Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        getContext().startActivity( intent );
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


}

