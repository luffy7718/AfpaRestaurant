package com.example.a77011_40_05.afparestaurant.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.fragments.HomeFragment;
import com.example.a77011_40_05.afparestaurant.fragments.MealsDialogFragment;
import com.example.a77011_40_05.afparestaurant.fragments.Order2Fragment;
import com.example.a77011_40_05.afparestaurant.fragments.Order3Fragment;
import com.example.a77011_40_05.afparestaurant.fragments.OrderFragment;
import com.example.a77011_40_05.afparestaurant.fragments.SettingsFragment;
import com.example.a77011_40_05.afparestaurant.fragments.TablesSelectorFragment;
import com.example.a77011_40_05.afparestaurant.interfaces.SWInterface;
import com.example.a77011_40_05.afparestaurant.models.User;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.example.a77011_40_05.afparestaurant.utils.RetrofitApi;
import com.example.a77011_40_05.afparestaurant.utils.Session;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    Fragment currentFragment;
    FragmentManager fragmentManager;
    TextView txtHeaderName;
    ImageView imgProfilePics;
    SWInterface swInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        swInterface = RetrofitApi.getInterface();
        context = this;

        if(Functions.getPreferenceString(this,"commandMode").equals("")){
            Functions.addPreferenceString(this,"commandMode","Menu et Commandes");
        }

        fragmentManager = getFragmentManager();
        changeFragment(Constants.FRAG_TABLES_SELECTOR, null);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //adapteDrawer(navigationView);
        txtHeaderName = navigationView.getHeaderView(0).findViewById(R.id.txtHeader_name);
        imgProfilePics = navigationView.getHeaderView(0).findViewById(R.id.imgProfilePic);
        userHasChange(Session.getMyUser());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            changeFragment(Constants.FRAG_SETTINGS,null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void clearFragments() {
        int end = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i <= end; i++) {
            //Log.e(Constants.TAG_LOG,i+"/"+end);
            fragmentManager.popBackStackImmediate();
        }
        //Log.e(Constants.TAG_LOG,"Finish "+fragmentManager.getBackStackEntryCount());
    }

    public void changeFragment(int code, Bundle params) {
        Fragment frag = null;
        switch (code) {
            case Constants.FRAG_HOME:
                frag = new HomeFragment();
                break;
            case Constants.FRAG_TABLES_SELECTOR:
                frag = new TablesSelectorFragment();
                break;
            case Constants.FRAG_ORDER:
                frag = OrderFragment.newInstance(params);
                break;
            case Constants.FRAG_ORDER2:
                frag = Order2Fragment.newInstance(params);
                break;
            case Constants.FRAG_ORDER3:
                frag = Order3Fragment.newInstance(params);
                break;
            case Constants.FRAG_SETTINGS:
                frag = new SettingsFragment();
                break;
            default:
                Log.e("[ERROR]", "changeFragment: code invalide " + code);
                break;
        }

        if (frag != null) {
            loadFragment(frag);
        }

    }

    private void loadFragment(Fragment fragment) {
        currentFragment = fragment;
        int backStackCount = fragmentManager.getBackStackEntryCount();
        String tag = "Frag" + backStackCount;

        fragmentManager.beginTransaction()
                .replace(R.id.frtHome, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public void showMealsDialog(int idStep){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        MealsDialogFragment mealsDialogFragment = MealsDialogFragment.newInstance(idStep);
        mealsDialogFragment.show(ft, "TAG detail");
    }

    public void userHasChange(User user) {
        txtHeaderName.setText(user.getFullName());
    }

    public Fragment getLastFragment() {
        return currentFragment;
    }
}
