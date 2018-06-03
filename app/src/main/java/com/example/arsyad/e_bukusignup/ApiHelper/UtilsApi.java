package com.example.arsyad.e_bukusignup.ApiHelper;

public class UtilsApi {
    //IP Localhost
    public static final String BASE_URL_API = "http://192.168.43.169:8888/api/user/";

    //Mendeklarasi Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
