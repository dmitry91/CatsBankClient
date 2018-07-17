package com.dmitry.catsbankclient;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createFragment();
    }

    public void createFragment(){
        setContentView(R.layout.activity_main);
        FragmentListCats fragmentListCats = new FragmentListCats();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragmentListCats);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
