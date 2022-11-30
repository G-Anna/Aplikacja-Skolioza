package com.example.myproject.Functions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class GoniometerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goniometer, container, false);
    }

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private Button load;
    private Button save;


    String mFilePath;
    private static int RESULT_LOAD_IMG = 1;
    String imgpath,storedpath;
    SharedPreferences sp;
    ImageView myImage;
    RelativeLayout mainLayout;

    Integer xDelta;
    Integer yDelta;

    DrawView drawView;
    DrawView drawView2;
    Double angle;
    TextView screen;


    /* Storage Permissions */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainLayout= (RelativeLayout) view.findViewById(R.id.photo);
        image1 = (ImageView) view.findViewById(R.id.Point1);
        image2 = (ImageView) view.findViewById(R.id.Point2);
        image3 = (ImageView) view.findViewById(R.id.Point3);
        image4 = (ImageView) view.findViewById(R.id.LineCheck1);
        image5 = (ImageView) view.findViewById(R.id.LineCheck2);
        screen = (TextView) view.findViewById(R.id.screen);
        load = (Button) view.findViewById(R.id.btnChangeImage);
        save = (Button) view.findViewById(R.id.screen_shot);


        image1.setOnTouchListener(onTouchListener());
        image2.setOnTouchListener(onTouchListener());
        image3.setOnTouchListener(onTouchListener());
        image4.setOnTouchListener(onTouchListener());
        image5.setOnTouchListener(onTouchListener());



        myImage = (ImageView) view.findViewById(R.id.imgView);
        sp=( SharedPreferences) getContext().getSharedPreferences("setback", Context.MODE_PRIVATE);
        if(sp.contains("imagepath")) {
            storedpath=sp.getString("imagepath", "");
            myImage.setImageBitmap(BitmapFactory.decodeFile(storedpath));
        }
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                loadImagefromGallery(view);

            }});
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                View rootView = ((Activity) getContext()).getWindow().getDecorView().findViewById(R.id.photo);
                Bitmap bitmap = getScreenShot(rootView);
                store(bitmap, "screenshot.png");
            }});

    }

    /* Uploading a picture form album */

    public void loadImagefromGallery (View view){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                try {
                    // When an Image is picked
                    if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK
                            && null != data) {
                        // Get the Image from data

                        Uri selectedImage = data.getData();
                        String[] filePathColumn = { MediaStore.MediaColumns.DATA };

                        // Get the cursor
                        Cursor cursor = (Cursor) getContext().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgpath = cursor.getString(columnIndex);
                        Log.d("path", imgpath);
                        cursor.close();

                        SharedPreferences.Editor edit=sp.edit();
                        edit.putString("imagepath",imgpath);
                        edit.commit();


                        Bitmap myBitmap = BitmapFactory.decodeFile(imgpath);

                        myImage.setImageBitmap(myBitmap);
                    }
                    else {
                        Toast.makeText(getContext(), "You haven't picked Image",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }
            }

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @RequiresApi(api = Build.VERSION_CODES.P)
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        mainLayout.removeView(drawView2);
                        mainLayout.removeView(drawView);
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        angle = angleBetween2Lines(image1, image2, image3);
                        break;

                    case MotionEvent.ACTION_UP:
                        mainLayout.removeView(drawView2);
                        mainLayout.removeView(drawView);
                        angle = angleBetween2Lines(image1, image2, image3);
                        break;

                    case MotionEvent.ACTION_MOVE:

                        mainLayout.removeView(drawView2);
                        mainLayout.removeView(drawView);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        /*if(layoutParams.leftMargin!=(x-xDelta) && layoutParams.topMargin!=(y-yDelta) ){
                                mainLayout.removeView(drawView2);
                                mainLayout.removeView(drawView);
                        }*/
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;

                        view.setLayoutParams(layoutParams);
                        angle = angleBetween2Lines(image1, image2, image3);
                        break;
                }


                drawView = new DrawView(getContext(),image1,image2);
                drawView2 = new DrawView(getContext(),image3,image2);
                mainLayout.addView(drawView2);
                mainLayout.addView(drawView);
                mainLayout.invalidate();
                screen.setText(angle.toString());
                return true;
            }
        };
    }

    public static double angleBetween2Lines(ImageView line1, ImageView line2, ImageView line3)
    {   /* Radian */
        double angle2 = Math.atan2(line2.getTop() - line1.getTop(),
                line2.getLeft() - line1.getLeft());
        double angle1 = Math.atan2(line2.getTop() - line3.getTop(),
                line2.getLeft() - line3.getLeft());
        /* angles */
        return ((angle1-angle2)*57.29);
    }

    public static Bitmap getScreenShot( View view){
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap=Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
   }

    public void store(Bitmap bm, String fileName){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/MyFiles";
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dirPath, fileName);
        try{
            FileOutputStream fos=new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
   }
}