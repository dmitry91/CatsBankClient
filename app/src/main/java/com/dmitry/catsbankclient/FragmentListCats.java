package com.dmitry.catsbankclient;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dmitry.catsbankclient.controllers.CatsController;


public class FragmentListCats extends Fragment {

    private CatsController catsController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_all_cats, container,false);
        catsController = new CatsController(getActivity(), v);

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
                    catsController.findCatOnListById(0);
                } else {
                    catsController.findCatOnListById(Integer.parseInt(number));
                }
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        catsController.getAllCats();
    }
}
