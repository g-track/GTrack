package com.example.g_track.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
   private Context context;
   private SharedPreferences preferences;
   private String userId;
   private String userType;

   public User(Context context) {
      this.context = context;
      preferences = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
   }

   public String getUserId() {
      userId = preferences.getString("Id","");
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
      preferences.edit().putString("Id",userId).apply();
   }

   public String getUserType() {
      userType = preferences.getString("Type","");
      return userType;
   }

   public void setUserType(String userType) {
      this.userType = userType;
      preferences.edit().putString("Type",userType).apply();
   }

   public void removeUser(){
      preferences.edit().clear().apply();
   }


}
