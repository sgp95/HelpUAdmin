package com.sgp95.santiago.helpuadmin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sgp95.santiago.helpuadmin.adapter.CommentAdpter;
import com.sgp95.santiago.helpuadmin.model.Complain;
import com.sgp95.santiago.helpuadmin.model.User;

import java.util.ArrayList;
import java.util.List;

public class ComplainFragment extends Fragment implements CommentAdpter.MyItemClickListener{

    private RecyclerView recyclerView;
    private CommentAdpter commentAdpter;
    private List<Complain> complaintList;
    private List<Complain> complaintListReverse;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private Bundle commentsData;
    String userfullname, userImage, userCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_complain,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        recyclerView = view.findViewById(R.id.recycler_view_comment);
        complaintList = new ArrayList<>();
        //final List<Complain> commentList;

        mFirebaseDatabase.child("student").child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String fullName = user.getLastName() + ", " + user.getFirstName();
                setUserData("admin", fullName, user.getImage());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        complaintList = new ArrayList<>();
        //final ArrayList<Complain> complaintListReverse = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentAdpter = new CommentAdpter(recyclerView, complaintList, getContext());
        commentAdpter.setOnItemClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(commentAdpter);


        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Cargando Quejas");
        mProgressDialog.show();


        mFirebaseDatabase.child("complaint").orderByChild("complainId").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Log.d("ChildChanged","seguir");


                Complain complain = dataSnapshot.getValue(Complain.class);
                    final Complain objcomplain = new Complain();

                    objcomplain.setCategory(complain.getCategory());
                    objcomplain.setComplain(complain.getComplain());
                    objcomplain.setComplainImage(complain.getComplainImage());
                    //Log.d(TAG_TEST,complain.getComplainImage());
                    objcomplain.setComplaintId(complain.getComplaintId());
                    objcomplain.setDateCreated(complain.getDateCreated());
                    objcomplain.setPrivacy(complain.getPrivacy());
                    objcomplain.setState(complain.getState());
                    objcomplain.setUserCode(complain.getUserCode());
                    objcomplain.setHeadquarter(complain.getHeadquarter());
                    objcomplain.setmFirebaseDatabase(mFirebaseDatabase);
                    objcomplain.setComCounter(complain.getComCounter());


                    complaintList.add(objcomplain);

                    if (dataSnapshot.exists()) {
                        mProgressDialog.dismiss();
                        commentAdpter.notifyDataSetChanged();
                    }
                mProgressDialog.dismiss();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int index = 0;
                Complain complain = dataSnapshot.getValue(Complain.class);
                for(Complain c:complaintList){
                    if(c!=null && c.getComplaintId().equals(complain.getComplaintId())){
                        try {
                            Log.d("ChildChanged","Item Cambiado");
                            complaintList.set(index,complain);
                            commentAdpter.notifyDataSetChanged();
                        }catch (Exception E){
                            Log.e("ChildChanged","Error en el update");
                        }
                    }
                    index= index+1;
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();
            }

            // ...
        });

    }

    public void setUserData(String userCode, String userFullName, String userImage) {
        this.userCode = userCode;
        this.userfullname = userFullName;
        this.userImage = userImage;
    }

    @Override
    public void onItemClick(Complain complein) {
        //Log.d("rastro",complein+"");
        commentsData = new Bundle();
        commentsData.putString("fullnameuser", userfullname);
        commentsData.putString("userCode", userCode);
        commentsData.putString("userImage", userImage);
        commentsData.putString("commentId", complein.getComplaintId());
        commentsData.putString("category", complein.getCategory());
        commentsData.putString("headquarter", complein.getHeadquarter());
        commentsData.putString("complainImage", complein.getComplainImage());

        mFirebaseDatabase.child("complaint").child(complein.getComplaintId()).child("state").setValue("En Proceso");

        UserCommentsFragment userCommentsFragment = new UserCommentsFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        userCommentsFragment.setArguments(commentsData);
        userCommentsFragment.setUserComplain(complein);
        ft.replace(R.id.content, userCommentsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
