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
import android.widget.EditText;
import android.widget.ImageView;
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
        View view = inflater.inflate(R.layout.app_adminrecyclerrow, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        final String a=event.get(position).getStrTaskName();
        String b=event.get(position).getStrTaskDescription();
        String status = event.get( position ).getTaskStatus();

        String reason = event.get( position ).getTaskReason();
        final String id =event.get(position).getTaskId();
        if(!reason.isEmpty())
        {
            holder.txtReason.setVisibility(View.VISIBLE  );
            holder.txtReason.setText("Reason : " + reason );
        }
        else
        {
            holder.txtReason.setVisibility(View.GONE  );

        }
        if(status.equalsIgnoreCase( "Pending" ) )
        {
            Log.d( "TaskStatuss",status + " " + id );
            holder.btnOpnTask.setVisibility( View.VISIBLE );

        }
        else if(status.equalsIgnoreCase("Seen"))
        {
            holder.btnApprovedTask.setVisibility( View.VISIBLE );
            holder.btnDeclineTask.setVisibility( View.VISIBLE );
        }
        else if(status.equalsIgnoreCase("Approve"))
        {
            holder.btnOpnTask.setVisibility( View.GONE );
            holder.btnCompleted.setVisibility( View.VISIBLE );
            holder.btnDeclineTask.setVisibility( View.VISIBLE );
            holder.btnDeclineTask.setText( "Not Completed" );

        }
        else if(status.equalsIgnoreCase("Completed"))
        {
            holder.txtApproved.setVisibility( View.VISIBLE );
            holder.imgCompleted.setVisibility( View.VISIBLE );
            holder.btnCompleted.setVisibility( View.GONE );
            holder.btnApprovedTask.setVisibility( View.GONE );
            holder.btnDeclineTask.setVisibility( View.GONE );
            holder.btnOpnTask.setVisibility( View.GONE );
            //   holder.btnCompleted.setVisibility( View.VISIBLE );
          //  holder.btnDeclineTask.setVisibility( View.VISIBLE );
        }
        else if(status.equalsIgnoreCase("Not Completed"))
        {
            holder.txtDeclined.setVisibility( View.VISIBLE );
            holder.txtDeclined.setText( "Declined After Approving" );
            holder.imgNotCompleted.setVisibility( View.VISIBLE );
            holder.btnCompleted.setVisibility( View.GONE );
            holder.btnApprovedTask.setVisibility( View.GONE );
            holder.btnDeclineTask.setVisibility( View.GONE );
            holder.btnOpnTask.setVisibility( View.GONE );
            //   holder.btnCompleted.setVisibility( View.VISIBLE );
            //  holder.btnDeclineTask.setVisibility( View.VISIBLE );
        }
        else if(status.equalsIgnoreCase("Declined"))
        {
            holder.txtDeclined.setVisibility( View.VISIBLE );
            holder.imgNotCompleted.setVisibility( View.VISIBLE );
            holder.btnCompleted.setVisibility( View.GONE );
            holder.btnApprovedTask.setVisibility( View.GONE );
            holder.btnDeclineTask.setVisibility( View.GONE );
            holder.btnOpnTask.setVisibility( View.GONE );
            //   holder.btnCompleted.setVisibility( View.VISIBLE );
            //  holder.btnDeclineTask.setVisibility( View.VISIBLE );
        }
        holder.txt_taskName.setText(a);
        holder.txt_taskDescription.setText(b);


        holder.btnOpnTask.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( "taskNae",a +"");
                holder.btnApprovedTask.setVisibility( View.VISIBLE );
                holder.btnDeclineTask.setVisibility( View.VISIBLE );
                holder.btnOpnTask.setVisibility( View.GONE );
                UpdateTaskStatus(id,"Seen","");
            }
        } );
        holder.btnApprovedTask.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnCompleted.setVisibility( View.VISIBLE );
                holder.btnApprovedTask.setVisibility( View.GONE );

                UpdateTaskStatus(id,"Approve","");
                holder.btnDeclineTask.setText( "Not Completed" );
            }
        } );
        holder.btnCompleted.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnCompleted.setVisibility( View.GONE );
                holder.btnApprovedTask.setVisibility( View.GONE );
                holder.btnDeclineTask.setVisibility( View.GONE );
                holder.btnOpnTask.setVisibility( View.GONE );
                holder.txtApproved.setVisibility( View.VISIBLE );
                holder.imgCompleted.setVisibility( View.VISIBLE );
                }
        } );
        holder.btnDeclineTask.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.taskdeclineview, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog editDelete = dialogBuilder.create();
                editDelete.show();
                final EditText edtReason;
                Button btnSubmit;
                edtReason = (EditText)dialogView.findViewById( R.id.reason );
                btnSubmit = (Button)dialogView.findViewById( R.id.btnSubmit );

                btnSubmit.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String btnText = holder.btnDeclineTask.getText().toString();
                        if(btnText.equalsIgnoreCase( "Not Completed" )) {
                            holder.txtDeclined.setVisibility( View.VISIBLE );
                            holder.txtDeclined.setText( "Declined After Approving" );
                            holder.imgNotCompleted.setVisibility( View.VISIBLE );
                            holder.btnCompleted.setVisibility( View.GONE );
                            holder.btnApprovedTask.setVisibility( View.GONE );
                            holder.btnDeclineTask.setVisibility( View.GONE );
                            holder.btnOpnTask.setVisibility( View.GONE );
                            UpdateTaskStatus( id, "Not Completed", edtReason.getText().toString() );
                        }
                        if(btnText.equalsIgnoreCase( "Decline"   )) {
                            holder.txtDeclined.setVisibility( View.VISIBLE );
                            holder.imgNotCompleted.setVisibility( View.VISIBLE );
                            UpdateTaskStatus( id, "Declined", edtReason.getText().toString() );
                            holder.btnCompleted.setVisibility( View.GONE );
                            holder.btnApprovedTask.setVisibility( View.GONE );
                            holder.btnDeclineTask.setVisibility( View.GONE );
                            holder.btnOpnTask.setVisibility( View.GONE );
                        }

                        editDelete.cancel();
                        notifyDataSetChanged();                    }
                } );




            }
        } );
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

    public void UpdateTaskStatus(String taskId,String taskStatus ,String taskReason) {
        detector = new ConnectionDetector(context);
        if (detector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
      //      Log.d("datee",reminderDate+"");
//Toast.makeText(this,reminderDate,Toast.LENGTH_LONG).show();
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                    Constant.PATH + "Task/taskstatusupdate?iUserTaskGuid=" + taskId + "&status=" + taskStatus + "&strReason=" + taskReason , null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("fullDetail", response.toString() + " ====>" + fId);
                    try {
                        boolean code = response.getBoolean("status");
                        Log.d("code",code+"");
                        String msg = response.getString( "message" ) ;
                        if (code == true) {
                            Toast.makeText(context, "Task Updated ", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(context, "Task Updating Failure", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return event.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txt_taskName, txt_taskDescription,txtApproved,txtDeclined,txtReason;
        Button btnOpnTask,btnApprovedTask,btnDeclineTask,btnCompleted;
        ImageView imgCompleted,imgNotCompleted;



        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txt_taskName = (TextView) itemView.findViewById(R.id.txt_task_Name);

            txt_taskDescription = (TextView) itemView.findViewById(R.id.txt_task_description);
            btnOpnTask = (Button)itemView.findViewById( R.id.taskOpn );
            btnApprovedTask=(Button)itemView.findViewById( R.id.taskApprove );
            btnDeclineTask = (Button)itemView.findViewById( R.id.taskDecline );
            btnCompleted = (Button)itemView.findViewById( R.id.taskCompleted );
            txtApproved = (TextView)itemView.findViewById( R.id.txtCompleted );
            txtDeclined = (TextView)itemView.findViewById( R.id.txtDeclined );
            imgCompleted = (ImageView)itemView.findViewById( R.id.imgCompleted );
            imgNotCompleted = (ImageView)itemView.findViewById( R.id.imgNotCompleted );
            txtReason = (TextView)itemView.findViewById( R.id.txt_task_Reason );


        }
    }






}
