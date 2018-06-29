package com.example.a77011_40_05.afparestaurant.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.fragments.MealsDialogFragment;
import com.example.a77011_40_05.afparestaurant.fragments.OrderFragment;
import com.example.a77011_40_05.afparestaurant.fragments.Order3Fragment;
import com.example.a77011_40_05.afparestaurant.fragments.Order4Fragment;
import com.example.a77011_40_05.afparestaurant.fragments.SettingsFragment;
import com.example.a77011_40_05.afparestaurant.fragments.TablesSelectorFragment;
import com.example.a77011_40_05.afparestaurant.interfaces.SWInterface;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.models.User;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.example.a77011_40_05.afparestaurant.utils.RetrofitApi;
import com.example.a77011_40_05.afparestaurant.utils.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    Fragment currentFragment;
    FragmentManager fragmentManager;
    TextView txtHeaderName;
    ImageView imgProfilePics;
    SWInterface swInterface;
    int backStackCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        swInterface = RetrofitApi.getInterface();

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
            backStackCount = fragmentManager.getBackStackEntryCount();

            Log.e("OnBackPressed", String.valueOf(backStackCount));

            if (backStackCount == 0) {
                showDisconnectAppliDialog();
            }
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

            return true;
        } else if (id == R.id.action_login) {
            showDisconnectDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            clearFragments();
            changeFragment(Constants.FRAG_HOME, null);
        }else if (id == R.id.nav_order) {
            changeFragment(Constants.FRAG_ORDER, null);
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
        Fragment frag = getLastFragment();
        switch (code) {
            case Constants.FRAG_HOME:
                frag = new TablesSelectorFragment();
                break;
            case Constants.FRAG_TABLES_SELECTOR:
                frag = new TablesSelectorFragment();
                break;
            case Constants.FRAG_ORDER:
                frag = OrderFragment.newInstance(params);
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

    public void showMealsDialog(int idCategoryMeal) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment frag = getLastFragment();
        int guests = 0;
        OrderFragment order = (OrderFragment) frag;
        guests = order.getGuests();
        Bundle bundle = new Bundle();
        bundle.putInt("idCategoryMeal", idCategoryMeal);
        bundle.putInt("guests", guests);

        MealsDialogFragment mealsDialogFragment = MealsDialogFragment.newInstance(bundle);
        mealsDialogFragment.show(ft, "TAG detail");
    }

    public void userHasChange(User user) {
        txtHeaderName.setText(user.getFullName());
    }

    public Fragment getLastFragment() {
        return currentFragment;
    }

    private void showDisconnectAppliDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Quitter l'application ?");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "non", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                changeFragment(Constants.FRAG_HOME, null);
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
                System.exit(0);

            }
        });
        alertDialog.show();
    }

    private void showDisconnectDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Déconnexion de votre compte ?");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "non", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                logout();
            }
        });
        alertDialog.show();
    }

    private void logout() {
        Call<Push> call = swInterface.logout(Functions.getAuth(), Session.getMyUser().getIdStaff());
        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, push.getData(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(Constants.TAG_LOG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });
    }
}
