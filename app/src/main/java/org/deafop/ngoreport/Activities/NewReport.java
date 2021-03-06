package org.deafop.ngoreport.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.deafop.ngoreport.R;
import org.deafop.ngoreport.Report;
import org.deafop.ngoreport.ReportUtil;
import org.deafop.ngoreport.models.InternetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewReport extends AppCompatActivity {

    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mDatabaseReference;
    public DatabaseReference mProjectReference;
    Spinner spinProject;
    EditText txtTitle, txtDescription, txtManagersName;
    protected static EditText btn_date;
    Button btn_Save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        // CALL getInternetStatus() function to check for internet and display error dialog
        if(new InternetDialog(this).getInternetStatus()){
            Toast.makeText(this, "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();

        }
        // Write a message to the database
        ReportUtil.openFBReference("report");
        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;
        mProjectReference = mFirebaseDatabase.getReference().child("projects");
         txtTitle = findViewById(R.id.edit_title);
         txtDescription = findViewById(R.id.edit_description);
         txtManagersName = findViewById(R.id.edit_managers_name);

         btn_Save = findViewById(R.id.btn_save_report);
         btn_date = findViewById(R.id.btn_date);
         assert btn_date !=null;
         btn_date.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatePickerFragment mDatePicker = new DatePickerFragment();
                 mDatePicker.show(getSupportFragmentManager(),"Select Date");

             }
         });


        mProjectReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Is better to use a List, because you don't know the size
            // of the iterator returned by dataSnapshot.getChildren() to
            // initialize the array
            final List<String> project = new ArrayList<>();

            for (DataSnapshot projectSnapshot: dataSnapshot.getChildren()) {
                String projectName = projectSnapshot.getValue(String.class);
                project.add(projectName);
            }

            spinProject = findViewById(R.id.spinner_projects);
            ArrayAdapter<String> projectsAdapter = new ArrayAdapter<>(NewReport.this,
                    android.R.layout.simple_spinner_item, project);
            projectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinProject.setAdapter(projectsAdapter);
            spinProject.setSelection(2);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

         btn_Save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 saveReport();
                 Toast.makeText(NewReport.this, "Report Saved", Toast.LENGTH_LONG).show();
                 clean();
                 return;
             }
         });
    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            btn_date.setText( String.valueOf(year) + " - " + String.valueOf(month + 1) + " - " + String.valueOf(day));
        }
    }
    private void clean() {
        txtManagersName.setText("");
        txtTitle.setText("");
        txtDescription.setText("");
        txtManagersName.requestFocus();
    }

    private void saveReport() {
        if (spinProject.getSelectedItem() == "Select a Donor" ){
            Toast.makeText(NewReport.this, "Please select a Donor!",Toast.LENGTH_LONG).show();

        }
       else if (txtTitle.getText().toString().equals("")){
            Toast.makeText(NewReport.this, "Please Enter The Activity name!",Toast.LENGTH_LONG).show();

        }
        else if (txtDescription.getText().toString().equals("")){
            Toast.makeText(NewReport.this, "Please Enter The Description!",Toast.LENGTH_LONG).show();

        }
        else if (btn_date.getText().toString().equals("")){
            Toast.makeText(NewReport.this, "Please Enter The Date!",Toast.LENGTH_LONG).show();

        }
        else {

        String project = spinProject.getSelectedItem().toString();
        String manager = txtManagersName.getText().toString();
        String title = txtTitle.getText().toString();
        String date = btn_date.getText().toString();
        String description = txtDescription.getText().toString();
        Report report = new Report(project, manager, title,date, description,"");
        mDatabaseReference.push().setValue(report);
    }
    }
}
