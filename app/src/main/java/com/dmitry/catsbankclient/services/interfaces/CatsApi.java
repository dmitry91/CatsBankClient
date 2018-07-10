package com.dmitry.catsbankclient.services.interfaces;

import com.dmitry.catsbankclient.entities.Cat;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CatsApi {

    @GET("getAllCats")
    Single<List<Cat>> getAllCats();

    @Multipart
    @POST("addCat")
    Call<ResponseBody> addCat(@Part("text") RequestBody description,@Part MultipartBody.Part photo);

    @Multipart
    @POST("updCat")
    Call<ResponseBody> updateCat(@Part("id") RequestBody id, @Part("text") RequestBody description,@Part MultipartBody.Part photo);

}
