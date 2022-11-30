package com.example.myproject.Functions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myproject.EnteringApp.DatabaseHelper;
import com.example.myproject.EnteringApp.LoginActivity;
import com.example.myproject.EnteringApp.RegisterActivity;
import com.example.myproject.R;

public class NotesFragment extends Fragment {
    DatabaseHelper db;
    EditText doctor;
    EditText medicine;
    EditText nextVisit;
    EditText Hospital;
    Button SaveButton;
    String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            doctor = (EditText)view.findViewById(R.id.doctor);
            medicine = (EditText)view.findViewById(R.id.medicine);
            nextVisit = (EditText)view.findViewById(R.id.nextVisit);
            Hospital = (EditText)view.findViewById(R.id.Hospital);
            SaveButton = (Button)view.findViewById(R.id.saveInfo);

            Bundle params = getActivity().getIntent().getExtras();
            String username= params.getString("username");
            db= new DatabaseHelper(getContext());
            String[] human = db.getUser(username);
            id = human[0];
            Log.e("tag","moje id to: "+id);

            SaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Sdoctor = doctor.getText().toString().trim();
                    String Smedicine = medicine.getText().toString().trim();
                    String SnextVisit = nextVisit.getText().toString().trim();
                    String SHospital = Hospital.getText().toString().trim();

                    Boolean res=db.checkUser2(id);
                    if(res.equals(true)){
                        long val = db.addUser2( id, Sdoctor, Smedicine, SnextVisit, SHospital);
                        if(val > 0) {
                            Toast.makeText(getContext(),"Saved",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),"Try again",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getContext(),"You have already made note",Toast.LENGTH_SHORT).show();


                    }
                }
            });

    }
}

