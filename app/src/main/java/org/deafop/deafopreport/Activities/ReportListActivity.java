package org.deafop.deafopreport.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.deafop.deafopreport.R;
import org.deafop.deafopreport.Report;
import org.deafop.deafopreport.ReportAdapter;
import org.deafop.deafopreport.ReportUtil;

import static org.deafop.deafopreport.Activities.ReportActivity.SHARE_DESCRIPTION;

public class ReportListActivity extends AppCompatActivity {
    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mDatabaseReference;
    private ImageView rptShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        ReportUtil.openFBReference("report");
        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;
        rptShare = findViewById(R.id.img_report_share);

        RecyclerView rvReport = findViewById(R.id.rvReport);
        final ReportAdapter adapter = new ReportAdapter();
        rvReport.setAdapter(adapter);
        LinearLayoutManager reportLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvReport.setLayoutManager(reportLayoutManager);
    }


}
