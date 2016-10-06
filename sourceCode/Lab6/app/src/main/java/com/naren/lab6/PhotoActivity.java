package com.naren.lab6;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PhotoActivity extends AppCompatActivity {
    int TAKE_PHOTO_CODE = 0;
    ImageView displayImg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ImageView capture = (ImageView) findViewById(R.id.imageView);
        displayImg = (ImageView) findViewById(R.id.imageView);
        //Button click eventlistener. Initializes the camera.
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            displayImg.setImageBitmap(photo);

            //store the image to the local storage of the app.
            try{
                FileOutputStream out = new FileOutputStream(new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),"img.jpg"));
                photo.compress(Bitmap.CompressFormat.PNG,100,out);
                out.flush();
                out.close();
            }
            catch (Exception ex){

            }
        }
        else{
            Uri imagePath = data.getData();
            displayImg.setImageURI(imagePath);

            Bitmap map = null;
            Bitmap tumbnailImg = null;

            //get the pic from the storage.
            try{
                final int Size_Of_Image = 50;

                InputStream inputStream = this.getContentResolver().openInputStream(imagePath);
                map = BitmapFactory.decodeStream(inputStream);


                tumbnailImg = ThumbnailUtils.extractThumbnail(map,Size_Of_Image,Size_Of_Image);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            try {
                FileOutputStream out = new FileOutputStream(new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "img.jpg"));
                tumbnailImg.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
            }
        }
    }

    public void display(View v){
        Intent intent = new Intent(getApplicationContext(), MapsActivity1.class);
        startActivity(intent);
    }
}
