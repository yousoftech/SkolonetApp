package com.example.admin.skolonetapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.example.admin.skolonetapp.fragments.HoldFragment;
import com.example.admin.skolonetapp.fragments.SalesFragment;
import com.example.admin.skolonetapp.fragments.commentFragment;
import com.example.admin.skolonetapp.fragments.taskFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.admin.skolonetapp.Activity.LoginActivity.PREFS_NAME;


public class HomeScreen extends AppCompatActivity {

    String firstName, lastName, Userid;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    ConnectionDetector detector;
    Button btnSales,btnTask,btnFilter,btnCloseLead,btnComment;
    Button btnLogout,btnSettings;
    TextView txtTitle;
    boolean doubleBackToExitPressedOnce = false;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        preferences = getSharedPreferences( PREFS_NAME, MODE_PRIVATE );
        txtTitle=(TextView)findViewById(R.id.txtTitle);
        firstName = preferences.getString( "firstName", null );
        lastName = preferences.getString( "lastName", null );
        Userid = preferences.getString( "LoggedUser", null );
        btnLogout = (Button) findViewById( R.id.btnLogout );
btnSettings = (Button)findViewById( R.id.btnSettings );


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar( "" + firstName + " " + lastName );

        setSupportActionBar(toolbar);



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        btnSettings.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext(),settingsMenu.class );
              //  finish();
                startActivity( i );
            }
        } );
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new commentFragment(), "Dashboard");
       adapter.addFragment(new taskFragment(), "Task");
       adapter.addFragment(new SalesFragment(), "Sales");
        adapter.addFragment(new HoldFragment(), "Hold");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void setupToolbar(String title) {
        setSupportActionBar( toolbar );
        txtTitle.setText( title );
        btnLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( HomeScreen.this );
                LayoutInflater inflater = HomeScreen.this.getLayoutInflater();
                dialogBuilder.setMessage( "Are you sure you want to logout ?" );

                dialogBuilder.setPositiveButton( "Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove( "logged" );
                        editor.clear();
                        editor.commit();
                        finish();
                        startActivity( new Intent( HomeScreen.this, LoginActivity.class ) );
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
         finish();

    }



}
