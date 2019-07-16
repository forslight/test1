package com.bytedance.androidcamp.network.dou.model;

import com.google.gson.annotations.SerializedName;

public class Video {

    // define all attribute of website's json's "feed"  data
    // define all related get() and set() functions
    @SerializedName("student_id") private String studentId;
    @SerializedName("user_name") private String userName;
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("video_url") private String videoUrl;
    @SerializedName("_id") private String id;
    @SerializedName("createAt") private String createAt;
    @SerializedName("updateAt") private String updateAt;
    @SerializedName("__v") private int __v;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getId(){return id;}

    public void setId(String id){this.id=id;}

    public String getCreateAt(){return createAt;}

    public void setCreateAt(String createAt){this.createAt=createAt;}

    public String getUpdateAt(){return updateAt;}

    public void setUpdateAt(String updateAt){this.updateAt=updateAt;}

    public int get__v(){return __v;}

    public void set__v(int __v){this.__v=__v;}

}
