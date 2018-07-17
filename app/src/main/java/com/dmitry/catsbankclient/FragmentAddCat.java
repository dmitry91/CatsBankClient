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
import com.dmitry.catsbankclient.services.ImageFilePath;

public class FragmentAddCat extends Fragment {

    private Uri chosenImageUri;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_cat, container, false);

        Button btnSelectPhoto = (Button) v.findViewById(R.id.btn_select_image);
        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        Button btnBack = (Button) v.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        Button btnSave = (Button) v.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PresenterCats presenterCats = new PresenterCats();
                presenterCats.setModelCats(new ModelCats(getActivity()));
                String realPath = "";
                if (chosenImageUri != null) {
                    realPath = ImageFilePath.getPath(getActivity(), chosenImageUri);
                }
                String text = ((EditText) getActivity().findViewById(R.id.editText_about_cat)).getText().toString();
                presenterCats.saveCat(text, realPath);
            }
        });
        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                chosenImageUri = resultData.getData();
                String path = ImageFilePath.getPath(getActivity(), chosenImageUri);
                String[] elem = path.split("/");
                TextView imageName = (TextView) v.findViewById(R.id.textView_select_image);
                imageName.setText("Image name - " + elem[elem.length - 1]);
                ImageView imageView = (ImageView) v.findViewById(R.id.add_imageView);
                Glide
                        .with(this)
                        .load(chosenImageUri)
                        .into(imageView);
            }
        }
    }

}
