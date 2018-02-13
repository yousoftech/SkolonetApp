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
import com.example.admin.skolonetapp.Activity.CloseLead;
import com.example.admin.skolonetapp.Activity.Comment;
import com.example.admin.skolonetapp.Activity.LoginActivity;
import com.example.admin.skolonetapp.Adapter.adapterAdminSales;
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

import static android.content.Context.MODE_PRIVATE;
import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;


public class HoldFragment extends Fragment {

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


    public HoldFragment() {
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


        detailFrom();
        return  view;

    }


    public void detailFrom() {
        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog( getContext() );
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

                                    int ipartTypeId = obj.getInt( "iPartyTypeId" );



                                    sales.setPartyInfoId( partyInfoId );
                                    sales.setStrPartyType( iPartyTypeName );
                                    sales.setLocation( "Address : " + strAddress );
                                    sales.setDatetimeCreated( partyDate.toString() );
                                    // sales.setStrLatitude( "Medium : " + strMedium );
                                    //sales.setStrLongitude( "Longitude : " + longitude );
                                    sales.setPriority( priority );
                                    String reminderDate1 = obj.getString( "reminderDate" );
                                    sales.setReminderDate( reminderDate1 );

                                    Calendar c1 = Calendar.getInstance();
                                    SimpleDateFormat df1 = new SimpleDateFormat( "d/M/yyyy" );
                                    String formattedDate1 = df1.format( c1.getTime() );

                                    //  Date date1=format.parse(reminderDate1);

                                    //reminderDate1 = df1.format( reminderDate1 );

                                    if (partyName != "null") {
                                        sales.setPartyName( partyName );
                                    } else {
                                        sales.setShopName( shopName );

                                    }
                                    event.add( sales );



                                    int totalElements = event.size();
                                    if (totalElements > 0) {
                                        txtRecords.setVisibility( View.GONE );
                                        recyclerView.setVisibility( View.VISIBLE );
                                        aSales = new adapterSales( getContext(), event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();
                              //          btnFilter.setVisibility( View.VISIBLE );

                                    }
                                    else{
                                        txtRecords.setVisibility( View.VISIBLE );
                                        recyclerView.setVisibility( View.GONE );
                                        aSales = new adapterSales( getContext(), event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();
                               //         btnFilter.setVisibility( View.GONE );
                                    }
                                    progressDialog.dismiss();

                                }

                            }
                            else
                            {
                                txtRecords.setVisibility( View.VISIBLE );
                                recyclerView.setVisibility( View.GONE );
                                aSales = new adapterSales( getContext(), event );
                                recyclerView.setAdapter( aSales );
                                recyclerView.setLayoutManager( new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false ) );
                                aSales.notifyDataSetChanged();
                               // btnFilter.setVisibility( View.GONE );
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

