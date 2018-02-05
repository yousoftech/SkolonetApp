package com.example.admin.skolonetapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.skolonetapp.R;
import com.example.admin.skolonetapp.Util.ConnectionDetector;


public class AdminSalesDetails extends AppCompatActivity {

    String firstName, lastName, Userid;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    RecyclerView recyclerView;
    TextView txtRecords;Toolbar toolbar;
    Button btnLogout;
    TextView txtTitle;
    boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_sales_details );

        firstName = preferences.getString( "firstName", null );
        lastName = preferences.getString( "lastName", null );
        Userid = preferences.getString( "LoggedUser", null );
        setupToolbar( "" + firstName + " " + lastName );
        recyclerView = (RecyclerView) findViewById( R.id.recyclerSales );
        txtRecords = (TextView) findViewById( R.id.txtNoRecords );
    }
    private void setupToolbar(String title) {
        setSupportActionBar( toolbar );
        txtTitle.setText( title );
        btnLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( AdminSalesDetails.this );
                LayoutInflater inflater = AdminSalesDetails.this.getLayoutInflater();
                dialogBuilder.setMessage( "Are you sure you want to logout ?" );

                dialogBuilder.setPositiveButton( "Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove( "logged" );
                        editor.clear();
                        editor.commit();
                        finish();
                        startActivity( new Intent( AdminSalesDetails.this, LoginActivity.class ) );
                        Toast.makeText( getApplicationContext(), "Logout", Toast.LENGTH_SHORT ).show();
                    }
                } );
                dialogBuilder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                } );

                AlertDialog a = dialogBuilder.create();
                a.show();
            }
        } );

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle( "" );
            actionBar.setDisplayHomeAsUpEnabled( false );
            actionBar.setHomeButtonEnabled( false );
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText( this, "Please click BACK again to exit", Toast.LENGTH_SHORT ).show();

        new Handler().postDelayed( new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000 );
        //  finish();

    }

}
