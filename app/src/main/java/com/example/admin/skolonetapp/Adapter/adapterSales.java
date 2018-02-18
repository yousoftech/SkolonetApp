package com.example.admin.skolonetapp.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SingleSpinner;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.example.admin.skolonetapp.Activity.FullDetail;
import com.example.admin.skolonetapp.Activity.HomeScreen;
import com.example.admin.skolonetapp.Activity.LoginActivity;
import com.example.admin.skolonetapp.Activity.SalesMan;
import com.example.admin.skolonetapp.Activity.School_ClassisActivity;
import com.example.admin.skolonetapp.Activity.ViewLeadActivity;
import com.example.admin.skolonetapp.Interfaces.ClickListenet;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.Pojo.SalesList;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by DELL on 1/21/2018.
 */

public class adapterSales extends RecyclerView.Adapter<adapterSales.RecyclerViewHolder>  {
    Calendar dateSelected = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    Context context;
    SingleSpinner spinnerreason;int reasonID;
    ArrayList<Sales> event;
    LayoutInflater inflater;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    String Id, fId, type, ContactNo;
    String reminderDate;
    int total = 1;
    String reDate;
    String time;
    String ParName,OrganisationName;
    final List<KeyPairBoolData> listReasons = new ArrayList<>();

    String selectedTime;
    float a;
    int RQS_1 = 0;

