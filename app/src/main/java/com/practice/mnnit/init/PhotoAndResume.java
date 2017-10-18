package com.practice.mnnit.init;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoAndResume extends Fragment implements View.OnClickListener{

    private String s = null;
    private String showStatus = null;
    private String reg = null;
    private View view;
    private Button ImageUpload, ImageDownload, PdfUpload, PdfDownload;

    public static final String PDF_UPLOAD_HTTP_URL1 = "http://192.168.43.25/pdf.php";
    public static final String PDF_UPLOAD_HTTP_URL2 = "http://192.168.43.25/add_image.php";
    public int PDF_REQ_CODE1 = 1;
    public int PDF_REQ_CODE2 = 2;
    private String PdfNameHolder, PdfPathHolder, PdfID;
    private Uri uri;
    private Bitmap bitmap;
    private ImageView iv;

    public PhotoAndResume() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_photo_and_resume, container, false);

        ImageUpload = (Button)view.findViewById(R.id.button24);
        ImageDownload = (Button)view.findViewById(R.id.button23);
        PdfUpload = (Button)view.findViewById(R.id.button25);
        PdfDownload = (Button)view.findViewById(R.id.button26);
        iv = (ImageView)view.findViewById(R.id.imageView2);

        if (getArguments() != null) {
            s = getArguments().getString("data");
            showStatus = getArguments().getString("status");
        }

        getImage(1);



        Log.d("S",s);
        JSONObject jobj = null;
        try {
            jobj = new JSONObject(s);
            reg = jobj.getString("reg_no");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        PdfDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getPdf();
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    JSONObject jobj = new JSONObject(s);
                    Log.d("register", reg);
                    intent.setData(Uri.parse(jobj.getString("resume")));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

            PdfUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("HEYYYYYYYYYYYYY","VVVVVVVVVVVVVVVVVVVV");
                    Intent intent = new Intent();

                    intent.setType("application/pdf");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE1);
                }
            });

        ImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String updatable = null;
                try {
                    JSONObject j = new JSONObject(s);
                    updatable = j.getString("updatable");
                    //Log.d("UPDATABLE", updatable);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                if(showStatus.compareTo("1") == 0 && updatable.compareTo("0") == 0)
                {
                    Snackbar snackbar = Snackbar
                            .make(view, "Last Date for Updating Details is Over", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }

                else
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select image"), PDF_REQ_CODE2);

                }
            }
        });

        ImageDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage(2);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            try {
                iv.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                PdfUploadFunction();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PDF_REQ_CODE2 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            try {
                ImageUploadFunction();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void ImageUploadFunction() throws JSONException {

        JSONObject jobj = new JSONObject(s);
        PdfNameHolder = jobj.getString("reg_no");

        // Getting file path using Filepath class.
        PdfPathHolder = FilePath.getPath(getActivity(), uri);

        Log.d("CHECKKKKK",PdfPathHolder);
        // If file path object is null then showing toast message to move file into internal storage.
        if (PdfPathHolder == null) {

            Toast.makeText(getActivity(), "Please move your Image file to internal storage & try again.", Toast.LENGTH_LONG).show();

        }
        // If file path is not null then PDF uploading file process will starts.
        else {

            try {

                PdfID = UUID.randomUUID().toString();
                // Log.d("CHECK",PdfID);

                new MultipartUploadRequest(getActivity(), PdfID, PDF_UPLOAD_HTTP_URL2)
                        .addFileToUpload(PdfPathHolder, "jpg")
                        .addParameter("name", PdfNameHolder)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload();

            } catch (Exception exception) {

                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void PdfUploadFunction() throws JSONException {

        Log.d("SSSSSSSSSSSSSSSSSS",s);
        JSONObject jobj = new JSONObject(s);
        PdfNameHolder = jobj.getString("reg_no");

        PdfPathHolder = FilePath.getPath(getActivity(), uri);

        if (PdfPathHolder == null) {
        }
        else {
            try {
                PdfID = UUID.randomUUID().toString();
                Log.d("CHECK",PdfID);
                new MultipartUploadRequest(getActivity(), PdfID, PDF_UPLOAD_HTTP_URL1)
                        .addFileToUpload(PdfPathHolder, "pdf")
                        .addParameter("name", PdfNameHolder)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload();

            } catch (Exception exception) {}
        }
    }

    @Override
    public void onClick(View view) {

    }

    /*void getPdf()
    {
        final JSONObject j = new JSONObject();
        String json = null;
        try {
            JSONObject jobj = new JSONObject(s);
            j.put("reg_no", jobj.getString("reg_no"));
            json = j.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalJson = json;
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    //progressDialog.setMessage("Fetching Pdfs... Please Wait");
                    //progressDialog.show();

                    URL url = new URL("http://192.168.43.25/sendPdf.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    OutputStream os = con.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write(("get_pdf=" + finalJson));

                    bw.flush();
                    bw.close();
                    os.close();


                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line,info="";
                    while ((line = in.readLine()) != null) {
                        info += line;
                    }
                    in.close();
                    Log.d("HEY","A" + info);
                    return info;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                JSONObject jobj = null;
                Log.d("register", reg);
                intent.setData(Uri.parse("http://192.168.43.25/PdfUploadFolder/"+reg+".pdf"));
                startActivity(intent);



            }
        }.execute();
    }*/

    void getImage(int code) {
        final int CODE = code;
        /*final JSONObject j = new JSONObject();
        String json = null;
        try {
            j.put("reg_no", "20155114");
            json = j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

//        final String finalJson = json;
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                try {

  /*                  URL url = new URL("http://192.168.43.25/send_image.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    OutputStream os = con.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write(("get_pdf=" + finalJson));

                    bw.flush();
                    bw.close();
                    os.close();


                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line, info = "";
                    while ((line = in.readLine()) != null) {
                        info += line;
                    }
                    in.close();


*/
                    JSONObject j = new JSONObject(s);
                    URL url1 = new URL(j.getString("photo"));
                    bitmap = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;


            }

            @Override
            protected void onPostExecute(String k) {
                super.onPostExecute(k);
                iv.setImageBitmap(bitmap);
                if(CODE==2)
                {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        JSONObject jobj = new JSONObject(s);
                        Log.d("register", reg);
                        intent.setData(Uri.parse(jobj.getString("photo")));
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.execute();
    }
}
