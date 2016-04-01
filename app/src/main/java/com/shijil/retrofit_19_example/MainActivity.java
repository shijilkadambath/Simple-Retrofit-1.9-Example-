package com.shijil.retrofit_19_example;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.shijil.retrofit_19_example.web.ServiceGenerator;
import com.shijil.retrofit_19_example.web.WebServices;

import org.json.JSONObject;

import java.io.File;
import java.net.URI;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String IMAGEPATH="";

    private ProgressDialog mProgressDialog;
    private WebServices service;
    private WebServices serviceWithHeader;

    private View l_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** map all views **/
        Button btn_postRequest  = (Button) findViewById(R.id.btn_post);
        Button btn_getRequest   = (Button) findViewById(R.id.btn_get);
        Button btn_withHeader   = (Button) findViewById(R.id.btn_with_header);
        Button btn_uploadFile   = (Button) findViewById(R.id.btn_file_upload);
        l_root                  =  findViewById(R.id.l_root);

        /** set click listener to buttons **/
        btn_getRequest.setOnClickListener(this);
        btn_postRequest.setOnClickListener(this);
        btn_withHeader.setOnClickListener(this);
        btn_uploadFile.setOnClickListener(this);

        /** loading progress dialog **/
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setCancelable(false);

        /** initialize webservice class **/
        service = ServiceGenerator.createService(WebServices.class);// normal service
        serviceWithHeader = ServiceGenerator.createServiceWithJsonHeader(WebServices.class);// service with custom headers,
    }
    private void showProgress(){
        mProgressDialog.show();
    }
    private void hideProgress(){
        if(mProgressDialog.isShowing())mProgressDialog.dismiss();
    }
    private void showSnackBar(String message){
        Snackbar snackbar = Snackbar.make(l_root,message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/shijilkadambath"));
                startActivity(browserIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View v) {

        if (ServiceGenerator.BASE_URL.isEmpty()){
            showSnackBar("please set valid base url");
            return;
        }

        /** manege all button click here **/
        switch (v.getId()){
            case R.id.btn_get:
                serviceWithGetMethodTask();
                break;
            case R.id.btn_post:
                serviceWithPostMethodTask();
                break;
            case R.id.btn_with_header:
                serviceWithHeaderTask();
                break;
            case R.id.btn_file_upload:
                serviceWithFileUpload();
                break;
        }
    }
    private void serviceWithFileUpload() {

        if (IMAGEPATH.isEmpty()){
            showSnackBar("please set image path");
            return;
        }

        String fileName    ="john_profile.jpg";

        File myFile = new File(IMAGEPATH);
        TypedFile file = new TypedFile("image/jpeg", myFile);

        serviceWithFileUploadTask(fileName,file);
    }

    private void serviceWithHeaderTask() {
        showProgress();
        /** create a sample json **/
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("email","john@gmail.com");
            jsonParams.put("password","john1212");

            jsonMain.put("function", "login");
            jsonMain.put("parameters", jsonParams);
        } catch (Exception e) {

        }
        /** convert sample json to retrofit typedString **/
        TypedString parm = new TypedString(jsonMain.toString());
        // service call
        service.postJsonString(parm, new Callback<Object>() {

            /** if have success  **/
            @Override
            public void success(Object object, Response response) {

                String responseString = new Gson().toJson(object);
                showSnackBar(responseString);

                hideProgress();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();

                showSnackBar(retrofitError.getMessage());
                hideProgress();
            }
        });
    }

    private void serviceWithPostMethodTask() {
        showProgress();
        String email    ="john@gmail.com";
        String password ="john1212";

        // service call
        service.loginUserPost(email, password, new Callback<JSONObject>() {

            /** if have success  response **/
            @Override
            public void success(JSONObject jsonObject, Response response) {

                showSnackBar(jsonObject.toString());
                hideProgress();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();

                showSnackBar(retrofitError.getMessage());
                hideProgress();
            }
        });
    }
    private void serviceWithGetMethodTask() {
        showProgress();
        String email    ="john@gmail.com";
        String password ="john1212";

        // service call
        service.loginUserGet(email, password, new Callback<JSONObject>() {

            /** if have success  response **/
            @Override
            public void success(JSONObject jsonObject, Response response) {

                showSnackBar(jsonObject.toString());
                hideProgress();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();

                showSnackBar(retrofitError.getMessage());
                hideProgress();
            }
        });
    }


    private void serviceWithFileUploadTask(String fileName,TypedFile file) {
        showProgress();

        // service call
        service.UploadImagePost(fileName, file, new Callback<Object>() {

            /** if have success  response **/
            @Override
            public void success(Object object, Response response) {

                String responseString=new Gson().toJson(object);
                showSnackBar(responseString);
                hideProgress();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();

                showSnackBar(retrofitError.getMessage());
                hideProgress();
            }
        });
    }
}