    public adapterSales(Context context, ArrayList<Sales> event) {
        this.event = event;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService( context.LAYOUT_INFLATER_SERVICE );
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate( R.layout.app_recyclerrow, parent, false );
        RecyclerViewHolder holder = new RecyclerViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {


        ParName = event.get( position ).getPartyName();
        reDate = event.get( position ).getReminderDate();
        final double priorityval = event.get( position ).getPriority();
        final String strAddress1 = event.get( position ).getAddressLine1();
        final String strAddress2 = event.get( position ).getAddressLine2();
        final String strCityName = event.get( position ).getCityName();
        final String strStateName = event.get( position ).getStateName();
        final String strAddress = strAddress1 + " " + strAddress2 + " " + strCityName + " " + strStateName;
        final String s = event.get( position ).getLocation();
        final String sdate = event.get( position ).getDatetimeCreated();

        Log.d( "PArName", ParName + " " );
        String shopName = event.get( position ).getShopName();
        Log.d( "PArName", shopName + " " );
        Log.d( "reDate", reDate + " " );
        Log.d( "priorityval", priorityval + " " );

        OrganisationName = event.get( position ).getOrganisationName();
        final String ShopName = event.get( position ).getShopName();
        final String  DistributorName = event.get( position ).getDistubitorName();
        final  String partyDate = event.get( position ).getDatetimeCreated().toString();
        if (OrganisationName != null) {
            Log.d( "adfgs", event.get( position ).getPartyName() + " " );
            holder.txt_ShoopName.setVisibility( View.GONE );
            holder.txt_OrganisationName.setText( event.get( position ).getOrganisationName() + " " );
        } else if (OrganisationName == null) {
            Log.d( "adfg", event.get( position ).getShopName() + " " );
            holder.txt_OrganisationName.setVisibility( View.GONE );
            holder.txt_ShoopName.setText( event.get( position ).getShopName()  + " ");
            holder.txt_ShoopName.setVisibility( View.VISIBLE );
        }
      if (ParName != null) {
            Log.d( "adfgs", event.get( position ).getPartyName() + " " );
            holder.txt_Distributor.setVisibility( View.GONE );
          holder.txt_PartyName.setVisibility( View.VISIBLE );
          holder.txt_PartyName.setText( event.get( position ).getPartyName().toString() + " " );
        }
        else if (ParName == null) {
            Log.d( "adfg", event.get( position ).getPartyName() + " " );
            holder.txt_PartyName.setVisibility( View.GONE );
            holder.txt_Distributor.setText( event.get( position ).getDistubitorName() +" " );
            holder.txt_Distributor.setVisibility( View.VISIBLE );
        }
        String Partytype = event.get( position ).getStrPartyType();

        Log.d( "adapterval", Partytype + "" );

        holder.ratingBar.setRating( (float) event.get( position ).getPriority() );

   //     holder.txt_PartyType.setText( "" + event.get( position ).getStrPartyType() );
   //     holder.txt_PartyLocation.setText( "" + event.get( position ).getLocation() );
   //     holder.txt_PartyDateTime.setText( "" + event.get( position ).getDatetimeCreated() );
        // holder.txt_Longitude.setText("" + event.get(position).getStrLatitude());
        //  holder.txt_Latitude.setText("" + event.get(position).getStrLongitude());

        holder.txt_Address.setText( event.get(position).getLocation());
        holder.txt_ContactNo.setText( event.get(position).getContactNo() );


        Id = event.get( position ).getPartyInfoId();
        type = event.get( position ).getStrPartyType();
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                fId = event.get( position ).getPartyInfoId();
                ContactNo=event.get(position).getContactNo();
                type = event.get( position ).getStrPartyType();
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( context );
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                final View dialogView = inflater.inflate( R.layout.app_edit, null );
                dialogBuilder.setView( dialogView );
                final AlertDialog editDelete = dialogBuilder.create();
                editDelete.show();

                RelativeLayout relativeCall = (RelativeLayout) dialogView.findViewById( R.id.relative_Call );
                RelativeLayout relativeLayoutView = (RelativeLayout) dialogView.findViewById( R.id.relative_viewlead );

                RelativeLayout relativeLayoutEdit = (RelativeLayout) dialogView.findViewById( R.id.relative_top );
                RelativeLayout relativeLayoutDelete = (RelativeLayout) dialogView.findViewById( R.id.relative_bottom );
                final RelativeLayout relativeLayoutReminder = (RelativeLayout) dialogView.findViewById( R.id.relative_reminder );
                final RelativeLayout relativeLayoutPriority = (RelativeLayout) dialogView.findViewById( R.id.relative_Priority );

relativeCall.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        call();
    }
} );

                relativeLayoutEdit.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDelete.cancel();
                        //clear sharedprefrece
                        Intent intent = new Intent( context, FullDetail.class );
                        intent.putExtra( "fromId", fId );
                        intent.putExtra( "Type", type );

                        context.startActivity( intent );
                    }
                } );
                relativeLayoutView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDelete.cancel();
                        //clear sharedprefrece
                        Intent intent = new Intent( context, ViewLeadActivity.class );
                        intent.putExtra( "fromId", fId );
                        intent.putExtra( "Type", type );

                        context.startActivity( intent );
                    }
                } );
                relativeLayoutDelete.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( context );
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        final View dialogView = inflater.inflate( R.layout.app_delete, null );
                        dialogBuilder.setView( dialogView );
                        final AlertDialog delete = dialogBuilder.create();
                        delete.show();
                        Button btnDelete = (Button) dialogView.findViewById( R.id.btnDelete );
                        spinnerreason = (SingleSpinner)dialogView.findViewById( R.id.r1 );
                        std();
                     btnDelete.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                deleteDetail(reasonID);
                                event.remove( position );
                                notifyItemRemoved( position );
                                if (event.size() == 0) {
                                    total = 0;
                                    Intent i = new Intent( context, SalesMan.class );
                                    i.putExtra( "total", total );

                                }
                                delete.cancel();
                                editDelete.cancel();
                                notifyDataSetChanged();
                            }
                        } );



                    }
                } );
                relativeLayoutReminder.setOnClickListener( new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( context );
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        final View dialogView = inflater.inflate( R.layout.app_reminder, null );
                        dialogBuilder.setView( dialogView );
                        final AlertDialog reminder = dialogBuilder.create();

                        reminder.show();
                        final CalendarView simpleCalendarView = (CalendarView) dialogView.findViewById( R.id.simpleCalendarView );
                        //final TimePickerDialog simpleTimePickerDialog;
                        //  simpleTimePickerDialog = (TimePickerDialog) dialogView.findViewById(R.id.simpleTimePicker);
                        //   Log.d( "datede",reDate + " " );


                        try {
                            setDateTimeField();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Sales sales = new Sales();
                        Id = event.get( position ).getPartyInfoId();
                        type = event.get( position ).getStrPartyType();
                        sales.setPartyInfoId( fId );
                        sales.setStrPartyType( type );
                        sales.setReminderDate( reminderDate );
                        sales.setPartyName( ParName );
                        sales.setDatetimeCreated( sdate );
                        sales.setContactNo( ContactNo );
                        sales.setDistubitorName( DistributorName );
                        sales.setShopName( ShopName );
                        sales.setOrganisationName( OrganisationName );
                        sales.setLocation( s );
                        sales.setPriority( priorityval );
                        event.set( position, sales );

                        notifyDataSetChanged();
                        reminder.cancel();
                        editDelete.cancel();


                        reminder.setOnDismissListener( new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        } );

                    }
                } );
                relativeLayoutPriority.setOnClickListener( new View.OnClickListener() {
                                                               @Override
                                                               public void onClick(View v) {
                                                                   final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( context );
                                                                   LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                                   final View dialogView = inflater.inflate( R.layout.app_ratings, null );
                                                                   dialogBuilder.setView( dialogView );
                                                                   final AlertDialog priority = dialogBuilder.create();

                                                                   priority.show();
                                                                   final RatingBar ratings = (RatingBar) dialogView.findViewById( R.id.Priority );
                                                                   final TextView txtRating = (TextView) dialogView.findViewById( R.id.txtRating );
                                                                   final Button btnPriority = (Button) dialogView.findViewById( R.id.btnPriority );

                                                                   ratings.setRating( (float) priorityval );
                                                                   int pr = (int) priorityval;
                                                                   txtRating.setText( new String( String.valueOf( priorityval ) ) + "/5" );
                                                                   ratings.setOnRatingBarChangeListener( new RatingBar.OnRatingBarChangeListener() {
                                                                       @Override
                                                                       public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                                                           a = ratings.getRating();
                                                                           txtRating.setText( a + "/5" );
                                                                       }
                                                                   } );
                                                                   btnPriority.setOnClickListener( new View.OnClickListener() {
                                                                       @Override
                                                                       public void onClick(View v) {
                                                                           Toast.makeText( context, a + "", Toast.LENGTH_SHORT ).show();
                                                                           AddPriority();
                                                                           Sales sales = new Sales();
                                                                           Id = event.get( position ).getPartyInfoId();
                                                                           type = event.get( position ).getStrPartyType();
                                                                           sales.setPartyInfoId( fId );
                                                                           sales.setStrPartyType( type );
                                                                           sales.setReminderDate( reDate );
                                                                           sales.setPartyName( ParName );
                                                                           sales.setDatetimeCreated( partyDate );
                                                                           sales.setContactNo( ContactNo );
                                                                           sales.setDistubitorName( DistributorName );
                                                                           sales.setShopName( ShopName );
                                                                           sales.setOrganisationName( OrganisationName );
                                                                           sales.setLocation( s );
                                                                           sales.setPriority( a );
                                                                           event.set( position, sales );

                                                                           priority.cancel();
                                                                           editDelete.cancel();
                                                                           notifyDataSetChanged();
                                                                       }
                                                                   } );


                                                                   //  priority1.cancel();
                                                               }

                                                           }

                );
            }
        } );
    }

    public void refreshEvents(ArrayList<Sales> events) {
        events.clear();
        events.addAll( events );
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return event.size();
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder  {

        TextView txt_PartyName, txt_OrganisationName, txt_ContactNo, txt_Address, txt_ShoopName,txt_Distributor;
        Button btnCall;

        RatingBar ratingBar;
        public RecyclerViewHolder(View itemView) {
            super( itemView );
            txt_ShoopName = (TextView) itemView.findViewById( R.id.txt_Shop_Name );
            ratingBar = (RatingBar)itemView.findViewById( R.id.ratingBar );
            txt_PartyName = (TextView) itemView.findViewById( R.id.txt_Party_Name );
            txt_OrganisationName = (TextView) itemView.findViewById( R.id.txt_Organisation_Name );
            txt_ContactNo = (TextView) itemView.findViewById( R.id.txt_ContatctNo );
            txt_Address = (TextView) itemView.findViewById( R.id.txt_Address );
            txt_Distributor = (TextView)itemView.findViewById( R.id.txt_Distributor_Name );
            // txt_Latitude = (TextView) itemView.findViewById(R.id.txt_Latitude);
            //txt_Longitude = (TextView) itemView.findViewById(R.id.txt_Longitude);




        }


    }

    public void deleteDetail(int resonid) {
        detector = new ConnectionDetector(context);
        if (detector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/Delete?id=" + fId + "&iReasonId=" + resonid, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("fullDetail", response.toString() + " ====>" + fId);
                    try {
                        boolean code = response.getBoolean("status");
                        if (code == true) {
                            Toast.makeText(context, "Data Delete SuccessFull", Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(objectRequest);
        } else {
            Toast.makeText(context, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
        }
    }


    public void AddReminder() {
        detector = new ConnectionDetector(context);
        if (detector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
Log.d("datee",reminderDate+"");
//Toast.makeText(this,reminderDate,Toast.LENGTH_LONG).show();
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/AddReminder?id=" + fId + "&reminderDate=" + reminderDate , null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("fullDetail", response.toString() + " ====>" + fId);
                    try {
                        boolean code = response.getBoolean("status");
                        Log.d("code",code+"");
                        String msg = response.getString( "message" ) ;
                        if (code == true) {
                            Toast.makeText(context, "Reminder Added", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(context, "Reminder Added", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(objectRequest);
        } else {
            Toast.makeText(context, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
        }
    }

    public void AddPriority() {
        detector = new ConnectionDetector(context);
        if (detector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
//Toast.makeText(this,reminderDate,Toast.LENGTH_LONG).show();
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/AddPriority?id=" + fId + "&addPriority=" + a , null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("fullDetail", response.toString() + " ====>" + fId);
                    try {
                        boolean code = response.getBoolean("status");
                        Log.d("code",code+"");
                        if (code == true) {
                            Toast.makeText(context, " Priority Updated", Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(objectRequest);
        } else {
            Toast.makeText(context, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
        }
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDateTimeField() throws ParseException {
        String parts[] = reDate.split(" ");
        String date[] = parts[0].split("/");
        String timeA[] = parts[1].split(":");

        int day = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[0]);
        int mHour = Integer.parseInt(timeA[0]);
        int mMinute =Integer.parseInt(timeA[1]);
        Calendar newCalendar = Calendar.getInstance();
newCalendar.set(year,month,day);
      //  newCalendar.set(Calendar.YEAR, year);
       // newCalendar.set(Calendar.MONTH, month-1);
      //  newCalendar.set(Calendar.DAY_OF_MONTH, day);


        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                reminderDate = String.format( "%d/%d/%d", year, monthOfYear + 1, dayOfMonth );
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());



        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        try {
                         time = " " +hourOfDay + ":" + minute + " ";
                            remindre();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mHour, mMinute, true);

        timePickerDialog.show();
        datePickerDialog.show();

    }    @SuppressLint("WrongConstant")
    public void remindre() throws ParseException {




        reminderDate += time;

        Date date =new SimpleDateFormat("yyyy/MM/d HH:mm").parse(reminderDate);
        Toast.makeText(context,date + " ",Toast.LENGTH_LONG);
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.setTime(date);
        calSet.set(Calendar.HOUR_OF_DAY, date.getHours());
        calSet.set(Calendar.MINUTE, date.getMinutes());
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        AddReminder();

        if (calSet.compareTo(calNow) <= 0) {
            // Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }

        setAlarm(calSet);


    }
    private void setAlarm(Calendar targetCal) {



        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra( "partyName",ParName );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);
        RQS_1++;
    }

    public void call()
    {


        String dial = "tel:" + ContactNo;
        //  Intent i = new Intent(Intent.ACTION_DIAL);
        //  i.setData(Uri.parse(dial.toString()));
        Intent callintent = new Intent( Intent.ACTION_CALL );
        callintent.setData( Uri.parse( "tel:" + ContactNo ) );
        if (ActivityCompat.checkSelfPermission( context, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)

            Toast.makeText(context, "Permission Required", Toast.LENGTH_SHORT).show();

            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity( callintent );

    }

    public void std() {
        detector = new ConnectionDetector(context);

        if (detector.isConnectingToInternet()) {

            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(context);


            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    Constant.PATH + "Reason/GetAll", null,
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
                                    JSONArray objArray1 = response.getJSONArray("data");
                                    for (int i = 0; i < objArray1.length(); i++) {
                                        JSONObject jresponse = objArray1.getJSONObject(i);
                                        int iReasonId = jresponse.getInt("reasonId");
                                        String reasonText = jresponse.getString("reasonText");
                                        KeyPairBoolData reasondata = new KeyPairBoolData();
                                        reasondata.setId(iReasonId);
                                        reasondata.setName(reasonText);
                                        reasondata.setSelected(false );

                                        listReasons.add(reasondata);

                                       }



                                    spinnerreason.setItems(listReasons, -1, new SpinnerListener() {

                                        @Override
                                        public void onItemsSelected(List<KeyPairBoolData> items) {

                                            for (int i = 0; i < items.size(); i++) {
                                                if (items.get(i).isSelected()) {
                                                    KeyPairBoolData stdkey = new KeyPairBoolData();
                                                    stdkey.setId(items.get( i ).getId() );
                                                    stdkey.setName( items.get( i ).getName() );
                                                    stdkey.setSelected( true );
                                                    reasonID = (int) items.get( i ).getId();
                                                    Log.i("adcs", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
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
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Something take longer time please try again..!", Toast.LENGTH_LONG).show();
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
            Toast.makeText(context, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
        }
    }
    public void AddResaon(String iUserGuid,int iReasonId ) {
        detector = new ConnectionDetector(context);
        if (detector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
//Toast.makeText(this,reminderDate,Toast.LENGTH_LONG).show();
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Sales/AddReason?iUserGuid=" + fId + "&iReasonId=" + iReasonId , null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("fullDetail", response.toString() + " ====>" + fId);
                    try {
                        boolean code = response.getBoolean("status");
                        Log.d("code",code+"");
                        if (code == true) {
                            Toast.makeText(context, " Delete Updated", Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(objectRequest);
        } else {
            Toast.makeText(context, "Please check your internet connection before verification..!", Toast.LENGTH_LONG).show();
        }
    }




}
