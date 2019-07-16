package com.bytedance.androidcamp.network.dou.api;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import com.bytedance.androidcamp.network.dou.MainActivity;
import com.bytedance.androidcamp.network.dou.model.*;

public interface IMiniDouyinService {
    // TODO 7: Define IMiniDouyinService
    //http://test.androidcamp.bytedance.com/mini_douyin/invoke/video
    String HOST="http://test.androidcamp.bytedance.com/";   //host
    String PATH="mini_douyin/invoke/video";     //resource path

    // get the feed's array content
    @GET(PATH)
   Call<Feed> getFeed();

    // post one single video's data
    @Multipart
    @POST(PATH)
    Call<Result> createVideo(
            @Query("student_id") String stuParam,
            @Query("user_name") String userParam,
            @Part MultipartBody.Part coverImage,
            @Part MultipartBody.Part video
    );


}
