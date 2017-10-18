package com.practice.mnnit.init;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class checkFileSend extends AppCompatActivity {

    private Button b1,b2,b3,b4,b5;
    public static final String IMAGE_UPLOAD_HTTP_URL = "http://192.168.43.25/add_image.php";
    public static final String PDF_UPLOAD_HTTP_URL = "http://192.168.43.25/pdf.php";
    public int PDF_REQ_CODE = 1;
    public int PDF_REQ_CODE1 = 2;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_file_send);

        Toolbar tool = (Toolbar)findViewById(R.id.update1Toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Upload Files");


        RequestRunTimePermission();
        b1 = (Button)findViewById(R.id.button10);
        b2 = (Button)findViewById(R.id.button11);
        b3 = (Button)findViewById(R.id.button20);
        b4 = (Button)findViewById(R.id.button21);
        b5 = (Button)findViewById(R.id.button22);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                // Setting up default file pickup time as PDF.
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select image"), PDF_REQ_CODE1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUploadFunction();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfUploadFunction();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(checkFileSend.this, Login.class);
                startActivity(i);
            }
        });
    }

    public void ImageUploadFunction() {

        String PdfPathHolder = FilePath.getPath(this, uri);

        Log.d("CHECKKKKK",PdfPathHolder);
        // If file path object is null then showing toast message to move file into internal storage.
        if (PdfPathHolder == null) {

            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

        }
        // If file path is not null then PDF uploading file process will starts.
        else {

            try {

                String PdfID = UUID.randomUUID().toString();
                // Log.d("CHECK",PdfID);

                new MultipartUploadRequest(this, PdfID, IMAGE_UPLOAD_HTTP_URL)
                        .addFileToUpload(PdfPathHolder, "jpg")
                        .addParameter("name", getIntent().getStringExtra("reg"))
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload();

            } catch (Exception exception) {

                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            // After selecting the PDF set PDF is Selected text inside Button.
            b3.setText("Resume is Selected");
        }

        if (requestCode == PDF_REQ_CODE1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            // After selecting the PDF set PDF is Selected text inside Button.
            b1.setText("image is Selected");
        }

    }


    public void PdfUploadFunction() {

        String PdfPathHolder = FilePath.getPath(this, uri);
        if (PdfPathHolder == null) {

            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

        }
        else {
            try {
                new MultipartUploadRequest(this, UUID.randomUUID().toString(), PDF_UPLOAD_HTTP_URL)
                        .addFileToUpload(PdfPathHolder, "pdf")
                        .addParameter("name", getIntent().getStringExtra("reg"))
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload();

            } catch (Exception exception) {

                Toast.makeText(checkFileSend.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void RequestRunTimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(checkFileSend.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        } else {
                ActivityCompat.requestPermissions(checkFileSend.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {
            case 1:
                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
        }
    }

}
