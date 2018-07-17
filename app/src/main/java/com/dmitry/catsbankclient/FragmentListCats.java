package com.dmitry.catsbankclient;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dmitry.catsbankclient.models.ModelCats;
import com.dmitry.catsbankclient.presenter.PresenterCats;


public class FragmentListCats extends Fragment {

    private PresenterCats presenterCats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_all_cats, container,false);
        presenterCats = new PresenterCats();
        presenterCats.setModelCats(new ModelCats(getActivity(), v));

        Button btnAddCat = (Button) v.findViewById(R.id.btn_add_cat);
        btnAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentAddCat fragmentAddCat = new FragmentAddCat();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragmentAddCat);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button btnSearch = (Button) v.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = ((EditText) getView().findViewById(R.id.editText_search)).getText().toString();
                if (number.equals("")) { // detect an empty string and set it to "0" search
                    presenterCats.findCatOnListById(0);
                } else {
                    presenterCats.findCatOnListById(Integer.parseInt(number));
                }
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenterCats.getAllCats();
    }
}
