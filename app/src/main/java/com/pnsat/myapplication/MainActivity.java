package com.pnsat.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private EditText userEditText, passEditText;
    private Button singInButton, singUpButton;
    private String userString, passwordString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText);
        passEditText = (EditText) findViewById(R.id.editText3);
        singInButton = (Button) findViewById(R.id.button2);
        singUpButton = (Button) findViewById(R.id.button);

        //get event from
        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, SingupMain2Activity.class));
            }
        });
        singInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userString = userEditText.getText().toString().trim();
                passwordString = passEditText.getText().toString().trim();
                if (userString.equals("")|| passwordString.equals("")) {
                    //MyAlert myAlert = new MyAlert(MainActivity.this, "มีช่องว่าง", "กรุณากรอกทุกช่องค่ะ");
                    //myAlert.myDialog();
                }

            } //on click
        });



    }  // Medthod

}  // Main Class
