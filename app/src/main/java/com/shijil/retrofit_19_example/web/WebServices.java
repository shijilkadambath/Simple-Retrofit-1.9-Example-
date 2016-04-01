package com.shijil.retrofit_19_example.web;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by shijil on 7/7/2015.
 */
public interface WebServices {

    @POST("/client")
    void postJsonString(@Body TypedString string, Callback<Object> cb);

    @Multipart
    @POST("/upload")
    void UploadImagePost(@Part("image_name") String first_name,
                          @Part("image") TypedFile file, Callback<Object> cb);
    @Multipart
    @GET("/login")
    void loginUserGet(@Part("email") String email, @Part("password") String password, Callback<JSONObject> cb);
    @Multipart
    @POST("/login")
    void loginUserPost(@Part("email") String email, @Part("password") String password, Callback<JSONObject> cb);

}
