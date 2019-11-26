package org.deafop.deafopreport.models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.deafop.deafopreport.R;
import org.deafop.deafopreport.ReportUtil;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    ArrayList<User> team;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;

    public TeamAdapter() {

        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;
        this.team = ReportUtil.mTeam;
        team.clear();
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               User tm = dataSnapshot.getValue(User.class);
                tm.setUser_id(dataSnapshot.getKey());
                Log.d("User:", tm.getName());
                team.add(tm);
                notifyItemInserted(team.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildListener);

    }

    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.tm_row, parent, false);
        return new TeamAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder holder, int position) {
        User teams = team.get(position);
        holder.Bind(teams);

    }

    @Override
    public int getItemCount() {
        return team.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tmName, tmPhone;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tmName = itemView.findViewById(R.id.text_tm_name);
         //   tmPhone = itemView.findViewById(R.id.text_date);
            itemView.setOnClickListener(this);
        }

        public void Bind(User teams) {
            tmName.setText(teams.getName());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
