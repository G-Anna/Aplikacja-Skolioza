package com.example.myproject.Functions;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;

import com.example.myproject.EnteringApp.DatabaseHelper;
import com.example.myproject.EnteringApp.LoginActivity;
import com.example.myproject.R;

import java.util.List;

public class ProfileFragment extends Fragment {
    TextView Username;
    String Email;
    String Age;
    String username;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginActivity User = new LoginActivity();
        Username = (TextView) view.findViewById(R.id.profileName);
        Bundle params = getActivity().getIntent().getExtras();
         username= params.getString("username");




        DatabaseHelper db = new DatabaseHelper(getContext());
        String[] human =db.getUser(username);
        TextView Email = (TextView) view.findViewById(R.id.profileEmail);
        Email.setText(human[3].toString()+"\n"+ "(age: "+human[2].toString()+") ");

        Username.setText(username);
        String[] human2=db.getUser2(username);
        TextView doctor = (TextView) view.findViewById(R.id.profileDoctor);
        TextView nextVisit = (TextView) view.findViewById(R.id.profileNextVisit);
        TextView Hospital = (TextView) view.findViewById(R.id.profileHospital);
        TextView medicine = (TextView) view.findViewById(R.id.profilemedicine);
        doctor.setText(human2[0].toString());
        medicine.setText(human2[1].toString());
        nextVisit.setText(human2[2].toString());
        Hospital.setText(human2[3].toString());


    }


}
