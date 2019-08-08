package com.pikally;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pikally.fragments.InstructionFragment;
import com.pikally.fragments.chatFragment;
import com.pikally.fragments.paymentFragment;
import com.pikally.fragments.privacyFragment;
import com.pikally.fragments.uploadFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.widget.TextView;

public class drawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    NavigationView navigationView = null;
    Toolbar toolbar= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer2);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        //initial fragment
        InstructionFragment fragment = new InstructionFragment();
        


        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();

        showHome();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(fragment instanceof InstructionFragment){
                super.onBackPressed();
            }else {
                showHome();

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //show home method

    private void showHome(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new InstructionFragment()).commit();
        if(fragment !=null){

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container,fragment,fragment.getTag()).commit();
        }
    }



    Fragment fragment = null;


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Instructions) {
            // Handle the instructions action

            getSupportFragmentManager().beginTransaction().replace(R.id.container,new InstructionFragment()).commit();

        } else if (id == R.id.upload) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new uploadFragment()).commit();
        } else if (id == R.id.payment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new paymentFragment()).commit();

        } else if (id == R.id.chat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new chatFragment()).commit();

        } else if (id == R.id.privacy) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new privacyFragment()).commit();

        } else if (id == R.id.logout) {
            mAuth.signOut();
            Intent logoutIntent = new Intent(drawerActivity.this,LoginActivity.class);
            startActivity(logoutIntent);
            finish();
        }


        //show home fragment

        if(fragment !=null){

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container,fragment,fragment.getTag()).commit();
        }
        //end here




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //UserAuthentication if user is already logged in or not

    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser == null){
            sendUsertoLoginActivity();
        }
    }

    private void sendUsertoLoginActivity() {
        Intent welcomeIntent = new Intent(drawerActivity.this,LoginActivity.class);
        startActivity(welcomeIntent);
    }


    //update navigation header with user info

    public void updateNavHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView =navigationView.getHeaderView(0);
        TextView navUserEmail =headerView.findViewById(R.id.userEmail);

        navUserEmail.setText(currentUser.getEmail());

    }
}
