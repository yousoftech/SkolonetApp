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
import com.example.admin.skolonetapp.Pojo.comment;
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

public class adaptercomment extends RecyclerView.Adapter<adaptercomment.RecyclerViewHolder> {

    Context context;
    ArrayList<comment> event;
    LayoutInflater inflater;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    String Id, fId, type;
    String reminderDate;
    int total = 1;
    float a;

    public adaptercomment(Context context, ArrayList<comment> event) {
        this.event = event;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService( context.LAYOUT_INFLATER_SERVICE );
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate( R.layout.commentrecyclerrow, parent, false );
        RecyclerViewHolder holder = new RecyclerViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        holder.txt_CommentDescription.setText( "" + event.get( position ).getRemark() );


    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txt_CommentBy, txt_CommentDescription;


        public RecyclerViewHolder(View itemView) {
            super( itemView );
            txt_CommentDescription = (TextView) itemView.findViewById( R.id.txtcommentDescription );


        }
    }
}





   

