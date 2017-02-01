package com.attendanceTracker;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.attendanceTracker.auxiliary.Const;
import com.attendanceTracker.auxiliary.Database;
import com.attendanceTracker.fragments.QRFragment;
import com.attendanceTracker.fragments.RegistrationFragment;
import com.example.attendanceTracker.R;


public class MainActivity extends AppCompatActivity {

    private boolean loaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment;

        Database db = new Database(this);
        if (db.get(Const.STUDENT_ID).equals(Const.NO_ENTRY)) {
            loaded = false;
            fragment = new RegistrationFragment();
        } else {
            loaded = true;
            fragment = new QRFragment();
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (loaded) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.helloworld_menu:
                Toast.makeText(getApplicationContext(), "Hello World", Toast.LENGTH_LONG).show();
                return true;

            case R.id.helloase_menu:
                Toast.makeText(getApplicationContext(), "Hello Ase", Toast.LENGTH_LONG).show();
                return true;

            default: return false;
        }
    }

}
