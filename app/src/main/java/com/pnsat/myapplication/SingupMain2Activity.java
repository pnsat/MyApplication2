package com.pnsat.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

import static com.pnsat.myapplication.R.id.imageView2;

public class SingupMain2Activity extends AppCompatActivity {
    //explisit
    private EditText nameEditText, userEditText, passEditText;
    private ImageView imageView;
    private Button button;
    private String nameString, userString, passwordString, pathImageString, nameImageString;

    private Uri uri;
    private String tag = "masterUng";
    private boolean aBoolean = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_main2);
        //bind widget
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passEditText = (EditText) findViewById(R.id.editText5);
        imageView = (ImageView) findViewById(R.id.imageView2);
        button = (Button) findViewById(R.id.button3);
        //get event click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get value from
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passEditText.getText().toString().trim();

                //check space
                if (nameString.equals("") || userString.equals("") || passwordString.equals("")) {
                    //have space
                    MyAlert myAlert = new MyAlert(SingupMain2Activity.this, "Have Space", "Please Fill All Blank");
                    myAlert.myDialog();

                }else if(aBoolean) {
                    //non choose Image
                    MyAlert myAlert = new MyAlert(SingupMain2Activity.this, "ยังไม่ได้เลือกรูปภาพ", "กรุณาเลือกรูปภาพด้วยครับ");
                    myAlert.myDialog();


            }else {

                    // Image OK
                    upLoadImage();
                    upLoadString();


                }

            } //on click
        });

        //Image controler
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please choose App"), 1);

            }
        });


    }//main medtod

    private void upLoadString() {

        try {

            AdduserTosServer adduserTosServer = new AdduserTosServer(SingupMain2Activity.this,
                    nameString, userString, passwordString,
                    "http://pnsat.com/c/Image" + nameImageString);
            adduserTosServer.execute();



            if (Boolean.parseBoolean(adduserTosServer.get())) {
                finish();
            } else {
                Toast.makeText(SingupMain2Activity.this, "Cannot Upload", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void upLoadImage() {

        try {

            //change policy
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21, "off@swiftcodingthai.com", "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("King3");
            simpleFTP.stor(new File(pathImageString));
            simpleFTP.disconnect();

            Toast.makeText(SingupMain2Activity.this, "Upload Finish", Toast.LENGTH_SHORT).show();



        } catch (Exception e){
            e.printStackTrace();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            aBoolean = false;

            uri = data.getData();
            showImage(uri);

            pathImageString = findPathImage(uri);
            nameImageString = pathImageString.substring(pathImageString.lastIndexOf("/"));
            Log.d(tag, "path ==>" + pathImageString);
            Log.d(tag, "name ==>" + nameImageString);





        }  //if

    }

    private String findPathImage(Uri uri) {

        String result = null;
        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);


        if (cursor != null) {

            cursor.moveToFirst();
            int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(i);


        } else {
            result = uri.getPath();
        }



        return result;
    }

    private void showImage(Uri uri) {

        try {

            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            imageView.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}  //mainclass
