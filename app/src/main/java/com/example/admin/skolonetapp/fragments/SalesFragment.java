package com.example.admin.skolonetapp.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
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
import com.example.admin.skolonetapp.Activity.HomeScreen;
import com.example.admin.skolonetapp.Activity.LoginActivity;
import com.example.admin.skolonetapp.Activity.OtherActivity;
import com.example.admin.skolonetapp.Activity.PartyActivity;
import com.example.admin.skolonetapp.Activity.SalesMan;
import com.example.admin.skolonetapp.Activity.SankulActivity;
import com.example.admin.skolonetapp.Activity.School_ClassisActivity;
import com.example.admin.skolonetapp.Adapter.adapterAdminSales;
import com.example.admin.skolonetapp.Adapter.adapterSales;
import com.example.admin.skolonetapp.Adapter.adaptercomment;
import com.example.admin.skolonetapp.Adapter.partyTypeAdapter;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.Pojo.SalesList;
import com.example.admin.skolonetapp.Pojo.comment;
import com.example.admin.skolonetapp.Pojo.party;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;
import com.example.admin.skolonetapp.Util.LocationAddress;
import com.example.admin.skolonetapp.Util.LocationResult;
import com.example.admin.skolonetapp.Util.MyLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;


public class SalesFragment extends Fragment implements LocationResult {


    FloatingActionButton fabButton;
    Spinner spinner;
    TextView txtTitle;
    Toolbar toolbar;
    Button btnLogout;
    String from, addtLocation, latlong;
    String firstName, lastName, Userid;
      String ratingval;
    double ratingTo=5.0,ratingFrom=0.0;
    int salesId=-1;
    SalesList salesList;
    Sales bindSalesData;
    Sales[] bindListSales = new Sales[6];
Button serach;
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
    private MyLocation myLocation = null;
    boolean doubleBackToExitPressedOnce = false;
    TextView txtRecords;
    RelativeLayout relativeFilter;
    Spinner spinnerFliter,spinnerPriority;
    final List<KeyPairBoolData> listArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> listArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoard = new ArrayList<>();
    String strMedium1 =null;
    RadioButton optSchool,optClasses,optSankul,optParty,optOthers;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    private static final int INITIAL_REQUEST = 13;


