package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.EnteringApp.LoginActivity;
import com.example.myproject.Functions.EducationFragment;
import com.example.myproject.Functions.ExercisesFragment;
import com.example.myproject.Functions.GoniometerFragment;
import com.example.myproject.Functions.NotesFragment;
import com.example.myproject.Functions.ProfileFragment;
import com.example.myproject.Functions.SensorFragment;
import com.example.myproject.Functions.SourcesFragment;
import com.example.myproject.Functions.SpineFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_home);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        updateNavHeader();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_profile);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_education:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EducationFragment()).commit();
                break;
            case R.id.nav_exercises:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExercisesFragment()).commit();
                break;
            case R.id.nav_notes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
                break;
            case R.id.nav_goniometer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GoniometerFragment()).commit();
                break;
            case R.id.nav_sensor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SensorFragment()).commit();
                break;
            case R.id.nav_sources:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SourcesFragment()).commit();
                break;
            case R.id.nav_drawing:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpineFragment()).commit();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent Login = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(Login);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader(){

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername=headerView.findViewById(R.id.somebody);

        Bundle params = getIntent().getExtras();
        String username= params.getString("username");
        navUsername.setText("      "+username);
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }
}
