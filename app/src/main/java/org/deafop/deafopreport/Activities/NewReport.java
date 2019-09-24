package org.deafop.deafopreport.Activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.deafop.deafopreport.R;
import org.deafop.deafopreport.Report;
import org.deafop.deafopreport.ReportUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewReport extends AppCompatActivity {

    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mDatabaseReference;
    public DatabaseReference mProjectReference;
    Spinner spinProject;
    EditText txtTitle, txtDescription, txtManagersName;
    Button btn_Save, btn_date;
    protected static TextView text_DisplayDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);
        // Write a message to the database
        ReportUtil.openFBReference("report");
        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;
        mProjectReference = mFirebaseDatabase.getReference().child("projects");
         txtTitle = findViewById(R.id.edit_title);
         txtDescription = findViewById(R.id.edit_description);
         txtManagersName = findViewById(R.id.edit_managers_name);

         btn_Save = findViewById(R.id.btn_save_report);
         text_DisplayDate = findViewById(R.id.text_Display_Date);
         btn_date = findViewById(R.id.btn_date);
         assert btn_date !=null;
         btn_date.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatePickerFragment mDatePicker = new DatePickerFragment();
                 mDatePicker.show(getSupportFragmentManager(),"Select Date");
                // mDatePicker.show(getFragmentManager(), "Select date");

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
            text_DisplayDate.setText( String.valueOf(year) + " - " + String.valueOf(month) + " - " + String.valueOf(day));
        }
    }
    private void clean() {
        txtManagersName.setText("");
        txtTitle.setText("");
        txtDescription.setText("");
        txtManagersName.requestFocus();
    }

    private void saveReport() {


        String project = spinProject.getSelectedItem().toString();
        String manager = txtManagersName.getText().toString();
        String title = txtTitle.getText().toString();
        String date = text_DisplayDate.getText().toString();
        String description = txtDescription.getText().toString();
        Report report = new Report(project, manager, title,date, description,"");
        mDatabaseReference.push().setValue(report);
    }
}
