package com.example.admin.skolonetapp.common;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.example.admin.skolonetapp.Activity.FullDetail;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 01-02-2018.
 */

public class BindStdData extends AppCompatActivity {

    MultiSpinnerSearch spinnerStdEnglish, spinnerStdHindi,spinnerStdGujarati,  spinnerMedium;
    SingleSpinner spinnerSchoolBoardEnglish, spinnerSchoolBoardHindi,spinnerSchoolBoardGujarati;
    Sales sales;
    String stdName="", mediumName="", boardName="";
    String selectedMediumName="",strMedium, selectedBoardNameEnglish="",selectedStdNameEnglish="",selectedBoardNameHindi="",selectedStdNameHindi="",selectedBoardNameGujarati="",selectedStdNameGujarati="" ;

    EditText edtSankulAverageStudentEnglish,edtSankulAverageStudentHindi,edtSankulAverageStudentGujarati;


    ProgressDialog progressDialog;
    ConnectionDetector detector;

    final List<KeyPairBoolData> listArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> listArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> listArrayBoard = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayStd = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayMedium = new ArrayList<>();
    final List<KeyPairBoolData> selectedlistArrayBoard = new ArrayList<>();

    List<String> getselectedMedium = null;
    Integer[] array ={};
    List<String> getselectedStdHindi;
    List<String> getselectedStdEnglish;
    List<String> getselectedStdGujarati;
    List<String> getselectedBoardHindi;
    List<String> getselectedBoardGujarati;
    List<String> getselectedBoardEnglish;


    String strBoardHindi,strBoardGujarati,strBoardEnglish,strStandardHindi,strStandardEnglish,strStandardGujarati;
    int avgStudentEnglish,avgStudentHindi,avgStudentGujarati;

    int stdId, mediumId, boardId;

    public void std() {


        spinnerMedium = (MultiSpinnerSearch) findViewById( R.id.spinnerSchoolMedium);
        spinnerStdEnglish = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardEnglish);
        spinnerSchoolBoardEnglish = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardEnglish);
        edtSankulAverageStudentEnglish = (EditText) findViewById(R.id.edtSchoolAverageStudentEnglish);

        spinnerStdHindi = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardHindi );
        spinnerSchoolBoardHindi  = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardHindi );
        edtSankulAverageStudentHindi  = (EditText) findViewById(R.id.edtSchoolAverageStudentHindi );

        spinnerStdGujarati = (MultiSpinnerSearch) findViewById(R.id.spinnerSchoolStandardGujarati );
        spinnerSchoolBoardGujarati  = (SingleSpinner) findViewById(R.id.spinnerSchoolBoardGujarati );
        edtSankulAverageStudentGujarati  = (EditText) findViewById(R.id.edtSchoolAverageStudentGujarati);


        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);


            final JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET,
                    Constant.PATH + "master/getcollection", null,
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
                                    JSONArray objArray1 = obj.getJSONArray("stdList");
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject(i);
                                        int stdId = jresponse.getInt("iStandardId");
                                        String stdName = jresponse.getString("strStandardName");
                                        KeyPairBoolData stddata = new KeyPairBoolData();

                                        /*if (i == 0) {
                                            stddata.setId(0);
                                            stddata.setName("Select Standard");
                                            stddata.setSelected(false );
                                            listArrayStd.add(stddata);

                                        }*/
                                        stddata.setId(stdId);
                                        stddata.setName(stdName);
                                        stddata.setSelected(false );
                                        Log.d("ArrayStd",stddata + "");

                                        listArrayStd.add(stddata);

                                        Log.d("nickname", "" + stdId + " " + stddata.getName());
                                    }
                                    Log.d("nicknameaas", "" + listArrayStd);

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
                                    spinnerStdHindi.setItems(listArrayStd, -1, new SpinnerListener() {

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
                                    spinnerStdGujarati.setItems(listArrayStd, -1, new SpinnerListener() {

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
                                        KeyPairBoolData mediumdata = new KeyPairBoolData();
                                       /* if (i == 0) {
                                            mediumdata.setId(0);
                                            mediumdata.setName("Select Medium");
                                            mediumdata.setSelected(false);
                                            listArrayMedium.add( mediumdata );

                                        }*/
                                        for(int k =0 ;k<getselectedMedium.size();k++)
                                        {
                                            String id = getselectedMedium.get( k );
                                            if(mediumId == Integer.parseInt( id ))
                                            {
                                                mediumdata.setId(mediumId);
                                                mediumdata.setName(mediumName);
                                                mediumdata.setSelected(true);
                                            }
                                          /* else
                                           {
                                               mediumdata.setId(mediumId);
                                               mediumdata.setName(mediumName);
                                               mediumdata.setSelected(false);

                                           }*/
                                            listArrayMedium.add( mediumdata );

                                        }


                                        Log.d("ArrayMedium",mediumdata+"");

                                        Log.d("nickname", "" + mediumId + " " + mediumName);
                                    }
                                    JSONArray objArray3 = obj.getJSONArray("boardList");
                                    for (int i = 0; i < objArray3.length(); i++) {
                                        JSONObject jresponse = objArray3.getJSONObject(i);
                                        int boardId = jresponse.getInt("iBoardId");
                                        String boardName = jresponse.getString("strBoardName");
                                        KeyPairBoolData boarddata = new KeyPairBoolData();

                                       /* if (i == 0) {
                                            boarddata.setId(0);
                                            boarddata.setName("Select Board");
                                            boarddata.setSelected( false );

                                            listArrayBoard.add( boarddata );

                                        }*/
                                        boarddata.setId(boardId);
                                        boarddata.setName(boardName);
                                        boarddata.setSelected( false );
                                        listArrayBoard.add(boarddata);



                                    }



                                    Log.d( "checkedevalmedium",listArrayMedium + "" );

                                    spinnerMedium.setItems(listArrayMedium, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();

                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedMediumName += items.get( i ).getId() + "";
                                                    if(items.get(i).getName().equals("Hindi"))
                                                    {
                                                        spinnerStdHindi.setVisibility( View.VISIBLE);

                                                        spinnerSchoolBoardHindi.setVisibility(View.VISIBLE);
                                                        edtSankulAverageStudentHindi.setVisibility( View.VISIBLE );

                                                    }
                                                    if(items.get(i).getName().equals("Gujarati"))
                                                    {
                                                        spinnerStdGujarati.setVisibility(View.VISIBLE);

                                                        spinnerSchoolBoardGujarati.setVisibility(View.VISIBLE);
                                                        edtSankulAverageStudentGujarati.setVisibility( View.VISIBLE );
                                                    }
                                                    if(items.get(i).getName().equals("English"))
                                                    {
                                                        spinnerStdEnglish.setVisibility(View.VISIBLE);

                                                        spinnerSchoolBoardEnglish.setVisibility(View.VISIBLE);
                                                        edtSankulAverageStudentEnglish.setVisibility( View.VISIBLE );
                                                    }
                                                    selectedlistArrayMedium.add( stdkey );
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }

                                        }

                                    });
                                    Log.d( "checkedevalboard",listArrayBoard + "" );

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

                                    spinnerSchoolBoardHindi.setItems(listArrayBoard, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedBoardNameHindi = items.get( i ).getId() + "";
                                                    selectedlistArrayBoard.add( stdkey );
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });
                                    spinnerSchoolBoardGujarati.setItems(listArrayBoard, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    selectedBoardNameGujarati = items.get( i ).getId() + "";
                                                    selectedlistArrayBoard.add( stdkey );
                                                    Log.i("", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                }
                                            }
                                        }
                                    });

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
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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
