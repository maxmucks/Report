package org.deafop.deafopreport.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.deafop.deafopreport.R;
import org.deafop.deafopreport.Report;
import org.deafop.deafopreport.ReportUtil;

public class ReportActivity extends AppCompatActivity {
    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mDatabaseReference;
    TextView textTVTitle;
    TextView textTVProject;
    TextView textTVDate;
    TextView textTVDescription;
    Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;
        textTVDate = findViewById(R.id.textTVDate);
        textTVDescription = findViewById(R.id.textTVDescription);
        textTVProject = findViewById(R.id.textTVProject);
        textTVTitle = findViewById(R.id.textTVtitle);
        Intent intent = getIntent();
        Report report = (Report) intent.getSerializableExtra("Report");
        if (report==null){
            report = new Report();
        }
        this.report = report;
        textTVTitle.setText(report.getTitle());
        textTVProject.setText(report.getProject());
        textTVDate.setText(report.getDate());
        textTVDescription.setText(report.getDescription());
    }
}
