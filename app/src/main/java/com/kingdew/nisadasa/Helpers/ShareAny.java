package com.kingdew.nisadasa.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ShareAny extends AsyncTask<URL,Void, Bitmap> {

    String body;
    AsyncTask mMyTask;
    ProgressDialog mProgressDialog;
    Context context;
    Uri file;

    public ShareAny(Context context, String postBody) {
        this.body=postBody;
        this.context=context;
    }

    protected void onPreExecute(){
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("AsyncTask");
        mProgressDialog.setMessage("Please wait, we are downloading your image file...");
        mProgressDialog.show();
    }
    protected Bitmap doInBackground(URL...urls){
        URL url = urls[0];
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            return BitmapFactory.decodeStream(bufferedInputStream);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    // When all async task done
    protected void onPostExecute(Bitmap result){
        // Hide the progress dialog
        mProgressDialog.dismiss();
        if(result!=null){
            String app="https://play.google.com/store/apps/details?id=com.kingdew.nisadasarana";
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), result, "nisadas_arana", null);
            file=Uri.parse(path);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/html");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared from Nisadasa App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, body+"\nShared by Nisadasa\n"+app);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
            context.startActivity(Intent.createChooser(shareIntent, "Share The Nisadas"));

            //mImageView.setImageBitmap(result);
        } else {
            // Notify user that an error occurred while downloading image
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }


}
