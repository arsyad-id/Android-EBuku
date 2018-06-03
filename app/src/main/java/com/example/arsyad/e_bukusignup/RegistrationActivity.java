package com.example.arsyad.e_bukusignup;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arsyad.e_bukusignup.ApiHelper.BaseApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    EditText txt_name,txt_mail,txt_password;
    Button bt_reg;
    ProgressDialog pDialog;

    Context mContext;
    BaseApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mContext = this;
        initComponents();
    }

    private void initComponents() {
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_mail = (EditText) findViewById(R.id.txt_mail);
        txt_password = (EditText) findViewById(R.id.txt_password);
        bt_reg = (Button) findViewById(R.id.bt_reg);

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = ProgressDialog.show(mContext,null, "Please wait...", true,false);
                requestRegister();
            }
        });

    }

    private void requestRegister() {
        mApiService.registerRequest(txt_name.getText().toString(),
                txt_mail.getText().toString(),
                txt_password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.i("debug", "onResponse: SUCCESS");
                            pDialog.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: FAILED");
                            pDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Internet Connection Problem", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
