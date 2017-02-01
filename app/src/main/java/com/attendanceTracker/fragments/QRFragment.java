package com.attendanceTracker.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.attendanceTracker.auxiliary.Const;
import com.attendanceTracker.auxiliary.Database;
import com.attendanceTracker.auxiliary.NetUtils;
import com.example.attendanceTracker.R;

import net.glxn.qrgen.android.QRCode;

/**
 * Created by tom on 01.02.17.
 */
public class QRFragment extends Fragment implements View.OnClickListener {

    private ImageView qrView;
    private ProgressBar spinner;
    private Button loadBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code, null);

        qrView = (ImageView) view.findViewById(R.id.qrCode);
        spinner = (ProgressBar) view.findViewById(R.id.spinner);
        loadBtn = (Button) view.findViewById(R.id.loadBtn);
        loadBtn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        if (view == loadBtn) {
            new AsyncTask<Void, Void, String>() {

                NetUtils net = new NetUtils();
                Database db = new Database(getActivity());

                @Override
                protected void onPreExecute() {
                    spinner.setVisibility(View.VISIBLE);

                }

                @Override
                protected String doInBackground(Void... voids) {
                    String answer = net.downloadString(Const.QR_URL + db.get(Const.STUDENT_ID), "POST");
                    Log.d("DEBUG", answer);

                    return answer;
                }

                @Override
                protected void onPostExecute(String qrCodeString) {
                    if (qrCodeString != null) {
                        QRCode qrCode = QRCode.from(qrCodeString).withSize(320, 320);
                        qrView.setImageBitmap(qrCode.bitmap());
                    }
                    spinner.setVisibility(View.INVISIBLE);
                }

            }.execute();
        }
    }
}
