package com.example.admin.skolonetapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.admin.skolonetapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;

public class SalesMan extends AppCompatActivity {

    FloatingActionButton fabButton;
    Spinner spinner;
    TextView txtTitle;
    Toolbar toolbar;
    Button btnLogout;
    String from;
    String firstName, lastName;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_man);
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        firstName = preferences.getString("firstName", null);
        lastName = preferences.getString("lastName", null);
        setupToolbar("" + firstName + " " + lastName);


        fabButton = (FloatingActionButton) findViewById(R.id.fabButton);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SalesMan.this);
                LayoutInflater inflater = (LayoutInflater) SalesMan.this.getSystemService(SalesMan.this.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.app_choose, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog a = dialogBuilder.create();
                spinner = (Spinner) dialogView.findViewById(R.id.spinnerForm);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(SalesMan.this,
                        R.array.type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        from = spinner.getSelectedItem().toString();

                        if (from.equalsIgnoreCase("School")) {
                            startActivity(new Intent(getApplicationContext(), School_ClassisActivity.class));
                        } else if (from.equalsIgnoreCase("Classes")) {
                            startActivity(new Intent(getApplicationContext(), School_ClassisActivity.class));
                        } else if (from.equalsIgnoreCase("Sankul")) {
                            startActivity(new Intent(getApplicationContext(), SankulActivity.class));
                        } else if (from.equalsIgnoreCase("Party")) {
                            startActivity(new Intent(getApplicationContext(), PartyActivity.class));
                        } else if (from.equalsIgnoreCase("Others")) {
                            startActivity(new Intent(getApplicationContext(), OtherActivity.class));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                a.show();
            }
        });
    }

    private void setupToolbar(String title) {
        setSupportActionBar(toolbar);
        txtTitle.setText(title);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SalesMan.this);
                LayoutInflater inflater = SalesMan.this.getLayoutInflater();
                dialogBuilder.setMessage("Are you sure you want to logout ?");

                dialogBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("logged");
                        editor.clear();
                        editor.commit();
                        finish();
                        startActivity(new Intent(SalesMan.this, LoginActivity.class));
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog a = dialogBuilder.create();
                a.show();
            }
        });
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
    }


}
