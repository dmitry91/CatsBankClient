package com.dmitry.catsbankclient.controllers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dmitry.catsbankclient.FragmentDetails;
import com.dmitry.catsbankclient.R;
import com.dmitry.catsbankclient.entities.Cat;
import com.dmitry.catsbankclient.services.Client;
import com.dmitry.catsbankclient.services.interfaces.CatsApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatsController {

    private ListView lvMain;
    private ArrayList<Cat> catsList;
    private Activity mActivity;
    private FragmentTransaction fragmentTransaction;

    public CatsController(Activity activity, View view) {
        mActivity = activity;
        lvMain = view.findViewById(R.id.lvMain);
        fragmentTransaction = mActivity.getFragmentManager().beginTransaction();
    }

    public CatsController(Activity activity) {
        mActivity = activity;
    }

    private void insertCatsToList(final ArrayList<Cat> catsForInsert) {
        ArrayList<String> catsText = new ArrayList<>();
        for (Cat cat : catsForInsert) {
            catsText.add("id " + cat.getId() + " : " + cat.getText());
        }
        String text[] = catsText.toArray(new String[catsText.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity,
                R.layout.lv_text, text) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the current item from ListView
                View view = super.getView(position, convertView, parent);
                view.setBackgroundColor((position & 1) == 1 ? Color.WHITE : Color.LTGRAY);
                return view;
            }
        };

        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putInt("id", catsForInsert.get(position).getId());
                b.putString("text", catsForInsert.get(position).getText());
                b.putString("photo", catsForInsert.get(position).getPhotoName());
                FragmentDetails fragmentDetails = new FragmentDetails();
                fragmentDetails.setArguments(b);
                fragmentTransaction.replace(R.id.fragmentContainer,fragmentDetails);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public void getAllCats() {
        Client client = new Client();
        CatsApi catsApi = client.getCatsApi();
        catsApi.getAllCats().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cat>>() {
                    @Override
                    public void accept(List<Cat> cats) throws Exception {
                        catsList = (ArrayList<Cat>) cats;
                        insertCatsToList(catsList);
                    }
                });
    }

    public void findCatOnListById(int idCat) {
        ArrayList<Cat> result = new ArrayList<>();
        if (idCat > 0) {
            for (Cat cat : catsList) {
                if (cat.getId() == idCat) {
                    result.add(cat);
                    insertCatsToList(result);
                }
            }
        } else {
            insertCatsToList(catsList);
        }
    }

    public void saveCat(String text, String path) {
        File file = new File(path);
        final RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), reqFile);
        RequestBody textAboutCat = RequestBody.create(MediaType.parse("text/plain"), text);
        // Executing the query
        Client client = new Client();
        CatsApi catsApi = client.getCatsApi();
        Call<ResponseBody> call;
        if (!Objects.equals(path, "")) {
            call = catsApi.addCat(textAboutCat, body);
        } else {
            call = catsApi.addCat(textAboutCat, null);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success " + response);
                mActivity.onBackPressed();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.toString());
            }
        });
    }

    public void updateCat(int id, String text, String path) {
        File file = new File(path);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), reqFile);
        RequestBody textAboutCat = RequestBody.create(MediaType.parse("text/plain"), text);
        RequestBody idCat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));
        // Executing the query
        Client client = new Client();
        CatsApi catsApi = client.getCatsApi();
        Call<ResponseBody> call;
        if (!Objects.equals(path, "")) {
            call = catsApi.updateCat(idCat, textAboutCat, body);
        } else {
            call = catsApi.updateCat(idCat, textAboutCat, null);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Update", "success " + response);
                mActivity.onBackPressed();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Update error:", t.toString());
            }
        });
    }

    public void deleteCat(int id){
        // Executing the query
        Client client = new Client();
        CatsApi catsApi = client.getCatsApi();
        Call<ResponseBody> call;

        call = catsApi.delCat(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Delete", "success " + response);
                mActivity.onBackPressed();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Delete error:", t.toString());
            }
        });
    }
}
