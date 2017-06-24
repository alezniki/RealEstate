package com.nikola.zadataktest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.nikola.zadataktest.R;
import com.nikola.zadataktest.db.DatabaseHelper;
import com.nikola.zadataktest.db.Estate;

import java.sql.SQLException;

public class DetailActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private Estate estate;

    private EditText etName;
    private EditText etDescription;
    private EditText etAddress;
    private EditText etPhoneNumber;
    private EditText etQuadrature;
    private EditText etRoomsNumber;
    private EditText etPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);

        int keyID = getIntent().getExtras().getInt(MainActivity.KEY_ID);
        try {
            estate = getDatabaseHelper().getEstateDao().queryForId(keyID);

            etName = (EditText)findViewById(R.id.et_detail_name);
            etDescription = (EditText)findViewById(R.id.et_detail_description);
            etAddress = (EditText)findViewById(R.id.et_detail_address);
            etPhoneNumber = (EditText)findViewById(R.id.et_detail_phone_number);
            etQuadrature = (EditText)findViewById(R.id.et_detail_quadrature);
            etRoomsNumber = (EditText)findViewById(R.id.et_detail_rooms_number);
            etPrice = (EditText)findViewById(R.id.et_detail_price);

            etName.setText(estate.getName());
            etDescription.setText(estate.getDescription());
            etAddress.setText(estate.getAddress());
            etPhoneNumber.setText(estate.getPhoneNumber());
            etQuadrature.setText(String.valueOf(estate.getQuadrature()));
            etRoomsNumber.setText(String.valueOf(estate.getRoomsNumber()));
            etPrice.setText(String.valueOf(estate.getPrice()));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//
//        if (id == R.id.action_edit) {
//            return true;
//        }

        switch (id) {
            case R.id.action_edit:
               editEstateItem();
                break;
            case R.id.action_delete:
                deleteEstateItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteEstateItem() {
        try {
            getDatabaseHelper().getEstateDao().delete(estate);

            finish();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editEstateItem() {
        estate.setName(etName.getText().toString());
        estate.setDescription(etDescription.getText().toString());
        estate.setAddress(etAddress.getText().toString());
        estate.setPhoneNumber(etPhoneNumber.getText().toString());
        estate.setQuadrature(Double.parseDouble(etQuadrature.getText().toString()));
        estate.setRoomsNumber(Integer.parseInt(etRoomsNumber.getText().toString()));
        estate.setPrice(Double.parseDouble(etPrice.getText().toString()));

        try {
            getDatabaseHelper().getEstateDao().update(estate);

            finish();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
