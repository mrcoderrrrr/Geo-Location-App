package com.example.photocapture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photocapture.Fragments.Button_fragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class Capture_img extends AppCompatActivity {
    ImageView back, imageView;
    TextView lat,lang;
    Button save;

    FusedLocationProviderClient fusedLocationProviderClient;
    int CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_img);
        back = findViewById(R.id.back);
        imageView = findViewById(R.id.camera);
        save = findViewById(R.id.save);
        lat=findViewById(R.id.lat);
        lang = findViewById(R.id.lang);
        backbutton();
        takepic();
        getCurrentLocation();
        saveImage();
    }


    //get Location
    private void getCurrentLocation() {
        fusedLocationProviderClient =LocationServices.getFusedLocationProviderClient(Capture_img.this);
//Check our Condition
        if (ActivityCompat.checkSelfPermission(Capture_img.this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Capture_img.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            //call method
            getMyLocation();
        }
        else{
            ActivityCompat.requestPermissions(Capture_img.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }
    @SuppressLint("MissingPermission")
    private void getMyLocation() {

    //Location Manager
        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            //when both condition is true then
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {
                    Location location=task.getResult();
                    lat.setText(String.valueOf(location.getLatitude()));
                    lang.setText(String.valueOf(location.getLongitude()));
                }
            });
        }

    }

//save image in gallery
    private void saveImage() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable=(BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap=drawable.getBitmap();
                OutputStream outputStream;
                File filepath= Environment.getExternalStorageDirectory();
                File dir=new File(filepath.getAbsoluteFile() + "/SaveImage");
                dir.mkdir();
                String fileName=System.currentTimeMillis()+".jpg";
                File file=new File(dir,fileName);

                try {
                    outputStream=new FileOutputStream(file);
                    //Bitmap image
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                   //flush or close
                    outputStream.flush();
                    outputStream.close();
                    //image view in gallery
                    Intent imageView=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    imageView.setData(Uri.fromFile(file));
                    sendBroadcast(imageView);
                    Toast.makeText(Capture_img.this,"Save in Gallery",Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //ImageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(image);
        }
    }
//Open Camera
    private void takepic() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_GRANTED) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CODE);
                    } else {
                        ActivityCompat.requestPermissions(Capture_img.this,
                                new String[]{Manifest.permission.CAMERA}, CODE);
                    }
                }
            }
        });

    }
//back button
    private void backbutton(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(getApplicationContext(),MainActivity.class);
              startActivity(intent);
            }
        });
    }
    }
