package com.example.admin.skolonetapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.skolonetapp.Activity.FullDetail;
import com.example.admin.skolonetapp.Pojo.Sales;
import com.example.admin.skolonetapp.R;

import java.util.ArrayList;

/**
 * Created by DELL on 1/21/2018.
 */

public class adapterSales extends RecyclerView.Adapter<adapterSales.RecyclerViewHolder> {

    Context context;
    ArrayList<Sales> event;
    LayoutInflater inflater;

    public adapterSales(Context context, ArrayList<Sales> event) {
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
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.txt_PartyName.setText(event.get(position).getPartyName());
        holder.txt_PartyType.setText("" + event.get(position).getStrPartyType());
        holder.txt_PartyLocation.setText("" + event.get(position).getLocation());
        holder.txt_PartyDateTime.setText("" + event.get(position).getDatetimeCreated());
        final String Id = event.get(position).getPartyInfoId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.app_edit, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog editDelete = dialogBuilder.create();
                editDelete.show();

                RelativeLayout relativeLayoutEdit = (RelativeLayout) dialogView.findViewById(R.id.relative_top);
                RelativeLayout relativeLayoutDelete = (RelativeLayout) dialogView.findViewById(R.id.relative_bottom);

                relativeLayoutEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDelete.cancel();
                        //clear sharedprefrece
                        Intent intent = new Intent(context, FullDetail.class);
                        intent.putExtra("fromId", Id);
                        context.startActivity(intent);
                    }
                });
                relativeLayoutDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Delete Coming Soon", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return event.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txt_PartyName, txt_PartyType, txt_PartyLocation, txt_PartyDateTime;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            txt_PartyName = (TextView) itemView.findViewById(R.id.txt_PartyName);
            txt_PartyType = (TextView) itemView.findViewById(R.id.txt_PartyType);
            txt_PartyLocation = (TextView) itemView.findViewById(R.id.txt_PartyLocation);
            txt_PartyDateTime = (TextView) itemView.findViewById(R.id.txt_PartyDateTime);
        }
    }
}
