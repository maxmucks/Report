package org.deafop.deafopreport.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.deafop.deafopreport.R;
import org.deafop.deafopreport.Report;
import org.deafop.deafopreport.ReportUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportActivity extends AppCompatActivity {
    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference mDatabaseReference;
    private CardView cardReport;
    private Button btnExportPDF;
    private Button btnShare;
    private Bitmap bitmap;
    TextView textTVTitle;
    TextView textTVProject;
    TextView textTVDate;
    TextView textTVDescription;
    Report report;


    public static final String SHARE_DESCRIPTION = "Look at this report from ";
    public static final String HASHTAG_DEAFOPREPORT = " #Deafop";
    String mReportUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mFirebaseDatabase = ReportUtil.mFirebaseDatabase;
        mDatabaseReference = ReportUtil.mDatabaseReference;
        cardReport = findViewById(R.id.card_report);
        btnExportPDF = findViewById(R.id.btn_exportPDF);
        btnShare = findViewById(R.id.btnShare);
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

        btnExportPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size"," "+cardReport.getWidth() +"  "+cardReport.getWidth());
                bitmap = loadBitmapFromView(cardReport, cardReport.getWidth(), cardReport.getHeight());
                createPdf();
            }
        });
        final Report finalReport = report;
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareMsg = SHARE_DESCRIPTION + finalReport.getManager() + "  Project: " + finalReport.getProject() +  "  Title:  " + finalReport.getTitle() +  " Date:  " + finalReport.getDate()+ "  Description  " + finalReport.getDescription() ;
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareMsg );
                startActivity(shareIntent);
            }
        });
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }


    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

    PdfDocument document = new PdfDocument();
    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
    PdfDocument.Page page = document.startPage(pageInfo);

    Canvas canvas = page.getCanvas();

    Paint paint = new Paint();
        canvas.drawPaint(paint);

    bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);


    // write the document content
    String targetPdf = "/sdcard/pdffromlayout.pdf";
    File filePath;
    filePath = new File(targetPdf);
        try {
        document.writeTo(new FileOutputStream(filePath));

    } catch (IOException e) {
        e.printStackTrace();
        Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
    }

    // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

    openGeneratedPDF();
    }


    private void openGeneratedPDF(){
       // File file = new File(Environment.getExternalStorageDirectory().getPath());
        File file = new File("/sdcard/pdffromlayout.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

}
