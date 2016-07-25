package com.example.administrator.quickinterface;

import com.example.administrator.model.Weibo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/7/25.
 */
public interface ApiService {
    @GET(Statuses.FRIENDS+"?access_token=2.00JEvemB0kfIu7b3e2dd9973QnKtVD")
    Call<String> loadFriends();

}
