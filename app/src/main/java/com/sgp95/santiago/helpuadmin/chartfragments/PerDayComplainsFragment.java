package com.sgp95.santiago.helpuadmin.chartfragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sgp95.santiago.helpuadmin.R;
import com.sgp95.santiago.helpuadmin.model.Complain;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.communication.IOnItemFocusChangedListener;
import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PerDayComplainsFragment extends ChartFragment {
    private PieChart pieChart;
    private DatabaseReference mFirebaseDatabase;
    private Date dateToday;
    private String today;
    private SimpleDateFormat dateFormat;
    private int complainCounter;

    public PerDayComplainsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChart = view.findViewById(R.id.piechart);

        Calendar calendar = Calendar.getInstance();
        dateToday = calendar.getTime();

        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        today = dateFormat.format(dateToday);

        complainCounter = 0;

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("complaint");

        mFirebaseDatabase.orderByChild("complainId").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Complain complain = dataSnapshot.getValue(Complain.class);
                //Log.d("rastro",today.substring(0,10)+"  "+complain.getDateCreated().substring(0,10));
                String dateToday, dateComplain;
                dateToday = today.substring(0,10);
                dateComplain = complain.getDateCreated().substring(0,10);
                if(dateToday.equals(dateComplain)){
                    Log.d("rastro","fechas igual");
                    complainCounter = complainCounter+1;
                    load(complainCounter);

                }else {
                    Log.e("rastro","fechas diferentes");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        pieChart.startAnimation();
    }

    @Override
    public void restartAnimation() {
        pieChart.startAnimation();

    }

    @Override
    public void onReset() {

    }

    private void load(int count){

        pieChart.clearChart();
        pieChart.setInnerValueUnit("");
        pieChart.addPieSlice(new PieModel("Quejas", count, Color.parseColor("#FE6DA8")));

        pieChart.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
            @Override
            public void onItemFocusChanged(int _Position) {

            }
        });
    }
}
