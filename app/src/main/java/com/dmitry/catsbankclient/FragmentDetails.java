package com.dmitry.catsbankclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmitry.catsbankclient.models.ModelCats;
import com.dmitry.catsbankclient.presenter.PresenterCats;
import com.dmitry.catsbankclient.services.Client;
import com.dmitry.catsbankclient.services.ImageFilePath;

import java.util.Objects;

public class FragmentDetails extends Fragment {

    private boolean redactFlag;
    private Uri chosenImageUri;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_details, container,false);
        Bundle b = this.getArguments();
        final int idCat;

        if (b != null) {
            idCat = b.getInt("id");
            TextView idView = (TextView) v.findViewById(R.id.det_id_textView);
            idView.setText("id: " + idCat);

            final String text;
            text = b.getString("text");
            final EditText editText = (EditText) v.findViewById(R.id.det_editText_about_cat);
            editText.setText(text);

            if (!Objects.equals(b.getString("photo"), null)) {
                String photoName;
                photoName = b.getString("photo");
                ImageView image = (ImageView) v.findViewById(R.id.det_imageView);
                Glide
                        .with(this)
                        .load(Client.baseUrl + "photo/{name}?name=" + photoName)
                        .into(image);
            }

            final Button btnSelectImage = (Button) v.findViewById(R.id.det_btn_select_image);
            final Button btnSave = (Button) v.findViewById(R.id.det_btn_save);
            redactFlag = false;
            Button redact = (Button) v.findViewById(R.id.det_btn_redact);
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
            final PresenterCats presenterCats = new PresenterCats();
            presenterCats.setModelCats(new ModelCats(getActivity()));
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String realPath = "";
                    if (chosenImageUri != null) {
                        realPath = ImageFilePath.getPath(v.getContext(), chosenImageUri);
                    }
                    String text = ((EditText) getActivity().findViewById(R.id.det_editText_about_cat)).getText().toString();
                    presenterCats.updateCat(idCat, text, realPath);
                }
            });

            Button btnDel = (Button) v.findViewById(R.id.det_btn_del);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenterCats.deleteCat(idCat);
                }
            });

            Button btnBack = (Button) v.findViewById(R.id.det_btn_back);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });
        }
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                chosenImageUri = resultData.getData();
                String path = ImageFilePath.getPath(getActivity(), chosenImageUri);
                String[] elem = path.split("/");
                TextView imageName = (TextView) v.findViewById(R.id.det_textView_select_image);
                imageName.setText("Image name - " + elem[elem.length - 1]);
                ImageView imageView = (ImageView) v.findViewById(R.id.det_imageView);
                Glide
                        .with(this)
                        .load(chosenImageUri)
                        .into(imageView);
            }
        }
    }
}
