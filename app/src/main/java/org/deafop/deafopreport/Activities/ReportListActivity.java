package org.deafop.deafopreport.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.deafop.deafopreport.R;
import org.deafop.deafopreport.ReportAdapter;
import org.deafop.deafopreport.ReportUtil;
import org.deafop.deafopreport.models.InternetDialog;

public class ReportListActivity extends AppCompatActivity {
    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(this).getInternetStatus()){
            Toast.makeText(this, "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();

        }
        ReportUtil.openFBReference("report");
        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;

        RecyclerView rvReport = findViewById(R.id.rvReport);
        final ReportAdapter adapter = new ReportAdapter();
        rvReport.setAdapter(adapter);
        LinearLayoutManager reportLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvReport.setLayoutManager(reportLayoutManager);
    }

}
