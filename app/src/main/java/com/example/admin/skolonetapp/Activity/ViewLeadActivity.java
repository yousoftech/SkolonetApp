package com.example.admin.skolonetapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinner;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.example.admin.skolonetapp.Pojo.BoardList;
import com.example.admin.skolonetapp.Pojo.MediumList;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.Pojo.stdList;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewLeadActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    ConnectionDetector detector;
    String fromId, type, itypeId;
    TextView edtSankulName, edtSankulOrganization, edtSankulPartyName, edtSankulDesignation, edtSankulContactNumber,
            edtSankulAddress1,edtSankulAverageStudentEnglish,edtSankulAverageStudentHindi,edtSankulAverageStudentGujarati, edtSankulAddress2, edtSankulCity, edtSankulState, edtSankulRemark, edtPartyShopName, edtPartyType, edtPartyDistributorName;
    MultiSpinnerSearch spinnerStdEnglish, spinnerStdHindi,spinnerStdGujarati,  spinnerMedium;
    SingleSpinner spinnerSchoolBoardEnglish, spinnerSchoolBoardHindi,spinnerSchoolBoardGujarati;
    Sales sales;
    String stdName="", mediumName="", boardName="";
    String selectedMediumName="",strMedium, selectedBoardNameEnglish="",selectedStdNameEnglish="",selectedBoardNameHindi="",selectedStdNameHindi="",selectedBoardNameGujarati="",selectedStdNameGujarati="" ;
    List<String> MediumNameList = new ArrayList<>(  );
    final List<KeyPairBoolData> listArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> listArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoard = new ArrayList<>();
    final List<KeyPairBoolData> listArrayStdGujarati = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoardGujarati = new ArrayList<>();
    final List<KeyPairBoolData> listArrayStdHindi = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoardHindi = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayBoard = new ArrayList<>();
RelativeLayout r1,r2,r3;
    List<String> getselectedMedium = null;
    Integer[] array ={};
    List<String> getselectedStdHindi;
    List<String> getselectedStdEnglish;
    List<String> getselectedStdGujarati;
    int getselectedBoardHindi;
    int getselectedBoardGujarati;
    int getselectedBoardEnglish;


    String strBoardHindi,strBoardGujarati,strBoardEnglish,strStandardHindi,strStandardEnglish,strStandardGujarati;
    int avgStudentEnglish,avgStudentHindi,avgStudentGujarati;

    int stdId, mediumId, boardId;
    int selectedStdId,selectedBoard,selectedMedium;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_lead );

        edtSankulName = (TextView) findViewById(R.id.edtSankulName);
        edtSankulOrganization = (TextView) findViewById(R.id.edtSankulOrganization);
        edtSankulPartyName = (TextView) findViewById(R.id.edtSankulPartyName);
        edtSankulDesignation = (TextView) findViewById(R.id.edtSankulDesignation);
        edtSankulContactNumber = (TextView) findViewById(R.id.edtSankulContactNumber);
        edtSankulAddress1 = (TextView) findViewById(R.id.edtSankulAddress1);
        edtSankulAddress2 = (TextView) findViewById(R.id.edtSankulAddress2);
        edtSankulCity = (TextView) findViewById(R.id.edtSankulCity);
        edtSankulState = (TextView) findViewById(R.id.edtSankulState);
        edtSankulRemark = (TextView) findViewById(R.id.edtSankulRemark);

        spinnerMedium = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolMedium);
        spinnerStdEnglish = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardEnglish);
        spinnerSchoolBoardEnglish = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardEnglish);
        edtSankulAverageStudentEnglish = (TextView) findViewById(R.id.edtSchoolAverageStudentEnglish);

        spinnerStdHindi = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardHindi );
        edtSankulAverageStudentHindi  = (TextView) findViewById(R.id.edtSchoolAverageStudentHindi );

        spinnerStdGujarati = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardGujarati );
        edtSankulAverageStudentGujarati  = (TextView) findViewById(R.id.edtSchoolAverageStudentGujarati);


