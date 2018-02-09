package com.example.admin.skolonetapp.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RatingBar;
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
import com.example.admin.skolonetapp.Activity.FullDetail;
import com.example.admin.skolonetapp.Activity.SalesMan;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;
import com.example.admin.skolonetapp.Util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.jar.Attributes;

/**
 * Created by DELL on 1/21/2018.
 */

public class adapterCloseLead extends RecyclerView.Adapter<adapterCloseLead.RecyclerViewHolder> {

    Context context;
    ArrayList<Sales> event;
    LayoutInflater inflater;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    String Id, fId, type;
    String reminderDate;
    int total=1;
    float a;
    public adapterCloseLead(Context context, ArrayList<Sales> event) {
        this.event = event;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.app_closeleadrecyclerrow, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final String ParName = event.get(position).getPartyName();
        final String reDate = event.get(position).getReminderDate();
        final double priorityval = event.get( position ).getPriority();
        final  String strAddress1 = event.get(position).getAddressLine1();
        final String strAddress2 = event.get(position).getAddressLine2();
        final String strCityName = event.get(position).getCityName();
        final String strStateName =event.get(position).getStateName();
        final String strAddress = strAddress1 + " " + strAddress2 + " " + strCityName + " " + strStateName;
        final String s = event.get( position ).getLocation();
        final String sdate = event.get( position ).getDatetimeCreated();

        Log.d("PArName", ParName + " ");
        String shopName=event.get(position).getShopName();
        Log.d("PArName", shopName + " ");
        Log.d("reDate", reDate + " ");
        Log.d("priorityval", priorityval + " ");

        if (ParName != null) {
            Log.d("adfgs", event.get(position).getPartyName() + " ");
            holder.txt_ShoopName.setVisibility(View.GONE);
            holder.txt_PartyName.setVisibility(View.VISIBLE);
            holder.txt_PartyName.setText(event.get(position).getPartyName());
        } else if (ParName == null) {
            Log.d("adfg", event.get(position).getShopName() + " ");
            holder.txt_PartyName.setVisibility(View.GONE);
            holder.txt_ShoopName.setText(event.get(position).getShopName());
            holder.txt_ShoopName.setVisibility(View.VISIBLE);
        }
        String Partytype = event.get(position).getStrPartyType();

        Log.d("adapterval", Partytype +"");


        holder.txt_PartyType.setText("" + event.get(position).getStrPartyType());
        holder.txt_PartyLocation.setText("" + event.get(position).getLocation());
        holder.txt_PartyDateTime.setText("" + event.get(position).getDatetimeCreated());
        // holder.txt_Longitude.setText("" + event.get(position).getStrLatitude());
        //  holder.txt_Latitude.setText("" + event.get(position).getStrLongitude());

        Id = event.get(position).getPartyInfoId();
        type = event.get(position).getStrPartyType();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                fId = event.get(position).getPartyInfoId();

                type = event.get(position).getStrPartyType();
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



            }
        });
    }
    public void refreshEvents(ArrayList<Sales> events) {
        events.clear();
        events.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txt_PartyName, txt_PartyType, txt_PartyLocation, txt_PartyDateTime, txt_Latitude, txt_Longitude, txt_ShoopName;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txt_ShoopName = (TextView) itemView.findViewById(R.id.txt_Shop_Name);

            txt_PartyName = (TextView) itemView.findViewById(R.id.txt_PartyName);
            txt_PartyType = (TextView) itemView.findViewById(R.id.txt_PartyType);
            txt_PartyLocation = (TextView) itemView.findViewById(R.id.txt_PartyLocation);
            txt_PartyDateTime = (TextView) itemView.findViewById(R.id.txt_PartyDateTime);
            // txt_Latitude = (TextView) itemView.findViewById(R.id.txt_Latitude);
            //txt_Longitude = (TextView) itemView.findViewById(R.id.txt_Longitude);


        }
    }





    public Long convertDate(String dat)
    {
        String parts[] = dat.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();
        return milliTime;
    }

}
