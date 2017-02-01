package com.attendanceTracker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendanceTracker.MainActivity;
import com.attendanceTracker.auxiliary.Const;
import com.attendanceTracker.auxiliary.Database;
import com.attendanceTracker.auxiliary.NetUtils;
import com.example.attendanceTracker.R;


/**
 * Created by tom on 01.02.17.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private EditText studentIdEdit;
    private Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, null);

        studentIdEdit = (EditText) view.findViewById(R.id.student_id);
        registerBtn = (Button) view.findViewById(R.id.registration_btn);
        registerBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == registerBtn) {
            String id = studentIdEdit.getText().toString();

            if (id.equals("")) {
                Toast.makeText(getActivity(), "ID is not valid.", Toast.LENGTH_SHORT).show();
            }

            // valid id
            else {
                Database db = new Database(getActivity());
                db.store(Const.STUDENT_ID, id);

                // url: REGISTER_URL + id + /42
                new AsyncTask<Void, Void, String>() {

                    NetUtils net = new NetUtils();
                    Database db = new Database(getActivity());

                    @Override
                    protected String doInBackground(Void... voids) {
                        String answer = net.downloadString(Const.REGISTER_URL + db.get(Const.STUDENT_ID) + "/1", "PUT");
                        Log.d("DEBUG", answer);

                        return answer;
                    }

                    @Override
                    protected void onPostExecute(String qrCodeString) {

                    }

                }.execute();

                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        }
    }
}