r1=(RelativeLayout)findViewById( R.id.r1 );
        r2=(RelativeLayout)findViewById( R.id.r2 );

        r3=(RelativeLayout)findViewById( R.id.r3 );

        edtPartyShopName = (TextView) findViewById(R.id.edtPartyShopName);
        edtPartyDistributorName = (TextView) findViewById(R.id.edtPartyDistributorName);
        edtPartyType = (TextView) findViewById(R.id.edtPartyType);

        detector = new ConnectionDetector(this);
        Intent intent = getIntent();
        fromId = intent.getStringExtra("fromId");
        Log.d("Typee", type + " ");

        fullDetail();


    }
    public void fullDetail() {
        if (detector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();


            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/GetSalesDataById?id=" + fromId, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("fullDetail", response.toString());
                    try {
                        boolean code = response.getBoolean("status");
                        Log.d("asdas", code + "");
                        if (code == true) {
                            progressDialog.dismiss();
                            JSONObject object = response.getJSONObject("data");
//                            Log.d("yatra", object.toString());
                            String partyInfoId = object.getString("partyInfoId");
                            String partyName = object.getString("partyName");
                            String organisationName = object.getString("organisationName");
                            String designation = object.getString("designation");
                            String sankulName = object.getString("sankulName");
                            String shopName = object.getString("shopName");
                            String distubitorName = object.getString("distubitorName");
                            String distubitorType = object.getString("distubitorType");
                            String iUserTypeId = object.getString("iUserTypeId");
                            String contactNo = object.getString("contactNo");

                            strMedium = object.getString( "strMedium" );
                            strBoardHindi = object.getString("strBoardHindi");
                            strBoardGujarati = object.getString("strBoardGujarati");
                            strBoardEnglish = object.getString("strBoardEnglish");
                            strStandardEnglish = object.getString("strStandardEnglish");
                            strStandardHindi = object.getString("strStandardHindi");
                            strStandardGujarati = object.getString("strStandardGujarati");
                            avgStudentEnglish = object.getInt("avgStudentEnglish");
                            avgStudentHindi = object.getInt("avgStudentHindi");
                            avgStudentGujarati = object.getInt("avgStudentGujarati");
                            getselectedMedium = Arrays.asList(strMedium.split(","));
                            if(!strBoardEnglish.isEmpty()) {
                                getselectedBoardEnglish = Integer.parseInt( strBoardEnglish );
                            }
                            getselectedStdEnglish = Arrays.asList(strStandardEnglish.split(","));
                            getselectedStdGujarati = Arrays.asList(strStandardGujarati.split(","));
                            getselectedStdHindi = Arrays.asList(strStandardHindi.split(","));

                            String addressLine1 = object.getString("addressLine1");
                            String addressLine2 = object.getString("addressLine2");
                            String cityName = object.getString("cityName");
                            String stateName = object.getString("stateName");
                            String remark = object.getString("remark");
                            String location = object.getString("location");
                            String datetimeCreated = object.getString("datetimeCreated");
                            String iPartyTypeId = object.getString("iPartyTypeId");
                            String strPartyType = object.getString("strPartyType");
                            Log.d("PartyTypee", strPartyType + "");
                            type = strPartyType;
                            itypeId = iPartyTypeId;
                            BoardList boardList = new BoardList();
                            MediumList mediumList = new MediumList();
                            stdList StdList = new stdList();

                            if (strPartyType.equals("Others")) {
                                edtSankulAverageStudentEnglish.setVisibility((View.GONE));
                                edtSankulName.setVisibility(View.GONE);
                                edtPartyShopName.setVisibility(View.GONE);
                                edtPartyDistributorName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                spinnerMedium.setVisibility( View.GONE );
                                spinnerSchoolBoardEnglish.setVisibility( View.GONE );
                                r1.setVisibility( View.GONE );
                                r2.setVisibility( View.GONE );
                                r3.setVisibility( View.GONE );
                            /*    spinnerStdEnglish.setVisibility(View.GONE);
                                edtSankulAverageStudentEnglish.setVisibility( View.GONE );
                                spinnerStdHindi.setVisibility( View.GONE );
                                edtSankulAverageStudentHindi.setVisibility( View.GONE );
                                spinnerStdGujarati.setVisibility( View.GONE );
                                edtSankulAverageStudentGujarati.setVisibility( View.GONE );*/

                                edtSankulOrganization.setText("Organisation Name : "+organisationName);
                                edtSankulPartyName.setText("Party Name : " +partyName);

                                //   disabled();


                            }
                            if (strPartyType.equals("Party")) {
                                edtSankulName.setVisibility(View.GONE);
                                spinnerMedium.setVisibility( View.GONE );

                                edtSankulOrganization.setVisibility(View.GONE);
                                edtSankulPartyName.setVisibility(View.GONE);

                                spinnerSchoolBoardEnglish.setVisibility( View.GONE );
                                r1.setVisibility( View.GONE );
                                r2.setVisibility( View.GONE );
                                r3.setVisibility( View.GONE );
                            /*    spinnerStdEnglish.setVisibility(View.GONE);
                                edtSankulAverageStudentEnglish.setVisibility( View.GONE );
                                spinnerStdHindi.setVisibility( View.GONE );
                                edtSankulAverageStudentHindi.setVisibility( View.GONE );
                                spinnerStdGujarati.setVisibility( View.GONE );
                                edtSankulAverageStudentGujarati.setVisibility( View.GONE );*/


                                edtPartyShopName.setText("Shop Name : "+ shopName);
                                edtPartyDistributorName.setText("Distributor Name : "+ distubitorName);
                                edtPartyType.setText("Distributor Type : "+distubitorType);

                            }
                            if (strPartyType.equals("Classes") || strPartyType.equals(("School"))) {

                                edtSankulName.setVisibility(View.GONE);
                                edtPartyShopName.setVisibility(View.GONE);
                                edtPartyDistributorName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                spinnerSchoolBoardEnglish.setVisibility( View.GONE );
                                r1.setVisibility( View.GONE );
                                r2.setVisibility( View.GONE );
                                r3.setVisibility( View.GONE );
                            /*    spinnerStdEnglish.setVisibility(View.GONE);
                                edtSankulAverageStudentEnglish.setVisibility( View.GONE );
                                spinnerStdHindi.setVisibility( View.GONE );
                                edtSankulAverageStudentHindi.setVisibility( View.GONE );
                                spinnerStdGujarati.setVisibility( View.GONE );
                                edtSankulAverageStudentGujarati.setVisibility( View.GONE );*/


                                edtSankulOrganization.setText("Organisation Name : "+organisationName);
                                edtSankulPartyName.setText("Party Name : "+ partyName);
                                edtSankulAverageStudentEnglish.setText(avgStudentEnglish+"");
                                edtSankulAverageStudentHindi.setText(avgStudentHindi+"");
                                edtSankulAverageStudentGujarati.setText(avgStudentGujarati+"");
                            }
                            if (strPartyType.equals("Sankul")) {
                                edtSankulName.setText("Sankul Name : "+sankulName);
                                edtPartyShopName.setVisibility(View.GONE);
                                edtPartyType.setVisibility(View.GONE);
                                edtPartyDistributorName.setVisibility(View.GONE);

                                spinnerSchoolBoardEnglish.setVisibility( View.GONE );
                                r1.setVisibility( View.GONE );
                                r2.setVisibility( View.GONE );
                                r3.setVisibility( View.GONE );
                            /*    spinnerStdEnglish.setVisibility(View.GONE);
                                edtSankulAverageStudentEnglish.setVisibility( View.GONE );
                                spinnerStdHindi.setVisibility( View.GONE );
                                edtSankulAverageStudentHindi.setVisibility( View.GONE );
                                spinnerStdGujarati.setVisibility( View.GONE );
                                edtSankulAverageStudentGujarati.setVisibility( View.GONE );*/

                                edtSankulOrganization.setText("Organisation Name :" + organisationName);
                                edtSankulPartyName.setText("Party Name : "+ partyName);
                                edtSankulAverageStudentEnglish.setText(avgStudentEnglish+"");
                                edtSankulAverageStudentHindi.setText(avgStudentHindi+"");
                                edtSankulAverageStudentGujarati.setText(avgStudentGujarati+"");
                                //     Log.d("indexofboard",getIndex(spinnerSchoolBoard,boardId + " ") + " "+ boardId);
                            }

                            edtSankulDesignation.setText("Designation : "+designation);
                            edtSankulContactNumber.setText("Contact No : "+contactNo);
                            edtSankulAddress1.setText("Address Line 1 : "+addressLine1);
                            edtSankulAddress2.setText("Address Line 2 : "+addressLine2);
                            edtSankulCity.setText("City : "+cityName);
                            edtSankulState.setText("State : "+stateName);
                            edtSankulRemark.setText("Remarks : "+remark);

                            std();

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
            RequestQueue requestQueue = Volley.newRequestQueue(ViewLeadActivity.this);
            requestQueue.add(objectRequest);
        } else {
            Toast.makeText(ViewLeadActivity.this, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
        }
    }

    //private method of your class

    public void std() {

        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);


            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "master/getcollectionbyid?id=" + fromId, null,
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


                                    progressDialog.dismiss();
                                    JSONObject obj = response.getJSONObject("data");
                                    JSONArray objArrayGujarati = obj.getJSONArray("gujaratiStdList");
                                    JSONArray objArrayEnglish = obj.getJSONArray("englishStdList");
                                    JSONArray objArrayHindi = obj.getJSONArray("hindiStdList");


                                    for (int i = 0; i < objArrayEnglish.length(); i++) {
                                        JSONObject jresponse = objArrayEnglish.getJSONObject(i);
                                        int stdId = jresponse.getInt("iStandardId");
                                        String stdName = jresponse.getString("strStandardName");
                                        boolean tIsActive =jresponse.getBoolean( "tIsActive" );
                                        KeyPairBoolData stddata = new KeyPairBoolData();
                                        stddata.setId(stdId);
                                        stddata.setName(stdName);
                                        stddata.setSelected(tIsActive );
                                        Log.d("ArrayStd",stddata + "");

                                        listArrayStd.add(stddata);

                                    }
                                    for (int i = 0; i < objArrayGujarati.length(); i++) {
                                        JSONObject jresponse = objArrayGujarati.getJSONObject(i);
                                        boolean tIsActive =jresponse.getBoolean( "tIsActive" );

                                        int stdId = jresponse.getInt("iStandardId");
                                        String stdName = jresponse.getString("strStandardName");
                                        KeyPairBoolData stddata = new KeyPairBoolData();
                                        stddata.setId(stdId);
                                        stddata.setName(stdName);
                                        stddata.setSelected(tIsActive );
                                        Log.d("ArrayStd",stddata + "");

                                        listArrayStdGujarati.add(stddata);
                                    }
                                    for (int i = 0; i < objArrayHindi.length(); i++) {
                                        JSONObject jresponse = objArrayHindi.getJSONObject(i);
                                        int stdId = jresponse.getInt("iStandardId");
                                        String stdName = jresponse.getString("strStandardName");
                                        boolean tIsActive =jresponse.getBoolean( "tIsActive" );

                                        KeyPairBoolData stddata = new KeyPairBoolData();
                                        stddata.setId(stdId);
                                        stddata.setName(stdName);
                                        stddata.setSelected(tIsActive );
                                        Log.d("ArrayStd",stddata + "");

                                        listArrayStdHindi.add(stddata);
                                    }


                                  /*  for(int k =0;k<=listArrayStd.size()-1;k++)
                                    {

                                        Object key = new Object();
                                        key= listArrayStd.get( k ).getId();
                                        String name=listArrayStd.get( k ).getName();
                                        String a = key.toString();
                                        if(k<=getselectedStdEnglish.size()) {
                                            for(int f =0;f<=getselectedStdEnglish.size()-1;f++)
                                            {
                                                String id=getselectedStdEnglish.get( f );

                                                Object as=new Object();
                                                as=id;
                                                if (a.equals( as ) ) {
                                                    listArrayStd.get( k ).setSelected( true );
                                                    break;
                                                }
                                                else
                                                {
                                                    listArrayStd.get( k ).setSelected( false );
                                                }
                                            }

                                        }
                                    }*/


                                    spinnerStdEnglish.setItems(listArrayStd, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedStdNameEnglish += items.get( i ).getId() + ",";

                                                    selectedlistArrayStd.add( stdkey );
                                                    Log.i("adcs", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });


                                   /* for(int k =0;k<=listArrayStdHindi.size()-1;k++)
                                    {

                                        Object key = new Object();
                                        key= listArrayStdHindi.get( k ).getId();
                                        String name=listArrayStdHindi.get( k ).getName();
                                        String a = key.toString();
                                        if(k<=getselectedStdHindi.size()) {
                                            for(int f =0;f<=getselectedStdHindi.size()-1;f++)
                                            {
                                                String id=getselectedStdHindi.get( f );

                                                Object as=new Object();
                                                as=id;
                                                if (a.equals( as ) ) {
                                                    listArrayStdHindi.get( k ).setSelected( true );
                                                    break;
                                                }
                                                else
                                                {
                                                    listArrayStdHindi.get( k ).setSelected( false );
                                                }
                                            }

                                        }
                                    }*/

                                    spinnerStdHindi.setItems(listArrayStdHindi, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedStdNameHindi += items.get( i ).getId() + ",";
                                                    selectedlistArrayStd.add( stdkey );
                                                    Log.i("adcs", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                  /*  for(int k =0;k<=listArrayStdGujarati.size()-1;k++)
                                    {

                                        Object key = new Object();
                                        key= listArrayStdGujarati.get( k ).getId();
                                        String name=listArrayStdGujarati.get( k ).getName();
                                        String a = key.toString();
                                        if(k<=getselectedStdGujarati.size()) {
                                            for(int f =0;f<=getselectedStdGujarati.size()-1;f++)
                                            {
                                                String id=getselectedStdGujarati.get( f );

                                                Object as=new Object();
                                                as=id;
                                                if (a.equals( as ) ) {
                                                    listArrayStdGujarati.get( k ).setSelected( true );
                                                }
                                                else
                                                {
                                                    listArrayStdGujarati.get( k ).setSelected( false );
                                                }
                                            }

                                        }
                                    }*/


                                    spinnerStdGujarati.setItems(listArrayStdGujarati, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedStdNameGujarati += items.get( i ).getId() + ",";
                                                    selectedlistArrayStd.add( stdkey );
                                                    Log.i("adcs", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                    JSONArray objArray2 = obj.getJSONArray("mediumList");
                                    for (int i = 0; i < objArray2.length(); i++) {
                                        JSONObject jresponse = objArray2.getJSONObject(i);
                                        int mediumId = jresponse.getInt( "iMedium" );
                                        String mediumName = jresponse.getString("strMediumName");
                                        boolean tIsActive =jresponse.getBoolean( "tIsActive" );

                                        KeyPairBoolData mediumdata = new KeyPairBoolData();
                                       /* if (i == 0) {
                                            mediumdata.setId(0);
                                            mediumdata.setName("Select Medium");
                                            mediumdata.setSelected(false);
                                            listArrayMedium.add( mediumdata );

                                        }*/
                                        mediumdata.setId(mediumId);
                                        mediumdata.setName(mediumName);
                                        mediumdata.setSelected(tIsActive);

                                        listArrayMedium.add( mediumdata );

                                        Log.d("ArrayMedium",mediumdata+"");

                                        Log.d("nickname", "" + mediumId + " " + mediumName);
                                    }




                                   for(int k =0;k<=listArrayMedium.size()-1;k++)
                                    {

                                        Object key = new Object();
                                        key= listArrayMedium.get( k ).getId();
                                        String name=listArrayMedium.get( k ).getName();
                                        String a = key.toString();
                                        if(k<=getselectedMedium.size()) {
                                            for(int f =0;f<=getselectedMedium.size()-1;f++)
                                            {
                                                String id=getselectedMedium.get( f );

                                                Object as=new Object();
                                                as=id;
                                                if (a.equals( as ) ) {
                                                    //listArrayMedium.get( k ).setSelected( true );
                                                    if(name.equalsIgnoreCase( "English Medium" ))
                                                    {
                                                        r1.setVisibility( View.VISIBLE );

                                                        break;

                                                    }
                                                    if(name.equalsIgnoreCase( "Hindi Medium" ))
                                                    {

                                                        r2.setVisibility( View.VISIBLE );
                                                        break;
                                                    }
                                                    if(name.equalsIgnoreCase( "Gujarati Medium" ))
                                                    {

                                                        r3.setVisibility( View.VISIBLE );
                                                        break;

                                                    }
                                                    break;
                                                }

                                            }

                                        }
                                    }



                                 //   Log.d( "checkedevalmedium",listArrayMedium + "" );

                                    spinnerMedium.setItems(listArrayMedium, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected() == true) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();

                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    mediumName += items.get( i ).getName() + ",";
                                                    selectedMediumName += items.get( i ).getId() + ",";
                                                    if(items.get(i).getName().equals("Hindi Medium") )
                                                    {

                                                        r2.setVisibility( View.VISIBLE );




                                                    }
                                                    if(items.get(i).getName().equals("Gujarati Medium") )
                                                    {
                                                        r3.setVisibility( View.VISIBLE );


                                                    }
                                                    if(items.get(i).getName().equals("English Medium"))
                                                    {
                                                        r1.setVisibility( View.VISIBLE );

                                                        //spinnerStdGujarati.setVisibility(View.GONE);


                                                    }

                                                    selectedlistArrayMedium.add( stdkey );
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                                if (items.get(i).isSelected() == false) {
                                                    if(items.get(i).getName().equals("English Medium"))
                                                    {



                                                        r1.setVisibility( View.GONE );
                                                    }
                                                    if(items.get(i).getName().equals("Hindi Medium"))
                                                    {

                                                        r2.setVisibility( View.GONE );


                                                    }
                                                    if(items.get(i).getName().equals("Gujarati Medium"))
                                                    {

                                                        r3.setVisibility( View.GONE );

                                                    }
                                                }
                                            }

                                        }

                                    });

                                    //   for(int k=0;k<getselectedMedium.size();k++)
                                    // {
                                    //   String id=getselectedMedium.get( k );
                                    //    KeyPairBoolData key = new KeyPairBoolData();
                                    //   key.setId(Integer.parseInt( id) );

                                    // int pos= listArrayMedium.indexOf(key);
                                    //  int a=pos;
                                    // }


                                    JSONArray objArray3 = obj.getJSONArray("boardList");
                                    for (int i = 0; i < objArray3.length(); i++) {
                                        JSONObject jresponse = objArray3.getJSONObject(i);
                                        int boardId = jresponse.getInt("iBoardId");
                                        String boardName = jresponse.getString("strBoardName");
                                        boolean tIsActive =jresponse.getBoolean( "tIsActive" );

                                        KeyPairBoolData boarddata = new KeyPairBoolData();
                                        boarddata.setId(boardId);
                                        boarddata.setName(boardName);
                                        boarddata.setSelected( tIsActive );
                                        listArrayBoard.add(boarddata);
                                    }
                                 /*   for (int i = 0; i < objArray3.length(); i++) {
                                        JSONObject jresponse = objArray3.getJSONObject(i);
                                        int boardId = jresponse.getInt("iBoardId");
                                        String boardName = jresponse.getString("strBoardName");
                                        KeyPairBoolData boarddata = new KeyPairBoolData();
                                        boarddata.setId(boardId);
                                        boarddata.setName(boardName);
                                        boarddata.setSelected( false );
                                        listArrayBoardGujarati.add(boarddata);
                                    }
                                    for (int i = 0; i < objArray3.length(); i++) {
                                        JSONObject jresponse = objArray3.getJSONObject(i);
                                        int boardId = jresponse.getInt("iBoardId");
                                        String boardName = jresponse.getString("strBoardName");
                                        KeyPairBoolData boarddata = new KeyPairBoolData();
                                        boarddata.setId(boardId);
                                        boarddata.setName(boardName);
                                        boarddata.setSelected( false );
                                        listArrayBoardHindi.add(boarddata);
                                    }*/


                                 /*   for(int k =0;k<=listArrayBoard.size()-1;k++)
                                    {

                                        Object key = new Object();
                                        key= listArrayBoard.get( k ).getId();
                                        String name=listArrayBoard.get( k ).getName();
                                        String a = key.toString();
                                        Object as = new Object();
                                        as= getselectedBoardEnglish;
                                        if(a.equals(as.toString())) {
                                            listArrayBoard.get( k ).setSelected( true );
                                        }
                                        else{
                                            listArrayBoard.get( k ).setSelected( false );

                                        }



                                    }*/


                                    spinnerSchoolBoardEnglish.setItems(listArrayBoard, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedBoardNameEnglish = items.get( i ).getId()+"";
                                                    selectedlistArrayBoard.add( stdkey );
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });

spinnerSchoolBoardEnglish.setVisibility( View.VISIBLE );



                                    //   ArrayAdapter medium = new ArrayAdapter(School_ClassisActivity.this, android.R.layout.simple_spinner_item, mediumArr);
                                    //    medium.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    //Setting the ArrayAdapter data on the Spinner
                                    //     spinnerMedium.setAdapter(medium);

                                    //  ArrayAdapter std = new ArrayAdapter(School_ClassisActivity.this, android.R.layout.simple_spinner_item, spotArr);
                                    // std.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    //Setting the ArrayAdapter data on the Spinner
                                    //  spinnerStd.setAdapter(std);


                                    //      ArrayAdapter board = new ArrayAdapter(School_ClassisActivity.this, android.R.layout.simple_spinner_item, boardArr);
                                    //      board.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                    //Setting the ArrayAdapter data on the Spinner
                                    //      spinnerSchoolBoard.setAdapter(board);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(ViewLeadActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(ViewLeadActivity.this, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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


