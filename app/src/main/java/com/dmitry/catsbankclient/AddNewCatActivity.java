package com.dmitry.catsbankclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmitry.catsbankclient.controllers.CatsController;
import com.dmitry.catsbankclient.services.ImageFilePath;

public class AddNewCatActivity extends AppCompatActivity {

    private Uri chosenImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cat);

        Button btnSelectPhoto = (Button) findViewById(R.id.btn_select_image);
        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        Button btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatsController controller = new CatsController(AddNewCatActivity.this);

                String realPath = "";
                if (chosenImageUri != null) {
                    realPath = ImageFilePath.getPath(AddNewCatActivity.this, chosenImageUri);
                }
                String text = ((EditText) findViewById(R.id.editText_about_cat)).getText().toString();
                controller.saveCat(text, realPath);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                chosenImageUri = resultData.getData();
                String path = ImageFilePath.getPath(this, chosenImageUri);
                String[] elem = path.split("/");
                TextView imageName = (TextView) findViewById(R.id.textView_select_image);
                imageName.setText("Image name - " + elem[elem.length - 1]);
                ImageView imageView = (ImageView) findViewById(R.id.add_imageView);
                Glide
                        .with(this)
                        .load(chosenImageUri)
                        .into(imageView);
            }
        }
    }

}