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

public class PendingComplainsChart extends ChartFragment {

    private PieChart pieChart;
    private int pendingComplains, resolvedComplains,totalComplains;


    private DatabaseReference mFirebaseDatabase;

    public PendingComplainsChart() {
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

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("complaint");


        pendingComplains = 0;
        resolvedComplains = 0;
        totalComplains = 0;

        mFirebaseDatabase.orderByChild("complainId").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Complain complain = dataSnapshot.getValue(Complain.class);
                totalComplains = totalComplains+1;
                if(complain.getState().equals("Pendiente")){
                    pendingComplains = pendingComplains+1;
                }
                else if(complain.getState().equals("Solucionado")){
                    resolvedComplains = resolvedComplains+1;
                }
                load(pendingComplains,resolvedComplains);
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

        //load();
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

    private void load(int pending, int resolved){
        Log.d("rastro","pendientes -> "+pending+" resueltos -> "+resolved);

        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Pendientes", intToPercentage(pending), Color.parseColor("#FE6DA8")));
        pieChart.addPieSlice(new PieModel("Resueltos", intToPercentage(resolved), Color.parseColor("#56B7F1")));

        pieChart.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
            @Override
            public void onItemFocusChanged(int _Position) {

            }
        });
    }

    private float intToPercentage(int count){
        float percentage = (100*count)/totalComplains;
        return percentage;
    }
}
