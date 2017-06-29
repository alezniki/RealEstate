package com.nikola.zadataktest.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.nikola.zadataktest.R;
import com.nikola.zadataktest.adapters.DrawerListAdapter;
import com.nikola.zadataktest.adapters.NavigationItem;
import com.nikola.zadataktest.db.DatabaseHelper;
import com.nikola.zadataktest.db.Estate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }


    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    // Intent KEY
    public static String KEY_ID = "KEY_ID";

    // Preferences KEY
    public static String NOTIFICATION_TOAST = "notification_toast";
    public static String NOTIFICATION_STATUS = "notification_status";

    // Navigation Items
    private ArrayList<NavigationItem> items = new ArrayList<>();


    // Navigation Drawer
    private DrawerLayout drawerLayout;
    private ListView drawerNavList;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout drawerPane;
    private CharSequence drawerTitle;
    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);


        final ListView listView = (ListView)findViewById(R.id.lv_main_list_items);
        try {
            List<Estate> list = getDatabaseHelper().getEstateDao().queryForAll();
            ListAdapter adaprer = new ArrayAdapter<>(MainActivity.this,R.layout.list_item, list);
            listView.setAdapter(adaprer);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Estate e = (Estate) listView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra(KEY_ID, e.getId());
                startActivity(intent);
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Draws Navigation Items
        items.add(new NavigationItem("Home", "Show All Estates", R.drawable.ic_action_home));
        items.add(new NavigationItem("Settings", "Change App Settings", R.drawable.ic_action_settings));
        title = drawerTitle = getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerNavList = (ListView) findViewById(R.id.drawer_navigation_list);

        // Populate the Navigation Drawer with options
        drawerPane = (LinearLayout) findViewById(R.id.drawer_pane);

        DrawerListAdapter drawerAdapter =  new DrawerListAdapter(this,items);
        drawerNavList.setOnItemClickListener(new DrawerItemClickListener());
        drawerNavList.setAdapter(drawerAdapter);

        // Action Bar
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_drawer);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu(); // Creates call to onPrepareOptionMenu
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_dialog) {
            addEstateDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addEstateDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_estate);
        dialog.setCancelable(false);

        final EditText etName = (EditText)dialog.findViewById(R.id.et_dialog_name);
        final EditText etDescription = (EditText)dialog.findViewById(R.id.et_dialog_description);
        final EditText etAddress = (EditText)dialog.findViewById(R.id.et_dialog_address);
        final EditText etPhoneNumber = (EditText)dialog.findViewById(R.id.et_dialog_phone_number);
        final EditText etQuadrature = (EditText)dialog.findViewById(R.id.et_dialog_quadrature);
        final EditText etRoomsNumber = (EditText)dialog.findViewById(R.id.et_dialog_rooms_number);
        final EditText etPrice = (EditText)dialog.findViewById(R.id.et_dialog_estate_price);


        Button btnOK = (Button)dialog.findViewById(R.id.btn_dialog_ok);
        Button btnCancel = (Button)dialog.findViewById(R.id.btn_dialog_cancel);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String address = etAddress.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                double quardrature = Double.parseDouble(etQuadrature.getText().toString());
                int roomsNumber = Integer.parseInt(etRoomsNumber.getText().toString());
                double price = Double.parseDouble(etPrice.getText().toString());

                Estate estate = new Estate();
                estate.setName(name);
                estate.setDescription(description);
                estate.setAddress(address);
                estate.setPhoneNumber(phoneNumber);
                estate.setQuadrature(quardrature);
                estate.setRoomsNumber(roomsNumber);
                estate.setPrice(price);

                try {
                    getDatabaseHelper().getEstateDao().create(estate);

                    boolean toast = sharedPreferences.getBoolean(NOTIFICATION_TOAST,false);

                    if (toast) Toast.makeText(MainActivity.this, "New Estate Added", Toast.LENGTH_SHORT).show();

                    refreshList();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        ListView listView = (ListView) findViewById(R.id.lv_main_list_items);

        if (listView != null) {
            ArrayAdapter<Estate> adapter = (ArrayAdapter<Estate>) listView.getAdapter();

            if (adapter != null) {

                try {
                    adapter.clear();
                    List<Estate> actors = getDatabaseHelper().getEstateDao().queryForAll();

                    adapter.addAll(actors);
                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private void selectItemFromDrawer(int position) {
        if (position == 0) {
            // MainListActivity

        } else if (position == 1) {
            // SettingsActivity
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));

        }

        drawerNavList.setItemChecked(position,true);
        setTitle(items.get(position).getTitle());
        drawerLayout.closeDrawer(drawerPane);
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }

        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}

