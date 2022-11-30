package com.example.myproject.Functions;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;

public class SensorFragment  extends Fragment implements SensorEventListener{
    private SensorManager sensorManager;
    Sensor accelerometer;
    Float  Tab[];
    Button Dot;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("tag", "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(SensorFragment.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Tab=new Float[3];

        Dot = (Button) view.findViewById(R.id.RunningDot);



    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("WYNIKI: ", "onSensorChanged X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+ event.values[2]);
                Tab[0]=event.values[0];
                Tab[1]=event.values[1];
                Tab[2]=event.values[2];
        Dot.setX((Tab[0]*110));
        Dot.setY((Tab[1]*100)+1090);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

