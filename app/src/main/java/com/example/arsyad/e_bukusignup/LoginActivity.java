package com.example.arsyad.e_bukusignup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arsyad.e_bukusignup.ApiHelper.BaseApiService;
import com.example.arsyad.e_bukusignup.ApiHelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    Button bt_reg, bt_login;
    EditText txt_mail, txt_password;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); //Inisialisasi isi package ApiHelper
        initComponents();

    }

    private void initComponents() {
        txt_mail = (EditText) findViewById(R.id.txt_mail);
        txt_password = (EditText) findViewById(R.id.txt_password);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_reg = (Button) findViewById(R.id.bt_reg);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = ProgressDialog.show(mContext, null, "Please wait...", true, false);
                requestLogin();
            }
        });

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegistrationActivity.class));
            }
        });

    }

    private void requestLogin() {
        mApiService.loginRequest(txt_mail.getText().toString(), txt_password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("error").equals("false")) {
                            //Jika Login Sukses, Maka data yang ada di response API akan
                            //diparsing ke activity berikutnya
                            Toast.makeText(mContext, "LOGIN SUCCES", Toast.LENGTH_SHORT).show();
                            //String nama = jsonRESULTS.getJSONObject("user").getString("name");
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                        } else {
                            //Jika Login Gagal
                            String error_message = jsonRESULTS.getString("error_msg");
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug","onFailure: ERROR > " + t.toString());
                pDialog.dismiss();

            }
        });
    }
}
