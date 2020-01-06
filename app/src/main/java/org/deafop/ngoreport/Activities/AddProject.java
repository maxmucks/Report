package org.deafop.ngoreport.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.deafop.ngoreport.R;
import org.deafop.ngoreport.ReportUtil;

public class AddProject extends AppCompatActivity {
    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mDatabaseReference;
    EditText newProject;
    Button btnSaveProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        newProject = findViewById(R.id.edit_donor_name);
        btnSaveProject = findViewById(R.id.btn_donor_save);
        ReportUtil.openFBReference("projects");
        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;

        btnSaveProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProject();
                Toast.makeText(AddProject.this, "Project Saved", Toast.LENGTH_LONG).show();
                clean();
            }

        });
    }
    private void clean() {
        newProject.setText("");
        newProject.requestFocus();
    }

    private void saveProject() {
        String project = newProject.getText().toString();
        mDatabaseReference.push().setValue(project);
    }
}
