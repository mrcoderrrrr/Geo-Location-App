package com.example.photocapture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.photocapture.Fragments.Button_fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button_fragment button_fragment=new Button_fragment();
       FragmentManager manager=getSupportFragmentManager();
      manager.beginTransaction().add(R.id.Relativelayout,button_fragment).commit();

    }
}