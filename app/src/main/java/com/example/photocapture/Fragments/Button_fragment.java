package com.example.photocapture.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.photocapture.Capture_img;
import com.example.photocapture.R;
import com.example.photocapture.Report_img;


public class Button_fragment extends Fragment {

 Button capture,report;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_button_fragment, container, false);
        capture=view.findViewById(R.id.Capture);
        report=view.findViewById(R.id.Report);

        capture();
        report();

        return view;
    }
//Report OnClick
    private void report() {
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 
                Intent Reportintent=new Intent(getContext(), Report_img.class);
                Reportintent.setFlags(Reportintent.FLAG_ACTIVITY_NEW_TASK | Reportintent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(Reportintent);
            }
        });

    }
//Capture OnClick
    private void capture() {
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Captureintent =new Intent(getContext(), Capture_img.class);
                Captureintent.setFlags(Captureintent.FLAG_ACTIVITY_NEW_TASK | Captureintent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(Captureintent);

            }
        });

    }
}