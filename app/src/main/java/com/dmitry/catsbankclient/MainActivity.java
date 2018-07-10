package com.dmitry.catsbankclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dmitry.catsbankclient.controllers.CatsController;

public class MainActivity extends AppCompatActivity {

    private CatsController catsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catsController = new CatsController(this, findViewById(R.id.lvMain));
        Button btnAddCat = (Button) findViewById(R.id.btn_add_cat);
        btnAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewCatActivity.class);
                startActivity(intent);
            }
        });
        Button btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = ((EditText) findViewById(R.id.editText_search)).getText().toString();
                if (number.equals("")) { // detect an empty string and set it to "0" search
                    catsController.findCatOnListById(0);
                } else {
                    catsController.findCatOnListById(Integer.parseInt(number));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        catsController.getAllCats();
    }
}
