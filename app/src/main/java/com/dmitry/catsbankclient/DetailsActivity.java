package com.dmitry.catsbankclient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmitry.catsbankclient.controllers.CatsController;
import com.dmitry.catsbankclient.services.Client;
import com.dmitry.catsbankclient.services.ImageFilePath;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    private boolean redactFlag;
    private Uri chosenImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle b = getIntent().getExtras();
        final int idCat;

        if (b != null) {
            idCat = b.getInt("id");
            TextView idView = (TextView) findViewById(R.id.det_id_textView);
            idView.setText("id: " + idCat);

            final String text;
            text = b.getString("text");
            final EditText editText = (EditText) findViewById(R.id.det_editText_about_cat);
            editText.setText(text);

            if (!Objects.equals(b.getString("photo"), null)) {
                String photoName;
                photoName = b.getString("photo");
                ImageView image = (ImageView) this.findViewById(R.id.det_imageView);
                Glide
                        .with(this)
                        .load(Client.baseUrl + "photo/{name}?name=" + photoName)
                        .into(image);
            }

            final Button btnSelectImage = (Button) findViewById(R.id.det_btn_select_image);
            final Button btnSave = (Button) findViewById(R.id.det_btn_save);
            redactFlag = false;
            Button redact = (Button) findViewById(R.id.det_btn_redact);
            redact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!redactFlag) {
                        btnSelectImage.setEnabled(true);
                        btnSave.setEnabled(true);
                        editText.setEnabled(true);
                    } else {
                        btnSelectImage.setEnabled(false);
                        btnSave.setEnabled(false);
                        editText.setEnabled(false);
                    }
                }
            });

            btnSelectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, 2);
                }
            });
            final CatsController controller = new CatsController(DetailsActivity.this);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String realPath = "";
                    if (chosenImageUri != null) {
                        realPath = ImageFilePath.getPath(DetailsActivity.this, chosenImageUri);
                    }
                    String text = ((EditText) findViewById(R.id.det_editText_about_cat)).getText().toString();
                    controller.updateCat(idCat, text, realPath);
                }
            });

            Button btnDel = (Button) findViewById(R.id.det_btn_del);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                controller.deleteCat(idCat);
                }
            });

            Button btnBack = (Button) findViewById(R.id.det_btn_back);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                chosenImageUri = resultData.getData();
                String path = ImageFilePath.getPath(this, chosenImageUri);
                String[] elem = path.split("/");
                TextView imageName = (TextView) findViewById(R.id.det_textView_select_image);
                imageName.setText("Image name - " + elem[elem.length - 1]);
                ImageView imageView = (ImageView) findViewById(R.id.det_imageView);
                Glide
                        .with(this)
                        .load(chosenImageUri)
                        .into(imageView);
            }
        }
    }
}
