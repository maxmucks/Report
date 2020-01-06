package org.deafop.ngoreport.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.deafop.ngoreport.R;
import org.deafop.ngoreport.ReportUtil;
import org.deafop.ngoreport.models.InternetDialog;
import org.deafop.ngoreport.models.TeamAdapter;

public class TeamActivity extends AppCompatActivity {
    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(this).getInternetStatus()){
            Toast.makeText(this, "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();

        }
        ReportUtil.openFBReference("users");
        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;

        RecyclerView rvTeam = findViewById(R.id.rvTeam);
        final TeamAdapter adapter = new TeamAdapter();
        rvTeam.setAdapter(adapter);
        LinearLayoutManager reportLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTeam.setLayoutManager(reportLayoutManager);
    }
}
