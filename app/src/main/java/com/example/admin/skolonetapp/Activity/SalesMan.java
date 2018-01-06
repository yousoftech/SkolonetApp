package com.example.admin.skolonetapp.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.admin.skolonetapp.R;

public class SalesMan extends AppCompatActivity {

    FloatingActionButton fabButton;
    Spinner spinner;
    Button btnOk;
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_man);

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
                btnOk = (Button) dialogView.findViewById(R.id.btnformOk);

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
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (from.equalsIgnoreCase("School")) {
                            startActivity(new Intent(getApplicationContext(), School_ClassisActivity.class));
                        } else if (from.equalsIgnoreCase("Classes")) {
                            startActivity(new Intent(getApplicationContext(), School_ClassisActivity.class));
                        } else if (from.equalsIgnoreCase("Sankul")) {
                            startActivity(new Intent(getApplicationContext(), SankulActivity.class));
                        } else if (from.equalsIgnoreCase("Party")) {
                            startActivity(new Intent(getApplicationContext(), PartyActivity.class));
                        } else if (from.equalsIgnoreCase("Other")) {
                            startActivity(new Intent(getApplicationContext(), OtherActivity.class));
                        }
                    }
                });


                a.show();
            }
        });
    }
}
