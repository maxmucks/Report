package org.deafop.deafopreport;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.deafop.deafopreport.Activities.ReportActivity;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    ArrayList<Report> repot;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    public ReportAdapter(){

        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;
        this.repot = ReportUtil.mRepot;
        repot.clear();
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Report rp = dataSnapshot.getValue(Report.class);
                rp.setId(dataSnapshot.getKey());
                Log.d("Report:", rp.getTitle());
                repot.add(rp);
                notifyItemInserted(repot.size()-1);
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.rv_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = repot.get(position);
        holder.Bind(report);

    }

    @Override
    public int getItemCount() {
        return repot.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rpTitle,rpDate,rpManagerName;
        ImageView rpReportShare;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rpTitle = itemView.findViewById(R.id.text_title);
            rpDate = itemView.findViewById(R.id.text_date);
            rpManagerName = itemView.findViewById(R.id.text_manager_name);
            rpReportShare = itemView.findViewById(R.id.img_report_share);
            itemView.setOnClickListener(this);
        }

        public void Bind(Report report) {
            rpTitle.setText(report.getTitle());
            rpDate.setText(report.getDate());
            rpManagerName.setText(report.getManager());
           String share = report.getTitle() + report.getDate() + report.getDescription();
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            Report selectedReport = repot.get(position);
            Intent intent = new Intent(v.getContext(), ReportActivity.class);
            intent.putExtra("Report", selectedReport);
            v.getContext().startActivity(intent);
        }
    }
}
