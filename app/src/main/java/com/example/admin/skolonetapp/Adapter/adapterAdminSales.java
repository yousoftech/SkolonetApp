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

/**
 * Created by DELL on 1/21/2018.
 */

public class adapterAdminSales extends RecyclerView.Adapter<adapterAdminSales.RecyclerViewHolder> {

    Context context;
    ArrayList<Sales> event;
    LayoutInflater inflater;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    String Id, fId, type;
    String reminderDate;
    float a;
    public adapterAdminSales(Context context, ArrayList<Sales> event) {
        this.event = event;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.app_recyclerrow, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        String ParName = event.get(position).getPartyName();

        Log.d("PArName", ParName + " ");
        String shopName=event.get(position).getShopName();
        Log.d("PArName", shopName + " ");


            Log.d("adfg", event.get(position).getShopName() + " ");
            holder.txt_PartyName.setVisibility(View.GONE);
            holder.txt_ShoopName.setText(event.get(position).getShopName());
            holder.txt_ShoopName.setVisibility(View.VISIBLE);

        String Partytype = event.get(position).getStrPartyType();
        Log.d("adapterval", Partytype);


        holder.txt_PartyType.setText("" + event.get(position).getStrPartyType());
        holder.txt_PartyLocation.setText("" + event.get(position).getLocation());
        holder.txt_PartyDateTime.setText("" + event.get(position).getDatetimeCreated());


        Id = event.get(position).getPartyInfoId();
        type = event.get(position).getStrPartyType();
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                fId = event.get(position).getPartyInfoId();

                type = event.get(position).getStrPartyType();
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.app_edit, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog editDelete = dialogBuilder.create();
                editDelete.show();

                RelativeLayout relativeLayoutEdit = (RelativeLayout) dialogView.findViewById(R.id.relative_top);
                RelativeLayout relativeLayoutDelete = (RelativeLayout) dialogView.findViewById(R.id.relative_bottom);
                final RelativeLayout relativeLayoutReminder = (RelativeLayout) dialogView.findViewById(R.id.relative_reminder);
                final RelativeLayout relativeLayoutPriority = (RelativeLayout) dialogView.findViewById(R.id.relative_Priority);


                relativeLayoutEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDelete.cancel();
                        //clear sharedprefrece
                        Intent intent = new Intent(context, FullDetail.class);
                        intent.putExtra("fromId", fId);
                        intent.putExtra("Type", type);

                        context.startActivity(intent);
                    }
                });
                relativeLayoutDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View dialogView = inflater.inflate(R.layout.app_delete, null);
                        dialogBuilder.setView(dialogView);
                        final AlertDialog delete = dialogBuilder.create();
                        delete.show();
                        Button btnDelete = (Button) dialogView.findViewById(R.id.btnDelete);
                        btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteDetail();
                                event.remove(position);
                                notifyItemRemoved(position);
                                delete.cancel();
                                editDelete.cancel();
                                notifyDataSetChanged();
                            }
                        });
                        Button btnClose = (Button) dialogView.findViewById(R.id.btnClose);
                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete.cancel();
                                editDelete.cancel();
                            }
                        });

                    }
                });
                relativeLayoutReminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View dialogView = inflater.inflate(R.layout.app_reminder, null);
                        dialogBuilder.setView(dialogView);
                        final AlertDialog reminder = dialogBuilder.create();

                        reminder.show();
                        final CalendarView simpleCalendarView = (CalendarView) dialogView.findViewById(R.id.simpleCalendarView);
                        //   Log.d( "datede",reDate + " " );
                        if(!reDate.equals(null)){

                            Log.d( "asdasdasdasd","hrllo" );
                            Long b= convertDate(reDate);
                            Toast.makeText( context,  b + " ",Toast.LENGTH_SHORT ).show();

                            simpleCalendarView.setDate(  b,false,false );
                            editDelete.cancel();
                            notifyDataSetChanged();

                        }



                        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                            @Override
                            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                                month=month+1;
                                reminderDate =  dayOfMonth + "/" + month+ "/" + year;

                                Log.d( "asdasddsad",reminderDate );
                                AddReminder();
                                notifyDataSetChanged();
                                reminder.cancel();
                                editDelete.cancel();

                            }
                        });
                    }
                });
                relativeLayoutPriority.setOnClickListener( new View.OnClickListener() {
                                                               @Override
                                                               public void onClick(View v) {
                                                                   final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                                                   LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                   final View dialogView = inflater.inflate(R.layout.app_ratings, null);
                                                                   dialogBuilder.setView(dialogView);
                                                                   final AlertDialog priority = dialogBuilder.create();

                                                                   priority.show();
                                                                   final RatingBar ratings=(RatingBar)dialogView.findViewById( R.id.Priority );
                                                                   final TextView txtRating = (TextView)dialogView.findViewById( R.id.txtRating );
                                                                   final Button btnPriority = (Button)dialogView.findViewById( R.id.btnPriority );

                                                                   ratings.setRating( (float) priorityval );
                                                                   int pr = (int) priorityval;
                                                                   txtRating.setText( new String( String.valueOf( priorityval  ) ) + "/5" );
                                                                   ratings.setOnRatingBarChangeListener( new RatingBar.OnRatingBarChangeListener() {
                                                                       @Override
                                                                       public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                                                           a=  ratings.getRating();
                                                                           txtRating .setText( a+"/5" );
                                                                       }
                                                                   } );
                                                                   btnPriority.setOnClickListener( new View.OnClickListener() {
                                                                       @Override
                                                                       public void onClick(View v) {
                                                                           Toast.makeText( context,a+"",Toast.LENGTH_SHORT ).show();
                                                                           AddPriority();
                                                                           priority.cancel();
                                                                           editDelete.cancel();
                                                                           notifyDataSetChanged();
                                                                       }
                                                                   } );
                                                                   priority.setOnDismissListener( new DialogInterface.OnDismissListener() {
                                                                       @Override
                                                                       public void onDismiss(DialogInterface dialog) {
                                                                           Intent intent=new Intent(context,SalesMan.class);
                                                                           intent.putExtra( "EXIT",true );
                                                                           context.startActivity(intent);                     }
                                                                   } );

                                                                   //  priority1.cancel();
                                                               }

                                                           }

                );
            }
        });*/
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

        }
    }






}
