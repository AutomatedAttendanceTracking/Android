package com.attendanceTracker;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.attendanceTracker.auxiliary.NetUtils;
import com.example.attendanceTracker.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView qrView;
    private ProgressBar spinner;
    private Button loadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrView = (ImageView) findViewById(R.id.qrCode);
        spinner = (ProgressBar) findViewById(R.id.spinner);
        loadBtn = (Button) findViewById(R.id.loadBtn);

        loadBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public void onClick(View view) {
        if (view == loadBtn) {
            new AsyncTask<Void, Void, Bitmap>() {

                NetUtils net = new NetUtils();

                @Override
                protected void onPreExecute() {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                protected Bitmap doInBackground(Void... voids) {
                    return net.downloadImage(NetUtils.DOWNLOAD_URL);
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) {
                        qrView.setImageBitmap(bitmap);
                    }
                    spinner.setVisibility(View.INVISIBLE);
                }

            }.execute();
        }
    }

}
