package com.example.myproject.Functions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myproject.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SpineFragment extends Fragment implements SensorEventListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spine, container, false);
    }


    DrawView drawView;
    private Button start;
    private Button start2;
    private Button save;
    private Button Dot;
    private Button Dot2;
    Integer time=0;

    RelativeLayout mainLayout;

    Integer xDelta;
    Integer yDelta;

    private SensorManager sensorManager;
    Sensor accelerometer;
    Float  Tab[];
    Integer i=240;

    String TabXString;
    String TabYString;
    List TabX;
    List TabY;

    String option;
    Date currentTime = Calendar.getInstance().getTime();

    String fileName="Spine.txt";
    File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainLayout= (RelativeLayout) view.findViewById(R.id.drawSpine);
        start = (Button) view.findViewById(R.id.start);
        start2 = (Button) view.findViewById(R.id.start2);
        save = (Button) view.findViewById(R.id.saveSpine);

        Dot = (Button) view.findViewById(R.id.startMoving);
        Dot2 = (Button) view.findViewById(R.id.startMoving2);

        Log.d("tag", "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(SpineFragment.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Tab=new Float[3];
        TabX=new ArrayList();
        TabY=new ArrayList();
        start.setOnTouchListener(onTouchListener());
        start2.setOnTouchListener(onTouchListener2());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Zawartosc tablicy", TabX.get(0)+" "+TabX.get(1)+" "+TabX.get(2)+" "+TabX.get(3)+" "+TabX.get(4)+" "+TabX.get(5)+" "+TabX.get(6));
                String filename="Spine";
                saveTextAsFile();
                TabX.clear();
                TabY.clear();
            }
        });
    }

    private View.OnTouchListener onTouchListener() {

        return new View.OnTouchListener() {


            @RequiresApi(api = Build.VERSION_CODES.P)
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:

                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();

                        view.setLayoutParams(layoutParams);
                        break;
                }
                Double myX = ((9.78+(9.78-Tab[0]))*120-600);
                if(Tab[1]<0){
                    Dot.setX(myX.floatValue());
                    Dot2.setX(myX.floatValue());}
                else{
                    Dot.setX((Tab[0]*120-600));
                    Dot2.setX((Tab[0]*120-600));
                }
                option="rotation";
                Dot2.setY(i++);
                Dot.setY(i);
                TabXString=String.valueOf(Dot2.getX());
                TabYString=String.valueOf(Dot2.getY());
                TabX.add(TabXString);
                TabY.add(TabYString);
                drawView = new DrawView(getContext(), Dot2, Dot);
                mainLayout.addView(drawView);
                mainLayout.invalidate();
                return false;
            }



            };

    }

    private View.OnTouchListener onTouchListener2() {

        return new View.OnTouchListener() {


            @RequiresApi(api = Build.VERSION_CODES.P)
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:

                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();

                        view.setLayoutParams(layoutParams);
                        break;
                }
                option="shape";
                Dot.setX((Tab[2]*50+500));
                Dot2.setX((Tab[2]*50+500));
                Dot2.setY(i++);
                Dot.setY(i);

                TabXString=String.valueOf(Dot2.getX());
                TabYString=String.valueOf(Dot2.getY());
                TabX.add(TabXString);
                TabY.add(TabYString);


                drawView = new DrawView(getContext(), Dot2, Dot);
                mainLayout.addView(drawView);
                mainLayout.invalidate();
                return false;
            }



        };

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("WYNIKI: ", "onSensorChanged X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+ event.values[2]);
        Tab[0]=event.values[0];
        Tab[1]=event.values[1];
        Tab[2]=event.values[2];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void saveTextAsFile(){


        try{
            FileWriter fos = new FileWriter(file, true);
            BufferedWriter myOutWriter = new BufferedWriter(fos);/*
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);*/
            Log.e("Zawartosc tablicy", TabX.get(0)+" "+TabX.get(1)+" "+TabX.get(2)+" "+TabX.get(3)+" "+TabX.get(4)+" "+TabX.get(5)+" "+TabX.get(6));
            myOutWriter.append("\n\r");
            myOutWriter.append(currentTime+" --> Spine "+option);
            myOutWriter.append("\n\r");
            myOutWriter.append(" --> OX values: ");
            for (Object s : TabX) {
                myOutWriter.append(s+", ");
            }
            myOutWriter.append("\n\r");
            myOutWriter.append(" --> OY values: ");
            for (Object s : TabY) {
                myOutWriter.append(s+", ");
            }

            myOutWriter.close();
            fos.flush();
            fos.close();

            Toast.makeText(getContext(), "saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Saved in file Spine.txt", Toast.LENGTH_SHORT).show();
        }
    }
}