    public SalesFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sales_man, container, false);

        detector = new ConnectionDetector( getContext() );


        preferences = getContext().getSharedPreferences( PREFS_NAME, MODE_PRIVATE );
        txtTitle = (TextView)view.findViewById( R.id.txtTitle );
        btnLogout = (Button) view.findViewById( R.id.btnLogout );
        relativeFilter = (RelativeLayout) view.findViewById( R.id.RelativeFilter );
        spinnerFliter = (Spinner) view.findViewById( R.id.spinnerFilter );
        spinnerPriority = (Spinner)view.findViewById( R.id.spinnerRatings );
        firstName = preferences.getString( "firstName", null );
        lastName = preferences.getString( "lastName", null );
        Userid = preferences.getString( "LoggedUser", null );
        recyclerView = (RecyclerView) view.findViewById( R.id.recyclerSales );
        txtRecords = (TextView) view.findViewById( R.id.txtNoRecords );
        serach = (Button)view.findViewById( R.id.btnSearch );
        event = new ArrayList<Sales>();
        myLocation = new MyLocation();
        NotificationManager manager;
        Notification myNotication;

        //  recyclerView.destroyDrawingCache();
       // Bundle bundle = getContext().getIntent().getExtras();


      //  Log.d( "asdv" ,bundle+"" );

       /*if (!bundle.getString("total").equalsIgnoreCase( "" ))
        {
           txtRecords.setVisibility( View.VISIBLE );

       }
       else
       {
           txtRecords.setVisibility( View.GONE );
       }*/
        manager = (NotificationManager)getContext().getSystemService( NOTIFICATION_SERVICE );


        databind();

        spinnerPriority.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ratingval   =  spinnerPriority.getSelectedItem().toString();
               if(ratingval == "5")
               {
                   ratingTo = 5.0;
                   ratingFrom = 4.5;
               //    detailFrom(salesId);

               }
                if(ratingval == "4")
                {
                    ratingTo = 4.0;
                    ratingFrom = 3.5;
              //      detailFrom(salesId);

                }
                if(ratingval == "3")
                {
                    ratingTo = 3.0;
                    ratingFrom = 2.5;
                //    detailFrom(salesId);

                }
                if(ratingval == "2")
                {
                    ratingTo = 2.0;
                    ratingFrom = 1.5;
                  //  detailFrom(salesId);

                }
                if(ratingval == "1")
                {
                    ratingTo = 1.0;
                    ratingFrom = 0.5;
              //      detailFrom(salesId);

                }
                if(ratingval == "0")
                {
                    ratingTo = 0.0;
                    ratingFrom = 0.0;
                //    detailFrom(salesId);

                }
                if (ratingval =="Select Priority")
                {
                   ratingTo = 5.0;
                   ratingFrom = 0.0;
               //     detailFrom(salesId);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


       spinnerFliter.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            //    aSales.notifyDataSetChanged();
                from = spinnerFliter.getSelectedItem().toString();
              //  salesList = arraySales.get( i );
              //  salesId = salesList.getSalesId();
                Log.d( "asdassadasdasd", salesId + " " + from );
                if (from == "Select Party") {
                    salesId = -1;
                }
                if (from == "School") {
                    salesId = 1;
               //     detailFrom( salesId );

                }

                if (from == "Classes") {
                    salesId = 2;
               //     detailFrom( salesId );

                }
                if (from == "Sankul") {
                    salesId = 3;
              //      detailFrom( salesId );

                }
                if (from == "Party") {
                    salesId = 4;
                  //  detailFrom( salesId );

                }
                if (from == "Others") {
                    salesId = 5;
                 //   detailFrom( salesId );

                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        serach.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.clear();
                detailFrom( salesId );
            }
        } );

        fabButton = (FloatingActionButton)view.findViewById( R.id.fabButton );
        fabButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canAccessLocation() || !canAccessCoreLocation()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions( INITIAL_PERMS, INITIAL_REQUEST );
                    }

                } else {
                    boolean networkPresent = myLocation.getLocation( getContext(), SalesFragment.this );
                    if (!networkPresent) {
                        showSettingsAlert();
                    }
                }
              //  std();
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( getContext() );
                final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( getContext().LAYOUT_INFLATER_SERVICE );
                final View dialogView = inflater.inflate( R.layout.app_choose, null );
                dialogBuilder.setView( dialogView );
                final AlertDialog a = dialogBuilder.create();

                optClasses =(RadioButton)dialogView.findViewById( R.id.Classes );
                optOthers =(RadioButton)dialogView.findViewById( R.id.Others );
                optParty =(RadioButton)dialogView.findViewById( R.id.Party );
                optSankul =(RadioButton)dialogView.findViewById( R.id.Sankul );
                optSchool =(RadioButton)dialogView.findViewById( R.id.School );

                optClasses.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Intent intent = new Intent( getActivity(), School_ClassisActivity.class );
                        intent.putExtra( "fromName", "Classes" );
                        intent.putExtra( "fromId", 2 );
                        startActivity( intent );
                     //   finish();
                    }
                } );

                optOthers.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Intent intent = new Intent( getActivity(), OtherActivity.class );
                        intent.putExtra( "fromName", "Others" );
                        intent.putExtra( "fromId", 5 );
                        startActivity( intent );
                     //   finish();
                    }
                } );
                optParty.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Intent intent = new Intent( getActivity(), PartyActivity.class );
                        intent.putExtra( "fromName", "Party" );
                        intent.putExtra( "fromId", 4 );
                        startActivity( intent );
                       // finish();
                    }
                } );
                optSankul.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Intent intent = new Intent( getActivity(), SankulActivity.class );
                        intent.putExtra( "fromName", "Sankul" );
                        intent.putExtra( "fromId", 3 );
                        startActivity( intent );
                      //  finish();
                    }
                } );
                optSchool.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Intent intent = new Intent( getActivity(), School_ClassisActivity.class );
                        intent.putExtra( "fromName", "School" );
                        intent.putExtra( "fromId", 1 );
                        startActivity( intent );
                     //   finish();
                    }
                } );

              /*  spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        from = spinner.getSelectedItem().toString();
                        salesList = arraySales.get( i );
                        salesId = salesList.getSalesId();

                        if (from.equalsIgnoreCase( "School" )) {
                            Intent intent = new Intent( SalesMan.this, School_ClassisActivity.class );
                            intent.putExtra( "fromName", from );
                            intent.putExtra( "fromId", salesId );
                            startActivity( intent );
                            finish();
                        } else if (from.equalsIgnoreCase( "Classes" )) {
                            Intent intent = new Intent( SalesMan.this, School_ClassisActivity.class );
                            intent.putExtra( "fromName", from );
                            intent.putExtra( "fromId", salesId );
                            startActivity( intent );
                            finish();
                        } else if (from.equalsIgnoreCase( "Sankul" )) {
                            Intent intent = new Intent( SalesMan.this, SankulActivity.class );
                            intent.putExtra( "fromName", from );
                            intent.putExtra( "fromId", salesId );
                            startActivity( intent );
                            finish();
                        } else if (from.equalsIgnoreCase( "Party" )) {
                            Intent intent = new Intent( SalesMan.this, PartyActivity.class );
                            intent.putExtra( "fromName", from );
                            intent.putExtra( "fromId", salesId );
                            startActivity( intent );
                            finish();
                        } else if (from.equalsIgnoreCase( "Others" )) {
                            Intent intent = new Intent( SalesMan.this, OtherActivity.class );
                            intent.putExtra( "fromName", from );
                            intent.putExtra( "fromId", salesId );
                            startActivity( intent );
                            finish();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                } );*/
                a.show();
            }
        } );
   //
            detailFrom( salesId );
        return  view;

    }




  /* public void std() {

        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog( getContext() );
            progressDialog.setCancelable( false );
            progressDialog.setMessage( "Loading..." );
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue( getContext() );


            final JsonObjectRequest request = new JsonObjectRequest( Request.Method.POST,
                    Constant.PATH + "User/GetPartyType", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d( "RESPONSE", response.toString() );

                            try {
                                boolean code = response.getBoolean( "status" );
                                Log.d( "Login", "" + code );
                                String msg = response.getString( "message" );
                                // Toast.makeText(this, ""+code, Toast.LENGTH_SHORT).show();
                                if (code == true) {
                                    arraySales = new ArrayList<>();
                                    salesArr = new ArrayList<>();

                                    arrayFilter = new ArrayList<>();
                                    filterArr = new ArrayList<>();

                                    progressDialog.dismiss();
                                    JSONArray objArray1 = response.getJSONArray( "data" );
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject( i );
                                        int stdId = jresponse.getInt( "iPartyTypeId" );
                                        String stdName = jresponse.getString( "strPartyType" );
                                        salesList = new SalesList();
                                        if (i == 0) {
                                            salesList.setSalesId( 0 );
                                            salesList.setSalesName( "Select Type" );
                                            salesArr.add( salesList.getSalesName() );
                                            arraySales.add( salesList );
                                            filterArr.add( salesList.getSalesName() );
                                            arrayFilter.add( salesList );
                                        }
                                        salesList.setSalesId( stdId );
                                        salesList.setSalesName( stdName );
                                        salesArr.add( salesList.getSalesName() );
                                        arraySales.add( salesList );
                                        filterArr.add( salesList.getSalesName() );
                                        arrayFilter.add( salesList );

                                        progressDialog.dismiss();
                                        Log.d( "nickname", "" + stdId + " " + stdName );
                                    }

                                    Log.d( "logg", salesArr + "" );


                                    ArrayAdapter board1 = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_item, salesArr );
                                    board1.setDropDownViewResource( android.R.layout.select_dialog_singlechoice );
                                    spinner.setAdapter( board1 );

//                                    ArrayAdapter board = new ArrayAdapter(SalesMan.this, android.R.layout.simple_spinner_item, salesArr);
//                                    board.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    //Setting the ArrayAdapter data on the Spinner
//                                    spinner.setAdapter(board);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText( getContext(), msg, Toast.LENGTH_SHORT ).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText( getContext(), "Something take longer time please try again..!", Toast.LENGTH_LONG ).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Log.d( "RESPONSE", "That didn't work!" );
                }
            } );
            request.setRetryPolicy( new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
            requestQueue.add( request );
        } else {
            Toast.makeText( getContext(), "Please check your internet connection before verification..!", Toast.LENGTH_LONG ).show();
        }
    }
*/
    public void std1() {

        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog( getContext() );
            progressDialog.setCancelable( true );
            progressDialog.setMessage( "Loading..." );
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue( getContext() );


            final JsonObjectRequest request = new JsonObjectRequest( Request.Method.POST,
                    Constant.PATH + "User/GetPartyType", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d( "RESPONSE", response.toString() );

                            try {
                                boolean code = response.getBoolean( "status" );
                                Log.d( "Login", "" + code );
                                String msg = response.getString( "message" );
                                // Toast.makeText(this, ""+code, Toast.LENGTH_SHORT).show();
                                if (code == true) {
                                    arraySales = new ArrayList<>();
                                    salesArr = new ArrayList<>();

                                    arrayFilter = new ArrayList<>();
                                    filterArr = new ArrayList<>();

                                    progressDialog.dismiss();
                                    JSONArray objArray1 = response.getJSONArray( "data" );
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject( i );
                                        int stdId = jresponse.getInt( "iPartyTypeId" );
                                        String stdName = jresponse.getString( "strPartyType" );
                                        salesList = new SalesList();
                                        if (i == 0) {
                                            salesList.setSalesId( 0 );
                                            salesList.setSalesName( "Select Type" );
                                            salesArr.add( salesList.getSalesName() );
                                            arraySales.add( salesList );
                                            filterArr.add( salesList.getSalesName() );
                                            arrayFilter.add( salesList );
                                        }
                                        salesList.setSalesId( stdId );
                                        salesList.setSalesName( stdName );
                                        salesArr.add( salesList.getSalesName() );
                                        arraySales.add( salesList );
                                        filterArr.add( salesList.getSalesName() );
                                        arrayFilter.add( salesList );

                                        progressDialog.dismiss();
                                        Log.d( "nickname", "" + stdId + " " + stdName );
                                    }

                                    Log.d( "logg", salesArr + "" );


                                    ArrayAdapter board1 = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_item, salesArr );
                                    board1.setDropDownViewResource( android.R.layout.select_dialog_singlechoice );
                                    spinnerFliter.setAdapter( board1 );
//                                    ArrayAdapter board = new ArrayAdapter(SalesMan.this, android.R.layout.simple_spinner_item, salesArr);
//                                    board.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    //Setting the ArrayAdapter data on the Spinner
//                                    spinner.setAdapter(board);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText( getContext(), msg, Toast.LENGTH_SHORT ).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText( getContext(), "Something take longer time please try again..!", Toast.LENGTH_LONG ).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Log.d( "RESPONSE", "That didn't work!" );
                }
            } );
            request.setRetryPolicy( new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
            requestQueue.add( request );
        } else {
            Toast.makeText( getContext(), "Please check your internet connection before verification..!", Toast.LENGTH_LONG ).show();
        }
    }

    public void detailFrom(final int typeId) {
        if (detector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog( getContext() );
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
                                    String strDistributorName = obj.getString( "distubitorName" );
                                    String strOrganisationName = obj.getString( "organisationName" );
                                    String shopName = obj.getString( "shopName" );
                                    String partyName = obj.getString( "partyName" );
                                    String strAddress1 = obj.getString( "addressLine1" );
                                    String strAddress2 = obj.getString( "addressLine2" );
                                    String strCityName = obj.getString( "cityName" );
                                    String strStateName = obj.getString( "stateName" );

                                    String strMedium = obj.getString( "strMedium" );
                                    List<String> list = new ArrayList<>();
                                    list = Arrays.asList( strMedium.split( "," ) );
                                    for (int i = 0; i <= listArrayMedium.size() - 1; i++) {
                                        for (int k = 0; k <= list.size() - 1; k++) {
                                            String a = listArrayMedium.get( i ).getId() + "";
                                            if (a == list.get( k )) {
                                                strMedium += listArrayMedium.get( i ).getName() + ",";
                                            }
                                        }
                                    }

                                    String strAddress = strAddress1 + " " + strAddress2 + " " + strCityName + " " + strStateName;
                                    String reminderDate = obj.getString( "reminderDate" );

                                    String location = obj.getString( "location" );
                                    String partyDate1 = obj.getString( "datetimeCreated" );
                                    String latitude = obj.getString( "latitude" );
                                    String longitude = obj.getString( "longitude" );
                                    double priority = obj.getDouble( "priority" );
                                    String partyDate = convert( partyDate1 );
                                    String ContactNo = obj.getString( "contactNo" );
                                    int ipartTypeId = obj.getInt( "iPartyTypeId" );

                                    if (ipartTypeId == typeId && priority >=  ratingFrom  && priority <=ratingTo) {
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
                                        Log.d( "asazza", event.size() + " " );


                                    } else {
                                        txtRecords.setVisibility( View.VISIBLE );
                                        recyclerView.setVisibility( View.GONE );

                                    }
                                    if (typeId == -1  &&  priority >=  ratingFrom  && priority <=ratingTo) {
                                        sales.setPartyInfoId( partyInfoId );
                                        sales.setStrPartyType( iPartyTypeName );
                                        sales.setLocation( "Address : " + strAddress );
                                        sales.setDatetimeCreated( partyDate.toString() );
                                        sales.setContactNo( ContactNo );

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
                                        int i = 0;
                      /*                  if (reminderDate1.equals( formattedDate1 )) {
                                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder( getBaseContext() );
                                            mBuilder.setSmallIcon( R.drawable.logo );
                                            mBuilder.setContentTitle( "Reminder Alert For" + partyName + " " );
                                            mBuilder.setVibrate( new long[]{1000, 1000, 1000, 1000, 1000} );
                                            mBuilder.setLights( Color.RED, 3000, 3000 );
                                            Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
                                            mBuilder.setSound( alarmSound );
                                            mBuilder.setContentText( iPartyTypeName + " " + reminderDate1 );


                                            // Moves events into the big view

                                            Intent resultIntent = new Intent( SalesMan.this, SalesMan.class );
                                            TaskStackBuilder stackBuilder = TaskStackBuilder.create( getApplicationContext() );
                                            stackBuilder.addParentStack( SalesMan.class );
                                            stackBuilder.addNextIntent( resultIntent );
                                            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT );
                                            mBuilder.setContentIntent( resultPendingIntent );
                                            NotificationManager mNotificationManager = (NotificationManager) getSystemService( getApplicationContext().NOTIFICATION_SERVICE );
                                            mNotificationManager.notify( 1, mBuilder.build() );
                                            //PendingIntent.getBroadcast(getApplicationContext(),1,resultIntent,0);
                                        }*/
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
                                    }


                                    int totalElements = event.size();
                                    if (totalElements > 0) {
                                        txtRecords.setVisibility( View.GONE );
                                        recyclerView.setVisibility( View.VISIBLE );
                                        aSales = new adapterSales( getContext(), event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();
                                   //     btnFilter.setVisibility( View.VISIBLE );

                                    }
                                    else{
                                        txtRecords.setVisibility( View.VISIBLE );
                                        recyclerView.setVisibility( View.GONE );
                                        aSales = new adapterSales( getContext(), event );
                                        recyclerView.setAdapter( aSales );
                                        recyclerView.setLayoutManager( new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false ) );
                                        aSales.notifyDataSetChanged();
                                      //  btnFilter.setVisibility( View.GONE );
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
                           //     btnFilter.setVisibility( View.GONE );
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

    private boolean canAccessLocation() {
        return (hasPermission( Manifest.permission.ACCESS_FINE_LOCATION ));
    }

    private boolean canAccessCoreLocation() {
        return (hasPermission( Manifest.permission.ACCESS_COARSE_LOCATION ));
    }

    private boolean hasPermission(String perm) {

        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission( getContext(), perm ));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case INITIAL_REQUEST:
                if (canAccessLocation() && canAccessCoreLocation()) {
                    boolean networkPresent = myLocation.getLocation( getContext(), this );
                    if (!networkPresent) {
                        showSettingsAlert();
                    }
                }
                break;
        }

    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getContext() );
        alertDialog.setTitle( "SETTINGS" );
        alertDialog.setMessage( "Enable Location Provider! Go to settings menu?" );
        alertDialog.setPositiveButton( "Settings",
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        getContext().startActivity( intent );
                    }
                } );
        alertDialog.setNegativeButton( "Cancel",
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );
        alertDialog.show();
    }

    @Override
    public void gotLocation(Location location) {
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();
        latlong = "Latitude: " + location.getLatitude() +
                " Longitude: " + location.getLongitude();
        Log.d( "latlongggg", latlong );

        preferences = getContext().getApplicationContext().getSharedPreferences( PREFS_NAME, 0 );
        SharedPreferences.Editor editor = preferences.edit();
        String lat = location.getLatitude() + "";
        String lon = location.getLongitude() + "";
        editor.putString( "latitude", lat );
        editor.putString( "longitude", lon );
        editor.commit();
        Log.d( "latlong", latlong + "" );
        getActivity().runOnUiThread( new Runnable() {
            @Override
            public void run() {
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation( latitude, longitude,
                     getActivity().getApplicationContext(), new GeocoderHandler() );
            }
        } );

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString( "address" );
                    break;
                default:
                    locationAddress = null;
            }
            addtLocation = locationAddress;
            Log.d( "location", addtLocation );

            preferences = getContext().getApplicationContext().getSharedPreferences( PREFS_NAME, 0 );
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString( "location", locationAddress );
            editor.commit();

            // tvAddress.setText(locationAddress);
        }
    }


    public void std12()
    {

        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog( getContext() );
            progressDialog.setCancelable( false );
            progressDialog.setMessage( "Loading..." );
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue( getContext() );


            final JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET,
                    Constant.PATH + "master/getcollection", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d( "RESPONSE", response.toString() );

                            try {
                                boolean code = response.getBoolean( "status" );
                                Log.d( "Login", "" + code );
                                String msg = response.getString( "message" );
                                // Toast.makeText(this, ""+code, Toast.LENGTH_SHORT).show();
                                if (code == true) {


                                    progressDialog.dismiss();
                                    JSONObject obj = response.getJSONObject( "data" );
                                    JSONArray objArray1 = obj.getJSONArray( "stdList" );
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject( i );
                                        int stdId = jresponse.getInt( "iStandardId" );
                                        String stdName = jresponse.getString( "strStandardName" );
                                        KeyPairBoolData stddata = new KeyPairBoolData();
                                        stddata.setId( stdId );
                                        stddata.setName( stdName );
                                        stddata.setSelected( false );
                                        Log.d( "ArrayStd", stddata + "" );

                                        listArrayStd.add( stddata );

                                    }
                                    JSONArray objArray2 = obj.getJSONArray( "mediumList" );
                                    for (int i = 0; i < objArray2.length(); i++) {
                                        JSONObject jresponse = objArray2.getJSONObject( i );
                                        int mediumId = jresponse.getInt( "iMedium" );
                                        String mediumName = jresponse.getString( "strMediumName" );
                                        KeyPairBoolData mediumdata = new KeyPairBoolData();
                                       /* if (i == 0) {
                                            mediumdata.setId(0);
                                            mediumdata.setName("Select Medium");
                                            mediumdata.setSelected(false);
                                            listArrayMedium.add( mediumdata );

                                        }*/
                                        mediumdata.setId( mediumId );
                                        mediumdata.setName( mediumName );
                                        mediumdata.setSelected( false );

                                        listArrayMedium.add( mediumdata );

                                        Log.d( "ArrayMedium", mediumdata + "" );

                                        Log.d( "nickname", "" + mediumId + " " + mediumName );
                                    }
                                    JSONArray objArray3 = obj.getJSONArray( "boardList" );
                                    for (int i = 0; i < objArray3.length(); i++) {
                                        JSONObject jresponse = objArray3.getJSONObject( i );
                                        int boardId = jresponse.getInt( "iBoardId" );
                                        String boardName = jresponse.getString( "strBoardName" );
                                        KeyPairBoolData boarddata = new KeyPairBoolData();
                                        boarddata.setId( boardId );
                                        boarddata.setName( boardName );
                                        boarddata.setSelected( false );
                                        listArrayBoard.add( boarddata );
                                    }


                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText( getContext(), msg, Toast.LENGTH_SHORT ).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText( getContext(), "Something take longer time please try again..!", Toast.LENGTH_LONG ).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Log.d( "RESPONSE", "That didn't work!" );
                }
            } );
            request.setRetryPolicy( new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
            requestQueue.add( request );
        } else {
            Toast.makeText( getContext(), "Please check your internet connection before verification..!", Toast.LENGTH_LONG ).show();
        }
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
    public void databind(){

        String[] party = {
                "Select Party",
                "School",
                "Classes",
                "Sankul",
                "Party",
                "Others",
                };

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_selectable_list_item,party);


        spinnerFliter.setAdapter(spinnerArrayAdapter);

        String[] priority = {
                "Select Priority",
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
        };
        ArrayAdapter spinnerPriorityArrayAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_selectable_list_item,priority);


        spinnerPriority.setAdapter(spinnerPriorityArrayAdapter);

    }

}

